package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MIInventory
extends Mapping {
    private MappingMethod e;
    private MappingMethod l;
    private final MappingMethod F;
    private final MappingMethod k;

    private Object a(Object object, int n) {
        return this.F.invokeObject(object, n);
    }


    public static int N(MIInventory mIInventory, Object object) {
        return mIInventory.d(object);
    }

    public MIInventory() {
        this(MSlot.getSlotControlFlowState());
    }

    private MIInventory(int[] nArray) {
        super(MappedClasses.l0);
        if (nArray != null) {
            Class[] classArray = new Class[]{};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "getSizeInventory";
            MIInventory mIInventory = this;
            this.k = mIInventory.Y(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{Integer.TYPE};
            Class clazz2 = MappedClasses.VK;
            boolean bl2 = true;
            String string2 = "getStackInSlot";
            MIInventory mIInventory2 = this;
            this.F = this.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[]{};
            Class<Boolean> clazz3 = Boolean.TYPE;
            boolean bl3 = true;
            String string3 = Wrapper.vapeInstance.isVanillaMinecraftPresent() ? "isCustomInventoryName" : "hasCustomInventoryName";
            MIInventory mIInventory3 = this;
            this.e = this.Y(string3, bl3, clazz3, classArray3);
            return;
        }
        Class[] classArray = new Class[]{};
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string = "getSizeInventory";
        MIInventory mIInventory = this;
        this.k = mIInventory.Y(string, bl, clazz, classArray); 
        Class[] classArray4 = new Class[]{Integer.TYPE};
        Class clazz4 = MappedClasses.VK;
        boolean bl4 = true;
        String string4 = "getStackInSlot";
        MIInventory mIInventory4 = this;
        this.F = this.Y(string4, bl4, clazz4, classArray4);
        if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray5 = new Class[]{};
            Class<String> clazz5 = String.class;
            boolean bl5 = true;
            String string5 = "getInventoryName";
            MIInventory mIInventory5 = this;
            this.l = this.Y(string5, bl5, clazz5, classArray5);
            Class[] classArray6 = new Class[]{};
            Class<Boolean> clazz6 = Boolean.TYPE;
            boolean bl6 = true;
            String string6 = Wrapper.vapeInstance.isVanillaMinecraftPresent() ? "isCustomInventoryName" : "hasCustomInventoryName";
            MIInventory mIInventory6 = this;
            this.e = this.Y(string6, bl6, clazz6, classArray6);
        }
    }

    public static Object W(MIInventory mIInventory, Object object, int n) {
        return mIInventory.a(object, n);
    }

    private int d(Object object) {
        return this.k.invokeInt(object, new Object[0]);
    }

    private boolean G(Object object) {
        return this.e.invokeBoolean(object, new Object[0]);
    }

    public static String F(MIInventory mIInventory, Object object) {
        return mIInventory.R(object);
    }

    private String R(Object object) {
        return this.l.invokeObject(object, new Object[0]).toString();
    }

    public static boolean e(MIInventory mIInventory, Object object) {
        return mIInventory.G(object);
    }
}

