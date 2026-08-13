package gg.vape.asm.helper;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.runtime.NativeBridge;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class DescUtils {
    private static final Map<Class<?>, String> knownDescriptors;
    private static int opaqueState;

    public static String getDescriptor(Class<?> type, boolean useMappedClassName) {
        if (type == null) {
            return NativeBridge.gcs(null);
        }
        String knownDescriptor = DescUtils.getKnownDescriptor(type);
        if (knownDescriptor != null) {
            return knownDescriptor;
        }
        if (useMappedClassName) {
            String descriptor = MappedClasses.b(type).replace('.', '/');
            if (!type.isArray()) {
                descriptor = "L" + descriptor + ";";
            }
            return descriptor;
        }
        return NativeBridge.gcs(type);
    }

    public static int opaquePredicate() {
        return opaqueState == 0 ? 77 : 0;
    }

    public static String getStackMethodName(int stackIndex) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackIndex >= 0 && stackIndex < stackTrace.length ? stackTrace[stackIndex].getMethodName() : "";
    }

    public static String getDescriptor(Class<?> type) {
        return DescUtils.getDescriptor(type, false);
    }

    public static int getOpaqueState() {
        return opaqueState;
    }

    public static String getMethodDescriptor(boolean useMappedClassNames, Class<?> returnType,
                                             Class<?> ... parameterTypes) {
        StringBuilder descriptor = new StringBuilder("(");
        for (Class<?> parameterType : parameterTypes) {
            descriptor.append(DescUtils.getDescriptor(parameterType, useMappedClassNames));
        }
        return descriptor.append(')').append(DescUtils.getDescriptor(returnType, useMappedClassNames)).toString();
    }

    public static void setOpaqueState(int state) {
        opaqueState = state;
    }

    public static void traceStack() {
        Vape.debugLog("");
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            Vape.debugLog(stackTraceElement.toString());
        }
        Vape.debugLog("");
    }

    public static boolean isCurrentThreadInMappedFrame(MappingMethod mappingMethod, int stackIndex) {
        return NativeBridge.gtcf(Thread.currentThread(), mappingMethod.getMethodId(), stackIndex);
    }

    static {
        if (DescUtils.opaquePredicate() != 0) {
            DescUtils.setOpaqueState(39);
        }
        String[] primitiveArrayDescriptors = new String[]{"[S", "[I", "[J", "[C", "[F", "[B", "[D", "[Z"};
        knownDescriptors = new HashMap();
        knownDescriptors.put(Boolean.TYPE, "Z");
        knownDescriptors.put(Byte.TYPE, "B");
        knownDescriptors.put(Character.TYPE, "C");
        knownDescriptors.put(Short.TYPE, "S");
        knownDescriptors.put(Integer.TYPE, "I");
        knownDescriptors.put(Long.TYPE, "J");
        knownDescriptors.put(Float.TYPE, "F");
        knownDescriptors.put(Double.TYPE, "D");
        knownDescriptors.put(Void.TYPE, "V");
        knownDescriptors.put(boolean[].class, primitiveArrayDescriptors[7]);
        knownDescriptors.put(byte[].class, primitiveArrayDescriptors[5]);
        knownDescriptors.put(char[].class, primitiveArrayDescriptors[3]);
        knownDescriptors.put(short[].class, primitiveArrayDescriptors[0]);
        knownDescriptors.put(int[].class, primitiveArrayDescriptors[1]);
        knownDescriptors.put(long[].class, primitiveArrayDescriptors[2]);
        knownDescriptors.put(float[].class, primitiveArrayDescriptors[4]);
        knownDescriptors.put(double[].class, primitiveArrayDescriptors[6]);
    }

    public static String genMethodDesc(Class<?> returnType, Class<?> ... parameterTypes) {
        return DescUtils.getMethodDescriptor(false, returnType, parameterTypes);
    }

    public static Class<?> getReflectiveArrayType(Class<?> componentType) {
        return componentType == null ? null : Array.newInstance(componentType, 1).getClass();
    }

    public static boolean isCallStackContaining(String className, String methodName) {
        if (className == null) {
            return false;
        }
        for (StackTraceElement frame : Thread.currentThread().getStackTrace()) {
            if (!className.equals(frame.getClassName())
                    || methodName != null && !methodName.equals(frame.getMethodName())) continue;
            return true;
        }
        return false;
    }

    public static Class<?> getArrayType(Class<?> componentType) {
        return DescUtils.getReflectiveArrayType(componentType);
    }


    public static String getKnownDescriptor(Class<?> type) {
        return knownDescriptors.get(type);
    }
}

