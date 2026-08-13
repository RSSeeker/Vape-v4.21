package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MPotion;
import gg.vape.wrapper.impl.ForgeVersion;

public class MStatusEffect
extends Mapping {
    private MappingMethod N;
    private MappingField B;
    private MappingMethod M;
    private MappingMethod O;
    private MappingMethod u;

    private int S(Object object) {
        return this.O.invokeInt(null, object);
    }

    private Object T(Object object) {
        return this.N.invokeObject(object, new Object[0]);
    }


    public static Object g(MStatusEffect mStatusEffect, Object object) {
        return mStatusEffect.T(object);
    }

    private boolean q(Object object) {
        return this.u.invokeBoolean(object, new Object[0]);
    }

    public Object O(int n) {
        return this.M.invokeObject(null, n);
    }

    public static boolean r(MStatusEffect mStatusEffect, Object object) {
        return mStatusEffect.q(object);
    }

    public MStatusEffect() {
        this(MPotion.K());
    }

    private MStatusEffect(int[] nArray) {
        super(MappedClasses.D3);
        if (nArray != null) {
            return;
        }
        if (ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray = new Class[]{Integer.TYPE};
            Class clazz = MappedClasses.D3;
            boolean bl = true;
            String string = "get";
            MStatusEffect mStatusEffect = this;
            this.M = mStatusEffect.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{MappedClasses.D3};
            Class<Integer> clazz2 = Integer.TYPE;
            boolean bl2 = true;
            String string2 = "getId";
            MStatusEffect mStatusEffect2 = this;
            this.O = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
        }
        Class[] classArray = new Class[]{};
        Class<Boolean> clazz = Boolean.TYPE;
        boolean bl = true;
        String string = "isBeneficial";
        MStatusEffect mStatusEffect = this;
        this.u = mStatusEffect.Y(string, bl, clazz, classArray);
        Class[] classArray3 = new Class[]{};
        Class clazz3 = MappedClasses.Yr;
        boolean bl3 = true;
        String string3 = "getDisplayName";
        MStatusEffect mStatusEffect3 = this;
        this.N = this.Y(string3, bl3, clazz3, classArray3);
    }

    public static int a(MStatusEffect mStatusEffect, Object object) {
        return mStatusEffect.S(object);
    }
}

