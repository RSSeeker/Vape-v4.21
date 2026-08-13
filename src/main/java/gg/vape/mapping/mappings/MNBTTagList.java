package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MNBTTagList
extends Mapping {
    private final MappingMethod F;
    public final MappingMethod r;
    private final MappingMethod I;
    private static String u;

    public static String s(MNBTTagList mNBTTagList, Object object, int n) {
        return mNBTTagList.o(object, n);
    }

    private int v(Object object) {
        return this.r.invokeInt(object, new Object[0]);
    }

    public static void Z(String string) {
        u = string;
    }

    public static int R(MNBTTagList mNBTTagList, Object object) {
        return mNBTTagList.v(object);
    }

    public static String Y() {
        return u;
    }

    static {
        MNBTTagList.Z(null);
    }

    public static Object w(MNBTTagList mNBTTagList, Object object, int n) {
        return mNBTTagList.Z(object, n);
    }


    public MNBTTagList() {
        this(MNBTTagList.Y());
    }

    private MNBTTagList(String string) {
        super(MappedClasses.qt);
        if (string != null) {
            Class[] classArray = new Class[]{Integer.TYPE};
            Class<String> clazz = String.class;
            boolean bl = true;
            String string2 = "getStringTagAt";
            MNBTTagList mNBTTagList = this;
            this.F = mNBTTagList.Y(string2, bl, clazz, classArray);
            if (GuiComponent.getLegacyComponentState() == null) {
                MNBTTagList.Z("tnDLE");
            }
            this.r = null;
            this.I = null;
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string3 = "size";
            MNBTTagList mNBTTagList = this;
            this.r = mNBTTagList.Y(string3, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{Integer.TYPE};
            Class clazz2 = MappedClasses.Yg;
            boolean bl2 = true;
            String string4 = "getCompound";
            MNBTTagList mNBTTagList2 = this;
            this.I = this.Y(string4, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[]{Integer.TYPE};
            Class<String> clazz3 = String.class;
            boolean bl3 = true;
            String string5 = "getString";
            MNBTTagList mNBTTagList3 = this;
            this.F = this.Y(string5, bl3, clazz3, classArray3);
        } else {
            Class[] classArray = new Class[]{};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string6 = "tagCount";
            MNBTTagList mNBTTagList = this;
            this.r = mNBTTagList.Y(string6, bl, clazz, classArray);
            Class[] classArray4 = new Class[]{Integer.TYPE};
            Class clazz4 = MappedClasses.Yg;
            boolean bl4 = true;
            String string7 = "getCompoundTagAt";
            MNBTTagList mNBTTagList4 = this;
            this.I = this.Y(string7, bl4, clazz4, classArray4);
            Class[] classArray5 = new Class[]{Integer.TYPE};
            Class<String> clazz5 = String.class;
            boolean bl5 = true;
            String string8 = "getStringTagAt";
            MNBTTagList mNBTTagList5 = this;
            this.F = this.Y(string8, bl5, clazz5, classArray5);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MNBTTagList.Z("tnDLE");
        }
    }

    private Object Z(Object object, int n) {
        return this.I.invokeObject(object, n);
    }

    private String o(Object object, int n) {
        if (ForgeVersion.MC_1_21_6.d()) {
            return (String)this.F.invokeObject(object, n, "");
        }
        return (String)this.F.invokeObject(object, n);
    }
}

