package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MModelBiped
extends Mapping {
    private final MappingField c;
    private final MappingField r;
    private final MappingField A;
    private final MappingField n;
    private final MappingField I;
    private final MappingField p;

    public MModelBiped() {
        super(MappedClasses.zV);
        Class clazz = MappedClasses.Yd;
        boolean bl = true;
        String string = "bipedHead";
        MModelBiped mModelBiped = this;
        this.n = this.J(string, bl, clazz);
        Class clazz2 = MappedClasses.Yd;
        boolean bl2 = true;
        String string2 = "bipedHeadwear";
        MModelBiped mModelBiped2 = this;
        this.r = this.J(string2, bl2, clazz2);
        Class clazz3 = MappedClasses.Yd;
        boolean bl3 = true;
        String string3 = "bipedRightArm";
        MModelBiped mModelBiped3 = this;
        this.p = this.J(string3, bl3, clazz3);
        Class clazz4 = MappedClasses.Yd;
        boolean bl4 = true;
        String string4 = "bipedLeftArm";
        MModelBiped mModelBiped4 = this;
        this.A = this.J(string4, bl4, clazz4);
        Class clazz5 = MappedClasses.Yd;
        boolean bl5 = true;
        String string5 = "bipedRightLeg";
        MModelBiped mModelBiped5 = this;
        this.c = this.J(string5, bl5, clazz5);
        Class clazz6 = MappedClasses.Yd;
        boolean bl6 = true;
        String string6 = "bipedLeftLeg";
        MModelBiped mModelBiped6 = this;
        this.I = this.J(string6, bl6, clazz6);
    }

    private Object L(Object object) {
        return this.r.getObject(object);
    }

    public static Object k(MModelBiped mModelBiped, Object object) {
        return mModelBiped.a(object);
    }

    public static Object R(MModelBiped mModelBiped, Object object) {
        return mModelBiped.U(object);
    }

    public static Object j(MModelBiped mModelBiped, Object object) {
        return mModelBiped.V(object);
    }

    private Object a(Object object) {
        return this.I.getObject(object);
    }

    public static Object J(MModelBiped mModelBiped, Object object) {
        return mModelBiped.g(object);
    }

    private Object U(Object object) {
        return this.p.getObject(object);
    }

    private Object V(Object object) {
        return this.A.getObject(object);
    }

    private Object g(Object object) {
        return this.c.getObject(object);
    }

    public static Object x(MModelBiped mModelBiped, Object object) {
        return mModelBiped.L(object);
    }

    public static Object a(MModelBiped mModelBiped, Object object) {
        return mModelBiped.D(object);
    }

    private Object D(Object object) {
        return this.n.getObject(object);
    }
}

