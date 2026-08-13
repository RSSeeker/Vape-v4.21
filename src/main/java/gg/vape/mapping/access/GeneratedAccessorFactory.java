package gg.vape.mapping.access;

import gg.vape.asm.helper.DescUtils;
import gg.vape.event.GeneratedEventHandlerInvokerMarker;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.access.FieldAccessor;
import gg.vape.mapping.access.GeneratedAccessorClassDefiner;
import gg.vape.mapping.access.MethodInvoker;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

public class GeneratedAccessorFactory {
    public static final String l;
    private static final String Q;
    public static final String s;
    private static final Map<Method, Class<? extends MethodInvoker>> D;
    private static Method G;
    private static Method Y;
    private static final Map<Field, Class<? extends FieldAccessor>> M;
    private static final String x;
    private static AtomicInteger p;
    private static Method a;
    private static final String S;
    private static Method c;

    public static Class<? extends MethodInvoker> Y(Mapping mapping, MappingMethod mappingMethod) {
        return GeneratedAccessorFactory.A(mapping.getMappedClass(), mappingMethod);
    }

    public static Class<? extends FieldAccessor> U(Class<?> clazz, Field field) {
        Class<? extends FieldAccessor> clazz2 = M.get(field);
        if (clazz2 != null) {
            return clazz2;
        }
        GeneratedAccessorFactory.s();
        String string = GeneratedAccessorFactory.x(clazz, field);
        ClassWriter classWriter = new ClassWriter(3);
        classWriter.visit(52, 1, string.replace('.', '/'), null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(MethodInvoker.class)});
        GeneratorAdapter generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, "<init>", "()V", null, null), 1, "<init>", "()V");
        generatorAdapter.loadThis();
        generatorAdapter.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
        generatorAdapter.returnValue();
        generatorAdapter.endMethod();
        generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, Y.getName(), "(Ljava/lang/Object;)Ljava/lang/Object;", null, null), 1, Y.getName(), "(Ljava/lang/Object;)Ljava/lang/Object;");
        generatorAdapter.visitCode();
        generatorAdapter.visitVarInsn(25, 1);
        generatorAdapter.visitTypeInsn(192, Type.getInternalName(clazz));
        generatorAdapter.visitFieldInsn(Modifier.isStatic(field.getModifiers()) ? 178 : 180, Type.getInternalName(clazz), field.getName(), DescUtils.getDescriptor(field.getType()));
        if (Boolean.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
        } else if (Byte.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
        } else if (Character.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
        } else if (Short.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
        } else if (Integer.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        } else if (Long.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
        } else if (Float.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
        } else if (Double.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
        } else if (DescUtils.getKnownDescriptor(field.getType()) == null) {
            generatorAdapter.visitTypeInsn(192, field.getType().getName());
        }
        generatorAdapter.visitInsn(176);
        generatorAdapter.visitMaxs(7, 3);
        generatorAdapter.visitEnd();
        classWriter.visitEnd();
        byte[] byArray = classWriter.toByteArray();
        try {
            Class<? extends FieldAccessor> clazz3 = GeneratedAccessorClassDefiner.o.defineGeneratedAccessorClass(GeneratedAccessorFactory.class.getClassLoader(), string, byArray).asSubclass(FieldAccessor.class);
            M.put(field, clazz3);
            return clazz3;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Class<? extends GeneratedEventHandlerInvokerMarker> N(Class<?> clazz, Method method) {
        Class<?> parameterType;
        GeneratedAccessorFactory.s();
        String string = GeneratedAccessorFactory.x(clazz, method);
        ClassWriter classWriter = new ClassWriter(3);
        classWriter.visit(52, 1, string.replace('.', '/'), null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(GeneratedEventHandlerInvokerMarker.class)});
        GeneratorAdapter generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, "<init>", "()V", null, null), 1, "<init>", "()V");
        generatorAdapter.loadThis();
        generatorAdapter.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
        generatorAdapter.returnValue();
        generatorAdapter.endMethod();
        generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, G.getName(), "(Ljava/lang/Object;Ljava/lang/Object;)V", null, null), 1, G.getName(), "(Ljava/lang/Object;Ljava/lang/Object;)V");
        generatorAdapter.visitCode();
        generatorAdapter.visitVarInsn(25, 1);
        generatorAdapter.visitTypeInsn(192, Type.getInternalName(clazz));
        if (method.getParameters().length > 0) {
            for (int i = 0; i < method.getParameters().length; ++i) {
                parameterType = method.getParameterTypes()[i];
                generatorAdapter.visitVarInsn(25, 2);
                if (Boolean.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Boolean");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z", false);
                    continue;
                }
                if (Byte.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Byte");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B", false);
                    continue;
                }
                if (Character.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Character");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Character", "charValue", "()C", false);
                    continue;
                }
                if (Short.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Short");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S", false);
                    continue;
                }
                if (Integer.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Integer");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I", false);
                    continue;
                }
                if (Long.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Long");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Long", "longValue", "()J", false);
                    continue;
                }
                if (Float.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Float");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F", false);
                    continue;
                }
                if (Double.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Double");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D", false);
                    continue;
                }
                generatorAdapter.visitTypeInsn(192, Type.getInternalName(parameterType));
            }
        }
        generatorAdapter.visitMethodInsn(Modifier.isStatic(method.getModifiers()) ? 184 : (clazz.isInterface() ? 185 : 182), Type.getInternalName(clazz), method.getName(), DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()), clazz.isInterface());
        generatorAdapter.visitInsn(177);
        generatorAdapter.endMethod();
        classWriter.visitEnd();
        byte[] byArray = classWriter.toByteArray();
        try {
            return GeneratedAccessorClassDefiner.o.defineGeneratedAccessorClass(GeneratedAccessorFactory.class.getClassLoader(), string, byArray).asSubclass(GeneratedEventHandlerInvokerMarker.class);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    static {
        Q = "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;";
        l = "(Ljava/lang/Object;)Ljava/lang/Object;";
        s = "(Ljava/lang/Object;Ljava/lang/Object;)V";
        S = "(Ljava/lang/Object;Ljava/lang/Object;)V";
        x = "(Ljava/lang/Object;)Ljava/lang/Object;";
        p = new AtomicInteger(0);
        D = new LinkedHashMap<Method, Class<? extends MethodInvoker>>();
        M = new LinkedHashMap<Field, Class<? extends FieldAccessor>>();
    }

    public static Class<? extends MethodInvoker> A(Class clazz, MappingMethod mappingMethod) {
        if (mappingMethod.hasResolutionFailed()) {
            return null;
        }
        try {
            try {
                Method method = clazz.getMethod(mappingMethod.getResolvedName(), mappingMethod.getResolvedParameterTypes());
                return GeneratedAccessorFactory.I(clazz, method);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                return null;
            }
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static void i(String string, byte[] byArray) {
    }

    public static Class<? extends FieldAccessor> z(Mapping mapping, MappingField mappingField) {
        if (mappingField.hasResolutionFailed()) {
            return null;
        }
        try {
            return GeneratedAccessorFactory.U(mapping.getMappedClass(), mappingField.findReflectedField());
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static Method s() {
        if (c == null) {
            for (Method method : MethodInvoker.class.getDeclaredMethods()) {
                if (!"(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;".equals(DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()))) continue;
                c = method;
                break;
            }
            if (c == null) {
                // empty if block
            }
            for (Method method : GeneratedEventHandlerInvokerMarker.class.getDeclaredMethods()) {
                if (!"(Ljava/lang/Object;Ljava/lang/Object;)V".equals(DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()))) continue;
                G = method;
                break;
            }
            if (G == null) {
                // empty if block
            }
        }
        if (Y == null || a == null) {
            for (Method method : FieldAccessor.class.getDeclaredMethods()) {
                if ("(Ljava/lang/Object;)Ljava/lang/Object;".equals(DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()))) {
                    Y = method;
                }
                if (!"(Ljava/lang/Object;Ljava/lang/Object;)V".equals(DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()))) continue;
                a = method;
            }
            if (Y == null) {
                // empty if block
            }
            if (a == null) {
                // empty if block
            }
        }
        return c;
    }

    public static Class<? extends MethodInvoker> I(Class<?> clazz, Method method) {
        Class<?> clazz2;
        Class<? extends MethodInvoker> clazz3 = D.get(method);
        if (clazz3 != null) {
            return clazz3;
        }
        GeneratedAccessorFactory.s();
        String string = GeneratedAccessorFactory.x(clazz, method);
        ClassWriter classWriter = new ClassWriter(3);
        classWriter.visit(52, 1, string.replace('.', '/'), null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(MethodInvoker.class)});
        GeneratorAdapter generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, "<init>", "()V", null, null), 1, "<init>", "()V");
        generatorAdapter.loadThis();
        generatorAdapter.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
        generatorAdapter.returnValue();
        generatorAdapter.endMethod();
        generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, c.getName(), "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", null, null), 1, c.getName(), "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;");
        generatorAdapter.visitCode();
        generatorAdapter.visitVarInsn(25, 1);
        generatorAdapter.visitTypeInsn(192, Type.getInternalName(clazz));
        for (int i = 0; i < method.getParameters().length; ++i) {
            clazz2 = method.getParameterTypes()[i];
            generatorAdapter.visitVarInsn(25, 2);
            generatorAdapter.visitIntInsn(16, i);
            generatorAdapter.visitInsn(50);
            if (Boolean.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Boolean");
                generatorAdapter.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z", false);
                continue;
            }
            if (Byte.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Byte");
                generatorAdapter.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B", false);
                continue;
            }
            if (Character.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Character");
                generatorAdapter.visitMethodInsn(182, "java/lang/Character", "charValue", "()C", false);
                continue;
            }
            if (Short.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Short");
                generatorAdapter.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S", false);
                continue;
            }
            if (Integer.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Integer");
                generatorAdapter.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I", false);
                continue;
            }
            if (Long.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Long");
                generatorAdapter.visitMethodInsn(182, "java/lang/Long", "longValue", "()J", false);
                continue;
            }
            if (Float.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Float");
                generatorAdapter.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F", false);
                continue;
            }
            if (Double.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Double");
                generatorAdapter.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D", false);
                continue;
            }
            generatorAdapter.visitTypeInsn(192, Type.getInternalName(clazz2));
        }
        generatorAdapter.visitMethodInsn(Modifier.isStatic(method.getModifiers()) ? 184 : (clazz.isInterface() ? 185 : 182), Type.getInternalName(clazz), method.getName(), DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()), clazz.isInterface());
        if (method.getReturnType() == Void.TYPE) {
            generatorAdapter.visitInsn(1);
            generatorAdapter.visitInsn(176);
        } else {
            if (Boolean.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
            } else if (Byte.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
            } else if (Character.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
            } else if (Short.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
            } else if (Integer.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            } else if (Long.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
            } else if (Float.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
            } else if (Double.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
            } else if (DescUtils.getKnownDescriptor(method.getReturnType()) == null) {
                // empty if block
            }
            generatorAdapter.visitInsn(176);
        }
        generatorAdapter.endMethod();
        classWriter.visitEnd();
        byte[] byArray = classWriter.toByteArray();
        try {
            Class<? extends MethodInvoker> generatedClass = GeneratedAccessorClassDefiner.o.defineGeneratedAccessorClass(GeneratedAccessorFactory.class.getClassLoader(), string, byArray).asSubclass(MethodInvoker.class);
            D.put(method, generatedClass);
            return generatedClass;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static String x(Class<?> clazz, Member member) {
        int n = p.incrementAndGet();
        return "aaa_" + n;
    }
}
