package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.wrapper.impl.ForgeVersion;

public class MPlayerInfo
extends Mapping {
    private final MappingMethod K;
    private final MappingField q;
    private final MappingMethod I;
    private final MappingMethod D;
    private final MappingMethod f;
    private static String[] p;

    public static Object Z(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.I(object);
    }

    private Object I(Object object) {
        return this.K.invokeObject(object, new Object[0]);
    }

    private int L(Object object) {
        return this.D.invokeInt(object, new Object[0]);
    }

    public static String[] K() {
        return p;
    }

    public static Object l(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.r(object);
    }

    public static Object W(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.e(object);
    }


    public static int H(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.L(object);
    }

    private Object r(Object object) {
        return this.q.getObject(object);
    }

    public static Object J(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.A(object);
    }

    public static void r(String[] stringArray) {
        p = stringArray;
    }

    private Object e(Object object) {
        return this.f.invokeObject(object, new Object[0]);
    }

    private Object A(Object object) {
        return this.I.invokeObject(object, new Object[0]);
    }

    static {
        MPlayerInfo.r(new String[3]);
    }

    public MPlayerInfo() {
        super(MappedClasses.Zc);
        Class[] classArray = new Class[]{};
        Class clazz = MappedClasses.VD;
        boolean bl = true;
        String string = "getGameProfile";
        MPlayerInfo mPlayerInfo = this;
        this.f = this.Y(string, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{};
        Class<Integer> clazz2 = Integer.TYPE;
        boolean bl2 = true;
        String string2 = "getResponseTime";
        MPlayerInfo mPlayerInfo2 = this;
        this.D = this.Y(string2, bl2, clazz2, classArray2);
        Class[] classArray3 = new Class[]{};
        Class clazz3 = MappedClasses.u6;
        String string3 = "getPlayerTeam";
        MPlayerInfo mPlayerInfo3 = this;
        this.K = ((MappingMethodBuilder)this.methodBuilder(string3, clazz3, classArray3).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getTeam")).buildMethod();
        String[] stringArray = MPlayerInfo.K();
        Class clazz4 = MappedClasses.Yr;
        String string4 = "displayName";
        MPlayerInfo mPlayerInfo4 = this;
        this.q = ((MappingFieldBuilder)this.fieldBuilder(string4, clazz4).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "tabListDisplayName")).buildField();
        Class[] classArray4 = new Class[]{};
        Class clazz5 = MappedClasses.zC;
        String string5 = "getLocationSkin";
        MPlayerInfo mPlayerInfo5 = this;
        this.I = ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string5, clazz5, classArray4).setNameForVersion(ForgeVersion.MC_1_20_6.n(), "getSkin")).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getSkinTextureLocation")).setTypeForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.uZ)).buildMethod();
    }
}

