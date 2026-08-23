package gg.vape.mapping.runtime;

import gg.vape.Vape;
import gg.vape.reflect.MappingRegistry;
import gg.vape.runtime.NativeBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

public class RuntimeNameMappingRegistry {
    private static MemberNameRemapTable memberNameRemapTable;
    private static final Map<String, String> registeredClassNames;
    private static final ClassNameRemapTable classNameRemapTable;

    public static void registerClassName(String sourceClassName, String runtimeClassName) {
        registeredClassNames.put(sourceClassName.replace("/", "."), runtimeClassName.replace("/", "."));
        NativeBridge.scm(sourceClassName, runtimeClassName);
    }

    @Nullable
    public static MemberLookupSignature lookupMethodMapping(Class ownerClass, String methodName) {
        return lookupMethodMapping(ownerClass, methodName, null);
    }

    @Nullable
    public static MemberLookupSignature lookupMethodMapping(Class ownerClass, String methodName, Class<?>[] parameterTypes) {
        if (memberNameRemapTable == null) {
            return null;
        }
        MemberLookupSignature signature = memberNameRemapTable.lookupMethodMapping(ownerClass, methodName);
        if (signature == null) {
            // Vanilla (obfuscated) 1.20.1/1.21.1 runtimes: the V50/V51 member
            // tables do not cover every 1.21+ member (e.g.
            // DeltaTracker.getGameTimeDeltaTicks). Translate the mojmap name
            // directly through the obfuscated member map instead.
            // Forge runtimes use SRG member names; the obfuscated-name
            // translation only applies when Forge is absent.
            if (!NativeBridge.isForgeAbsent()) {
                return null;
            }
            int version = ForgeVersion.c();
            // 纯原版 1.16.5：SRG 名（func_xxx）方法请求反向查表（值 -> 源名），
            // 得到 mojmap 名后由 vanilla1165 joined.srg 映射到运行时混淆名。
            // 只对 SRG 形式的名字做反向：普通 mojmap/MCP 名可能是其他类的
            // 表值（如 "x"、"name"），反向会误返回无关的 MCP 源名。
            if ((version == 35 || version == 36)
                    && (methodName.startsWith("func_") || methodName.startsWith("method_"))) {
                MemberLookupSignature reversed =
                        memberNameRemapTable.lookupMethodMappingByRuntimeName(methodName);
                if (reversed != null) {
                    return reversed;
                }
                // 链条补齐：SRG -> (CSV) -> MCP 名 -> (V35V36 表) -> mojmap 名
                // 例如 func_228426_a_ -> updateCameraAndRender -> renderLevel。
                MemberLookupSignature bridged = lookupVanilla1165SrgBridge(
                        methodName, ownerClass, false);
                if (bridged != null) {
                    return bridged;
                }
            }
            if (version == 47 && !NativeBridge.isNeoForge1201Runtime()) {
                String obfuscated = NeoForgeObfMap.lookupMethod1201(
                        ownerClass, methodName, buildParamDesc(parameterTypes));
                if (obfuscated != null) {
                    return new MemberLookupSignature(obfuscated, null, null, parameterTypes);
                }
            } else if (version == 52 && !NativeBridge.isNeoForge1211Runtime()) {
                String obfuscated = NeoForgeObfMap.lookupMethod1211(
                        ownerClass, methodName, buildParamDesc(parameterTypes));
                if (obfuscated != null) {
                    return new MemberLookupSignature(obfuscated, null, null, parameterTypes);
                }
            }
            return null;
        }
        // Vanilla (obfuscated) 1.20.1/1.21.1 runtimes: V50/V51 method values are
        // mojmap names (e.g. "render"); translate them to the obfuscated names.
        // Forge 1.20.1/1.21.1 run SRG member names, so the obfuscated-name
        // translation only applies when Forge is absent.
        if (!NativeBridge.isForgeAbsent()) {
            return signature;
        }
        int version = ForgeVersion.c();
        if (version == 47 && !NativeBridge.isNeoForge1201Runtime()) {
            String obfuscated = NeoForgeObfMap.lookupMethod1201(
                    ownerClass, signature.runtimeName, buildParamDesc(descTypes(signature, parameterTypes)));
            if (obfuscated != null) {
                return new MemberLookupSignature(obfuscated,
                        signature.getMappedMemberOverride(), signature.resolvedType, signature.parameterTypes);
            }
        } else if (version == 52 && !NativeBridge.isNeoForge1211Runtime()) {
            String obfuscated = NeoForgeObfMap.lookupMethod1211(
                    ownerClass, signature.runtimeName, buildParamDesc(descTypes(signature, parameterTypes)));
            if (obfuscated != null) {
                return new MemberLookupSignature(obfuscated,
                        signature.getMappedMemberOverride(), signature.resolvedType, signature.parameterTypes);
            }
        }
        return signature;
    }

