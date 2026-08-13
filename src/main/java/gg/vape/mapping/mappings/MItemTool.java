package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MItemTool
extends Mapping {
    private MappingMethod E;
    private MappingField B;

    public MItemTool() {
        this(MItemStack.M());
    }

    private MItemTool(int n) {
        super(MappedClasses.V5);
        int n2 = n;
        if (ForgeVersion.MC_1_8_9.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_21_4.v()) {
                    Class clazz = MappedClasses.lh;
                    boolean bl = true;
                    String string = "tier";
                    Class clazz2 = MappedClasses.uT;
                    MItemTool mItemTool = this;
                    this.B = mItemTool.registerInstanceFieldForOwner(clazz2, string, bl, clazz);
                }
            } else {
                Class clazz = MappedClasses.F5;
                boolean bl = true;
                String string = "material";
                MItemTool mItemTool = this;
                this.B = mItemTool.J(string, bl, clazz);
                Class[] classArray = new Class[]{};
                Class<Float> clazz3 = Float.TYPE;
                boolean bl2 = true;
                String string2 = ForgeVersion.c() >= 23 ? "getAttackDamage" : "getDamageVsEntity";
                MItemTool mItemTool2 = this;
                this.E = this.Y(string2, bl2, clazz3, classArray);
            }
        } else {
            Class[] classArray = new Class[]{};
            Class<Float> clazz = Float.TYPE;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "func_150931_i";
            MItemTool mItemTool = this;
            this.E = mItemTool.Y(string, bl, clazz, classArray); 
            if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                Class clazz4 = MappedClasses.F5;
                boolean bl3 = true;
                String string3 = "repairMaterial";
                MItemTool mItemTool3 = this;
                this.B = this.J(string3, bl3, clazz4);
            } else {
                Class clazz5 = MappedClasses.F5;
                boolean bl4 = Wrapper.isNativeAvailable;
                String string4 = "field_150933_b";
                MItemTool mItemTool4 = this;
                this.B = this.J(string4, bl4, clazz5);
            }
        }
    }


    public static float T(MItemTool mItemTool, Object object) {
        return mItemTool.x(object);
    }

    private Object w(Object object) {
        return this.B.getObject(object);
    }

    private float x(Object object) {
        return this.E.invokeFloat(object, new Object[0]);
    }

    public static Object R(MItemTool mItemTool, Object object) {
        return mItemTool.w(object);
    }
}

