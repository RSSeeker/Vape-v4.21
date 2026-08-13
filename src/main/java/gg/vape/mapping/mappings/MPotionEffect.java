package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MPotion;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MPotionEffect
extends Mapping {
    private MappingMethod C;
    private MappingField l;
    private final MappingMethod Q;
    private MappingField m;
    private MappingMethod e;
    private final MappingMethod Y;
    private MappingMethod V;

    public MPotionEffect() {
        this(MPotion.K());
    }

    private MPotionEffect(int[] nArray) {
        super(MappedClasses.u3);
        if (nArray != null) {
            Class[] classArray = new Class[]{};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "getPotionID";
            MPotionEffect mPotionEffect = this;
            this.e = mPotionEffect.Y(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{};
            Class<Integer> clazz2 = Integer.TYPE;
            boolean bl2 = true;
            String string2 = "getAmplifier";
            MPotionEffect mPotionEffect2 = this;
            this.Q = this.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[]{};
            Class<Integer> clazz3 = Integer.TYPE;
            boolean bl3 = true;
            String string3 = "getDuration";
            MPotionEffect mPotionEffect3 = this;
            this.Y = this.Y(string3, bl3, clazz3, classArray3);
            if (GuiComponent.getLegacyComponentState() == null) {
                MPotion.U(new int[1]);
            }
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_20_6.d()) {
                    Class[] classArray = new Class[]{MappedClasses.Vo, Integer.TYPE, Integer.TYPE};
                    Class<Void> clazz = Void.TYPE;
                    boolean bl = false;
                    String string = "<init>";
                    MPotionEffect mPotionEffect = this;
                    this.V = mPotionEffect.Y(string, bl, clazz, classArray);
                } else {
                    Class[] classArray = new Class[]{MappedClasses.D3, Integer.TYPE, Integer.TYPE};
                    Class<Void> clazz = Void.TYPE;
                    boolean bl = false;
                    String string = "<init>";
                    MPotionEffect mPotionEffect = this;
                    this.V = mPotionEffect.Y(string, bl, clazz, classArray);
                }
                Class clazz = MappedClasses.D3;
                boolean bl = true;
                String string = "potion";
                MPotionEffect mPotionEffect = this;
                this.m = mPotionEffect.J(string, bl, clazz);
                Class<Boolean> clazz4 = Boolean.TYPE;
                boolean bl4 = true;
                String string4 = "showIcon";
                MPotionEffect mPotionEffect4 = this;
                this.l = this.J(string4, bl4, clazz4);
            } else {
                Class[] classArray = new Class[]{MappedClasses.FR, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = false;
                String string = "<init>";
                MPotionEffect mPotionEffect = this;
                this.V = mPotionEffect.Y(string, bl, clazz, classArray);
                Class clazz5 = MappedClasses.FR;
                boolean bl5 = true;
                String string5 = "potion";
                MPotionEffect mPotionEffect5 = this;
                this.m = this.J(string5, bl5, clazz5);
            }
        } else {
            Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            String string = "<init>";
            MPotionEffect mPotionEffect = this;
            this.C = mPotionEffect.Y(string, bl, clazz, classArray);
            Class[] classArray4 = new Class[]{};
            Class<Integer> clazz6 = Integer.TYPE;
            boolean bl6 = true;
            String string6 = "getPotionID";
            MPotionEffect mPotionEffect6 = this;
            this.e = this.Y(string6, bl6, clazz6, classArray4);
        }
        Class[] classArray = new Class[]{};
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string = "getAmplifier";
        MPotionEffect mPotionEffect = this;
        this.Q = mPotionEffect.Y(string, bl, clazz, classArray);
        Class[] classArray5 = new Class[]{};
        Class<Integer> clazz7 = Integer.TYPE;
        boolean bl7 = true;
        String string7 = "getDuration";
        MPotionEffect mPotionEffect7 = this;
        this.Y = this.Y(string7, bl7, clazz7, classArray5);
        if (GuiComponent.getLegacyComponentState() == null) {
            MPotion.U(new int[1]);
        }
    }

    private Object a(Object object) {
        return this.m.getObject(object);
    }

    public static int e(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.N(object);
    }

    private Object u(int n, int n2, int n3) {
        return this.C.newInstance(n, n2, n3);
    }

    private int N(Object object) {
        return this.Y.invokeInt(object, new Object[0]);
    }

    public static boolean e$src$Z$v3xxtq(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.w(object);
    }

    public static int I(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.h(object);
    }

    public static Object q(MPotionEffect mPotionEffect, int n, int n2, int n3) {
        return mPotionEffect.u(n, n2, n3);
    }

    public static int K(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.e(object);
    }

    private boolean w(Object object) {
        return this.l.getBoolean(object);
    }

    private int e(Object object) {
        return this.Q.invokeInt(object, new Object[0]);
    }


    public Object A(Object object, int n, int n2) {
        return this.V.newInstance(object, n, n2);
    }

    private int h(Object object) {
        return this.e.invokeInt(object, new Object[0]);
    }

    public static Object I$src$Ljava_lang_Object_$1dcpybi(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.a(object);
    }
}

