package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.mapping.MappingProfileSnapshotRegistry;
import gg.vape.mapping.NativeMappedMemberInvoker;
import gg.vape.mapping.access.GeneratedAccessorFactory;
import gg.vape.mapping.access.MethodInvoker;
import gg.vape.mapping.runtime.MemberLookupSignature;
import gg.vape.mapping.runtime.RuntimeNameMappingRegistry;
import gg.vape.wrapper.impl.ForgeVersion;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MappingMethod {
    private final String mappedDescriptor;
    private final Class<?> ownerClass;
    private static int nextMethodId;
    private Constructor<?> reflectedConstructor;
    private final boolean mappedMember;
    private Class<?> resolvedReturnType;
    private Class<?> declaredReturnType;
    private final int methodId;
    private boolean resolutionFailed;
    private boolean skipAccessorGeneration;
    private final String descriptor;
    private final Class<?>[] declaredParameterTypes;
    private final String ownerInternalName;
    private Class<?>[] resolvedParameterTypes;
    private MethodInvoker generatedInvoker;
    private final boolean staticMethod;
    private Method reflectedMethod;
    private final String originalName;
    private boolean secondaryMember;
    private final String runtimeName;
    private static String controlFlowMarker;
    private final Mapping mappingOwner;

    public char[] invokeCharArray(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (char[])this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (char[])this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeCharArray(this.methodId, object, objectArray);
    }

    public void initializeAccessor(Class<?> owner) {
        if ((Vape.INSTANCE.isMappingsRemapped() || Vape.INSTANCE.isForgeRemapInactive()) && !this.resolutionFailed) {
            if (this.runtimeName.equals("<init>") || this.runtimeName.equals("<clinit>")) {
                this.resolveConstructor(owner);
                return;
            }
            try {
                Class<? extends MethodInvoker> clazz2 = GeneratedAccessorFactory.A(owner, this);
                if (clazz2 == null) {
                    return;
                }
                this.generatedInvoker = clazz2.newInstance();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    public Object[] invokeObjectArray(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (Object[])this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (Object[])this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeObjectArray(this.methodId, object, objectArray);
    }

    public void invokeVoid(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            this.generatedInvoker.invoke(object, objectArray);
        } else if (this.reflectedMethod != null) {
            this.invokeReflected(object, objectArray);
        } else {
            NativeMappedMemberInvoker.invokeVoid(this.methodId, object, objectArray);
        }
    }

    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public String getResolvedName() {
        if (Vape.INSTANCE.isForgeRemapInactive()) {
            return this.runtimeName;
        }
        return NativeMappedMemberInvoker.getMethodName(this.methodId);
    }

    public Class<?>[] getDeclaredParameterTypes() {
        return this.declaredParameterTypes;
    }

    public boolean[] invokeBooleanArray(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (boolean[])this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (boolean[])this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeBooleanArray(this.methodId, object, objectArray);
    }

    public long[] invokeLongArray(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (long[])this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (long[])this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeLongArray(this.methodId, object, objectArray);
    }

    public short invokeShort(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (Short)this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (Short)this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeShort(this.methodId, object, objectArray);
    }

    public MappingMethod register() {
        this.resolutionFailed = false;
        try {
            if (Vape.INSTANCE.isForgeRemapInactive()) {
                this.resolveReflectionMember();
            } else if (this.mappedMember) {
                String string2 = this.runtimeName;
                if (!Vape.INSTANCE.isVanillaMinecraftPresent()) {
                    string2 = string2 + ":" + this.descriptor;
                }
                NativeMappedMemberInvoker.registerMethodWithMetadata(this.methodId, this.ownerClass, this.ownerInternalName, this.runtimeName, this.descriptor, this.mappedDescriptor, this.staticMethod);
            } else {
                NativeMappedMemberInvoker.registerMethod(this.methodId, this.ownerClass, this.runtimeName, this.descriptor, this.staticMethod);
            }
            if (!this.skipAccessorGeneration && !Vape.INSTANCE.isForgeRemapInactive()) {
                this.initializeAccessor(this.ownerClass);
            }
        }
        catch (Throwable throwable) {
            if (this.secondaryMember) {
                MappingProfileSnapshotRegistry.O();
            } else {
                MappingProfileSnapshotRegistry.X(this);
            }
            this.resolutionFailed = true;
        }
        return this;
    }

    public float[] invokeFloatArray(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (float[])this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (float[])this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeFloatArray(this.methodId, object, objectArray);
    }

    public Class<?> getOwnerClass() {
        return this.ownerClass;
    }

    private void resolveReflectionMember() throws Throwable {
        if (this.runtimeName.equals("<init>") || this.runtimeName.equals("<clinit>")) {
            this.resolveConstructor(this.ownerClass);
            if (this.reflectedConstructor == null) {
                throw new NoSuchMethodException(this.ownerClass.getName() + "#" + this.runtimeName + this.descriptor);
            }
            return;
        }
        this.initializeAccessor(this.ownerClass);
        if (this.generatedInvoker != null) {
            return;
        }
        this.reflectedMethod = this.findMethodInHierarchy(this.ownerClass, this.runtimeName, this.resolvedParameterTypes);
        if (this.reflectedMethod == null) {
            throw new NoSuchMethodException(this.ownerClass.getName() + "#" + this.runtimeName + this.descriptor);
        }
        this.reflectedMethod.setAccessible(true);
    }

    public Mapping getMappingOwner() {
        return this.mappingOwner;
    }

    public Object newInstance(Object ... objectArray) {
        if (this.reflectedConstructor != null) {
            try {
                return this.reflectedConstructor.newInstance(objectArray);
            }
            catch (Throwable throwable) {
                Vape.logThrowable(throwable);
            }
        }
        try {
            return NativeMappedMemberInvoker.invokeConstructor(this.methodId, this.ownerClass, objectArray);
        }
        catch (Exception exception) {
            return MappingMethod.<RuntimeException, Object>sneakyThrow(exception);
        }
    }

    public Class<?>[] getResolvedParameterTypes() {
        return this.resolvedParameterTypes;
    }

    public double[] invokeDoubleArray(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (double[])this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (double[])this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeDoubleArray(this.methodId, object, objectArray);
    }

    public short[] invokeShortArray(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (short[])this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (short[])this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeShortArray(this.methodId, object, objectArray);
    }

    public Object invokeNativeBridge(Object object, Object ... objectArray) {
        return NativeMappedMemberInvoker.invokeNativeBridge(this.methodId, object, objectArray);
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable, T> T sneakyThrow(Throwable throwable) throws E {
        throw (E)throwable;
    }

    private Object invokeReflected(Object instance, Object ... arguments) {
        try {
            return this.reflectedMethod.invoke(instance, arguments);
        }
        catch (Throwable throwable) {
            Vape.logThrowable(throwable);
            return null;
        }
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public MappingMethod(Mapping mappingOwner, Class<?> ownerClass, String methodName, boolean mappedMember, boolean staticMethod, boolean secondaryMember, Class<?> returnType, Class<?> ... parameterTypes) {
        if (ForgeVersion.MC_26_1.d()) {
            mappedMember = false;
        }
        this.mappingOwner = mappingOwner;
        this.ownerClass = ownerClass;
        this.ownerInternalName = MappedClasses.b(ownerClass).replace(".", "/");
        this.originalName = methodName;
        this.declaredReturnType = returnType;
        this.declaredParameterTypes = parameterTypes;
        MemberLookupSignature memberLookupSignature = RuntimeNameMappingRegistry.lookupMethodMapping(ownerClass, methodName);
        if (memberLookupSignature != null) {
            this.runtimeName = memberLookupSignature.runtimeName;
            this.resolvedReturnType = memberLookupSignature.resolvedType != null ? memberLookupSignature.resolvedType : returnType;
            this.resolvedParameterTypes = memberLookupSignature.parameterTypes.length > 0 ? memberLookupSignature.parameterTypes : parameterTypes;
            this.mappedMember = memberLookupSignature.getMappedMemberOverride() != null ? memberLookupSignature.getMappedMemberOverride() : mappedMember;
        } else {
            this.runtimeName = methodName;
            this.resolvedReturnType = returnType;
            this.resolvedParameterTypes = parameterTypes;
            this.mappedMember = mappedMember;
        }
        this.staticMethod = staticMethod;
        this.secondaryMember = secondaryMember;
        this.methodId = ++nextMethodId;
        this.descriptor = DescUtils.getMethodDescriptor(false, this.resolvedReturnType, this.resolvedParameterTypes);
        this.mappedDescriptor = DescUtils.getMethodDescriptor(true, this.resolvedReturnType, this.resolvedParameterTypes);
    }

    public Class<?> getResolvedReturnType() {
        return this.resolvedReturnType;
    }

    public void resolveConstructor(Class<?> owner) {
        try {
            this.reflectedConstructor = owner.getDeclaredConstructor(this.getResolvedParameterTypes());
            this.reflectedConstructor.setAccessible(true);
        }
        catch (Throwable throwable) {
            Vape.logThrowable(throwable);
        }
    }

    public int getMethodId() {
        return this.methodId;
    }

    public Class<?> getDeclaredReturnType() {
        return this.declaredReturnType;
    }

    public byte invokeByte(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (Byte)this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (Byte)this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeByte(this.methodId, object, objectArray);
    }

    public long invokeLong(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (Long)this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (Long)this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeLong(this.methodId, object, objectArray);
    }

    public char invokeChar(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return ((Character)this.generatedInvoker.invoke(object, objectArray)).charValue();
        }
        if (this.reflectedMethod != null) {
            return ((Character)this.invokeReflected(object, objectArray)).charValue();
        }
        return NativeMappedMemberInvoker.invokeChar(this.methodId, object, objectArray);
    }

    public boolean isMappedMember() {
        return this.mappedMember;
    }

    static {
        MappingMethod.setControlFlowMarker("YeDWk");
        nextMethodId = 0;
    }

    public Object invokeObject(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeObject(this.methodId, object, objectArray);
    }

    public boolean isStaticMethod() {
        return this.staticMethod;
    }

    public boolean invokeBoolean(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (Boolean)this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (Boolean)this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeBoolean(this.methodId, object, objectArray);
    }

    public void invokeVoidNative(Object object, Object ... objectArray) {
        NativeMappedMemberInvoker.invokeVoid(this.methodId, object, objectArray);
    }

    public static MappingMethod fromBuilder(MappingMethodBuilder mappingMethodBuilder) {
        MemberLookupSignature memberLookupSignature = RuntimeNameMappingRegistry.lookupMethodMapping(mappingMethodBuilder.getOwnerClass(), mappingMethodBuilder.getMemberName());
        if (memberLookupSignature != null) {
            mappingMethodBuilder.setMemberName(memberLookupSignature.runtimeName);
            if (memberLookupSignature.resolvedType != null) {
                mappingMethodBuilder.setType(memberLookupSignature.resolvedType);
            }
            if (memberLookupSignature.parameterTypes.length > 0) {
                mappingMethodBuilder.setParameterTypes(memberLookupSignature.parameterTypes);
            }
            if (memberLookupSignature.getMappedMemberOverride() != null) {
                mappingMethodBuilder.setMappedMember(memberLookupSignature.getMappedMemberOverride());
            }
        }
        MappingMethod mappingMethod = new MappingMethod(mappingMethodBuilder.getMappingOwner(), mappingMethodBuilder.getOwnerClass(), mappingMethodBuilder.getMemberName(), mappingMethodBuilder.isMappedMember(), mappingMethodBuilder.isStaticMember(), mappingMethodBuilder.isSecondaryMember(), mappingMethodBuilder.getType(), mappingMethodBuilder.getParameterTypes());
        if (mappingMethodBuilder.isSkipAccessorGeneration()) {
            mappingMethod.skipAccessorGeneration = true;
        }
        MappingMethod mappingMethod2 = mappingMethod;
        return mappingMethod2.register();
    }

    public void invokeVoidNoArgs(Object object) {
        if (this.generatedInvoker != null) {
            this.generatedInvoker.invoke(object, new Object[0]);
        } else if (this.reflectedMethod != null) {
            this.invokeReflected(object, new Object[0]);
        } else {
            NativeMappedMemberInvoker.invokeVoid(this.methodId, object, new Object[0]);
        }
    }

    public double invokeDouble(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (Double)this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (Double)this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeDouble(this.methodId, object, objectArray);
    }

    public int invokeInt(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (Integer)this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (Integer)this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeInt(this.methodId, object, objectArray);
    }

    public boolean hasResolutionFailed() {
        return this.resolutionFailed;
    }

    private Method findMethodInHierarchy(Class<?> owner, String name, Class<?>[] parameterTypes) {
        try {
            return owner.getMethod(name, parameterTypes);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            for (Class<?> currentClass = owner; currentClass != null; currentClass = currentClass.getSuperclass()) {
                try {
                    return currentClass.getDeclaredMethod(name, parameterTypes);
                }
                catch (NoSuchMethodException noSuchMethodException2) {
                    continue;
                }
            }
            return null;
        }
    }

    public String getRuntimeName() {
        return this.runtimeName;
    }

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }

    public float invokeFloat(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return ((Float)this.generatedInvoker.invoke(object, objectArray)).floatValue();
        }
        if (this.reflectedMethod != null) {
            return ((Float)this.invokeReflected(object, objectArray)).floatValue();
        }
        return NativeMappedMemberInvoker.invokeFloat(this.methodId, object, objectArray);
    }

    public int[] invokeIntArray(Object object, Object ... objectArray) {
        if (this.generatedInvoker != null) {
            return (int[])this.generatedInvoker.invoke(object, objectArray);
        }
        if (this.reflectedMethod != null) {
            return (int[])this.invokeReflected(object, objectArray);
        }
        return NativeMappedMemberInvoker.invokeIntArray(this.methodId, object, objectArray);
    }
}
