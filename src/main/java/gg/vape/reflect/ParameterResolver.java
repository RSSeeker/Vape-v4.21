package gg.vape.reflect;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ParameterResolver {
    private static final Map<Class<?>, String> PRIMITIVE_DESCRIPTORS;

    private ParameterResolver() {
    }

    public static String descriptorFor(Class<?> type) {
        String primitiveDescriptor = PRIMITIVE_DESCRIPTORS.get(type);
        if (primitiveDescriptor != null) {
            return primitiveDescriptor;
        }
        if (type.isArray()) {
            return type.getName().replace('.', '/');
        }
        return 'L' + type.getName().replace('.', '/') + ';';
    }

    public static String methodDescriptor(Class<?>[] parameterTypes, Class<?> returnType) {
        StringBuilder descriptor = new StringBuilder();
        descriptor.append('(');
        for (Class<?> parameterType : parameterTypes) {
            descriptor.append(ParameterResolver.descriptorFor(parameterType));
        }
        descriptor.append(')').append(ParameterResolver.descriptorFor(returnType));
        return descriptor.toString();
    }

    public static boolean matchesDescriptor(Class<?>[] parameterTypes, Class<?> returnType, String descriptor) {
        return ParameterResolver.methodDescriptor(parameterTypes, returnType).equals(descriptor);
    }

    static {
        HashMap<Class<?>, String> descriptors = new HashMap<Class<?>, String>();
        descriptors.put(Boolean.TYPE, "Z");
        descriptors.put(Byte.TYPE, "B");
        descriptors.put(Character.TYPE, "C");
        descriptors.put(Short.TYPE, "S");
        descriptors.put(Integer.TYPE, "I");
        descriptors.put(Long.TYPE, "J");
        descriptors.put(Float.TYPE, "F");
        descriptors.put(Double.TYPE, "D");
        descriptors.put(Void.TYPE, "V");
        PRIMITIVE_DESCRIPTORS = Collections.unmodifiableMap(descriptors);
    }
}
