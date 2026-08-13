package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEntityRemovalReason
extends Mapping {
    private MappingField O;
    private MappingField x;
    private MappingField X;
    private MappingField v;
    private MappingField f;

    private Object D() {
        return this.f.getObject(null);
    }

    public static Object k(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.l();
    }

    public MEntityRemovalReason() {
        super(MappedClasses.c);
        Class clazz = MappedClasses.c;
        boolean bl = true;
        String string = "KILLED";
        MEntityRemovalReason mEntityRemovalReason = this;
        this.x = this.registerStaticField(string, bl, clazz);
        Class clazz2 = MappedClasses.c;
        boolean bl2 = true;
        String string2 = "DISCARDED";
        MEntityRemovalReason mEntityRemovalReason2 = this;
        this.O = this.registerStaticField(string2, bl2, clazz2);
        Class clazz3 = MappedClasses.c;
        boolean bl3 = true;
        String string3 = "UNLOADED_TO_CHUNK";
        MEntityRemovalReason mEntityRemovalReason3 = this;
        this.X = this.registerStaticField(string3, bl3, clazz3);
        Class clazz4 = MappedClasses.c;
        boolean bl4 = true;
        String string4 = "UNLOADED_WITH_PLAYER";
        MEntityRemovalReason mEntityRemovalReason4 = this;
        this.f = this.registerStaticField(string4, bl4, clazz4);
        Class clazz5 = MappedClasses.c;
        boolean bl5 = true;
        String string5 = "CHANGED_DIMENSION";
        MEntityRemovalReason mEntityRemovalReason5 = this;
        this.v = this.registerStaticField(string5, bl5, clazz5);
    }

    public static Object Z(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.D();
    }

    public static Object N(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.Y();
    }

    private Object l() {
        return this.x.getObject(null);
    }

    public static Object U(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.Z();
    }

    private Object e() {
        return this.O.getObject(null);
    }

    private Object Z() {
        return this.v.getObject(null);
    }

    public static Object i(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.e();
    }

    private Object Y() {
        return this.X.getObject(null);
    }
}

