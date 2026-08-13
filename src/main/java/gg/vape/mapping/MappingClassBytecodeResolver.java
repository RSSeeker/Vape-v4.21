package gg.vape.mapping;

import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.runtime.ClassBytecodeCache;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MappingClassBytecodeResolver {
    private static final String b = ".class";
    private final Set<ClassLoader> k;
    private final Map<String, Class<?>> y = new HashMap();

    private Class<?> M(String string) {
        try {
            Object[] objectArray = MappedClasses.x();
            if (objectArray == null) {
                return null;
            }
            for (Object object : objectArray) {
                Class clazz;
                if (!(object instanceof Class) || !string.equals((clazz = (Class)object).getName())) continue;
                return clazz;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return null;
    }

    public synchronized void O(ClassLoader classLoader) {
        if (classLoader != null) {
            this.k.add(classLoader);
        }
    }

    private Class<?> c(String string) {
        Class<?> clazz = this.y.get(string);
        if (clazz != null) {
            return clazz;
        }
        Class<?> clazz2 = this.y.get(this.R(string));
        if (clazz2 != null) {
            return clazz2;
        }
        Class<?> clazz3 = this.M(string);
        if (clazz3 != null) {
            return clazz3;
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> clazz4 = this.y(string, classLoader);
        if (clazz4 != null) {
            return clazz4;
        }
        for (ClassLoader classLoader2 : this.k) {
            Class<?> clazz5 = this.y(string, classLoader2);
            if (clazz5 == null) continue;
            return clazz5;
        }
        return this.y(string, JavassistMappingTask.class.getClassLoader());
    }

    public MappingClassBytecodeResolver() {
        this.k = new HashSet<ClassLoader>();
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    private String R(String string) {
        int n = string.lastIndexOf(46);
        if (n < 0) {
            return string;
        }
        return string.substring(0, n) + "$" + string.substring(n + 1);
    }

    private Class<?> y(String string, ClassLoader classLoader) {
        if (classLoader == null) {
            return null;
        }
        try {
            return Class.forName(string, false, classLoader);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public synchronized byte[] y(String string) {
        Class<?> clazz = this.c(string);
        if (clazz == null) {
            return null;
        }
        byte[] byArray = ClassBytecodeCache.getClassBytecode(clazz, true);
        if (byArray == null) {
            byArray = this.I(clazz);
        }
        return byArray;
    }

    public synchronized void O(Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        if (clazz.isArray()) {
            this.O(clazz.getComponentType());
            return;
        }
        this.y.put(clazz.getName(), clazz);
        if (clazz.getCanonicalName() != null) {
            this.y.put(clazz.getCanonicalName(), clazz);
        }
        if (clazz.getTypeName() != null) {
            this.y.put(clazz.getTypeName(), clazz);
        }
        for (Class<?> clazz2 : clazz.getInterfaces()) {
            this.O(clazz2);
        }
        Class<?> clazz3 = clazz.getSuperclass();
        if (clazz3 != null && clazz3 != Object.class) {
            this.O(clazz3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private byte[] I(Class<?> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        if (classLoader == null) {
            return null;
        }
        String string = clazz.getName().replace('.', '/') + b;
        try {
            byte[] byArray2;
            InputStream inputStream = classLoader.getResourceAsStream(string);
            Throwable throwable = null;
            try {
                int n;
                if (inputStream == null) {
                    return null;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] byArray = new byte[4096];
                while ((n = inputStream.read(byArray)) != -1) {
                    byteArrayOutputStream.write(byArray, 0, n);
                }
                byArray2 = byteArrayOutputStream.toByteArray();
            }
            catch (Throwable throwable2) {
                try {
                    throwable = throwable2;
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    if (inputStream == null) throw throwable3;
                    if (throwable == null) {
                        inputStream.close();
                        throw throwable3;
                    }
                    try {
                        inputStream.close();
                        throw throwable3;
                    }
                    catch (Throwable throwable4) {
                        throwable.addSuppressed(throwable4);
                        throw throwable3;
                    }
                }
            }
            inputStream.close();
            return byArray2;
        }
        catch (IOException iOException) {
            return null;
        }
    }
}