    /**
     * The V50/V51 tables register methods without parameter types (the t()
     * helper stores an empty array), so translating the member name through
     * the obfuscated map must use the caller's parameter types when the
     * table signature has none, otherwise lookups like runTick|fgo|() miss
     * the real runTick|fgo|(Z) entry.
     */
    private static Class<?>[] descTypes(MemberLookupSignature signature, Class<?>[] parameterTypes) {
        if (signature.parameterTypes != null && signature.parameterTypes.length > 0) {
            return signature.parameterTypes;
        }
        return parameterTypes;
    }

    /**
     * 纯原版 1.16.5 的 SRG 名补齐链：SRG -> (forge1165 CSV) -> MCP 可读名
     * -> (V35V36 成员表) -> mojmap 名。例如 func_228426_a_ 在 CSV 里是
     * updateCameraAndRender，表里 updateCameraAndRender -> renderLevel。
     */
    @Nullable
    private static MemberLookupSignature lookupVanilla1165SrgBridge(
            String srgName, Class<?> ownerClass, boolean field) {
        if (memberNameRemapTable == null) {
            return null;
        }
        Set<String> readableNames = field
                ? MappingRegistry.FIELDS_REVERSED.get(srgName)
                : MappingRegistry.METHODS_REVERSED.get(srgName);
        if (readableNames == null || readableNames.isEmpty()) {
            return null;
        }
        for (String readableName : readableNames) {
            MemberLookupSignature signature = field
                    ? memberNameRemapTable.lookupFieldMapping(ownerClass, readableName)
                    : memberNameRemapTable.lookupMethodMapping(ownerClass, readableName);
            if (signature != null) {
                return signature;
            }
        }
        return null;
    }

    private static String buildParamDesc(Class<?>[] parameterTypes) {
        if (parameterTypes == null) {
            return "()";
        }
        StringBuilder descriptor = new StringBuilder("(");
        for (Class<?> parameterType : parameterTypes) {
            if (parameterType == null) {
                // Unresolved MappedClasses reference (class absent in this
                // runtime); the descriptor cannot name it, so omit it.
                continue;
            }
            if (parameterType == Integer.TYPE) {
                descriptor.append('I');
            } else if (parameterType == Boolean.TYPE) {
                descriptor.append('Z');
            } else if (parameterType == Float.TYPE) {
                descriptor.append('F');
            } else if (parameterType == Double.TYPE) {
                descriptor.append('D');
            } else if (parameterType == Long.TYPE) {
                descriptor.append('J');
            } else if (parameterType == Short.TYPE) {
                descriptor.append('S');
            } else if (parameterType == Byte.TYPE) {
                descriptor.append('B');
            } else if (parameterType == Character.TYPE) {
                descriptor.append('C');
            } else if (parameterType == Void.TYPE) {
                descriptor.append('V');
            } else {
                descriptor.append('L').append(parameterType.getName().replace('.', '/')).append(';');
            }
        }
        return descriptor.append(')').toString();
    }

