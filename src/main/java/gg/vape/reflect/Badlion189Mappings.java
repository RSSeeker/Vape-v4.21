package gg.vape.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Badlion 1.8.9 keeps Minecraft's vanilla obfuscated namespace. This profile
 * identifies the Badlion runtime while deliberately sharing the canonical
 * 1.8.9 SRG data instead of maintaining a divergent copy.
 */
public final class Badlion189Mappings {
    private static final String[] MARKER_RESOURCES = {
            "net/badlion/client/Wrapper.class",
            "net/badlion/client/tweaker/BadlionTransformer.class",
            "net/badlion/clientcommon/main/Launch.class"
    };

    private Badlion189Mappings() {
    }

    public static boolean isRuntimePresent(ClassLoader... preferredLoaders) {
        return hasBadlionMarker(preferredLoaders)
                && Vanilla189Mappings.isRuntimePresent(preferredLoaders);
    }

    public static Class<?> resolveClass(String sourceClassName,
                                        ClassLoader... preferredLoaders) {
        return Vanilla189Mappings.resolveClass(sourceClassName, preferredLoaders);
    }

    static String lookupFieldSrgName(Field field) {
        return Vanilla189Mappings.lookupFieldSrgName(field);
    }

    static String lookupMethodSrgName(Method method) {
        return Vanilla189Mappings.lookupMethodSrgName(method);
    }

    private static boolean hasBadlionMarker(ClassLoader... preferredLoaders) {
        for (ClassLoader loader : candidateLoaders(preferredLoaders)) {
            for (String marker : MARKER_RESOURCES) {
                try {
                    URL resource = loader.getResource(marker);
                    if (resource != null) {
                        return true;
                    }
                }
                catch (RuntimeException ignored) {
                    // A protected client loader may reject one lookup. Try the rest.
                }
                catch (LinkageError ignored) {
                    // Resource lookup must not make client detection fatal.
                }
            }
        }
        return false;
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
        ClassLoader profileLoader = Badlion189Mappings.class.getClassLoader();
        if (profileLoader != null) {
            loaders.add(profileLoader);
        }
        try {
            ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
            if (systemLoader != null) {
                loaders.add(systemLoader);
            }
        }
        catch (SecurityException ignored) {
            // The injected context and profile loaders are sufficient.
        }
        return loaders;
    }
}
