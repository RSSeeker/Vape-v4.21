package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class MLaunchClassLoader
extends Mapping {
    private MappingField g;
    private MappingField C;
    private MappingField Y;
    public MappingMethod I;
    private MappingField L;
    private static int[] Q;

    public Object getInstance() {
        if (this.g == null) {
            return null;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.g.getObject(this.C.getObject(null));
        }
        return this.g.getObject(null);
    }

    public Map cachedClasses(Object object) {
        return this.Y == null ? null : (Map)this.Y.getObject(object);
    }

    public boolean supportsLegacyClassCache() {
        return this.g != null && this.Y != null && this.L != null;
    }

    public static Set l(MLaunchClassLoader mLaunchClassLoader, Object object) {
        return mLaunchClassLoader.V(object);
    }

    static {
        MLaunchClassLoader.Q(new int[2]);
    }

    public MLaunchClassLoader() {
        this(MLaunchClassLoader.N());
    }

    private MLaunchClassLoader(int[] nArray) {
        super(MappedClasses.uG);
        int[] nArray2 = nArray;
        if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz = MappedClasses.D1;
            boolean bl = false;
            String string = "INSTANCE";
            Class clazz2 = MappedClasses.D1;
            MLaunchClassLoader mLaunchClassLoader = this;
            this.C = mLaunchClassLoader.registerStaticFieldForOwner(clazz2, string, bl, clazz);
            Class clazz3 = MappedClasses.uG;
            boolean bl2 = false;
            String string2 = "classLoader";
            Class clazz4 = MappedClasses.D1;
            MLaunchClassLoader mLaunchClassLoader2 = this;
            this.g = this.registerInstanceFieldForOwner(clazz4, string2, bl2, clazz3);
            Class[] classArray = new Class[]{String.class, Function.class};
            Class<Class> clazz5 = Class.class;
            boolean bl3 = false;
            String string3 = "loadClass";
            MLaunchClassLoader mLaunchClassLoader3 = this;
            this.I = this.Y(string3, bl3, clazz5, classArray);
        } else {
            if (!MLaunchClassLoader.hasDeclaredField(MappedClasses.D1, "classLoader")
                    || !MLaunchClassLoader.hasDeclaredField(MappedClasses.uG, "cachedClasses")
                    || !MLaunchClassLoader.hasDeclaredField(MappedClasses.uG, "invalidClasses")) {
                return;
            }
            Class clazz = MappedClasses.uG;
            boolean bl = false;
            String string = "classLoader";
            Class clazz6 = MappedClasses.D1;
            MLaunchClassLoader mLaunchClassLoader = this;
            this.g = mLaunchClassLoader.registerStaticFieldForOwner(clazz6, string, bl, clazz);
            Class<Map> clazz7 = Map.class;
            boolean bl4 = false;
            String string4 = "cachedClasses";
            MLaunchClassLoader mLaunchClassLoader4 = this;
            this.Y = this.J(string4, bl4, clazz7);
            Class<Set> clazz8 = Set.class;
            boolean bl5 = false;
            String string5 = "invalidClasses";
            MLaunchClassLoader mLaunchClassLoader5 = this;
            this.L = this.J(string5, bl5, clazz8);
        }
    }

    public static void Q(int[] nArray) {
        Q = nArray;
    }

    public static int[] N() {
        return Q;
    }


    private Set V(Object object) {
        return this.L == null ? null : (Set)this.L.getObject(object);
    }

    private static boolean hasDeclaredField(Class<?> ownerClass, String fieldName) {
        if (ownerClass == null) {
            return false;
        }
        try {
            ownerClass.getDeclaredField(fieldName);
            return true;
        }
        catch (NoSuchFieldException ignored) {
            return false;
        }
    }
}

