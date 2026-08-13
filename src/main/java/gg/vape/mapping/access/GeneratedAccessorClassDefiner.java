package gg.vape.mapping.access;

import com.google.common.collect.MapMaker;
import gg.vape.mapping.access.GeneratedAccessorClassLoader;
import java.util.concurrent.ConcurrentMap;

public class GeneratedAccessorClassDefiner {
    private final ConcurrentMap<ClassLoader, GeneratedAccessorClassLoader> t = new MapMaker().weakKeys().makeMap();
    static final boolean G;
    private static String[] x;
    public static final GeneratedAccessorClassDefiner o;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Class<?> defineGeneratedAccessorClass(ClassLoader classLoader, String string, byte[] byArray) {
        GeneratedAccessorClassLoader generatedAccessorClassLoader = this.t.computeIfAbsent(classLoader, GeneratedAccessorClassLoader::new);
        Object object = generatedAccessorClassLoader.getClassLoadingLock(string);
        synchronized (object) {
            Class clazz = GeneratedAccessorClassLoader.d(generatedAccessorClassLoader, string, byArray);
            if (!G && !clazz.getName().equals(string)) {
                throw new AssertionError();
            }
            return clazz;
        }
    }

    public static void setStringTable(String[] stringArray) {
        x = stringArray;
    }


    public static String[] getStringTable() {
        return x;
    }

    static {
        if (GeneratedAccessorClassDefiner.getStringTable() == null) {
            GeneratedAccessorClassDefiner.setStringTable(new String[2]);
        }
        G = !GeneratedAccessorClassDefiner.class.desiredAssertionStatus();
        o = new GeneratedAccessorClassDefiner();
    }
}

