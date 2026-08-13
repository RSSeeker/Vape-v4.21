package gg.vape.reflect;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/** Parses and resolves one legacy Minecraft joined.srg mapping set. */
final class VanillaSrgMappings {
    private final String minecraftVersion;
    private final String resource;
    private final String runtimeMinecraftClass;
    private final String runtimeMinecraftGetter;
    private final String runtimeMinecraftInstanceField;
    private final String[] runtimeAnchors;
    private volatile MappingData data;

    VanillaSrgMappings(String minecraftVersion, String resource,
                       String runtimeMinecraftClass,
                       String runtimeMinecraftGetter,
                       String runtimeMinecraftInstanceField,
                       String... runtimeAnchors) {
        this.minecraftVersion = minecraftVersion;
        this.resource = resource;
        this.runtimeMinecraftClass = runtimeMinecraftClass;
        this.runtimeMinecraftGetter = runtimeMinecraftGetter;
        this.runtimeMinecraftInstanceField = runtimeMinecraftInstanceField;
        this.runtimeAnchors = runtimeAnchors.clone();
    }

    String remapClassName(String sourceClassName) {
        return this.getData().classNames.get(normalizeInternalName(sourceClassName));
    }

    Class<?> resolveClass(String sourceClassName,
                          ClassLoader... preferredLoaders) {
        String normalizedName = normalizeInternalName(sourceClassName);
        if (normalizedName == null || normalizedName.isEmpty()) {
            return null;
        }

        Set<String> candidateNames = new LinkedHashSet<String>();
        String remappedName = this.getData().classNames.get(normalizedName);
        if (remappedName != null) {
            candidateNames.add(remappedName);
        }
        candidateNames.add(normalizedName);

        Set<ClassLoader> loaders = candidateLoaders(preferredLoaders);
        for (String candidateName : candidateNames) {
            String binaryName = candidateName.replace('/', '.');
            for (ClassLoader loader : loaders) {
                try {
                    return Class.forName(binaryName, false, loader);
                }
                catch (ClassNotFoundException ignored) {
                    // Try the next loader/name pair.
                }
                catch (LinkageError ignored) {
                    // An incompatible class with the same name is not a valid match.
                }
            }
        }
        return null;
    }

    boolean isRuntimePresent(ClassLoader... preferredLoaders) {
        for (ClassLoader loader : candidateLoaders(preferredLoaders)) {
            Class<?> minecraftClass = resolveRuntimeClass(
                    this.runtimeMinecraftClass, loader);
            if (minecraftClass == null) {
                continue;
            }
            ClassLoader definingLoader = minecraftClass.getClassLoader();
            boolean anchorsMatch = true;
            for (String runtimeName : this.runtimeAnchors) {
                Class<?> anchorClass = resolveRuntimeClass(runtimeName, loader);
                if (anchorClass == null
                        || anchorClass.getClassLoader() != definingLoader) {
                    anchorsMatch = false;
                    break;
                }
            }
            if (anchorsMatch && this.matchesMinecraftStructure(minecraftClass)) {
                return true;
            }
        }
        return false;
    }

    String lookupFieldSrgName(Field field) {
        if (field == null) {
            return null;
        }
        return this.getData().fieldNames.get(memberKey(
                field.getDeclaringClass().getName(), field.getName()));
    }

    String lookupMethodSrgName(Method method) {
        if (method == null) {
            return null;
        }
        String ownerAndName = memberKey(
                method.getDeclaringClass().getName(), method.getName());
        String descriptor = ParameterResolver.methodDescriptor(
                method.getParameterTypes(), method.getReturnType());
        return this.getData().methodNames.get(ownerAndName + ' ' + descriptor);
    }

    int getClassMappingCount() {
        return this.getData().classNames.size();
    }

    int getFieldMappingCount() {
        return this.getData().fieldNames.size();
    }

    int getMethodMappingCount() {
        return this.getData().methodNames.size();
    }

