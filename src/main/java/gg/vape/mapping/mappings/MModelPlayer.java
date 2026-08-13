package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MModelPlayer
extends Mapping {
    private final MappingField q;
    private final MappingField E;
    private final MappingMethod n;
    private final MappingMethod x;
    private final MappingMethod P;
    private final MappingField y;
    private static String m;
    private final MappingMethod H;
    private final MappingField b;
    private final MappingField M;
    private final MappingMethod u;

    private boolean h(Object object) {
        return this.q.getBoolean(object);
    }

    private boolean r(Object object) {
        return this.b.getBoolean(object);
    }

    private void D(Object object, boolean bl) {
        this.E.setBoolean(object, bl);
    }

    public static float Y(MModelPlayer mModelPlayer, Object object) {
        return mModelPlayer.p(object);
    }

    public static boolean v(MModelPlayer mModelPlayer, Object object) {
        return mModelPlayer.h(object);
    }

    static {
        MModelPlayer.W("idI0Xb");
    }

    public static boolean a(MModelPlayer mModelPlayer, Object object) {
        return mModelPlayer.b(object);
    }

    private void A(Object object, float f) {
        this.x.invokeVoid(object, Float.valueOf(f));
    }

    public static boolean u(MModelPlayer mModelPlayer, Object object) {
        return mModelPlayer.t(object);
    }


    private float p(Object object) {
        return this.H.invokeFloat(object, new Object[0]);
    }

    private float k(Object object) {
        return this.u.invokeFloat(object, new Object[0]);
    }

    public MModelPlayer() {
        super(MappedClasses.q9);
        Class[] classArray = new Class[]{};
        Class<Void> clazz = Void.TYPE;
        boolean bl = false;
        String string = "<init>";
        MModelPlayer mModelPlayer = this;
        this.n = this.Y(string, bl, clazz, classArray);
        Class<Boolean> clazz2 = Boolean.TYPE;
        boolean bl2 = true;
        String string2 = "isCreativeMode";
        MModelPlayer mModelPlayer2 = this;
        this.b = this.J(string2, bl2, clazz2);
        Class<Boolean> clazz3 = Boolean.TYPE;
        boolean bl3 = true;
        String string3 = "disableDamage";
        MModelPlayer mModelPlayer3 = this;
        this.M = this.J(string3, bl3, clazz3);
        Class<Boolean> clazz4 = Boolean.TYPE;
        boolean bl4 = true;
        String string4 = "allowFlying";
        MModelPlayer mModelPlayer4 = this;
        this.E = this.J(string4, bl4, clazz4);
        Class<Boolean> clazz5 = Boolean.TYPE;
        boolean bl5 = true;
        String string5 = "allowEdit";
        MModelPlayer mModelPlayer5 = this;
        this.y = this.J(string5, bl5, clazz5);
        if (MModelPlayer.n() != null) {
            Class<Boolean> clazz6 = Boolean.TYPE;
            boolean bl6 = true;
            String string6 = "isFlying";
            MModelPlayer mModelPlayer6 = this;
            this.q = this.J(string6, bl6, clazz6);
            Class[] classArray2 = new Class[]{};
            Class<Float> clazz7 = Float.TYPE;
            boolean bl7 = true;
            String string7 = "getWalkSpeed";
            MModelPlayer mModelPlayer7 = this;
            this.H = this.Y(string7, bl7, clazz7, classArray2);
            Class[] classArray3 = new Class[]{};
            Class<Float> clazz8 = Float.TYPE;
            boolean bl8 = true;
            String string8 = "getFlySpeed";
            MModelPlayer mModelPlayer8 = this;
            this.u = this.Y(string8, bl8, clazz8, classArray3);
            Class[] classArray4 = new Class[]{Float.TYPE};
            Class<Void> clazz9 = Void.TYPE;
            boolean bl9 = true;
            String string9 = "setFlySpeed";
            MModelPlayer mModelPlayer9 = this;
            this.x = this.Y(string9, bl9, clazz9, classArray4);
            if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray5 = new Class[]{Float.TYPE};
                Class<Void> clazz10 = Void.TYPE;
                boolean bl10 = true;
                String string10 = "setWalkSpeed";
                MModelPlayer mModelPlayer10 = this;
                this.P = this.Y(string10, bl10, clazz10, classArray5);
            } else {
                Class[] classArray6 = new Class[]{Float.TYPE};
                Class<Void> clazz11 = Void.TYPE;
                boolean bl11 = true;
                String string11 = "setPlayerWalkSpeed";
                MModelPlayer mModelPlayer11 = this;
                this.P = this.Y(string11, bl11, clazz11, classArray6);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MModelPlayer.W("m4zzKb");
            }
            return;
        }
        Class<Boolean> clazz12 = Boolean.TYPE;
        boolean bl12 = true;
        String string12 = "isFlying";
        MModelPlayer mModelPlayer12 = this;
        this.q = this.J(string12, bl12, clazz12);
        Class[] classArray7 = new Class[]{};
        Class<Float> clazz13 = Float.TYPE;
        boolean bl13 = true;
        String string13 = "getWalkSpeed";
        MModelPlayer mModelPlayer13 = this;
        this.H = this.Y(string13, bl13, clazz13, classArray7);
        Class[] classArray8 = new Class[]{};
        Class<Float> clazz14 = Float.TYPE;
        boolean bl14 = true;
        String string14 = "getFlySpeed";
        MModelPlayer mModelPlayer14 = this;
        this.u = this.Y(string14, bl14, clazz14, classArray8);
        Class[] classArray9 = new Class[]{Float.TYPE};
        Class<Void> clazz15 = Void.TYPE;
        boolean bl15 = true;
        String string15 = "setFlySpeed";
        MModelPlayer mModelPlayer15 = this;
        this.P = this.Y(string15, bl15, clazz15, classArray9);
        this.x = null;
        if (GuiComponent.getLegacyComponentState() == null) {
            MModelPlayer.W("m4zzKb");
        }
    }

    public static Object C(MModelPlayer mModelPlayer) {
        return mModelPlayer.b();
    }

    public static String n() {
        return m;
    }

    public static void E(MModelPlayer mModelPlayer, Object object, float f) {
        mModelPlayer.A(object, f);
    }

    public static void H(MModelPlayer mModelPlayer, Object object, float f) {
        mModelPlayer.L(object, f);
    }

    public static boolean k(MModelPlayer mModelPlayer, Object object) {
        return mModelPlayer.r(object);
    }

    public static void W(String string) {
        m = string;
    }

    private boolean t(Object object) {
        return this.M.getBoolean(object);
    }

    private boolean v(Object object) {
        return this.E.getBoolean(object);
    }

    public static boolean D(MModelPlayer mModelPlayer, Object object) {
        return mModelPlayer.v(object);
    }

    public static void t(MModelPlayer mModelPlayer, Object object, boolean bl) {
        mModelPlayer.z(object, bl);
    }

    public static float p(MModelPlayer mModelPlayer, Object object) {
        return mModelPlayer.k(object);
    }

    private void L(Object object, float f) {
        this.P.invokeVoid(object, Float.valueOf(f));
    }

    private Object b() {
        return this.n.newInstance(new Object[0]);
    }

    public static void y(MModelPlayer mModelPlayer, Object object, boolean bl) {
        mModelPlayer.D(object, bl);
    }

    private boolean b(Object object) {
        return this.y.getBoolean(object);
    }

    private void z(Object object, boolean bl) {
        this.q.setBoolean(object, bl);
    }
}

