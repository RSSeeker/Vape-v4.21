package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MRenderStateBridge
extends Mapping {
    private MappingField z;
    private static final String c;
    private MappingField q;
    private MappingField h;
    private MappingField D;
    private static int a;

    private double e(Object object) {
        return this.D.getDouble(object);
    }


    private double Z(Object object) {
        return this.h.getDouble(object);
    }

    public Object u(Object object) {
        return this.z.getObject(object);
    }

    public static double Z(MRenderStateBridge mRenderStateBridge, Object object) {
        return mRenderStateBridge.e(object);
    }

    public static int h() {
        int n = MRenderStateBridge.e();
        return 57;
    }

    static {
        MRenderStateBridge.f(0);
        c = "nameTag";
    }

    public static void f(int n) {
        a = n;
    }

    private double M(Object object) {
        return this.q.getDouble(object);
    }

    public static double Y(MRenderStateBridge mRenderStateBridge, Object object) {
        return mRenderStateBridge.M(object);
    }

    public void q(Object object, Object object2) {
        this.z.setObject(object, object2);
    }

    public static int e() {
        return a;
    }

    public MRenderStateBridge() {
        this(MRenderStateBridge.e());
    }

    private MRenderStateBridge(int n) {
        super(MappedClasses.qX);
        Class<Double> clazz = Double.TYPE;
        boolean bl = true;
        String string = "x";
        MRenderStateBridge mRenderStateBridge = this;
        this.D = this.J(string, bl, clazz);
        Class<Double> clazz2 = Double.TYPE;
        boolean bl2 = true;
        String string2 = "y";
        MRenderStateBridge mRenderStateBridge2 = this;
        this.q = this.J(string2, bl2, clazz2);
        Class<Double> clazz3 = Double.TYPE;
        boolean bl3 = true;
        String string3 = "z";
        MRenderStateBridge mRenderStateBridge3 = this;
        this.h = this.J(string3, bl3, clazz3);
        int n2 = n;
        Class clazz4 = MappedClasses.Yr;
        boolean bl4 = true;
        String string4 = c;
        MRenderStateBridge mRenderStateBridge4 = this;
        this.z = this.J(string4, bl4, clazz4);
    }

    public static double g(MRenderStateBridge mRenderStateBridge, Object object) {
        return mRenderStateBridge.Z(object);
    }
}