    @Nullable
    public static String lookupRegisteredClassName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return registeredClassNames.get(clazz.getName());
    }

    @Nullable
    public static MemberLookupSignature lookupFieldMapping(Class ownerClass, String fieldName) {
        if (memberNameRemapTable == null) {
            return null;
        }
        MemberLookupSignature signature = memberNameRemapTable.lookupFieldMapping(ownerClass, fieldName);
        if (signature == null) {
            // Vanilla (obfuscated) 1.20.1/1.21.1 runtimes: the V50/V51 member
            // tables do not cover every field (e.g. DamageSource.FALL moved to
            // DamageTypes in 1.21). Translate the mojmap name directly through
            // the obfuscated member map instead.
            // Forge runtimes use SRG member names; skip the translation.
            if (!NativeBridge.isForgeAbsent()) {
                return null;
            }
            int version = ForgeVersion.c();
            // 纯原版 1.16.5：SRG 名（field_xxx）字段请求反向查表（值 -> 源名），
            // 得到 mojmap 名后由 vanilla1165 joined.srg 映射到运行时混淆名。
            // 只对 SRG 形式的名字做反向：普通 mojmap/MCP 名可能是其他类的
            // 表值（如 "x"、"name"），反向会误返回无关的 MCP 源名。
            if ((version == 35 || version == 36)
                    && (fieldName.startsWith("field_") || fieldName.startsWith("f_"))) {
                MemberLookupSignature reversed =
                        memberNameRemapTable.lookupFieldMappingByRuntimeName(fieldName);
                if (reversed != null) {
                    return reversed;
                }
                // 链条补齐：SRG -> (CSV) -> MCP 名 -> (V35V36 表) -> mojmap 名
                MemberLookupSignature bridged = lookupVanilla1165SrgBridge(
                        fieldName, ownerClass, true);
                if (bridged != null) {
                    return bridged;
                }
            }
            if (version == 47 && !NativeBridge.isNeoForge1201Runtime()) {
                String obfuscated = NeoForgeObfMap.lookupField1201(ownerClass, fieldName);
                if (obfuscated != null) {
                    return new MemberLookupSignature(obfuscated, null, null);
                }
            } else if (version == 52 && !NativeBridge.isNeoForge1211Runtime()) {
                String obfuscated = NeoForgeObfMap.lookupField1211(ownerClass, fieldName);
                if (obfuscated != null) {
                    return new MemberLookupSignature(obfuscated, null, null);
                }
            }
            return null;
        }
        // Mojmap runtimes: V50/V51 field values may be obfuscated names
        // (e.g. "a"); translate them to the mojmap field names that exist.
        if (NativeBridge.isNeoForge1211Runtime()) {
            String mojmap = NeoForgeFieldMap.lookup1211(ownerClass, signature.runtimeName);
            if (mojmap != null) {
                return new MemberLookupSignature(mojmap, signature.getMappedMemberOverride(), signature.resolvedType);
            }
        } else if (NativeBridge.isNeoForge1201Runtime()) {
            String mojmap = NeoForgeFieldMap.lookup1201(ownerClass, signature.runtimeName);
            if (mojmap != null) {
                return new MemberLookupSignature(mojmap, signature.getMappedMemberOverride(), signature.resolvedType);
            }
        } else {
            // Vanilla (obfuscated) runtimes: V50/V51 field values may be mojmap
            // names (e.g. "level"); translate them to the obfuscated names.
            // Forge 1.20.1/1.21.1 run SRG member names, so the obfuscated-name
            // translation only applies when Forge is absent.
            if (!NativeBridge.isForgeAbsent()) {
                return signature;
            }
            int version = ForgeVersion.c();
            if (version == 47) {
                String obfuscated = NeoForgeObfMap.lookupField1201(ownerClass, signature.runtimeName);
                if (obfuscated != null) {
                    return new MemberLookupSignature(obfuscated,
                            signature.getMappedMemberOverride(), signature.resolvedType);
                }
            } else if (version == 52) {
                String obfuscated = NeoForgeObfMap.lookupField1211(ownerClass, signature.runtimeName);
                if (obfuscated != null) {
                    return new MemberLookupSignature(obfuscated,
                            signature.getMappedMemberOverride(), signature.resolvedType);
                }
            }
        }
        return signature;
    }

    public static void initializeRegistry() {
        int forgeVersion = ForgeVersion.c();
        switch (forgeVersion) {
            case 35: 
            case 36: {
                memberNameRemapTable = new MemberNameRemapTableV35V36();
                break;
            }
            case 37: {
                memberNameRemapTable = new MemberNameRemapTableV37();
                break;
            }
            case 47: {
                // 1.20.1 shares the 1.20.x mojmap member names with 1.20.6.
                memberNameRemapTable = new MemberNameRemapTableV50();
                break;
            }
            case 50: {
                memberNameRemapTable = new MemberNameRemapTableV50();
                break;
            }
            case 51: {
                memberNameRemapTable = new MemberNameRemapTableV51();
                break;
            }
            case 52: {
                // 1.21.1 is a bugfix of 1.21.0; mojmap member names match.
                memberNameRemapTable = new MemberNameRemapTableV51();
                break;
            }
            case 54: {
                memberNameRemapTable = new MemberNameRemapTableV54();
                break;
            }
            case 55: {
                memberNameRemapTable = new MemberNameRemapTableV55();
                break;
            }
            case 56: {
                memberNameRemapTable = new MemberNameRemapTableV56();
                break;
            }
            case 60: {
                memberNameRemapTable = new MemberNameRemapTableV60();
                break;
            }
            case 61: {
                memberNameRemapTable = new MemberNameRemapTableV61();
                break;
            }
            case 100: {
                memberNameRemapTable = new MemberNameRemapTableV100();
                break;
            }
            case 110: {
                memberNameRemapTable = new MemberNameRemapTableV110();
            }
        }
        if (memberNameRemapTable != null) {
            memberNameRemapTable.initializeMappings();
        }
    }

    static {
        registeredClassNames = new LinkedHashMap<String, String>();
        int forgeVersion = ForgeVersion.c();
        switch (forgeVersion) {
            case 23: {
                classNameRemapTable = new ClassNameRemapTableV23();
                break;
            }
            case 35: 
            case 36: {
                if (Vape.INSTANCE.isForgeAbsent()) {
                    classNameRemapTable = new ClassNameRemapTableV35V36Direct();
                    break;
                }
                classNameRemapTable = new ClassNameRemapTableV35V36Layered();
                break;
            }
            case 37: {
                classNameRemapTable = new ClassNameRemapTableV37();
                break;
            }
            case 47: {
                // Class-name table values are already mojmap names, so the
                // same table works for the Forge (mojmap) runtime.
                classNameRemapTable = new ClassNameRemapTableV50();
                break;
            }
            case 50: {
                classNameRemapTable = new ClassNameRemapTableV50();
                break;
            }
            case 51: {
                classNameRemapTable = new ClassNameRemapTableV51();
                break;
            }
            case 52: {
                // Class-name table values are already mojmap names, so the
                // same table works for the NeoForge (mojmap) runtime.
                classNameRemapTable = new ClassNameRemapTableV51();
                break;
            }
            case 54: {
                classNameRemapTable = new ClassNameRemapTableV54();
                break;
            }
            case 55: {
                classNameRemapTable = new ClassNameRemapTableV55();
                break;
            }
            case 56: {
                classNameRemapTable = new ClassNameRemapTableV56();
                break;
            }
            case 60: {
                classNameRemapTable = new ClassNameRemapTableV60();
                break;
            }
            case 61: {
                classNameRemapTable = new ClassNameRemapTableV61();
                break;
            }
            case 100: {
                classNameRemapTable = new ClassNameRemapTableV100();
                break;
            }
            case 110: {
                classNameRemapTable = new ClassNameRemapTableV110();
                break;
            }
            default: {
                classNameRemapTable = null;
            }
        }
        if (ForgeVersion.MC_1_16_5_ACTUAL.Y() && !Vape.INSTANCE.isForgeAbsent()) {
            ClassNameRemapTable.propagateMappingsToRuntimeRegistry = true;
            new ClassNameRemapTableV35V36Direct();
        }
    }


    public static String remapClassName(String sourceClassName) {
        String mapped = classNameRemapTable == null ? null
                : classNameRemapTable.lookupRemappedClassName(sourceClassName);
        boolean mojmapRuntime = NativeBridge.isNeoForge1201Runtime()
                || NativeBridge.isNeoForge1211Runtime()
                // Forge 1.20.1 runs mojmap class names (with SRG member names),
                // so its class names must resolve like a mojmap runtime too.
                || (!NativeBridge.isForgeAbsent() && ForgeVersion.c() == 47);
        if (mapped != null) {
            if (mojmapRuntime) {
                // V50/V51 table values may be obfuscated names (e.g.
                // GlStateManager$l); translate them to mojmap names.
                String mojmap = NativeBridge.isNeoForge1211Runtime()
                        ? NeoForgeClassMap.lookupObfuscated1211(mapped)
                        : NeoForgeClassMap.lookupObfuscated1201(mapped);
                return mojmap != null ? mojmap : mapped;
            }
            return mapped;
        }
        // Mojmap runtimes: some module code still uses obfuscated class names
        // (e.g. GlStateManager$b) which the V50/V51 legacy tables do not cover.
        boolean forge1201Mojmap = !NativeBridge.isForgeAbsent()
                && ForgeVersion.c() == 47;
        if (NativeBridge.isNeoForge1211Runtime()) {
            return NeoForgeClassMap.lookupObfuscated1211(sourceClassName);
        }
        if (NativeBridge.isNeoForge1201Runtime() || forge1201Mojmap) {
            return NeoForgeClassMap.lookupObfuscated1201(sourceClassName);
        }
        return null;
    }
}

