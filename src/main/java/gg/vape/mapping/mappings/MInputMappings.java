package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MInputMappings
extends Mapping {
    private final MappingField H;
    private MappingMethod J;
    private final MappingField u;
    private MappingField U;
    private MappingMethod L;
    private MappingField e;
    private MappingField D;
    private MappingMethod M;
    private MappingMethod r;

    public static Object k(MInputMappings mInputMappings, int n, int n2) {
        return mInputMappings.z(n, n2);
    }

    public static void F$src$V$13evi63(MInputMappings mInputMappings, Object object) {
        mInputMappings.p(object);
    }

    private void J(Object object) {
        this.M.invokeVoidNoArgs(object);
    }

    private int k(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return (int)this.u.getDouble(object);
        }
        return this.u.getInt(object);
    }

    public static boolean w(MInputMappings mInputMappings, Object object) {
        return mInputMappings.l(object);
    }

    public static int F(MInputMappings mInputMappings, Object object) {
        return mInputMappings.k(object);
    }

    private boolean l(Object object) {
        return this.e.getBoolean(object);
    }

    private Object z(int n, int n2) {
        return this.J.newInstance(n, n2);
    }

    public static int T(MInputMappings mInputMappings, Object object) {
        return mInputMappings.Q(object);
    }

    public static double g(MInputMappings mInputMappings, Object object) {
        return mInputMappings.D(object);
    }

    public MInputMappings() {
        this(BlockData.W());
    }

    private MInputMappings(String[] stringArray) {
        super(MappedClasses.Ys);
        if (stringArray != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                Class<Double> clazz = Double.TYPE;
                boolean bl = true;
                String string = "xVelocity";
                MInputMappings mInputMappings = this;
                this.H = mInputMappings.J(string, bl, clazz);
                Class<Double> clazz2 = Double.TYPE;
                boolean bl2 = true;
                String string2 = "yVelocity";
                MInputMappings mInputMappings2 = this;
                this.u = this.J(string2, bl2, clazz2);
                Class<Double> clazz3 = Double.TYPE;
                boolean bl3 = true;
                String string3 = "mouseX";
                MInputMappings mInputMappings3 = this;
                this.U = this.J(string3, bl3, clazz3);
                Class<Double> clazz4 = Double.TYPE;
                boolean bl4 = true;
                String string4 = "mouseY";
                MInputMappings mInputMappings4 = this;
                this.D = this.J(string4, bl4, clazz4);
                Class<Boolean> clazz5 = Boolean.TYPE;
                boolean bl5 = true;
                String string5 = "mouseGrabbed";
                MInputMappings mInputMappings5 = this;
                this.e = this.J(string5, bl5, clazz5);
                Class[] classArray = new Class[]{};
                Class<Void> clazz6 = Void.TYPE;
                boolean bl6 = true;
                String string6 = "ungrabMouse";
                MInputMappings mInputMappings6 = this;
                this.r = this.Y(string6, bl6, clazz6, classArray);
                Class[] classArray2 = new Class[]{};
                Class<Void> clazz7 = Void.TYPE;
                boolean bl7 = true;
                String string7 = "grabMouse";
                MInputMappings mInputMappings7 = this;
                this.M = this.Y(string7, bl7, clazz7, classArray2);
                if (ForgeVersion.MC_1_21_10.d()) {
                    Class[] classArray3 = new Class[]{Integer.TYPE, Integer.TYPE};
                    Class<Void> clazz8 = Void.TYPE;
                    boolean bl8 = false;
                    String string8 = "<init>";
                    Class clazz9 = MappedClasses.Fu;
                    MInputMappings mInputMappings8 = this;
                    this.J = this.registerInstanceMethodForOwner(clazz9, string8, bl8, clazz8, classArray3);
                    Class[] classArray4 = new Class[]{Long.TYPE, MappedClasses.Fu, Integer.TYPE};
                    Class<Void> clazz10 = Void.TYPE;
                    boolean bl9 = true;
                    String string9 = "onButton";
                    MInputMappings mInputMappings9 = this;
                    this.L = this.Y(string9, bl9, clazz10, classArray4);
                } else if (ForgeVersion.MC_1_21_4.d()) {
                    Class[] classArray5 = new Class[]{Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
                    Class<Void> clazz11 = Void.TYPE;
                    boolean bl10 = true;
                    String string10 = "onPress";
                    MInputMappings mInputMappings10 = this;
                    this.L = this.Y(string10, bl10, clazz11, classArray5);
                }
            } else {
                Class<Integer> clazz = Integer.TYPE;
                boolean bl = true;
                String string = "deltaX";
                MInputMappings mInputMappings = this;
                this.H = mInputMappings.J(string, bl, clazz);
                Class<Integer> clazz12 = Integer.TYPE;
                boolean bl11 = true;
                String string11 = "deltaY";
                MInputMappings mInputMappings11 = this;
                this.u = this.J(string11, bl11, clazz12);
            }
            return;
        }
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string = "deltaY";
        MInputMappings mInputMappings = this;
        this.u = mInputMappings.J(string, bl, clazz);
        this.H = null;
    }

    public static double B(MInputMappings mInputMappings, Object object) {
        return mInputMappings.a(object);
    }

    private void l(Object object, long l, int n, boolean bl) {
        int n2;
        int n3 = n2 = bl ? 1 : 0;
        if (ForgeVersion.MC_1_21_10.d()) {
            this.L.invokeVoid(object, l, this.z(n, 0), n2);
        } else {
            this.L.invokeVoid(object, l, n, n2, 0);
        }
    }

    private void p(Object object) {
        this.r.invokeVoidNoArgs(object);
    }

    public static void d(MInputMappings mInputMappings, Object object, long l, int n, boolean bl) {
        mInputMappings.l(object, l, n, bl);
    }


    private double a(Object object) {
        return this.D.getDouble(object);
    }

    private int Q(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return (int)this.H.getDouble(object);
        }
        return this.H.getInt(object);
    }

    private double D(Object object) {
        return this.U.getDouble(object);
    }

    public static void V(MInputMappings mInputMappings, Object object) {
        mInputMappings.J(object);
    }
}

