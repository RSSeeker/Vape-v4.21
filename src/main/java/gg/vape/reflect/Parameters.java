package gg.vape.reflect;

import gg.vape.reflect.ParameterResolver;

public class Parameters {
    public static boolean checkParameterTypes(Class<?>[] parameterTypes, Class<?> returnType, String descriptor) {
        return ParameterResolver.matchesDescriptor(parameterTypes, returnType, descriptor);
    }
}
