package gg.vape.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/** Resolves MCP/SRG names against the Fabric 1.21.11 intermediary runtime. */
public final class Fabric12111Mappings {
    private static final VanillaSrgMappings MAPPINGS = new VanillaSrgMappings(
            "Minecraft 1.21.11 Fabric",
            "/mappings/fabric12111/joined.srg",
            "net/minecraft/class_310",
            "method_1551",
            "field_1700",
            "net/minecraft/class_638",
            "net/minecraft/class_746",
            "net/minecraft/class_742",
            "net/minecraft/class_1937");

    private Fabric12111Mappings() {
    }

    public static String remapClassName(String sourceClassName) {
        return MAPPINGS.remapClassName(sourceClassName);
    }

    public static Class<?> resolveClass(String sourceClassName,
                                        ClassLoader... preferredLoaders) {
        return MAPPINGS.resolveClass(sourceClassName, preferredLoaders);
    }

    public static boolean isRuntimePresent(ClassLoader... preferredLoaders) {
        return MAPPINGS.isRuntimePresent(preferredLoaders);
    }

    static String lookupFieldSrgName(Field field) {
        return MAPPINGS.lookupFieldSrgName(field);
    }

    static String lookupMethodSrgName(Method method) {
        return MAPPINGS.lookupMethodSrgName(method);
    }

    public static int getClassMappingCount() {
        return MAPPINGS.getClassMappingCount();
    }

    public static int getFieldMappingCount() {
        return MAPPINGS.getFieldMappingCount();
    }

    public static int getMethodMappingCount() {
        return MAPPINGS.getMethodMappingCount();
    }
}
