package gg.vape.reflect;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

/** Resolves official names in the unobfuscated Minecraft 26.1.2/26.2 client. */
public final class Vanilla262Mappings {
    private static final String MINECRAFT_CLASS =
            "net/minecraft/client/Minecraft";
    private static final String CLIENT_LEVEL_CLASS =
            "net/minecraft/client/multiplayer/ClientLevel";
    private static final String LOCAL_PLAYER_CLASS =
            "net/minecraft/client/player/LocalPlayer";
    private static final String ABSTRACT_PLAYER_CLASS =
            "net/minecraft/client/player/AbstractClientPlayer";
    private static final String LEVEL_CLASS =
            "net/minecraft/world/level/Level";

    private Vanilla262Mappings() {
    }

    public static Class<?> resolveClass(String sourceClassName,
                                        ClassLoader... preferredLoaders) {
        String internalName = normalizeInternalName(sourceClassName);
        if (internalName == null || internalName.isEmpty()) {
            return null;
        }
        for (ClassLoader loader : candidateLoaders(preferredLoaders)) {
            Class<?> resolved = resolveRuntimeClass(internalName, loader);
            if (resolved != null) {
                return resolved;
            }
        }
        return null;
    }

    public static boolean isRuntimePresent(ClassLoader... preferredLoaders) {
        return Vanilla262Mappings.protocolVersion(preferredLoaders) != 0;
    }

    /**
     * Returns the recovered protocol version (100 for 26.1.2, 110 for 26.2)
     * when the running client matches the recovered 26.x structure, or 0 when
     * the runtime does not look like a supported modern Minecraft client.
     */
    public static int protocolVersion(ClassLoader... preferredLoaders) {
        for (ClassLoader loader : candidateLoaders(preferredLoaders)) {
            Class<?> minecraftClass = resolveRuntimeClass(
                    MINECRAFT_CLASS, loader);
            if (minecraftClass == null) {
                continue;
            }
            ClassLoader definingLoader = minecraftClass.getClassLoader();
            if (definingLoader == null) {
                continue;
            }
            Class<?> clientLevel = resolveRuntimeClass(
                    CLIENT_LEVEL_CLASS, definingLoader);
            Class<?> localPlayer = resolveRuntimeClass(
                    LOCAL_PLAYER_CLASS, definingLoader);
            Class<?> abstractPlayer = resolveRuntimeClass(
                    ABSTRACT_PLAYER_CLASS, definingLoader);
            Class<?> level = resolveRuntimeClass(LEVEL_CLASS, definingLoader);
            if (clientLevel == null || localPlayer == null
                    || abstractPlayer == null || level == null
                    || clientLevel.getSuperclass() != level
                    || localPlayer.getSuperclass() != abstractPlayer) {
                continue;
            }
            if (matchesMinecraftStructure(minecraftClass)) {
                int protocol = matchesVersionMetadata(minecraftClass);
                if (protocol != 0) {
                    return protocol;
                }
            }
        }
        return 0;
    }

    private static boolean matchesMinecraftStructure(Class<?> minecraftClass) {
        try {
            Method getter = minecraftClass.getDeclaredMethod("getInstance");
            Field instance = minecraftClass.getDeclaredField("instance");
            return Modifier.isStatic(getter.getModifiers())
                    && getter.getReturnType() == minecraftClass
                    && Modifier.isStatic(instance.getModifiers())
                    && instance.getType() == minecraftClass;
        }
        catch (ReflectiveOperationException ignored) {
            return false;
        }
        catch (SecurityException ignored) {
            return false;
        }
        catch (LinkageError ignored) {
            return false;
        }
    }

    private static int matchesVersionMetadata(Class<?> minecraftClass) {
        try (InputStream stream = minecraftClass.getResourceAsStream(
                "/version.json")) {
            if (stream == null) {
                return 0;
            }
            JsonElement element = new JsonParser().parse(new InputStreamReader(
                    stream, StandardCharsets.UTF_8));
            if (!element.isJsonObject()) {
                return 0;
            }
            JsonObject version = element.getAsJsonObject();
            if (getInteger(version, "java_version") != 25) {
                return 0;
            }
            String id = getString(version, "id");
            String name = getString(version, "name");
            if (id != null && id.equals(name)) {
                boolean is261Family = id.startsWith("26.1")
                        && (id.length() == 4 || id.charAt(4) == '.');
                boolean is262Family = id.startsWith("26.2")
                        && (id.length() == 4 || id.charAt(4) == '.');
                if (is261Family) {
                    return 100;
                }
                if (is262Family) {
                    return 110;
                }
            }
            return 0;
        }
        catch (Exception ignored) {
            return 0;
        }
        catch (LinkageError ignored) {
            return 0;
        }
    }

    private static String getString(JsonObject object, String name) {
        JsonElement value = object.get(name);
        return value != null && value.isJsonPrimitive()
                ? value.getAsString() : null;
    }

    private static int getInteger(JsonObject object, String name) {
        JsonElement value = object.get(name);
        return value != null && value.isJsonPrimitive()
                ? value.getAsInt() : Integer.MIN_VALUE;
    }

    private static Class<?> resolveRuntimeClass(
            String internalName, ClassLoader loader) {
        try {
            return Class.forName(internalName.replace('/', '.'), false, loader);
        }
        catch (ClassNotFoundException ignored) {
            return null;
        }
        catch (LinkageError ignored) {
            return null;
        }
        catch (SecurityException ignored) {
            return null;
        }
    }

    private static Set<ClassLoader> candidateLoaders(
            ClassLoader... preferredLoaders) {
        Set<ClassLoader> loaders = new LinkedHashSet<ClassLoader>();
        if (preferredLoaders != null) {
            for (ClassLoader loader : preferredLoaders) {
                if (loader != null) {
                    loaders.add(loader);
                }
            }
        }
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        if (contextLoader != null) {
            loaders.add(contextLoader);
        }
        ClassLoader mappingLoader = Vanilla262Mappings.class.getClassLoader();
        if (mappingLoader != null) {
            loaders.add(mappingLoader);
        }
        try {
            ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
            if (systemLoader != null) {
                loaders.add(systemLoader);
            }
        }
        catch (SecurityException ignored) {
            // The injected runtime loaders above are sufficient.
        }
        return loaders;
    }

    private static String normalizeInternalName(String className) {
        if (className == null) {
            return null;
        }
        String normalized = className.trim().replace('.', '/');
        if (normalized.length() > 2 && normalized.charAt(0) == 'L'
                && normalized.charAt(normalized.length() - 1) == ';') {
            normalized = normalized.substring(1, normalized.length() - 1);
        }
        return normalized;
    }
}
