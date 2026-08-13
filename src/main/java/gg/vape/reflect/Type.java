package gg.vape.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Type {
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
    private static final int INTERNAL = 12;
    private static final String PRIMITIVE_DESCRIPTORS = "VZCBSIFJD";
    public static final Type VOID_TYPE = new Type(0, "VZCBSIFJD", 0, 1);
    public static final Type BOOLEAN_TYPE = new Type(1, "VZCBSIFJD", 1, 2);
    public static final Type CHAR_TYPE = new Type(2, "VZCBSIFJD", 2, 3);
    public static final Type BYTE_TYPE = new Type(3, "VZCBSIFJD", 3, 4);
    public static final Type SHORT_TYPE = new Type(4, "VZCBSIFJD", 4, 5);
    public static final Type INT_TYPE = new Type(5, "VZCBSIFJD", 5, 6);
    public static final Type FLOAT_TYPE = new Type(6, "VZCBSIFJD", 6, 7);
    public static final Type LONG_TYPE = new Type(7, "VZCBSIFJD", 7, 8);
    public static final Type DOUBLE_TYPE = new Type(8, "VZCBSIFJD", 8, 9);
    private final int sort;
    private final String descriptorBuffer;
    private final int descriptorStart;
    private final int descriptorEnd;

    private Type(int sort, String descriptorBuffer, int descriptorStart, int descriptorEnd) {
        this.sort = sort;
        this.descriptorBuffer = descriptorBuffer;
        this.descriptorStart = descriptorStart;
        this.descriptorEnd = descriptorEnd;
    }

    public static Type getType(String typeDescriptor) {
        return Type.getTypeInternal(typeDescriptor, 0, typeDescriptor.length());
    }

    public static Type getType(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            if (clazz == Integer.TYPE) {
                return INT_TYPE;
            }
            if (clazz == Void.TYPE) {
                return VOID_TYPE;
            }
            if (clazz == Boolean.TYPE) {
                return BOOLEAN_TYPE;
            }
            if (clazz == Byte.TYPE) {
                return BYTE_TYPE;
            }
            if (clazz == Character.TYPE) {
                return CHAR_TYPE;
            }
            if (clazz == Short.TYPE) {
                return SHORT_TYPE;
            }
            if (clazz == Double.TYPE) {
                return DOUBLE_TYPE;
            }
            if (clazz == Float.TYPE) {
                return FLOAT_TYPE;
            }
            if (clazz == Long.TYPE) {
                return LONG_TYPE;
            }
            throw new AssertionError();
        }
        return Type.getType(Type.getDescriptor(clazz));
    }

    public static Type getType(Constructor<?> constructor) {
        return Type.getType(Type.getConstructorDescriptor(constructor));
    }

    public static Type getType(Method method) {
        return Type.getType(Type.getMethodDescriptor(method));
    }

    public Type getElementType() {
        return Type.getTypeInternal(this.descriptorBuffer, this.descriptorStart + this.getDimensions(), this.descriptorEnd);
    }

    public static Type getObjectType(String internalName) {
        return new Type(internalName.charAt(0) == '[' ? 9 : 12, internalName, 0, internalName.length());
    }

    public static Type getMethodType(String methodDescriptor) {
        return new Type(11, methodDescriptor, 0, methodDescriptor.length());
    }

    public static Type getMethodType(Type returnType, Type ... argumentTypes) {
        return Type.getType(Type.getMethodDescriptor(returnType, argumentTypes));
    }

    public Type[] getArgumentTypes() {
        return Type.getArgumentTypes(this.getDescriptor());
    }

    public static Type[] getArgumentTypes(String methodDescriptor) {
        int argumentCount = 0;
        int offset = 1;
        while (methodDescriptor.charAt(offset) != ')') {
            while (methodDescriptor.charAt(offset) == '[') {
                ++offset;
            }
            if (methodDescriptor.charAt(offset++) == 'L') {
                int semicolon = methodDescriptor.indexOf(59, offset);
                offset = Math.max(offset, semicolon + 1);
            }
            ++argumentCount;
        }
        Type[] argumentTypes = new Type[argumentCount];
        offset = 1;
        int argumentIndex = 0;
        while (methodDescriptor.charAt(offset) != ')') {
            int argumentStart = offset;
            while (methodDescriptor.charAt(offset) == '[') {
                ++offset;
            }
            if (methodDescriptor.charAt(offset++) == 'L') {
                int semicolon = methodDescriptor.indexOf(59, offset);
                offset = Math.max(offset, semicolon + 1);
            }
            argumentTypes[argumentIndex++] = Type.getTypeInternal(methodDescriptor, argumentStart, offset);
        }
        return argumentTypes;
    }

    public static Type[] getArgumentTypes(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Type[] types = new Type[parameterTypes.length];
        for (int index = parameterTypes.length - 1; index >= 0; --index) {
            types[index] = Type.getType(parameterTypes[index]);
        }
        return types;
    }

    public Type getReturnType() {
        return Type.getReturnType(this.getDescriptor());
    }

    public static Type getReturnType(String methodDescriptor) {
        return Type.getTypeInternal(methodDescriptor, Type.getReturnTypeOffset(methodDescriptor), methodDescriptor.length());
    }

    public static Type getReturnType(Method method) {
        return Type.getType(method.getReturnType());
    }

    static int getReturnTypeOffset(String methodDescriptor) {
        int offset = 1;
        while (methodDescriptor.charAt(offset) != ')') {
            while (methodDescriptor.charAt(offset) == '[') {
                ++offset;
            }
            if (methodDescriptor.charAt(offset++) != 'L') continue;
            int semicolon = methodDescriptor.indexOf(59, offset);
            offset = Math.max(offset, semicolon + 1);
        }
        return offset + 1;
    }

    private static Type getTypeInternal(String descriptor, int start, int end) {
        switch (descriptor.charAt(start)) {
            case 'V': {
                return VOID_TYPE;
            }
            case 'Z': {
                return BOOLEAN_TYPE;
            }
            case 'C': {
                return CHAR_TYPE;
            }
            case 'B': {
                return BYTE_TYPE;
            }
            case 'S': {
                return SHORT_TYPE;
            }
            case 'I': {
                return INT_TYPE;
            }
            case 'F': {
                return FLOAT_TYPE;
            }
            case 'J': {
                return LONG_TYPE;
            }
            case 'D': {
                return DOUBLE_TYPE;
            }
            case '[': {
                return new Type(9, descriptor, start, end);
            }
            case 'L': {
                return new Type(10, descriptor, start + 1, end - 1);
            }
            case '(': {
                return new Type(11, descriptor, start, end);
            }
        }
        throw new IllegalArgumentException("Invalid descriptor: " + descriptor);
    }

    public String getClassName() {
        switch (this.sort) {
            case 0: {
                return "void";
            }
            case 1: {
                return "boolean";
            }
            case 2: {
                return "char";
            }
            case 3: {
                return "byte";
            }
            case 4: {
                return "short";
            }
            case 5: {
                return "int";
            }
            case 6: {
                return "float";
            }
            case 7: {
                return "long";
            }
            case 8: {
                return "double";
            }
            case 9: {
                StringBuilder name = new StringBuilder(this.getElementType().getClassName());
                for (int dimensions = this.getDimensions(); dimensions > 0; --dimensions) {
                    name.append("[]");
                }
                return name.toString();
            }
            case 10: 
            case 12: {
                return this.descriptorBuffer.substring(this.descriptorStart, this.descriptorEnd).replace('/', '.');
            }
        }
        throw new AssertionError();
    }

    public String getInternalName() {
        return this.descriptorBuffer.substring(this.descriptorStart, this.descriptorEnd);
    }

    public static String getInternalName(Class<?> clazz) {
        return clazz.getName().replace('.', '/');
    }

    public String getDescriptor() {
        if (this.sort == 10) {
            return this.descriptorBuffer.substring(this.descriptorStart - 1, this.descriptorEnd + 1);
        }
        if (this.sort == 12) {
            return 'L' + this.descriptorBuffer.substring(this.descriptorStart, this.descriptorEnd) + ';';
        }
        return this.descriptorBuffer.substring(this.descriptorStart, this.descriptorEnd);
    }

    public static String getDescriptor(Class<?> clazz) {
        StringBuilder descriptor = new StringBuilder();
        Type.appendDescriptor(clazz, descriptor);
        return descriptor.toString();
    }

    public static String getConstructorDescriptor(Constructor<?> constructor) {
        StringBuilder descriptor = new StringBuilder();
        descriptor.append('(');
        for (Class<?> parameterType : constructor.getParameterTypes()) {
            Type.appendDescriptor(parameterType, descriptor);
        }
        return descriptor.append(")V").toString();
    }

    public static String getMethodDescriptor(Type returnType, Type ... argumentTypes) {
        StringBuilder descriptor = new StringBuilder();
        descriptor.append('(');
        for (Type argumentType : argumentTypes) {
            argumentType.appendDescriptor(descriptor);
        }
        descriptor.append(')');
        returnType.appendDescriptor(descriptor);
        return descriptor.toString();
    }

    public static String getMethodDescriptor(Method method) {
        StringBuilder descriptor = new StringBuilder();
        descriptor.append('(');
        for (Class<?> parameterType : method.getParameterTypes()) {
            Type.appendDescriptor(parameterType, descriptor);
        }
        descriptor.append(')');
        Type.appendDescriptor(method.getReturnType(), descriptor);
        return descriptor.toString();
    }

    private void appendDescriptor(StringBuilder destination) {
        if (this.sort == 10) {
            destination.append(this.descriptorBuffer, this.descriptorStart - 1, this.descriptorEnd + 1);
        } else if (this.sort == 12) {
            destination.append('L').append(this.descriptorBuffer, this.descriptorStart, this.descriptorEnd).append(';');
        } else {
            destination.append(this.descriptorBuffer, this.descriptorStart, this.descriptorEnd);
        }
    }

    private static void appendDescriptor(Class<?> clazz, StringBuilder destination) {
        Class<?> elementClass = clazz;
        while (elementClass.isArray()) {
            destination.append('[');
            elementClass = elementClass.getComponentType();
        }
        if (elementClass.isPrimitive()) {
            int descriptor;
            if (elementClass == Integer.TYPE) {
                descriptor = 73;
            } else if (elementClass == Void.TYPE) {
                descriptor = 86;
            } else if (elementClass == Boolean.TYPE) {
                descriptor = 90;
            } else if (elementClass == Byte.TYPE) {
                descriptor = 66;
            } else if (elementClass == Character.TYPE) {
                descriptor = 67;
            } else if (elementClass == Short.TYPE) {
                descriptor = 83;
            } else if (elementClass == Double.TYPE) {
                descriptor = 68;
            } else if (elementClass == Float.TYPE) {
                descriptor = 70;
            } else if (elementClass == Long.TYPE) {
                descriptor = 74;
            } else {
                throw new AssertionError();
            }
            destination.append((char)descriptor);
        } else {
            destination.append('L').append(Type.getInternalName(elementClass)).append(';');
        }
    }

    public int getSort() {
        return this.sort == 12 ? 10 : this.sort;
    }

    public int getDimensions() {
        int dimensions = 1;
        while (this.descriptorBuffer.charAt(this.descriptorStart + dimensions) == '[') {
            ++dimensions;
        }
        return dimensions;
    }

    public int getSize() {
        switch (this.sort) {
            case 0: {
                return 0;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 9: 
            case 10: 
            case 12: {
                return 1;
            }
            case 7: 
            case 8: {
                return 2;
            }
        }
        throw new AssertionError();
    }

    public int getArgumentsAndReturnSizes() {
        return Type.getArgumentsAndReturnSizes(this.getDescriptor());
    }

    public static int getArgumentsAndReturnSizes(String methodDescriptor) {
        int argumentSize = 1;
        int offset = 1;
        char current = methodDescriptor.charAt(offset);
        while (current != ')') {
            if (current == 'J' || current == 'D') {
                ++offset;
                argumentSize += 2;
            } else {
                while (methodDescriptor.charAt(offset) == '[') {
                    ++offset;
                }
                if (methodDescriptor.charAt(offset++) == 'L') {
                    int semicolon = methodDescriptor.indexOf(59, offset);
                    offset = Math.max(offset, semicolon + 1);
                }
                ++argumentSize;
            }
            current = methodDescriptor.charAt(offset);
        }
        current = methodDescriptor.charAt(offset + 1);
        if (current == 'V') {
            return argumentSize << 2;
        }
        int returnSize = current == 'J' || current == 'D' ? 2 : 1;
        return argumentSize << 2 | returnSize;
    }

    public int hashCode() {
        int hash = 13 * (this.sort == 12 ? 10 : this.sort);
        if (this.sort >= 9) {
            for (int index = this.descriptorStart; index < this.descriptorEnd; ++index) {
                hash = 17 * (hash + this.descriptorBuffer.charAt(index));
            }
        }
        return hash;
    }

    public String toString() {
        return this.getDescriptor();
    }
}

