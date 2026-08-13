package gg.vape.mapping.access;

public class GeneratedAccessorClassLoader
extends ClassLoader {
    public GeneratedAccessorClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public Object getClassLoadingLock(String string) {
        return super.getClassLoadingLock(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Class<?> Z(String string, byte[] byArray) {
        Object object;
        Object object2;
        Object object3 = object2 = (object = this.getClassLoadingLock(string));
        synchronized (object3) {
            Class<?> clazz = this.defineClass(string, byArray, 0, byArray.length);
            this.resolveClass(clazz);
            return clazz;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean X(String string) {
        Object object;
        Object object2;
        Object object3 = object2 = (object = this.getClassLoadingLock(string));
        synchronized (object3) {
            try {
                Class.forName(string);
                return true;
            }
            catch (ClassNotFoundException classNotFoundException) {
                return false;
            }
        }
    }

    public static Class d(GeneratedAccessorClassLoader generatedAccessorClassLoader, String string, byte[] byArray) {
        return generatedAccessorClassLoader.Z(string, byArray);
    }

    static {
        ClassLoader.registerAsParallelCapable();
    }
}

