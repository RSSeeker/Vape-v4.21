package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class MIAttributeInstance
extends Mapping {
    private MappingMethod g;
    private final MappingMethod E;
    private final MappingMethod H;
    private MappingMethod Z;
    private MappingMethod Q;
    private static String Y;
    private final MappingMethod o;

    public static Set d(MIAttributeInstance mIAttributeInstance, Object object) {
        return mIAttributeInstance.q(object);
    }

    private void V(Object object, Object object2) {
        this.o.invokeVoid(object, object2);
    }

    private Object o(Object object, UUID uUID) {
        return this.E.invokeObject(object, uUID);
    }

    public static void V(MIAttributeInstance mIAttributeInstance, Object object) {
        mIAttributeInstance.j(object);
    }

    private double U(Object object) {
        return this.Z.invokeDouble(object, new Object[0]);
    }


    public static Collection h(MIAttributeInstance mIAttributeInstance, Object object) {
        return mIAttributeInstance.y(object);
    }

    static {
        MIAttributeInstance.k("XSh99");
    }

    public static Object x(MIAttributeInstance mIAttributeInstance, Object object, UUID uUID) {
        return mIAttributeInstance.o(object, uUID);
    }

    public static String B() {
        return Y;
    }

    public static void W(MIAttributeInstance mIAttributeInstance, Object object, double d) {
        mIAttributeInstance.V(object, d);
    }

    public static void k(String string) {
        Y = string;
    }

    public static void b(MIAttributeInstance mIAttributeInstance, Object object, Object object2) {
        mIAttributeInstance.V(object, object2);
    }

    private void j(Object object) {
        this.Q.invokeVoidNoArgs(object);
    }

    private void V(Object object, double d) {
        this.H.invokeVoid(object, d);
    }

    private Set q(Object object) {
        return (Set)this.g.invokeObject(object, new Object[0]);
    }

    public static double I(MIAttributeInstance mIAttributeInstance, Object object) {
        return mIAttributeInstance.U(object);
    }

    public MIAttributeInstance() {
        this(MIAttributeInstance.B());
    }

    private MIAttributeInstance(String string) {
        super(MappedClasses.FJ);
        Class[] classArray = new Class[]{UUID.class};
        Class clazz = MappedClasses.z_;
        boolean bl = true;
        String string2 = "getModifier";
        MIAttributeInstance mIAttributeInstance = this;
        this.E = this.Y(string2, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{Double.TYPE};
        Class<Void> clazz2 = Void.TYPE;
        boolean bl2 = true;
        String string3 = "setBaseValue";
        MIAttributeInstance mIAttributeInstance2 = this;
        this.H = this.Y(string3, bl2, clazz2, classArray2);
        if (string != null) {
            Class[] classArray3 = new Class[]{MappedClasses.z_};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl3 = true;
            String string4 = "applyModifier";
            MIAttributeInstance mIAttributeInstance3 = this;
            this.o = this.Y(string4, bl3, clazz3, classArray3);
            if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray4 = new Class[]{};
                Class<Double> clazz4 = Double.TYPE;
                boolean bl4 = true;
                String string5 = "getValue";
                MIAttributeInstance mIAttributeInstance4 = this;
                this.Z = this.Y(string5, bl4, clazz4, classArray4);
                if (ForgeVersion.MC_1_20_6.d()) {
                    this.g = this.Y("getModifiers", true, Set.class, new Class[]{});
                    this.Q = this.Y("removeModifiers", true, Void.TYPE, new Class[]{});
                } else {
                    Class[] classArray5 = new Class[]{};
                    Class<Set> clazz5 = Set.class;
                    boolean bl5 = Wrapper.isNativeAvailable;
                    String string6 = "func_225505_c_";
                    MIAttributeInstance mIAttributeInstance5 = this;
                    this.g = this.Y(string6, bl5, clazz5, classArray5);
                    Class[] classArray6 = new Class[]{};
                    Class<Void> clazz6 = Void.TYPE;
                    boolean bl6 = Wrapper.isNativeAvailable;
                    String string7 = "func_142049_d";
                    MIAttributeInstance mIAttributeInstance6 = this;
                    this.Q = this.Y(string7, bl6, clazz6, classArray6);
                }
            } else {
                Class[] classArray7 = new Class[]{};
                Class<Double> clazz7 = Double.TYPE;
                boolean bl7 = true;
                String string8 = "getAttributeValue";
                MIAttributeInstance mIAttributeInstance7 = this;
                this.Z = this.Y(string8, bl7, clazz7, classArray7);
                Class[] classArray8 = new Class[]{};
                Class<Collection> clazz8 = Collection.class;
                boolean bl8 = Wrapper.isNativeAvailable;
                String string9 = "func_111122_c";
                MIAttributeInstance mIAttributeInstance8 = this;
                this.g = this.Y(string9, bl8, clazz8, classArray8);
                Class[] classArray9 = new Class[]{};
                Class<Void> clazz9 = Void.TYPE;
                boolean bl9 = true;
                String string10 = "removeAllModifiers";
                MIAttributeInstance mIAttributeInstance9 = this;
                this.Q = this.Y(string10, bl9, clazz9, classArray9);
            }
            return;
        }
        Class[] classArray10 = new Class[]{MappedClasses.z_};
        Class<Void> clazz10 = Void.TYPE;
        boolean bl10 = true;
        String string11 = "applyModifier";
        MIAttributeInstance mIAttributeInstance10 = this;
        this.o = this.Y(string11, bl10, clazz10, classArray10);
        Class[] classArray11 = new Class[]{};
        Class<Void> clazz11 = Void.TYPE;
        boolean bl11 = true;
        String string12 = "removeAllModifiers";
        MIAttributeInstance mIAttributeInstance11 = this;
        this.Q = this.Y(string12, bl11, clazz11, classArray11);
    }

    private Collection y(Object object) {
        return (Collection)this.g.invokeObject(object, new Object[0]);
    }
}

