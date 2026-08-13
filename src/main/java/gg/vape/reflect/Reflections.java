package gg.vape.reflect;

import gg.vape.reflect.MappingRegistry;
import gg.vape.reflect.ParameterResolver;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Reflections {
    public static final Map<Integer, Method> METHODS_BY_ID = new HashMap<Integer, Method>();
    public static final Map<Integer, Field> FIELDS_BY_ID = new HashMap<Integer, Field>();
    public static final Map<Integer, Constructor<?>> CONSTRUCTORS_BY_ID = new HashMap();
    private static final Set<Integer> REPORTED_INVOCATION_FAILURES =
            Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    public static void getMethod(int id, Class<?> owner, String name, String descriptor, boolean remap, boolean isStatic) throws NoSuchMethodException {
        if (name.equals("<init>")) {
            Reflections.getConstructor(id, owner, descriptor);
            return;
        }
        if (!name.equals("ordinal") && !name.equals("values")) {
            boolean skipParameterCheck = name.equals("getEnchantmentModifierDamage");
            for (Method method : owner.getDeclaredMethods()) {
                if (!MappingRegistry.matches(method, name) || !ParameterResolver.matchesDescriptor(method.getParameterTypes(), method.getReturnType(), descriptor) && !skipParameterCheck) continue;
                method.setAccessible(true);
                METHODS_BY_ID.put(id, method);
                return;
            }
            for (Method method : owner.getMethods()) {
                if (method.getDeclaringClass() == owner || !MappingRegistry.matches(method, name) || !ParameterResolver.matchesDescriptor(method.getParameterTypes(), method.getReturnType(), descriptor) && !skipParameterCheck) continue;
                method.setAccessible(true);
                METHODS_BY_ID.put(id, method);
                return;
            }
            throw new NoSuchMethodException();
        }
        for (Method method : Reflections.getAllMethods(owner)) {
            if (!method.getName().equals(name)) continue;
            METHODS_BY_ID.put(id, method);
        }
    }

    public static void getMinecraftMethod(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchMethodException {
        Reflections.getMethod(id, owner, name, descriptor, remap, false);
    }

    public static void getMethod(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchMethodException {
        Reflections.getMethod(id, owner, name, descriptor, remap, true);
    }

    public static Method[] getAllMethods(Class<?> clazz) {
        ArrayList<Method> methods = new ArrayList<Method>();
        ArrayList hierarchy = new ArrayList();
        Class<?> current = clazz;
        do {
            hierarchy.add(current);
        } while ((current = current.getSuperclass()) != Object.class && current != null);
        for (int index = hierarchy.size() - 1; index >= 0; --index) {
            for (Method method : ((Class)hierarchy.get(index)).getDeclaredMethods()) {
                methods.add(method);
            }
        }
        return methods.toArray(new Method[0]);
    }

    public static void getConstructor(int id, Class<?> owner, String descriptor) {
        for (Constructor<?> constructor : owner.getDeclaredConstructors()) {
            if (!ParameterResolver.matchesDescriptor(constructor.getParameterTypes(), Void.TYPE, descriptor)) continue;
            constructor.setAccessible(true);
            CONSTRUCTORS_BY_ID.put(id, constructor);
            return;
        }
    }

    public static void invoke(int id, Object instance, Object ... params) {
        Reflections.invokeMethod(id, instance, Void.TYPE, params);
    }

    public static boolean invokeMethodBoolean(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Boolean.TYPE, params);
    }

    public static char invokeMethodChar(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Character.TYPE, params).charValue();
    }

    public static short invokeMethodShort(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Short.TYPE, params);
    }

    public static int invokeMethodInt(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Integer.TYPE, params);
    }

    public static long invokeMethodLong(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Long.TYPE, params);
    }

    public static float invokeMethodFloat(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Float.TYPE, params).floatValue();
    }

    public static double invokeMethodDouble(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Double.TYPE, params);
    }

    public static Object invokeMethodObject(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Object.class, params);
    }

    public static boolean[] invokeMethodBooleanArray(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, boolean[].class, params);
    }

    public static char[] invokeMethodCharArray(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, char[].class, params);
    }

    public static short[] invokeMethodShortArray(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, short[].class, params);
    }

    public static int[] invokeMethodIntArray(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, int[].class, params);
    }

    public static long[] invokeMethodLongArray(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, long[].class, params);
    }

    public static float[] invokeMethodFloatArray(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, float[].class, params);
    }

    public static double[] invokeMethodDoubleArray(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, double[].class, params);
    }

    public static Object[] invokeMethodObjectArray(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Object[].class, params);
    }

    public static <T> T invokeMethod(int id, Object instance, Class<T> resultType, Object ... arguments) {
        try {
            Method method = METHODS_BY_ID.get(id);
            if (method == null) {
                throw new IllegalStateException("Method mapping was not registered: id=" + id);
            }
            return (T)method.invoke(instance, arguments);
        }
        catch (Throwable failure) {
            if (REPORTED_INVOCATION_FAILURES.add(id)) {
                System.out.println("Failed invoke method: " + id + "," + resultType.getName() + "," + instance + "," + Arrays.toString(arguments));
                failure.printStackTrace();
            }
            return null;
        }
    }

    public static void getMinecraftField(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchFieldException {
        Reflections.getField(id, owner, name, descriptor, remap, false);
    }

    public static void getField(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchFieldException {
        Reflections.getField(id, owner, name, descriptor, remap, true);
    }

    public static void getField(int id, Class<?> owner, String name, String descriptor, boolean remap, boolean isStatic) throws NoSuchFieldException {
        for (Field field : owner.getDeclaredFields()) {
            if (!MappingRegistry.matches(field, name)) continue;
            field.setAccessible(true);
            FIELDS_BY_ID.put(id, field);
            return;
        }
        System.out.println("Failed allocate field: " + id + " " + name + " IN " + owner.getName());
        throw new NoSuchFieldException();
    }

    public static boolean getFieldBoolean(int id, Object instance) {
        try {
            return Reflections.field(id).getBoolean(instance);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static char getFieldChar(int id, Object instance) {
        try {
            return Reflections.field(id).getChar(instance);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static short getFieldShort(int id, Object instance) {
        try {
            return Reflections.field(id).getShort(instance);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static int getFieldInt(int id, Object instance) {
        try {
            return Reflections.field(id).getInt(instance);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static long getFieldLong(int id, Object instance) {
        try {
            return Reflections.field(id).getLong(instance);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static float getFieldFloat(int id, Object instance) {
        try {
            Field field = Reflections.field(id);
            return field.getType() == Float.TYPE ? field.getFloat(instance) : (float)field.getDouble(instance);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static double getFieldDouble(int id, Object instance) {
        try {
            return Reflections.field(id).getDouble(instance);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static Object getFieldObject(int id, Object instance) {
        return Reflections.getField(id, instance);
    }

    public static boolean[] getFieldBooleanArray(int id, Object instance) {
        return (boolean[])Reflections.getField(id, instance);
    }

    public static char[] getFieldCharArray(int id, Object instance) {
        return (char[])Reflections.getField(id, instance);
    }

    public static short[] getFieldShortArray(int id, Object instance) {
        return (short[])Reflections.getField(id, instance);
    }

    public static int[] getFieldIntArray(int id, Object instance) {
        return (int[])Reflections.getField(id, instance);
    }

    public static long[] getFieldLongArray(int id, Object instance) {
        return (long[])Reflections.getField(id, instance);
    }

    public static float[] getFieldFloatArray(int id, Object instance) {
        return (float[])Reflections.getField(id, instance);
    }

    public static double[] getFieldDoubleArray(int id, Object instance) {
        return (double[])Reflections.getField(id, instance);
    }

    public static Object[] getFieldObjectArray(int id, Object instance) {
        return (Object[])Reflections.getField(id, instance);
    }

    public static <T> T getField(int id, Object instance) {
        try {
            return (T)Reflections.field(id).get(instance);
        }
        catch (Exception failure) {
            System.err.println("Failed allocate field: " + id + " IN " + instance + " " + FIELDS_BY_ID.size());
            failure.printStackTrace();
            return null;
        }
    }

    public static void setFieldBoolean(int id, Object instance, boolean value) {
        try {
            Reflections.field(id).setBoolean(instance, value);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static void setFieldChar(int id, Object instance, char value) {
        try {
            Reflections.field(id).setChar(instance, value);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static void setFieldShort(int id, Object instance, short value) {
        try {
            Reflections.field(id).setShort(instance, value);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static void setFieldInt(int id, Object instance, int value) {
        try {
            Reflections.field(id).setInt(instance, value);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static void setFieldLong(int id, Object instance, long value) {
        try {
            Reflections.field(id).setLong(instance, value);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static void setFieldFloat(int id, Object instance, float value) {
        try {
            Reflections.field(id).setFloat(instance, value);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static void setFieldDouble(int id, Object instance, double value) {
        try {
            Reflections.field(id).setDouble(instance, value);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static void setFieldObject(int id, Object instance, Object value) {
        Reflections.setObjectField(id, instance, value);
    }

    public static void setFieldBooleanArray(int id, Object instance, boolean[] value) {
        Reflections.setObjectField(id, instance, value);
    }

    public static void setFieldCharArray(int id, Object instance, char[] value) {
        Reflections.setObjectField(id, instance, value);
    }

    public static void setFieldShortArray(int id, Object instance, short[] value) {
        Reflections.setObjectField(id, instance, value);
    }

    public static void setFieldIntArray(int id, Object instance, int[] value) {
        Reflections.setObjectField(id, instance, value);
    }

    public static void setFieldLongArray(int id, Object instance, long[] value) {
        Reflections.setObjectField(id, instance, value);
    }

    public static void setFieldFloatArray(int id, Object instance, float[] value) {
        Reflections.setObjectField(id, instance, value);
    }

    public static void setFieldDoubleArray(int id, Object instance, double[] value) {
        Reflections.setObjectField(id, instance, value);
    }

    public static void setFieldObjectArray(int id, Object instance, Object[] value) {
        Reflections.setObjectField(id, instance, value);
    }

    public static void setObjectField(int id, Object instance, Object value) {
        try {
            Reflections.field(id).set(instance, value);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static void invokeMethodVoid(int id, Object instance, Object ... params) {
        Reflections.invokeMethod(id, instance, Void.TYPE, params);
    }

    public static Object create(int id, Class<?> cls, Object ... params) {
        return Reflections.invokeConstructor(id, params);
    }

    public static <T> T invokeConstructor(int id, Object ... params) {
        try {
            return (T)CONSTRUCTORS_BY_ID.get(id).newInstance(params);
        }
        catch (Throwable failure) {
            throw Reflections.sneakyThrow(failure);
        }
    }

    public static byte invokeMethodByte(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Byte.TYPE, params);
    }

    public static String getFieldName(int id) {
        return Reflections.field(id).getName();
    }

    public static String getMethodName(int id) {
        return METHODS_BY_ID.get(id).getName();
    }

    public static Object invokeMethodObjects(int id, Object instance, Object ... params) {
        return Reflections.invokeMethod(id, instance, Object.class, params);
    }

    private static Field field(int id) {
        return FIELDS_BY_ID.get(id);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> RuntimeException sneakyThrow(Throwable failure) throws T {
        throw (T)failure;
    }
}
