package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MFreecamPlayerBridge
extends Mapping {
    private MappingField H;
    private MappingField C;
    private MappingField w;
    private MappingField o;
    private MappingField B;
    private MappingField F;
    private MappingField W;
    private MappingField a;
    private MappingField d;
    private MappingField L;

    public static double Y(MFreecamPlayerBridge mFreecamPlayerBridge, Object object) {
        return mFreecamPlayerBridge.E(object);
    }

    private double E(Object object) {
        return this.L.getDouble(object);
    }

    public static double Z(MFreecamPlayerBridge mFreecamPlayerBridge, Object object) {
        return mFreecamPlayerBridge.W(object);
    }

    public static double l(MFreecamPlayerBridge mFreecamPlayerBridge, Object object) {
        return mFreecamPlayerBridge.u(object);
    }

    private void T(Object object, double d) {
        this.B.setDouble(object, d);
    }

    private void r(Object object, double d) {
        this.F.setDouble(object, d);
    }

    public static void c(MFreecamPlayerBridge mFreecamPlayerBridge, Object object, double d) {
        mFreecamPlayerBridge.X(object, d);
    }

    private void X(Object object, double d) {
        this.a.setDouble(object, d);
    }

    public static void A(MFreecamPlayerBridge mFreecamPlayerBridge, Object object, double d) {
        mFreecamPlayerBridge.g(object, d);
    }

    private void v(Object object, double d) {
        this.L.setDouble(object, d);
    }

    public MFreecamPlayerBridge() {
        super(MappedClasses.zT);
        Class<Double> clazz = Double.TYPE;
        boolean bl = true;
        String string = "xCloak";
        MFreecamPlayerBridge mFreecamPlayerBridge = this;
        this.a = this.J(string, bl, clazz);
        Class<Double> clazz2 = Double.TYPE;
        boolean bl2 = true;
        String string2 = "yCloak";
        MFreecamPlayerBridge mFreecamPlayerBridge2 = this;
        this.L = this.J(string2, bl2, clazz2);
        Class<Double> clazz3 = Double.TYPE;
        boolean bl3 = true;
        String string3 = "zCloak";
        MFreecamPlayerBridge mFreecamPlayerBridge3 = this;
        this.d = this.J(string3, bl3, clazz3);
        Class<Double> clazz4 = Double.TYPE;
        boolean bl4 = true;
        String string4 = "xCloakO";
        MFreecamPlayerBridge mFreecamPlayerBridge4 = this;
        this.B = this.J(string4, bl4, clazz4);
        Class<Double> clazz5 = Double.TYPE;
        boolean bl5 = true;
        String string5 = "yCloakO";
        MFreecamPlayerBridge mFreecamPlayerBridge5 = this;
        this.F = this.J(string5, bl5, clazz5);
        Class<Double> clazz6 = Double.TYPE;
        boolean bl6 = true;
        String string6 = "zCloakO";
        MFreecamPlayerBridge mFreecamPlayerBridge6 = this;
        this.H = this.J(string6, bl6, clazz6);
        Class<Float> clazz7 = Float.TYPE;
        boolean bl7 = true;
        String string7 = "bob";
        MFreecamPlayerBridge mFreecamPlayerBridge7 = this;
        this.w = this.J(string7, bl7, clazz7);
        Class<Float> clazz8 = Float.TYPE;
        boolean bl8 = true;
        String string8 = "bobO";
        MFreecamPlayerBridge mFreecamPlayerBridge8 = this;
        this.o = this.J(string8, bl8, clazz8);
        Class<Float> clazz9 = Float.TYPE;
        boolean bl9 = true;
        String string9 = "walkDist";
        MFreecamPlayerBridge mFreecamPlayerBridge9 = this;
        this.W = this.J(string9, bl9, clazz9);
        Class<Float> clazz10 = Float.TYPE;
        boolean bl10 = true;
        String string10 = "walkDistO";
        MFreecamPlayerBridge mFreecamPlayerBridge10 = this;
        this.C = this.J(string10, bl10, clazz10);
    }

    public static void n(MFreecamPlayerBridge mFreecamPlayerBridge, Object object, double d) {
        mFreecamPlayerBridge.v(object, d);
    }

    private double W(Object object) {
        return this.H.getDouble(object);
    }

    private double n(Object object) {
        return this.a.getDouble(object);
    }

    private double M(Object object) {
        return this.B.getDouble(object);
    }

    private double u(Object object) {
        return this.d.getDouble(object);
    }

    public static void d(MFreecamPlayerBridge mFreecamPlayerBridge, Object object, double d) {
        mFreecamPlayerBridge.z(object, d);
    }

    public static void y(MFreecamPlayerBridge mFreecamPlayerBridge, Object object, double d) {
        mFreecamPlayerBridge.T(object, d);
    }

    private void g(Object object, double d) {
        this.d.setDouble(object, d);
    }

    public static double C(MFreecamPlayerBridge mFreecamPlayerBridge, Object object) {
        return mFreecamPlayerBridge.M(object);
    }

    public static double m(MFreecamPlayerBridge mFreecamPlayerBridge, Object object) {
        return mFreecamPlayerBridge.I(object);
    }

    private void V(Object object) {
        this.w.setFloat(object, 0.0f);
        this.o.setFloat(object, 0.0f);
        this.W.setFloat(object, 0.0f);
        this.C.setFloat(object, 0.0f);
    }

    public static double T(MFreecamPlayerBridge mFreecamPlayerBridge, Object object) {
        return mFreecamPlayerBridge.n(object);
    }

    public static void m$src$V$n8prsk(MFreecamPlayerBridge mFreecamPlayerBridge, Object object) {
        mFreecamPlayerBridge.V(object);
    }

    private double I(Object object) {
        return this.F.getDouble(object);
    }

    public static void E(MFreecamPlayerBridge mFreecamPlayerBridge, Object object, double d) {
        mFreecamPlayerBridge.r(object, d);
    }

    private void z(Object object, double d) {
        this.H.setDouble(object, d);
    }
}

