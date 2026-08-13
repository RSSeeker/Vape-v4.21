package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MSlot;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityEquipmentSlot
extends Mapping {
    private MappingMethod a;
    private final MappingField W;
    private final MappingField c;

    public static Object Q(MEntityEquipmentSlot mEntityEquipmentSlot) {
        return mEntityEquipmentSlot.R();
    }

    private boolean N(Object object) {
        return this.a.invokeBoolean(object, new Object[0]);
    }

    public MEntityEquipmentSlot() {
        this(MSlot.getSlotControlFlowState());
    }

    private MEntityEquipmentSlot(int[] nArray) {
        super(MappedClasses.FY);
        if (nArray != null) {
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "index";
            MEntityEquipmentSlot mEntityEquipmentSlot = this;
            this.c = mEntityEquipmentSlot.J(string, bl, clazz);
            Class[] classArray = new Class[]{};
            Class clazz2 = MappedClasses.FY;
            boolean bl2 = true;
            String string2 = "MAINHAND";
            MEntityEquipmentSlot mEntityEquipmentSlot2 = this;
            this.a = this.Y(string2, bl2, clazz2, classArray);
            this.W = null;
            return;
        }
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string = "index";
        MEntityEquipmentSlot mEntityEquipmentSlot = this;
        this.c = mEntityEquipmentSlot.J(string, bl, clazz); 
        Class clazz3 = MappedClasses.FY;
        boolean bl3 = true;
        String string3 = "MAINHAND";
        MEntityEquipmentSlot mEntityEquipmentSlot3 = this;
        this.W = this.registerStaticField(string3, bl3, clazz3);
        if (ForgeVersion.MC_1_21_6.d()) {
            Class[] classArray = new Class[]{};
            Class<Boolean> clazz4 = Boolean.TYPE;
            boolean bl4 = true;
            String string4 = "isArmor";
            MEntityEquipmentSlot mEntityEquipmentSlot4 = this;
            this.a = this.Y(string4, bl4, clazz4, classArray);
        }
    }

    private Object R() {
        return this.W.getObject(null);
    }

    public static boolean S(MEntityEquipmentSlot mEntityEquipmentSlot, Object object) {
        return mEntityEquipmentSlot.N(object);
    }

    public static int c(MEntityEquipmentSlot mEntityEquipmentSlot, Object object) {
        return mEntityEquipmentSlot.m(object);
    }

    private int m(Object object) {
        return this.c.getInt(object);
    }

}

