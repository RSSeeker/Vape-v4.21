package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettingsValue;
import gg.vape.wrapper.impl.Minecraft;
import java.util.List;

public class MGameSettings
extends Mapping {
    private MappingField A;
    private MappingField S;
    private final MappingField b;
    private final MappingField N;
    private MappingField f;
    private final MappingField L;
    private final MappingField H;
    private final MappingField G;
    private MappingField a;
    private MappingField B;
    private final MappingField p;
    private final MappingField Y;
    private final MappingField J;
    private final MappingField C;
    private final MappingField q;
    private MappingMethod X;
    private final MappingField D;
    private MappingMethod l;
    private final MappingField T;
    private final MappingField E;
    private final MappingField Z;
    private MappingField o;
    private MappingField r;
    private MappingField n;
    private final MappingField z;
    private final MappingField P;
    private final MappingField i;
    private final MappingField k;
    private final MappingField u;

    private float Q(Object object) {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(this.G.getObject(object));
            Integer n = (Integer)gameSettingsValue.i();
            return n.intValue();
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return (float)this.G.getDouble(object);
        }
        return this.G.getFloat(object);
    }

    private Object H(Object object) {
        return this.o.getObject(object);
    }

    public static boolean U$src$Z$16z50cw(MGameSettings mGameSettings, Object object) {
        return mGameSettings.A$src$Z$nt71g2(object);
    }

    public static Object U(MGameSettings mGameSettings, Object object) {
        return mGameSettings.p(object);
    }

    private Object b(Object object) {
        return this.T.getObject(object);
    }

    public static void D(MGameSettings mGameSettings, Object object, int n) {
        mGameSettings.T(object, n);
    }

    public static Object E(MGameSettings mGameSettings, Object object) {
        return mGameSettings.w(object);
    }

    public static Object f(MGameSettings mGameSettings, Object object) {
        return mGameSettings.j(object);
    }

    private boolean V(Object object) {
        return this.r.getBoolean(object);
    }

    public static boolean L(MGameSettings mGameSettings, Object object) {
        return mGameSettings.R$src$Z$imccpv(object);
    }

    public static void U(MGameSettings mGameSettings, Object object, float f) {
        mGameSettings.q(object, f);
    }

    public static Object w(MGameSettings mGameSettings, Object object) {
        return mGameSettings.r(object);
    }

    public static void Q(MGameSettings mGameSettings, Object object, boolean bl) {
        mGameSettings.o(object, bl);
    }

    private boolean R$src$Z$imccpv(Object object) {
        return this.p.getBoolean(object);
    }

    public static List o(MGameSettings mGameSettings, Object object) {
        return mGameSettings.w$src$Ljava_util_List_$1otljmr(object);
    }

    public static void E(MGameSettings mGameSettings, Object object, Object object2) {
        mGameSettings.i(object, object2);
    }

    private void s(Object object, boolean bl) {
        this.p.setBoolean(object, bl);
    }

    public static Object p(MGameSettings mGameSettings, Object object) {
        return mGameSettings.P(object);
    }

    private int X(Object object) {
        return this.H.getInt(object);
    }

    private boolean T(Object object) {
        if (ForgeVersion.MC_26_2.d()) {
            Object object2 = Minecraft.k().getObject();
            Object object3 = this.S.getObject(object2);
            return this.l.invokeBoolean(object3, new Object[0]);
        }
        return this.n.getBoolean(object);
    }

    private int Z(Object object) {
        return this.B.getInt(object);
    }

    public static Object v(MGameSettings mGameSettings, Object object) {
        return mGameSettings.S(object);
    }

    public static Object g(MGameSettings mGameSettings, Object object) {
        return mGameSettings.H(object);
    }

    private Object M(Object object) {
        return this.B.getObject(object);
    }

    private void m(Object object, int n) {
        this.A.setInt(object, n);
    }

    public static float H(MGameSettings mGameSettings, Object object) {
        return mGameSettings.J(object);
    }

    public static Object Y(MGameSettings mGameSettings, Object object) {
        return mGameSettings.o(object);
    }

    public static void r(MGameSettings mGameSettings, Object object, int n) {
        mGameSettings.m(object, n);
    }

    public static void z(MGameSettings mGameSettings, Object object, boolean bl) {
        mGameSettings.s(object, bl);
    }

    public static int A(MGameSettings mGameSettings, Object object) {
        return mGameSettings.O(object);
    }

    public static boolean F$src$Z$nptw01(MGameSettings mGameSettings, Object object) {
        return mGameSettings.V(object);
    }

    public static Object y(MGameSettings mGameSettings, Object object) {
        return mGameSettings.c(object);
    }

    public static Object H$src$Ljava_lang_Object_$1jqua4d(MGameSettings mGameSettings, Object object) {
        return mGameSettings.R(object);
    }

    public static void m(MGameSettings mGameSettings, Object object, boolean bl) {
        mGameSettings.v(object, bl);
    }


    private Object v(Object object) {
        return this.H.getObject(object);
    }

    public static Object B(MGameSettings mGameSettings, Object object) {
        return mGameSettings.M(object);
    }

    public static boolean D(MGameSettings mGameSettings, Object object) {
        return mGameSettings.T(object);
    }

    public static float s(MGameSettings mGameSettings, Object object) {
        return mGameSettings.Q(object);
    }

    public static Object k(MGameSettings mGameSettings, Object object) {
        return mGameSettings.F(object);
    }

    public static int t(MGameSettings mGameSettings, Object object) {
        return mGameSettings.Z(object);
    }

    public static Object s$src$Ljava_lang_Object_$4nr0aa(MGameSettings mGameSettings, Object object) {
        return mGameSettings.s(object);
    }

    private Object c(Object object) {
        return this.i.getObject(object);
    }

    private float Y(Object object) {
        return this.z.getFloat(object);
    }

    public static boolean h$src$Z$1cffm5f(MGameSettings mGameSettings, Object object) {
        return mGameSettings.p$src$Z$1vyglj5(object);
    }

    private Object s(Object object) {
        return this.k.getObject(object);
    }

    private void T(Object object, int n) {
        this.H.setInt(object, n);
    }

    private void v(Object object, boolean bl) {
        this.L.setBoolean(object, bl);
    }

    private void i(Object object, Object object2) {
        this.o.setObject(object, object2);
    }

    public static void O(MGameSettings mGameSettings, Object object, boolean bl) {
        mGameSettings.U(object, bl);
    }

    private Object A(Object object) {
        return this.N.getObject(object);
    }

    private Object R(Object object) {
        return this.z.getObject(object);
    }

    private void d(Object object, float f) {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(this.G.getObject(object));
            gameSettingsValue.a((int)f);
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            this.G.setDouble(object, f);
            return;
        }
        this.G.setFloat(object, f);
    }

    private Object r(Object object) {
        return this.a.getObject(object);
    }

    private int O(Object object) {
        return this.A.getInt(object);
    }

    private float J(Object object) {
        return this.a.getFloat(object);
    }

    private void U(Object object, boolean bl) {
        if (this.J.hasResolutionFailed()) {
            return;
        }
        this.J.setBoolean(object, bl);
    }

    public static Object u(MGameSettings mGameSettings, Object object) {
        return mGameSettings.b(object);
    }

    public static float C(MGameSettings mGameSettings, Object object) {
        return mGameSettings.Y(object);
    }

    public static Object r(MGameSettings mGameSettings, Object object) {
        return mGameSettings.v(object);
    }

    private Object X$src$Ljava_lang_Object_$frylp3(Object object) {
        return this.p.getObject(object);
    }

    private boolean A$src$Z$nt71g2(Object object) {
        if (this.J.hasResolutionFailed()) {
            return false;
        }
        return this.J.getBoolean(object);
    }

    private boolean p$src$Z$1vyglj5(Object object) {
        return this.L.getBoolean(object);
    }

    private float q(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return (float)this.Z.getDouble(object);
        }
        return this.Z.getFloat(object);
    }

    public static Object X(MGameSettings mGameSettings, Object object) {
        return mGameSettings.G(object);
    }

    private Object w(Object object) {
        return this.D.getObject(object);
    }

    private Object S(Object object) {
        return this.P.getObject(object);
    }

    private List<String> w$src$Ljava_util_List_$1otljmr(Object object) {
        return this.f == null ? null : (List)this.f.getObject(object);
    }

    private Object P(Object object) {
        return this.Y.getObject(object);
    }

    private void q(Object object, float f) {
        if (ForgeVersion.MC_1_16_5.d()) {
            this.z.setDouble(object, f);
            return;
        }
        this.z.setFloat(object, f);
    }

    public static void c(MGameSettings mGameSettings, Object object, float f) {
        mGameSettings.d(object, f);
    }

    private void o(Object object, boolean bl) {
        if (ForgeVersion.MC_26_2.d()) {
            Object object2 = Minecraft.k().getObject();
            Object object3 = this.S.getObject(object2);
            if (this.l.invokeBoolean(object3, new Object[0]) != bl) {
                this.X.invokeVoidNoArgs(object3);
            }
            return;
        }
        this.n.setBoolean(object, bl);
    }

    private Object j(Object object) {
        return this.u.getObject(object);
    }

    private Object G(Object object) {
        return this.q.getObject(object);
    }

    private Object o(Object object) {
        return this.C.getObject(object);
    }

    private Object p(Object object) {
        return this.E.getObject(object);
    }

    public static int h(MGameSettings mGameSettings, Object object) {
        return mGameSettings.X(object);
    }

    public static Object F(MGameSettings mGameSettings, Object object) {
        return mGameSettings.A(object);
    }

    private Object F(Object object) {
        return this.Z.getObject(object);
    }

    public MGameSettings() {
        super(MappedClasses.lT);
        Class clazz = MappedClasses.DR;
        boolean bl = true;
        String string = "keyBindAttack";
        MGameSettings mGameSettings = this;
        this.u = this.J(string, bl, clazz);
        Class clazz2 = MappedClasses.DR;
        boolean bl2 = true;
        String string2 = "keyBindUseItem";
        MGameSettings mGameSettings2 = this;
        this.E = this.J(string2, bl2, clazz2);
        boolean bl3 = MKeyBinding.j();
        Class<Integer> clazz3 = Integer.TYPE;
        boolean bl4 = true;
        String string3 = "guiScale";
        MGameSettings mGameSettings3 = this;
        this.H = this.J(string3, bl4, clazz3);
        Class clazz4 = MappedClasses.DR;
        boolean bl5 = true;
        String string4 = "keyBindInventory";
        MGameSettings mGameSettings4 = this;
        this.Y = this.J(string4, bl5, clazz4);
        Class<Boolean> clazz5 = Boolean.TYPE;
        boolean bl6 = true;
        String string5 = "viewBobbing";
        MGameSettings mGameSettings5 = this;
        this.p = this.J(string5, bl6, clazz5);
        Class<Boolean> clazz6 = Boolean.TYPE;
        boolean bl7 = true;
        boolean bl8 = false;
        String string6 = "ofFastRender";
        MGameSettings mGameSettings6 = this;
        this.J = this.registerInstanceFieldWithSecondaryFlag(string6, bl8, bl7, clazz6);
        Class clazz7 = MappedClasses.DR;
        boolean bl9 = true;
        String string7 = "keyBindForward";
        MGameSettings mGameSettings7 = this;
        this.b = this.J(string7, bl9, clazz7);
        Class clazz8 = MappedClasses.DR;
        boolean bl10 = true;
        String string8 = "keyBindBack";
        MGameSettings mGameSettings8 = this;
        this.i = this.J(string8, bl10, clazz8);
        Class clazz9 = MappedClasses.DR;
        boolean bl11 = true;
        String string9 = "keyBindLeft";
        MGameSettings mGameSettings9 = this;
        this.D = this.J(string9, bl11, clazz9);
        Class clazz10 = MappedClasses.DR;
        boolean bl12 = true;
        String string10 = "keyBindRight";
        MGameSettings mGameSettings10 = this;
        this.N = this.J(string10, bl12, clazz10);
        Class clazz11 = MappedClasses.DR;
        boolean bl13 = true;
        String string11 = "keyBindJump";
        MGameSettings mGameSettings11 = this;
        this.T = this.J(string11, bl13, clazz11);
        Class clazz12 = MappedClasses.DR;
        boolean bl14 = true;
        String string12 = "keyBindSneak";
        MGameSettings mGameSettings12 = this;
        this.P = this.J(string12, bl14, clazz12);
        if (ForgeVersion.MC_1_7_10.L() && !Vape.INSTANCE.isVanillaMinecraftPresent()) {
            Class clazz13 = MappedClasses.DR;
            boolean bl15 = Wrapper.isNativeAvailable;
            String string13 = "field_152395_am";
            MGameSettings mGameSettings13 = this;
            this.C = this.J(string13, bl15, clazz13);
        } else {
            Class clazz14 = MappedClasses.DR;
            boolean bl16 = true;
            String string14 = "keyBindFullscreen";
            MGameSettings mGameSettings14 = this;
            this.C = this.J(string14, bl16, clazz14);
        }
        Class clazz15 = MappedClasses.DR;
        boolean bl17 = true;
        String string15 = "keyBindSprint";
        MGameSettings mGameSettings15 = this;
        this.q = this.J(string15, bl17, clazz15);
        Class clazz16 = MappedClasses.DR;
        boolean bl18 = true;
        String string16 = "keyBindDrop";
        MGameSettings mGameSettings16 = this;
        this.k = this.J(string16, bl18, clazz16);
        if (ForgeVersion.MC_26_2.d()) {
            this.n = null;
            Class clazz17 = MappedClasses.zK;
            boolean bl19 = true;
            String string17 = "hud";
            Class clazz18 = MappedClasses.Zj;
            MGameSettings mGameSettings17 = this;
            this.S = this.registerInstanceFieldForOwner(clazz18, string17, bl19, clazz17);
            Class[] classArray = new Class[]{};
            Class<Boolean> clazz19 = Boolean.TYPE;
            boolean bl20 = true;
            String string18 = "isHidden";
            Class clazz20 = MappedClasses.zK;
            MGameSettings mGameSettings18 = this;
            this.l = this.registerInstanceMethodForOwner(clazz20, string18, bl20, clazz19, classArray);
            Class[] classArray2 = new Class[]{};
            Class<Void> clazz21 = Void.TYPE;
            boolean bl21 = true;
            String string19 = "toggle";
            Class clazz22 = MappedClasses.zK;
            MGameSettings mGameSettings19 = this;
            this.X = this.registerInstanceMethodForOwner(clazz22, string19, bl21, clazz21, classArray2);
        } else {
            Class<Boolean> clazz23 = Boolean.TYPE;
            boolean bl22 = true;
            String string20 = "hideGUI";
            MGameSettings mGameSettings20 = this;
            this.n = this.J(string20, bl22, clazz23);
        }
        Class<Integer> clazz24 = Integer.TYPE;
        boolean bl23 = true;
        String string21 = "renderDistanceChunks";
        MGameSettings mGameSettings21 = this;
        this.B = this.J(string21, bl23, clazz24);
        Class<Boolean> clazz25 = Boolean.TYPE;
        boolean bl24 = true;
        String string22 = "pauseOnLostFocus";
        MGameSettings mGameSettings22 = this;
        this.L = this.J(string22, bl24, clazz25);
        if (ForgeVersion.MC_1_16_5.d()) {
            Class<Double> clazz26 = Double.TYPE;
            boolean bl25 = true;
            String string23 = "gamma";
            MGameSettings mGameSettings23 = this;
            this.z = this.J(string23, bl25, clazz26);
            Class clazz27 = MappedClasses.ZR;
            boolean bl26 = true;
            String string24 = "pointOfView";
            MGameSettings mGameSettings24 = this;
            this.o = this.J(string24, bl26, clazz27);
            Class<Double> clazz28 = Double.TYPE;
            boolean bl27 = true;
            String string25 = "mouseSensitivity";
            MGameSettings mGameSettings25 = this;
            this.Z = this.J(string25, bl27, clazz28);
            Class<Double> clazz29 = Double.TYPE;
            boolean bl28 = true;
            String string26 = "fov";
            MGameSettings mGameSettings26 = this;
            this.G = this.J(string26, bl28, clazz29);
            Class<Float> clazz30 = Float.TYPE;
            boolean bl29 = true;
            String string27 = "screenEffectScale";
            MGameSettings mGameSettings27 = this;
            this.a = this.J(string27, bl29, clazz30);
        } else {
            Class<Boolean> clazz31 = Boolean.TYPE;
            boolean bl30 = true;
            String string28 = "fboEnable";
            MGameSettings mGameSettings28 = this;
            this.r = this.J(string28, bl30, clazz31);
            Class<Float> clazz32 = Float.TYPE;
            boolean bl31 = true;
            String string29 = "gammaSetting";
            MGameSettings mGameSettings29 = this;
            this.z = this.J(string29, bl31, clazz32);
            Class<Integer> clazz33 = Integer.TYPE;
            boolean bl32 = true;
            String string30 = "thirdPersonView";
            MGameSettings mGameSettings30 = this;
            this.A = this.J(string30, bl32, clazz33);
            Class<Float> clazz34 = Float.TYPE;
            boolean bl33 = true;
            String string31 = "mouseSensitivity";
            MGameSettings mGameSettings31 = this;
            this.Z = this.J(string31, bl33, clazz34);
            Class<Float> clazz35 = Float.TYPE;
            boolean bl34 = true;
            String string32 = "fovSetting";
            MGameSettings mGameSettings32 = this;
            this.G = this.J(string32, bl34, clazz35);
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            Class<List> clazz36 = List.class;
            boolean bl35 = true;
            String string33 = "resourcePacks";
            MGameSettings mGameSettings33 = this;
            this.f = this.J(string33, bl35, clazz36);
        }
    }

    private Object n(Object object) {
        return this.b.getObject(object);
    }

    public static Object R(MGameSettings mGameSettings, Object object) {
        return mGameSettings.X$src$Ljava_lang_Object_$frylp3(object);
    }

    public static float V(MGameSettings mGameSettings, Object object) {
        return mGameSettings.q(object);
    }

    public static Object l(MGameSettings mGameSettings, Object object) {
        return mGameSettings.n(object);
    }
}

