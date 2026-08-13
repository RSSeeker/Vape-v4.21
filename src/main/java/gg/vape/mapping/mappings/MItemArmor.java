package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MItemStack;
import gg.vape.wrapper.impl.ForgeVersion;

public class MItemArmor
extends Mapping {
    private MappingField K;
    private MappingField G;
    private MappingField Q;
    private MappingField T;
    private MappingMethod U;
    private MappingMethod u;

    public MItemArmor() {
        this(MItemStack.M());
    }

    private MItemArmor(int n) {
        super(MappedClasses.qU);
        int n2 = n;
        if (ForgeVersion.MC_1_21_4.v()) {
            if (ForgeVersion.MC_1_20_6.d()) {
                Class[] classArray = new Class[]{};
                Class<Integer> clazz = Integer.TYPE;
                boolean bl = true;
                String string = "getDefense";
                MItemArmor mItemArmor = this;
                this.u = mItemArmor.Y(string, bl, clazz, classArray);
            } else {
                Class<Integer> clazz = Integer.TYPE;
                boolean bl = true;
                String string = "damageReduceAmount";
                MItemArmor mItemArmor = this;
                this.T = mItemArmor.J(string, bl, clazz);
            }
        }
        if (!ForgeVersion.MC_1_21_0.d()) {
            if (ForgeVersion.MC_1_20_6.d()) {
                Class clazz = MappedClasses.YW;
                boolean bl = true;
                String string = "type";
                MItemArmor mItemArmor = this;
                this.Q = mItemArmor.J(string, bl, clazz);
                Class clazz2 = MappedClasses.Vo;
                boolean bl2 = true;
                String string2 = "material";
                MItemArmor mItemArmor2 = this;
                this.K = this.J(string2, bl2, clazz2);
            } else if (ForgeVersion.MC_1_16_5.d()) {
                Class clazz = MappedClasses.FY;
                boolean bl = true;
                String string = "slot";
                MItemArmor mItemArmor = this;
                this.Q = mItemArmor.J(string, bl, clazz);
                Class clazz3 = MappedClasses.uc;
                boolean bl3 = true;
                String string3 = "material";
                MItemArmor mItemArmor3 = this;
                this.K = this.J(string3, bl3, clazz3);
            } else if (ForgeVersion.MC_1_12_2.d()) {
                Class clazz = MappedClasses.FY;
                boolean bl = true;
                String string = "armorType";
                MItemArmor mItemArmor = this;
                this.Q = mItemArmor.J(string, bl, clazz);
                Class clazz4 = MappedClasses.ZM;
                boolean bl4 = true;
                String string4 = "material";
                MItemArmor mItemArmor4 = this;
                this.K = this.J(string4, bl4, clazz4);
            } else {
                Class<Integer> clazz = Integer.TYPE;
                boolean bl = true;
                String string = "armorType";
                MItemArmor mItemArmor = this;
                this.Q = mItemArmor.J(string, bl, clazz);
                Class clazz5 = MappedClasses.ZM;
                boolean bl5 = true;
                String string5 = "material";
                MItemArmor mItemArmor5 = this;
                this.K = this.J(string5, bl5, clazz5);
            }
        }
        if (ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray = new Class[]{MappedClasses.VK};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "getColor";
            MItemArmor mItemArmor = this;
            this.U = mItemArmor.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            Class<Float> clazz = Float.TYPE;
            boolean bl = true;
            String string = "toughness";
            MItemArmor mItemArmor = this;
            this.G = mItemArmor.J(string, bl, clazz); 
        }
    }


    private int o(Object object, Object object2) {
        return this.U.invokeInt(object, object2);
    }

    public static float e(MItemArmor mItemArmor, Object object) {
        return mItemArmor.x(object);
    }

    public static int F(MItemArmor mItemArmor, Object object) {
        return mItemArmor.j(object);
    }

    private float x(Object object) {
        if (ForgeVersion.MC_1_12_2.d()) {
            return this.G.getFloat(object);
        }
        return 0.0f;
    }

    private Object D(Object object) {
        return this.K.getObject(object);
    }

    public static int q(MItemArmor mItemArmor, Object object) {
        return mItemArmor.P(object);
    }

    private Object T(Object object) {
        return this.Q.getObject(object);
    }

    public static Object o(MItemArmor mItemArmor, Object object) {
        return mItemArmor.D(object);
    }

    public static int w(MItemArmor mItemArmor, Object object, Object object2) {
        return mItemArmor.o(object, object2);
    }

    private int P(Object object) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.u.invokeInt(object, new Object[0]);
        }
        return this.T.getInt(object);
    }

    public static Object D(MItemArmor mItemArmor, Object object) {
        return mItemArmor.T(object);
    }

    private int j(Object object) {
        return this.Q.getInt(object);
    }
}

