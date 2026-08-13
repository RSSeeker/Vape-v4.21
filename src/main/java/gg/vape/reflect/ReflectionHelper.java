package gg.vape.reflect;

import gg.vape.reflect.Reflections;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public final class ReflectionHelper
extends Reflections {
    public static final Map<Integer, Method> METHODS_BY_ID = Reflections.METHODS_BY_ID;
    public static final Map<Integer, Field> FIELDS_BY_ID = Reflections.FIELDS_BY_ID;
    public static final Map<Integer, Constructor<?>> CONSTRUCTORS_BY_ID = Reflections.CONSTRUCTORS_BY_ID;

    private ReflectionHelper() {
    }

    public static void registerMethod(int id, Class<?> owner, String name, String descriptor, boolean remap, boolean isStatic) throws NoSuchMethodException {
        Reflections.getMethod(id, owner, name, descriptor, remap, isStatic);
    }

    public static void registerMinecraftMethod(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchMethodException {
        Reflections.getMinecraftMethod(id, owner, name, descriptor, remap);
    }

    public static void registerConstructor(int id, Class<?> owner, String descriptor) {
        Reflections.getConstructor(id, owner, descriptor);
    }

    public static void registerField(int id, Class<?> owner, String name, String descriptor, boolean remap, boolean isStatic) throws NoSuchFieldException {
        Reflections.getField(id, owner, name, descriptor, remap, isStatic);
    }

    public static void registerMinecraftField(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchFieldException {
        Reflections.getMinecraftField(id, owner, name, descriptor, remap);
    }

    public static Method[] declaredMethodsInHierarchy(Class<?> owner) {
        return Reflections.getAllMethods(owner);
    }

    public static <T> T invokeCached(int id, Object receiver, Class<T> resultType, Object ... arguments) {
        return Reflections.invokeMethod(id, receiver, resultType, arguments);
    }

    public static <T> T readCachedField(int id, Object receiver) {
        return Reflections.getField(id, receiver);
    }

    public static void writeCachedField(int id, Object receiver, Object value) {
        Reflections.setObjectField(id, receiver, value);
    }

    public static <T> T constructCached(int id, Object ... arguments) {
        return Reflections.invokeConstructor(id, arguments);
    }
}
