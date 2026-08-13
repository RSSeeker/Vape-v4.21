package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSoundAwareEntityFX
extends Mapping {
    private final MappingField I;
    private static String[] O;
    private final MappingField o;

    public static void u(MSoundAwareEntityFX mSoundAwareEntityFX, Object object, Object object2) {
        mSoundAwareEntityFX.g(object, object2);
    }

    static {
        MSoundAwareEntityFX.Z(new String[5]);
    }

    public static void Z(String[] stringArray) {
        O = stringArray;
    }


    private Object o(Object object) {
        return this.I.getObject(object);
    }

    private void g(Object object, Object object2) {
        this.o.setObject(object, object2);
    }

    public MSoundAwareEntityFX() {
        this(MSoundAwareEntityFX.t());
    }

    private MSoundAwareEntityFX(String[] stringArray) {
        super(MappedClasses.uu);
        if (stringArray != null) {
            Class clazz = MappedClasses.zc;
            boolean bl = true;
            String string = "attachedEntity";
            MSoundAwareEntityFX mSoundAwareEntityFX = this;
            this.o = mSoundAwareEntityFX.J(string, bl, clazz);
            if (ForgeVersion.MC_1_16_5.d()) {
                Class clazz2 = MappedClasses.lZ;
                boolean bl2 = true;
                String string2 = "particleTypes";
                MSoundAwareEntityFX mSoundAwareEntityFX2 = this;
                this.I = this.J(string2, bl2, clazz2);
            } else {
                Class clazz3 = MappedClasses.qi;
                boolean bl3 = true;
                String string3 = "particleTypes";
                MSoundAwareEntityFX mSoundAwareEntityFX3 = this;
                this.I = this.J(string3, bl3, clazz3);
            }
            return;
        }
        Class clazz = MappedClasses.zc;
        boolean bl = true;
        String string = "attachedEntity";
        MSoundAwareEntityFX mSoundAwareEntityFX = this;
        this.I = mSoundAwareEntityFX.J(string, bl, clazz);
        this.o = null;
    }

    private Object H(Object object) {
        return this.o.getObject(object);
    }

    public static String[] t() {
        return O;
    }

    public static Object b(MSoundAwareEntityFX mSoundAwareEntityFX, Object object) {
        return mSoundAwareEntityFX.o(object);
    }

    public static Object q(MSoundAwareEntityFX mSoundAwareEntityFX, Object object) {
        return mSoundAwareEntityFX.H(object);
    }
}

