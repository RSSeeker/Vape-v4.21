package gg.vape.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Resolves MCP/SRG names against the Forge 1.20.1 runtime, which runs the game
 * with Mojang mojmap names instead of the obfuscated names used by vanilla.
 */
public final class NeoForge1201Mappings {
    private static final VanillaSrgMappings MAPPINGS = new VanillaSrgMappings(
            "Minecraft 1.20.1 Forge",
            "/mappings/neoforge1201/joined.srg",
            "net/minecraft/client/Minecraft",
            "getInstance",
            "instance",
            "net/minecraft/client/multiplayer/ClientLevel",
            "net/minecraft/client/player/LocalPlayer",
            "net/minecraft/client/player/AbstractClientPlayer",
            "net/minecraft/world/level/Level");

    private NeoForge1201Mappings() {
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
