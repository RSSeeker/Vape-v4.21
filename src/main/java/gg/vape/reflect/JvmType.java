package gg.vape.reflect;

import gg.vape.reflect.Type;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class JvmType {
    public static final int VOID = 0;
    public static final int BOOLEAN = 1;
    public static final int CHAR = 2;
    public static final int BYTE = 3;
    public static final int SHORT = 4;
    public static final int INT = 5;
    public static final int FLOAT = 6;
    public static final int LONG = 7;
    public static final int DOUBLE = 8;
    public static final int ARRAY = 9;
    public static final int OBJECT = 10;
    public static final int METHOD = 11;
    public static final JvmType VOID_TYPE = new JvmType(Type.VOID_TYPE);
    public static final JvmType BOOLEAN_TYPE = new JvmType(Type.BOOLEAN_TYPE);
    public static final JvmType CHAR_TYPE = new JvmType(Type.CHAR_TYPE);
    public static final JvmType BYTE_TYPE = new JvmType(Type.BYTE_TYPE);
    public static final JvmType SHORT_TYPE = new JvmType(Type.SHORT_TYPE);
    public static final JvmType INT_TYPE = new JvmType(Type.INT_TYPE);
    public static final JvmType FLOAT_TYPE = new JvmType(Type.FLOAT_TYPE);
    public static final JvmType LONG_TYPE = new JvmType(Type.LONG_TYPE);
    public static final JvmType DOUBLE_TYPE = new JvmType(Type.DOUBLE_TYPE);
    private final Type legacyType;

    private JvmType(Type legacyType) {
        if (legacyType == null) {
            throw new NullPointerException("legacyType");
        }
        this.legacyType = legacyType;
    }

    public static JvmType parse(String descriptor) {
        return JvmType.fromLegacyType(Type.getType(descriptor));
    }

    public static JvmType of(Class<?> type) {
        return JvmType.fromLegacyType(Type.getType(type));
    }

    public static JvmType of(Constructor<?> constructor) {
        return JvmType.fromLegacyType(Type.getType(constructor));
    }

    public static JvmType of(Method method) {
        return JvmType.fromLegacyType(Type.getType(method));
    }

    public static JvmType object(String internalName) {
        return JvmType.fromLegacyType(Type.getObjectType(internalName));
    }

    public static JvmType method(String descriptor) {
        return JvmType.fromLegacyType(Type.getMethodType(descriptor));
    }

    public static JvmType method(JvmType returnType, JvmType ... argumentTypes) {
        Type[] legacyArguments = new Type[argumentTypes.length];
        for (int index = 0; index < argumentTypes.length; ++index) {
            legacyArguments[index] = argumentTypes[index].legacyType;
        }
        return JvmType.fromLegacyType(Type.getMethodType(returnType.legacyType, legacyArguments));
    }

    public static JvmType fromLegacyType(Type type) {
        if (type == Type.VOID_TYPE) {
            return VOID_TYPE;
        }
        if (type == Type.BOOLEAN_TYPE) {
            return BOOLEAN_TYPE;
        }
        if (type == Type.CHAR_TYPE) {
            return CHAR_TYPE;
        }
        if (type == Type.BYTE_TYPE) {
            return BYTE_TYPE;
        }
        if (type == Type.SHORT_TYPE) {
            return SHORT_TYPE;
        }
        if (type == Type.INT_TYPE) {
            return INT_TYPE;
        }
        if (type == Type.FLOAT_TYPE) {
            return FLOAT_TYPE;
        }
        if (type == Type.LONG_TYPE) {
            return LONG_TYPE;
        }
        if (type == Type.DOUBLE_TYPE) {
            return DOUBLE_TYPE;
        }
        return new JvmType(type);
    }

    public Type toLegacyType() {
        return this.legacyType;
    }

    public JvmType elementType() {
        return JvmType.fromLegacyType(this.legacyType.getElementType());
    }

    public JvmType[] argumentTypes() {
        Type[] legacyArguments = this.legacyType.getArgumentTypes();
        JvmType[] arguments = new JvmType[legacyArguments.length];
        for (int index = 0; index < legacyArguments.length; ++index) {
            arguments[index] = JvmType.fromLegacyType(legacyArguments[index]);
        }
        return arguments;
    }

    public JvmType returnType() {
        return JvmType.fromLegacyType(this.legacyType.getReturnType());
    }

    public int sort() {
        return this.legacyType.getSort();
    }

    public int dimensions() {
        return this.legacyType.getDimensions();
    }

    public int size() {
        return this.legacyType.getSize();
    }

    public int argumentsAndReturnSizes() {
        return this.legacyType.getArgumentsAndReturnSizes();
    }

    public String className() {
        return this.legacyType.getClassName();
    }

    public String internalName() {
        return this.legacyType.getInternalName();
    }

    public String descriptor() {
        return this.legacyType.getDescriptor();
    }

    public static String descriptorOf(Class<?> type) {
        return Type.getDescriptor(type);
    }

    public static String descriptorOf(Constructor<?> constructor) {
        return Type.getConstructorDescriptor(constructor);
    }

    public static String descriptorOf(Method method) {
        return Type.getMethodDescriptor(method);
    }

    public static String methodDescriptor(JvmType returnType, JvmType ... argumentTypes) {
        Type[] legacyArguments = new Type[argumentTypes.length];
        for (int index = 0; index < argumentTypes.length; ++index) {
            legacyArguments[index] = argumentTypes[index].legacyType;
        }
        return Type.getMethodDescriptor(returnType.legacyType, legacyArguments);
    }

    public boolean equals(Object other) {
        return other instanceof JvmType && this.descriptor().equals(((JvmType)other).descriptor());
    }

    public int hashCode() {
        return this.descriptor().hashCode();
    }

    public String toString() {
        return this.descriptor();
    }
}

