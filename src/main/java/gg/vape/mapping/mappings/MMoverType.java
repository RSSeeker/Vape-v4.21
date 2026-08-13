package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;

public class MMoverType
extends Mapping {
    private final MappingField W;
    private final MappingField x;
    private final MappingField M;
    private final MappingField N;
    private final MappingField J;

    public Object k() {
        return this.M.getObject(null);
    }

    public MMoverType() {
        super(MappedClasses.k);
        Class clazz = MappedClasses.k;
        boolean bl = Wrapper.isNativeAvailable;
        String string = "SELF";
        MMoverType mMoverType = this;
        this.x = this.registerStaticField(string, bl, clazz);
        Class clazz2 = MappedClasses.k;
        boolean bl2 = Wrapper.isNativeAvailable;
        String string2 = "PLAYER";
        MMoverType mMoverType2 = this;
        this.W = this.registerStaticField(string2, bl2, clazz2);
        Class clazz3 = MappedClasses.k;
        boolean bl3 = Wrapper.isNativeAvailable;
        String string3 = "PISTON";
        MMoverType mMoverType3 = this;
        this.N = this.registerStaticField(string3, bl3, clazz3);
        Class clazz4 = MappedClasses.k;
        boolean bl4 = Wrapper.isNativeAvailable;
        String string4 = "SHULKER_BOX";
        MMoverType mMoverType4 = this;
        this.J = this.registerStaticField(string4, bl4, clazz4);
        Class clazz5 = MappedClasses.k;
        boolean bl5 = Wrapper.isNativeAvailable;
        String string5 = "SHULKER";
        MMoverType mMoverType5 = this;
        this.M = this.registerStaticField(string5, bl5, clazz5);
    }

    public Object x() {
        return this.N.getObject(null);
    }

    public Object W() {
        return this.x.getObject(null);
    }

    public Object h() {
        return this.W.getObject(null);
    }

    public Object q() {
        return this.J.getObject(null);
    }
}

