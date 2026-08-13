package gg.vape.mapping.mappings;

import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.mapping.mappings.MModelPlayer;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.AbstractList;

public class MInventoryPlayer
extends Mapping {
    private MappingField B;
    private MappingMethod E;
    private final MappingField n;
    private final MappingField V;
    private final MappingMethod Q;

    public Object N(Object object, int n) {
        return this.Q.invokeObject(object, n);
    }

    private Object G(Object object) {
        return this.E.invokeObject(object, new Object[0]);
    }

    public void A(Object object, int n) {
        this.n.setInt(object, n);
    }

    public MInventoryPlayer() {
        this(MModelPlayer.n());
    }

    private MInventoryPlayer(String string) {
        super(MappedClasses.Yu);
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string2 = "currentItem";
        MInventoryPlayer mInventoryPlayer = this;
        this.n = this.J(string2, bl, clazz);
        String string3 = string;
        if (ForgeVersion.MC_1_12_2.d()) {
            Class clazz2 = MappedClasses.Vd;
            boolean bl2 = true;
            String string4 = "field_70462_a";
            MInventoryPlayer mInventoryPlayer2 = this;
            this.V = this.J(string4, bl2, clazz2);
            if (ForgeVersion.MC_1_21_6.v()) {
                Class clazz3 = MappedClasses.Vd;
                boolean bl3 = true;
                String string5 = "armorInventory";
                MInventoryPlayer mInventoryPlayer3 = this;
                this.B = this.J(string5, bl3, clazz3);
            }
        } else {
            Class<?> clazz4 = DescUtils.getArrayType(MappedClasses.VK);
            boolean bl4 = true;
            String string6 = "mainInventory";
            MInventoryPlayer mInventoryPlayer4 = this;
            this.V = this.J(string6, bl4, clazz4);
            Class<?> clazz5 = DescUtils.getArrayType(MappedClasses.VK);
            boolean bl5 = true;
            String string7 = "armorInventory";
            MInventoryPlayer mInventoryPlayer5 = this;
            this.B = this.J(string7, bl5, clazz5);
        }
        Class[] classArray = new Class[]{Integer.TYPE};
        Class clazz6 = MappedClasses.VK;
        String string8 = "getStackInSlot";
        MInventoryPlayer mInventoryPlayer6 = this;
        this.Q = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string8, clazz6, classArray).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getItem")).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.l0)).buildMethod();
        if (ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray2 = new Class[]{};
            Class clazz7 = MappedClasses.VK;
            boolean bl6 = true;
            String string9 = "getItemStack";
            MInventoryPlayer mInventoryPlayer7 = this;
            this.E = this.Y(string9, bl6, clazz7, classArray2);
        }
    }


    public AbstractList T(Object object) {
        return (AbstractList)this.V.getObject(object);
    }

    private AbstractList M(Object object) {
        return (AbstractList)this.B.getObject(object);
    }

    public int s(Object object) {
        return this.n.getInt(object);
    }

    private Object[] u(Object object) {
        return this.B.getObjectArray(object);
    }

    public static Object[] i(MInventoryPlayer mInventoryPlayer, Object object) {
        return mInventoryPlayer.u(object);
    }

    public static AbstractList S(MInventoryPlayer mInventoryPlayer, Object object) {
        return mInventoryPlayer.M(object);
    }

    public Object[] X(Object object) {
        return this.V.getObjectArray(object);
    }

    public static Object N(MInventoryPlayer mInventoryPlayer, Object object) {
        return mInventoryPlayer.G(object);
    }
}

