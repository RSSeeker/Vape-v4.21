package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Optional;

public class MAxisAlignedBB
extends Mapping {
    private MappingMethod Y;
    private MappingMethod h;
    private MappingMethod f;
    private final MappingMethod N;
    public final MappingField n;
    private MappingMethod S;
    public MappingMethod e;
    public final MappingMethod B;
    public final MappingField g;
    private MappingMethod A;
    private MappingMethod x;
    public final MappingField L;
    public final MappingField Q;
    private MappingMethod b;
    public final MappingField s;
    private MappingMethod W;
    private MappingMethod l;
    public final MappingField z;

    public double p(Object object) {
        return this.z.getDouble(object);
    }

    private Object j(Object object, double d, double d2, double d3) {
        return this.f.invokeObject(object, d, d2, d3);
    }


    public static boolean j(MAxisAlignedBB mAxisAlignedBB, Object object, Object object2) {
        return mAxisAlignedBB.n(object, object2);
    }

    public double T(Object object) {
        return this.n.getDouble(object);
    }

    public Object init(double d, double d2, double d3, double d4, double d5, double d6) {
        return this.B.newInstance(d, d2, d3, d4, d5, d6);
    }

    public static boolean e(MAxisAlignedBB mAxisAlignedBB, Object object, Object object2) {
        return mAxisAlignedBB.V(object, object2);
    }

    public static Object K(MAxisAlignedBB mAxisAlignedBB, Object object, Object object2, double[] dArray, Object object3, double d, double d2, double d3) {
        return mAxisAlignedBB.t(object, object2, dArray, object3, d, d2, d3);
    }

    public static Object Z(MAxisAlignedBB mAxisAlignedBB, Object object, double d, double d2, double d3) {
        return mAxisAlignedBB.j(object, d, d2, d3);
    }

    public static Object v(MAxisAlignedBB mAxisAlignedBB, Object object, double d) {
        return mAxisAlignedBB.k(object, d);
    }

    public Object B(Object object, double d, double d2, double d3) {
        return this.N.invokeObject(object, d, d2, d3);
    }

    private Object k(Object object, double d, double d2, double d3) {
        return this.W.invokeObject(object, d, d2, d3);
    }

    private boolean V(Object object, Object object2) {
        return this.e.invokeBoolean(object, object2);
    }

    public MAxisAlignedBB() {
        this(BlockData.W());
    }

    private MAxisAlignedBB(String[] stringArray) {
        super(MappedClasses.uk);
        Class<Double> clazz = Double.TYPE;
        boolean bl = true;
        String string = "minX";
        MAxisAlignedBB mAxisAlignedBB = this;
        this.s = this.J(string, bl, clazz);
        String[] stringArray2 = stringArray;
        Class<Double> clazz2 = Double.TYPE;
        boolean bl2 = true;
        String string2 = "minY";
        MAxisAlignedBB mAxisAlignedBB2 = this;
        this.n = this.J(string2, bl2, clazz2);
        Class<Double> clazz3 = Double.TYPE;
        boolean bl3 = true;
        String string3 = "minZ";
        MAxisAlignedBB mAxisAlignedBB3 = this;
        this.g = this.J(string3, bl3, clazz3);
        Class<Double> clazz4 = Double.TYPE;
        boolean bl4 = true;
        String string4 = "maxX";
        MAxisAlignedBB mAxisAlignedBB4 = this;
        this.Q = this.J(string4, bl4, clazz4);
        Class<Double> clazz5 = Double.TYPE;
        boolean bl5 = true;
        String string5 = "maxY";
        MAxisAlignedBB mAxisAlignedBB5 = this;
        this.L = this.J(string5, bl5, clazz5);
        Class<Double> clazz6 = Double.TYPE;
        boolean bl6 = true;
        String string6 = "maxZ";
        MAxisAlignedBB mAxisAlignedBB6 = this;
        this.z = this.J(string6, bl6, clazz6);
        if (ForgeVersion.MC_1_12_2.d()) {
            Class[] classArray = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
            Class clazz7 = MappedClasses.uk;
            boolean bl7 = Wrapper.isNativeAvailable;
            String string7 = "func_72317_d";
            MAxisAlignedBB mAxisAlignedBB7 = this;
            this.N = this.Y(string7, bl7, clazz7, classArray);
        } else {
            Class[] classArray = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
            Class clazz8 = MappedClasses.uk;
            boolean bl8 = true;
            String string8 = "offset";
            MAxisAlignedBB mAxisAlignedBB8 = this;
            this.N = this.Y(string8, bl8, clazz8, classArray);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_20_6.d()) {
                Class[] classArray = new Class[]{MappedClasses.qP, MappedClasses.qP};
                Class<Optional> clazz9 = Optional.class;
                boolean bl9 = true;
                String string9 = "clip";
                MAxisAlignedBB mAxisAlignedBB9 = this;
                this.l = this.Y(string9, bl9, clazz9, classArray);
                Class[] classArray2 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
                Class clazz10 = MappedClasses.uk;
                boolean bl10 = true;
                String string10 = "inflate";
                MAxisAlignedBB mAxisAlignedBB10 = this;
                this.f = this.Y(string10, bl10, clazz10, classArray2);
                Class[] classArray3 = new Class[]{MappedClasses.qP};
                Class<Boolean> clazz11 = Boolean.TYPE;
                boolean bl11 = true;
                String string11 = "contains";
                MAxisAlignedBB mAxisAlignedBB11 = this;
                this.Y = this.Y(string11, bl11, clazz11, classArray3);
            } else {
                Class[] classArray = new Class[]{MappedClasses.qP, MappedClasses.qP};
                Class<Optional> clazz12 = Optional.class;
                boolean bl12 = Wrapper.isNativeAvailable;
                String string12 = "func_216365_b";
                MAxisAlignedBB mAxisAlignedBB12 = this;
                this.l = this.Y(string12, bl12, clazz12, classArray);
                Class[] classArray4 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
                Class clazz13 = MappedClasses.uk;
                boolean bl13 = Wrapper.isNativeAvailable;
                String string13 = "func_72314_b";
                MAxisAlignedBB mAxisAlignedBB13 = this;
                this.f = this.Y(string13, bl13, clazz13, classArray4);
                Class[] classArray5 = new Class[]{MappedClasses.qP};
                Class<Boolean> clazz14 = Boolean.TYPE;
                boolean bl14 = Wrapper.isNativeAvailable;
                String string14 = "func_72318_a";
                MAxisAlignedBB mAxisAlignedBB14 = this;
                this.Y = this.Y(string14, bl14, clazz14, classArray5);
            }
            Class[] classArray = new Class[]{MappedClasses.uk, MappedClasses.qP, double[].class, MappedClasses.q0, Double.TYPE, Double.TYPE, Double.TYPE};
            Class clazz15 = MappedClasses.q0;
            boolean bl15 = true;
            String string15 = "calcSideHit";
            MAxisAlignedBB mAxisAlignedBB15 = this;
            this.b = this.registerStaticMethod(string15, bl15, clazz15, classArray);
            Class[] classArray6 = new Class[]{Double.TYPE};
            Class clazz16 = MappedClasses.uk;
            boolean bl16 = Wrapper.isNativeAvailable;
            String string16 = "func_186662_g";
            MAxisAlignedBB mAxisAlignedBB16 = this;
            this.x = this.Y(string16, bl16, clazz16, classArray6);
            Class[] classArray7 = new Class[]{MappedClasses.qP};
            Class clazz17 = MappedClasses.uk;
            boolean bl17 = Wrapper.isNativeAvailable;
            String string17 = "func_216361_a";
            MAxisAlignedBB mAxisAlignedBB17 = this;
            this.S = this.Y(string17, bl17, clazz17, classArray7);
            if (ForgeVersion.MC_1_20_6.d()) {
                Class[] classArray8 = new Class[]{MappedClasses.uk};
                Class<Boolean> clazz18 = Boolean.TYPE;
                boolean bl18 = true;
                String string18 = "intersects";
                MAxisAlignedBB mAxisAlignedBB18 = this;
                this.e = this.Y(string18, bl18, clazz18, classArray8);
            } else {
                Class[] classArray9 = new Class[]{MappedClasses.uk};
                Class<Boolean> clazz19 = Boolean.TYPE;
                boolean bl19 = Wrapper.isNativeAvailable;
                String string19 = "func_72326_a";
                MAxisAlignedBB mAxisAlignedBB19 = this;
                this.e = this.Y(string19, bl19, clazz19, classArray9);
            }
        } else {
            Class[] classArray = new Class[]{MappedClasses.qP, MappedClasses.qP};
            Class clazz20 = MappedClasses.DT;
            boolean bl20 = true;
            String string20 = "calculateIntercept";
            MAxisAlignedBB mAxisAlignedBB20 = this;
            this.A = this.Y(string20, bl20, clazz20, classArray);
            Class[] classArray10 = new Class[]{MappedClasses.uk};
            Class<Boolean> clazz21 = Boolean.TYPE;
            boolean bl21 = ForgeVersion.MC_1_12_2.d() ? Wrapper.isNativeAvailable : true;
            String string21 = ForgeVersion.MC_1_12_2.d() ? "func_72326_a" : "intersectsWith";
            MAxisAlignedBB mAxisAlignedBB21 = this;
            this.e = this.Y(string21, bl21, clazz21, classArray10);
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray = new Class[]{};
            Class clazz22 = MappedClasses.uk;
            boolean bl22 = true;
            String string22 = "copy";
            MAxisAlignedBB mAxisAlignedBB22 = this;
            this.h = this.Y(string22, bl22, clazz22, classArray);
            Class[] classArray11 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
            Class clazz23 = MappedClasses.uk;
            boolean bl23 = true;
            String string23 = "getOffsetBoundingBox";
            MAxisAlignedBB mAxisAlignedBB23 = this;
            this.W = this.Y(string23, bl23, clazz23, classArray11);
        }
        Class[] classArray = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE};
        Class<Void> clazz24 = Void.TYPE;
        boolean bl24 = false;
        String string24 = "<init>";
        MAxisAlignedBB mAxisAlignedBB24 = this;
        this.B = this.Y(string24, bl24, clazz24, classArray);
    }

    private Object copy(Object object) {
        return this.h.invokeObject(object, new Object[0]);
    }

    public static Object c(MAxisAlignedBB mAxisAlignedBB, Object object, double d, double d2, double d3) {
        return mAxisAlignedBB.k(object, d, d2, d3);
    }

    public double G(Object object) {
        return this.g.getDouble(object);
    }

    public double z(Object object) {
        return this.s.getDouble(object);
    }

    public static Object Y(MAxisAlignedBB mAxisAlignedBB, Object object) {
        return mAxisAlignedBB.copy(object);
    }

    public double k(Object object) {
        return this.Q.getDouble(object);
    }

    private Object p(Object object, Object object2, Object object3) {
        return this.l.invokeObject(object, object2, object3);
    }

    public static Object a(MAxisAlignedBB mAxisAlignedBB, Object object, Object object2, Object object3) {
        return mAxisAlignedBB.p(object, object2, object3);
    }

    public static Object C(MAxisAlignedBB mAxisAlignedBB, Object object, Object object2) {
        return mAxisAlignedBB.i(object, object2);
    }

    public double L(Object object) {
        return this.L.getDouble(object);
    }

    private boolean n(Object object, Object object2) {
        return this.Y.invokeBoolean(object, object2);
    }

    public Object x(Object object, Object object2, Object object3) {
        return this.A.invokeObject(object, object2, object3);
    }

    private Object k(Object object, double d) {
        return this.x.invokeObject(object, d);
    }

    private Object t(Object object, Object object2, double[] dArray, Object object3, double d, double d2, double d3) {
        return this.b.invokeObject(null, object, object2, dArray, object3, d, d2, d3);
    }

    private Object i(Object object, Object object2) {
        return this.S.invokeObject(object, object2);
    }
}

