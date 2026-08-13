package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Map;

public class MNBTTagCompound
extends Mapping {
    private final MappingMethod m;
    private final MappingField O;
    private final MappingMethod V;
    private final MappingMethod M;
    private final MappingMethod a;

    private short F(Object object, String string) {
        if (ForgeVersion.MC_1_21_6.d()) {
            return this.m.invokeShort(object, string, -1);
        }
        return this.m.invokeShort(object, string);
    }

    private boolean n(Object object, String string, int n) {
        if (this.M == null) {
            Map map = this.G(object);
            return map != null && map.containsKey(string);
        }
        return this.M.invokeBoolean(object, string, n);
    }

    public static Map a(MNBTTagCompound mNBTTagCompound, Object object) {
        return mNBTTagCompound.G(object);
    }

    public MNBTTagCompound() {
        super(MappedClasses.Yg);
        Class<Map> clazz = Map.class;
        boolean bl = true;
        String string = "tagMap";
        MNBTTagCompound mNBTTagCompound = this;
        this.O = this.J(string, bl, clazz);
        Class[] classArray = new Class[]{String.class};
        Class<Short> clazz2 = Short.TYPE;
        boolean bl2 = true;
        String string2 = "getShort";
        MNBTTagCompound mNBTTagCompound2 = this;
        this.m = this.Y(string2, bl2, clazz2, classArray);
        Class[] classArray2 = new Class[]{String.class};
        Class clazz3 = MappedClasses.YR;
        String string3 = "getTag";
        MNBTTagCompound mNBTTagCompound3 = this;
        this.a = ((MappingMethodBuilder)this.methodBuilder(string3, clazz3, classArray2).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "get")).buildMethod();
        if (MNBTTagList.Y() != null) {
            Class[] classArray3 = new Class[]{String.class, Integer.TYPE};
            Class clazz4 = MappedClasses.qt;
            String string4 = "getTagList";
            MNBTTagCompound mNBTTagCompound4 = this;
            this.V = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string4, clazz4, classArray3).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getList")).skipForVersion(ForgeVersion.MC_1_21_6.n())).buildMethod();
            Class[] classArray4 = new Class[]{String.class, Integer.TYPE};
            Class<Boolean> clazz5 = Boolean.TYPE;
            String string5 = "hasKey";
            MNBTTagCompound mNBTTagCompound5 = this;
            this.M = ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string5, clazz5, classArray4).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "contains")).setNameForVersion(ForgeVersion.MC_1_7_10.S(), "func_150297_b")).setMappedMemberForVersion(ForgeVersion.MC_1_7_10.S(), Wrapper.isNativeAvailable)).skipForVersion(ForgeVersion.MC_1_21_6.n())).buildMethod();
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
            return;
        }
        Class[] classArray5 = new Class[]{String.class, Integer.TYPE};
        Class clazz6 = MappedClasses.qt;
        String string6 = "getTagList";
        MNBTTagCompound mNBTTagCompound6 = this;
        this.V = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string6, clazz6, classArray5).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getList")).skipForVersion(ForgeVersion.MC_1_21_6.n())).buildMethod();
        Class[] classArray6 = new Class[]{String.class, Integer.TYPE};
        Class<Boolean> clazz7 = Boolean.TYPE;
        String string7 = "hasKey";
        MNBTTagCompound mNBTTagCompound7 = this;
        this.M = ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string7, clazz7, classArray6).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "contains")).setNameForVersion(ForgeVersion.MC_1_7_10.S(), "func_150297_b")).setMappedMemberForVersion(ForgeVersion.MC_1_7_10.S(), Wrapper.isNativeAvailable)).skipForVersion(ForgeVersion.MC_1_21_6.n())).buildMethod();
    }

    public static Object c(MNBTTagCompound mNBTTagCompound, Object object, String string) {
        return mNBTTagCompound.J(object, string);
    }


    private Object z(Object object, String string, int n) {
        if (this.V == null || ForgeVersion.MC_1_21_6.d()) {
            return null;
        }
        return this.V.invokeObject(object, string, n);
    }

    public static short z(MNBTTagCompound mNBTTagCompound, Object object, String string) {
        return mNBTTagCompound.F(object, string);
    }

    private Object J(Object object, String string) {
        return this.a.invokeObject(object, string);
    }

    public static Object c(MNBTTagCompound mNBTTagCompound, Object object, String string, int n) {
        return mNBTTagCompound.z(object, string, n);
    }

    private Map G(Object object) {
        return (Map)this.O.getObject(object);
    }

    public static boolean z(MNBTTagCompound mNBTTagCompound, Object object, String string, int n) {
        return mNBTTagCompound.n(object, string, n);
    }
}