    private MappingData getData() {
        MappingData result = this.data;
        if (result != null) {
            return result;
        }
        synchronized (this) {
            result = this.data;
            if (result == null) {
                result = this.loadMappings();
                this.data = result;
            }
        }
        return result;
    }

    private MappingData loadMappings() {
        Map<String, String> classNames = new LinkedHashMap<String, String>();
        Map<String, String> fieldNames = new LinkedHashMap<String, String>();
        Map<String, String> methodNames = new LinkedHashMap<String, String>();

        try (InputStream stream = VanillaSrgMappings.class.getResourceAsStream(this.resource)) {
            if (stream == null) {
                throw new FileNotFoundException(
                        "Missing mapping resource: " + this.resource);
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("CL: ")) {
                        String[] columns = line.split("\\s+");
                        if (columns.length == 3) {
                            classNames.put(columns[2], columns[1]);
                        }
                    } else if (line.startsWith("FD: ")) {
                        String[] columns = line.split("\\s+");
                        if (columns.length == 3) {
                            fieldNames.put(columns[1], simpleMemberName(columns[2]));
                        }
                    } else if (line.startsWith("MD: ")) {
                        String[] columns = line.split("\\s+");
                        if (columns.length == 5) {
                            methodNames.put(columns[1] + ' ' + columns[2],
                                    simpleMemberName(columns[3]));
                        }
                    }
                }
            }
        }
        catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load " + this.minecraftVersion + " mappings", exception);
        }

        if (classNames.isEmpty() || fieldNames.isEmpty() || methodNames.isEmpty()) {
            throw new IllegalStateException(
                    "Incomplete " + this.minecraftVersion + " mappings in " + this.resource);
        }
        return new MappingData(classNames, fieldNames, methodNames);
    }

    private boolean matchesMinecraftStructure(Class<?> minecraftClass) {
        try {
            Method getter = minecraftClass.getDeclaredMethod(this.runtimeMinecraftGetter);
            Field instance = minecraftClass.getDeclaredField(
                    this.runtimeMinecraftInstanceField);
            return Modifier.isStatic(getter.getModifiers())
                    && getter.getReturnType() == minecraftClass
                    && Modifier.isStatic(instance.getModifiers())
                    && instance.getType() == minecraftClass;
        }
        catch (NoSuchMethodException ignored) {
            return false;
        }
        catch (NoSuchFieldException ignored) {
            return false;
        }
        catch (SecurityException ignored) {
            return false;
        }
        catch (LinkageError ignored) {
            return false;
        }
    }

    private static Class<?> resolveRuntimeClass(String runtimeInternalName,
                                                ClassLoader loader) {
        String binaryName = runtimeInternalName.replace('/', '.');
        try {
            return Class.forName(binaryName, false, loader);
        }
        catch (ClassNotFoundException ignored) {
            return null;
        }
        catch (LinkageError ignored) {
            return null;
        }
    }

    private static Set<ClassLoader> candidateLoaders(ClassLoader... preferredLoaders) {
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
        ClassLoader mappingLoader = VanillaSrgMappings.class.getClassLoader();
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

    private static String memberKey(String ownerName, String memberName) {
        return ownerName.replace('.', '/') + '/' + memberName;
    }

    private static String simpleMemberName(String qualifiedName) {
        int separator = qualifiedName.lastIndexOf('/');
        return separator < 0 ? qualifiedName : qualifiedName.substring(separator + 1);
    }

    private static final class MappingData {
        final Map<String, String> classNames;
        final Map<String, String> fieldNames;
        final Map<String, String> methodNames;

        MappingData(Map<String, String> classNames, Map<String, String> fieldNames,
                    Map<String, String> methodNames) {
            this.classNames = Collections.unmodifiableMap(classNames);
            this.fieldNames = Collections.unmodifiableMap(fieldNames);
            this.methodNames = Collections.unmodifiableMap(methodNames);
        }
    }
}
