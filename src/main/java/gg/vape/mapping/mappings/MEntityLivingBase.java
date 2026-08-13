package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Collection;

public class MEntityLivingBase
extends Mapping {
    private MappingMethod A;
    private MappingMethod F;
    private MappingField V_;
    private MappingMethod o;
    private MappingField N;
    private MappingField Vf;
    private MappingMethod B;
    private MappingMethod b;
    private final MappingMethod Vy;
    private MappingMethod VZ;
    private final MappingMethod Vp;
    private MappingMethod H;
    private MappingMethod e;
    private final MappingMethod G;
    private final MappingMethod g;
    private final MappingField Vo;
    private final MappingMethod Q;
    private final MappingField Vh;
    private final MappingMethod k;
    private MappingMethod T;
    private MappingMethod h;
    private MappingField X;
    public MappingMethod x;
    private final MappingField p;
    private final MappingMethod a;
    private final MappingField V5;
    public MappingMethod O;
    private MappingField w;
    private MappingField W;
    private MappingMethod d;
    private MappingField Y;
    private MappingMethod Z;
    private MappingMethod V3;
    private final MappingField M;
    private final MappingMethod q;
    private final MappingMethod J;
    private MappingMethod D;
    private final MappingField Vc;
    public final MappingMethod U;
    private MappingField P;
    private MappingMethod I;
    private MappingMethod j;
    private MappingField m;
    private MappingMethod V6;
    private final MappingField y;
    private MappingField f;
    private final MappingMethod z;
    private final MappingField l;
    private MappingMethod C;
    private MappingMethod n;
    private MappingMethod K;
    public final MappingMethod s;
    private MappingMethod u;
    private final MappingMethod E;
    private final MappingMethod r;
    private final MappingMethod V;
    private final MappingField v;
    private final MappingMethod S;
    private final MappingMethod Vz;
    private MappingMethod VJ;
    private final MappingField i;
    private MappingMethod VB;
    private final MappingField L;
    private MappingMethod c;

    public static void V(MEntityLivingBase mEntityLivingBase, Object object) {
        mEntityLivingBase.w(object);
    }

    private Object q(Object object, Object object2) {
        return this.x.invokeObject(object, object2);
    }

    private float E(Object object) {
        return this.y.getFloat(object);
    }

    public static boolean p$src$Z$qthfwb(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.T(object);
    }

    public static void h(MEntityLivingBase mEntityLivingBase, Object object) {
        mEntityLivingBase.s(object);
    }

    public static void B(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        mEntityLivingBase.a(object, object2);
    }

    public static int M(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.H(object);
    }

    private float q(Object object) {
        return this.p.getFloat(object);
    }

    private int n(Object object) {
        return this.Vo.getInt(object);
    }

    private void a(Object object, Object object2) {
        this.z.invokeVoid(object, object2);
    }

    private Object G(Object object, Object object2) {
        return this.V.invokeObject(object, object2);
    }

    public static Object s(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.m(object);
    }

    private void g(Object object, float f) {
        this.y.setFloat(object, f);
    }

    public static Object T(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        return mEntityLivingBase.S$src$Ljava_lang_Object_$mij01c(object, object2);
    }

    public static void X(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.A(object, f);
    }

    public static void E(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        mEntityLivingBase.l(object, object2);
    }

    public Object m(Object object, float f) {
        return this.O.invokeObject(object, Float.valueOf(f));
    }

    public Object Y(Object object, double d, float f, boolean bl) {
        return this.r.invokeObject(object, d, Float.valueOf(f), bl);
    }

    private boolean G$src$Z$4wgqw4(Object object, Object object2) {
        return this.s.invokeBoolean(object, object2);
    }

    private float G(Object object) {
        return this.o.invokeFloat(object, new Object[0]);
    }

    public float X(Object object, float f) {
        return this.I.invokeFloat(object, Float.valueOf(f));
    }

    public float J(Object object) {
        return this.L.getFloat(object);
    }

    public static float z(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.q(object);
    }

    private void c$src$V$85q5i8(Object object) {
        this.S.invokeVoidNoArgs(object);
    }


    public static boolean e(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.z(object);
    }

    public static Object I(MEntityLivingBase mEntityLivingBase) {
        return mEntityLivingBase.s();
    }

    public static void e(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.E(object, f);
    }

    public static boolean O$src$Z$3j0onu(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.S(object);
    }

    public static void Q(MEntityLivingBase mEntityLivingBase, Object object) {
        mEntityLivingBase.f$src$V$1xx10rn(object);
    }

    private void s(Object object) {
        this.K.invokeVoidNoArgs(object);
    }

    private float B(Object object) {
        return this.P.getFloat(object);
    }

    private boolean S(Object object) {
        return this.a.invokeBoolean(object, new Object[0]);
    }

    private float L(Object object, float f) {
        return this.F.invokeFloat(object, Float.valueOf(f));
    }

    public static void P(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.I(object, f);
    }

    public static Object C(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.o$src$Ljava_lang_Object_$1loj6t4(object);
    }

    private Object A(Object object) {
        return this.J.invokeObject(object, new Object[0]);
    }

    private boolean T(Object object) {
        return this.V3.invokeBoolean(object, new Object[0]);
    }

    public static float w(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.i$src$F$1on7to6(object);
    }

    public boolean C(Object object, Object object2) {
        return this.G.invokeBoolean(object, object2);
    }

    public static boolean r(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        return mEntityLivingBase.y(object, object2);
    }

    private void A(Object object, float f) {
        this.VB.invokeVoid(object, Float.valueOf(f));
    }

    private void a(Object object) {
        this.U.invokeVoidNoArgs(object);
    }

    public Object w(Object object, float f) {
        return this.Vp.invokeObject(object, Float.valueOf(f));
    }

    public static void c(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.U(object, f);
    }

    private Object m(Object object) {
        return this.Q.invokeObject(object, new Object[0]);
    }

    public static int H(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.n(object);
    }

    private boolean y(Object object, Object object2) {
        return this.d.invokeBoolean(object, object2);
    }

    private float o(Object object) {
        return this.Vh.getFloat(object);
    }

    private Object s() {
        return this.l.getObject(null);
    }

    private float I(Object object) {
        return this.f.getFloat(object);
    }

    private void f$src$V$1xx10rn(Object object) {
        this.n.invokeVoidNoArgs(object);
    }

    public Object i(Object object) {
        return this.c.invokeObject(object, new Object[0]);
    }

    private void Y(Object object, int n) {
        this.Vc.setInt(object, n);
    }

    private Object o$src$Ljava_lang_Object_$1loj6t4(Object object) {
        return this.E.invokeObject(object, new Object[0]);
    }

    public static void Y(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.l(object, f);
    }

    public static void l(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.g(object, f);
    }

    public static void L(MEntityLivingBase mEntityLivingBase, Object object, boolean bl) {
        mEntityLivingBase.Z(object, bl);
    }

    public static float b(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        return mEntityLivingBase.L(object, f);
    }

    private void c(Object object, float f) {
        this.p.setFloat(object, f);
    }

    public static float b(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.E(object);
    }

    public static void S(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.K(object, f);
    }

    public MEntityLivingBase() {
        super(MappedClasses.zm);
        Class<Float> clazz = Float.TYPE;
        boolean bl = true;
        String string = "prevRenderYawOffset";
        MEntityLivingBase mEntityLivingBase = this;
        this.M = this.J(string, bl, clazz);
        Class<Float> clazz2 = Float.TYPE;
        boolean bl2 = true;
        String string2 = "prevRotationYawHead";
        MEntityLivingBase mEntityLivingBase2 = this;
        this.L = this.J(string2, bl2, clazz2);
        int n = MEntity.T();
        if (n != 0) {
            Class<Float> clazz3 = Float.TYPE;
            boolean bl3 = true;
            String string3 = "moveForward";
            MEntityLivingBase mEntityLivingBase3 = this;
            this.v = this.J(string3, bl3, clazz3);
            Class<Float> clazz4 = Float.TYPE;
            boolean bl4 = true;
            String string4 = "moveStrafing";
            MEntityLivingBase mEntityLivingBase4 = this;
            this.y = this.J(string4, bl4, clazz4);
            if (ForgeVersion.MC_1_12_2.d()) {
                Class clazz5 = MappedClasses.z_;
                boolean bl5 = true;
                String string5 = "SPRINTING_SPEED_BOOST";
                MEntityLivingBase mEntityLivingBase5 = this;
                this.l = this.registerStaticField(string5, bl5, clazz5);
                Class[] classArray = new Class[]{};
                Class clazz6 = MappedClasses.VK;
                boolean bl6 = true;
                String string6 = "getHeldItemMainhand";
                MEntityLivingBase mEntityLivingBase6 = this;
                this.Q = this.Y(string6, bl6, clazz6, classArray);
                if (ForgeVersion.MC_1_21_11.d()) {
                    Class[] classArray2 = new Class[]{};
                    Class clazz7 = MappedClasses.VK;
                    boolean bl7 = true;
                    String string7 = "getActiveItem";
                    MEntityLivingBase mEntityLivingBase7 = this;
                    this.V6 = this.Y(string7, bl7, clazz7, classArray2);
                }
                Class[] classArray3 = new Class[]{MappedClasses.Yf};
                Class clazz8 = MappedClasses.VK;
                boolean bl8 = true;
                String string8 = "getHeldItem";
                MEntityLivingBase mEntityLivingBase8 = this;
                this.x = this.Y(string8, bl8, clazz8, classArray3);
                Class[] classArray4 = new Class[]{MappedClasses.Yf};
                Class<Void> clazz9 = Void.TYPE;
                boolean bl9 = true;
                String string9 = "swingArm";
                MEntityLivingBase mEntityLivingBase9 = this;
                this.S = this.Y(string9, bl9, clazz9, classArray4);
                Class[] classArray5 = new Class[]{};
                Class<Boolean> clazz10 = Boolean.TYPE;
                String string10 = "isElytraFlying";
                MEntityLivingBase mEntityLivingBase10 = this;
            this.Z = ((MappingMethodBuilder)this.methodBuilder(string10, clazz10, classArray5).setNameForVersion(ForgeVersion.MC_1_20_6.n(), "isFallFlying")).buildMethod();
                if (ForgeVersion.MC_1_16_5.d()) {
                    Class[] classArray6 = new Class[]{Float.TYPE};
                    Class<Float> clazz11 = Float.TYPE;
                    boolean bl10 = true;
                    String string11 = "getFrictionInfluencedSpeed";
                    MEntityLivingBase mEntityLivingBase11 = this;
                    this.F = this.Y(string11, bl10, clazz11, classArray6);
                    Class[] classArray7 = new Class[]{MappedClasses.qP};
                    Class clazz12 = MappedClasses.qP;
                    boolean bl11 = true;
                    String string12 = "handleOnClimbable";
                    MEntityLivingBase mEntityLivingBase12 = this;
                    this.b = this.Y(string12, bl11, clazz12, classArray7);
                    Class[] classArray8 = new Class[]{MappedClasses.zc};
                    Class<Void> clazz13 = Void.TYPE;
                    boolean bl12 = true;
                    String string13 = "doPush";
                    MEntityLivingBase mEntityLivingBase13 = this;
                    this.VJ = this.Y(string13, bl12, clazz13, classArray8);
                    Class[] classArray9 = new Class[]{MappedClasses.D3};
                    Class<Boolean> clazz14 = Boolean.TYPE;
                    boolean bl13 = true;
                    String string14 = "removePotionEffect";
                    MEntityLivingBase mEntityLivingBase14 = this;
                    this.d = this.Y(string14, bl13, clazz14, classArray9);
                } else {
                    Class[] classArray10 = new Class[]{MappedClasses.zc};
                    Class<Void> clazz15 = Void.TYPE;
                    boolean bl14 = true;
                    String string15 = "collideWithEntity";
                    MEntityLivingBase mEntityLivingBase15 = this;
                    this.VJ = this.Y(string15, bl14, clazz15, classArray10);
                    Class[] classArray11 = new Class[]{MappedClasses.FR};
                    Class<Void> clazz16 = Void.TYPE;
                    boolean bl15 = true;
                    String string16 = "removePotionEffect";
                    MEntityLivingBase mEntityLivingBase16 = this;
                    this.u = this.Y(string16, bl15, clazz16, classArray11);
                }
                if (ForgeVersion.MC_1_21_4.d()) {
                    Class[] classArray12 = new Class[]{};
                    Class<Float> clazz17 = Float.TYPE;
                    String string17 = "getScale";
                    MEntityLivingBase mEntityLivingBase17 = this;
                    this.j = this.methodBuilder(string17, clazz17, classArray12).buildMethod();
                    if (ForgeVersion.MC_1_21_10.v()) {
                        Class<Float> clazz18 = Float.TYPE;
                        String string18 = "appliedScale";
                        MEntityLivingBase mEntityLivingBase18 = this;
                        this.m = this.fieldBuilder(string18, clazz18).buildField();
                    }
                    Class[] classArray13 = new Class[]{};
                    Class<Boolean> clazz19 = Boolean.TYPE;
                    String string19 = "isImmobile";
                    MEntityLivingBase mEntityLivingBase19 = this;
                    this.V3 = this.methodBuilder(string19, clazz19, classArray13).buildMethod();
                    Class[] classArray14 = new Class[]{};
                    Class<Boolean> clazz20 = Boolean.TYPE;
                    String string20 = "shouldDiscardFriction";
                    MEntityLivingBase mEntityLivingBase20 = this;
                    this.C = this.methodBuilder(string20, clazz20, classArray14).buildMethod();
                    Class[] classArray15 = new Class[]{};
                    Class<Double> clazz21 = Double.TYPE;
                    String string21 = "getEffectiveGravity";
                    MEntityLivingBase mEntityLivingBase21 = this;
                    this.e = this.methodBuilder(string21, clazz21, classArray15).buildMethod();
                    Class[] classArray16 = new Class[]{};
                    Class<Void> clazz22 = Void.TYPE;
                    String string22 = "removeFrost";
                    MEntityLivingBase mEntityLivingBase22 = this;
                    this.VZ = this.methodBuilder(string22, clazz22, classArray16).buildMethod();
                    Class[] classArray17 = new Class[]{};
                    Class<Void> clazz23 = Void.TYPE;
                    String string23 = "tryAddFrost";
                    MEntityLivingBase mEntityLivingBase23 = this;
                    this.n = this.methodBuilder(string23, clazz23, classArray17).buildMethod();
                }
                Class[] classArray18 = new Class[]{};
                Class clazz24 = MappedClasses.Yf;
                String string24 = "getActiveHand";
                MEntityLivingBase mEntityLivingBase24 = this;
                this.H = ((MappingMethodBuilder)this.methodBuilder(string24, clazz24, classArray18).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getUsedItemHand")).buildMethod();
                if (ForgeVersion.MC_1_12_2.L()) {
                    Class<Integer> clazz25 = Integer.TYPE;
                    boolean bl16 = true;
                    String string25 = "activeItemStackUseCount";
                    MEntityLivingBase mEntityLivingBase25 = this;
                    this.Vf = this.J(string25, bl16, clazz25);
                } else {
                    Class<Integer> clazz26 = Integer.TYPE;
                    boolean bl17 = true;
                    String string26 = "itemInUseCount";
                    MEntityLivingBase mEntityLivingBase26 = this;
                    this.Vf = this.J(string26, bl17, clazz26);
                }
            } else {
                Class clazz27 = MappedClasses.z_;
                boolean bl18 = true;
                String string27 = "sprintingSpeedBoostModifier";
                MEntityLivingBase mEntityLivingBase27 = this;
                this.l = this.registerStaticField(string27, bl18, clazz27);
                Class[] classArray = new Class[]{};
                Class clazz28 = MappedClasses.VK;
                boolean bl19 = true;
                String string28 = "getHeldItem";
                MEntityLivingBase mEntityLivingBase28 = this;
                this.Q = this.Y(string28, bl19, clazz28, classArray);
                Class[] classArray19 = new Class[]{};
                Class<Void> clazz29 = Void.TYPE;
                boolean bl20 = true;
                String string29 = "swingItem";
                MEntityLivingBase mEntityLivingBase29 = this;
                this.S = this.Y(string29, bl20, clazz29, classArray19);
                Class[] classArray20 = new Class[]{Integer.TYPE};
                Class<Void> clazz30 = Void.TYPE;
                boolean bl21 = true;
                String string30 = "removePotionEffect";
                MEntityLivingBase mEntityLivingBase30 = this;
                this.u = this.Y(string30, bl21, clazz30, classArray20);
            }
            if (ForgeVersion.MC_1_7_10.L()) {
                Class[] classArray = new Class[]{Float.TYPE};
                Class clazz31 = MappedClasses.qP;
                boolean bl22 = true;
                String string31 = "getLook";
                MEntityLivingBase mEntityLivingBase31 = this;
                this.O = this.Y(string31, bl22, clazz31, classArray);
                Class[] classArray21 = new Class[]{Float.TYPE};
                Class clazz32 = MappedClasses.qP;
                boolean bl23 = true;
                String string32 = "getPosition";
                MEntityLivingBase mEntityLivingBase32 = this;
                this.Vp = this.Y(string32, bl23, clazz32, classArray21);
                Class[] classArray22 = new Class[]{Double.TYPE, Float.TYPE};
                Class clazz33 = MappedClasses.DT;
                boolean bl24 = true;
                String string33 = "rayTrace";
                MEntityLivingBase mEntityLivingBase33 = this;
                this.r = this.Y(string33, bl24, clazz33, classArray22);
            } else if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray = new Class[]{Float.TYPE};
                Class clazz34 = MappedClasses.qP;
                boolean bl25 = true;
                String string34 = "getLook";
                Class clazz35 = MappedClasses.zc;
                MEntityLivingBase mEntityLivingBase34 = this;
                this.O = this.registerInstanceMethodForOwner(clazz35, string34, bl25, clazz34, classArray);
                Class[] classArray23 = new Class[]{Float.TYPE};
                Class clazz36 = MappedClasses.qP;
                boolean bl26 = true;
                String string35 = "getEyePosition";
                Class clazz37 = MappedClasses.zc;
                MEntityLivingBase mEntityLivingBase35 = this;
                this.Vp = this.registerInstanceMethodForOwner(clazz37, string35, bl26, clazz36, classArray23);
                Class[] classArray24 = new Class[]{Double.TYPE, Float.TYPE, Boolean.TYPE};
                Class clazz38 = MappedClasses.DT;
                boolean bl27 = true;
                String string36 = "pick";
                Class clazz39 = MappedClasses.zc;
                MEntityLivingBase mEntityLivingBase36 = this;
                this.r = this.registerInstanceMethodForOwner(clazz39, string36, bl27, clazz38, classArray24);
            } else {
                Class[] classArray = new Class[]{Float.TYPE};
                Class clazz40 = MappedClasses.qP;
                boolean bl28 = true;
                String string37 = "getLook";
                Class clazz41 = MappedClasses.zc;
                MEntityLivingBase mEntityLivingBase37 = this;
                this.O = this.registerInstanceMethodForOwner(clazz41, string37, bl28, clazz40, classArray);
                Class[] classArray25 = new Class[]{Float.TYPE};
                Class clazz42 = MappedClasses.qP;
                boolean bl29 = true;
                String string38 = "getPositionEyes";
                Class clazz43 = MappedClasses.zc;
                MEntityLivingBase mEntityLivingBase38 = this;
                this.Vp = this.registerInstanceMethodForOwner(clazz43, string38, bl29, clazz42, classArray25);
                Class[] classArray26 = new Class[]{Double.TYPE, Float.TYPE};
                Class clazz44 = MappedClasses.DT;
                boolean bl30 = true;
                String string39 = "rayTrace";
                Class clazz45 = MappedClasses.zc;
                MEntityLivingBase mEntityLivingBase39 = this;
                this.r = this.registerInstanceMethodForOwner(clazz45, string39, bl30, clazz44, classArray26);
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray = new Class[]{MappedClasses.D3};
                Class<Boolean> clazz46 = Boolean.TYPE;
                boolean bl31 = true;
                String string40 = "isPotionActive";
                MEntityLivingBase mEntityLivingBase40 = this;
                this.s = this.Y(string40, bl31, clazz46, classArray);
                Class[] classArray27 = new Class[]{MappedClasses.D3};
                Class clazz47 = MappedClasses.u3;
                boolean bl32 = true;
                String string41 = "getActivePotionEffect";
                MEntityLivingBase mEntityLivingBase41 = this;
                this.q = this.Y(string41, bl32, clazz47, classArray27);
                Class[] classArray28 = new Class[]{};
                Class clazz48 = MappedClasses.Ya;
                boolean bl33 = true;
                String string42 = "getAttributeManager";
                MEntityLivingBase mEntityLivingBase42 = this;
                this.J = this.Y(string42, bl33, clazz48, classArray28);
                Class[] classArray29 = new Class[]{};
                Class<Void> clazz49 = Void.TYPE;
                boolean bl34 = true;
                String string43 = "livingTick";
                MEntityLivingBase mEntityLivingBase43 = this;
                this.U = this.Y(string43, bl34, clazz49, classArray29);
                if (ForgeVersion.MC_1_20_6.d()) {
                    Class[] classArray30 = new Class[]{MappedClasses.Vo};
                    Class clazz50 = MappedClasses.FJ;
                    boolean bl35 = true;
                    String string44 = "getAttribute";
                    MEntityLivingBase mEntityLivingBase44 = this;
                    this.V = this.Y(string44, bl35, clazz50, classArray30);
                } else {
                    Class[] classArray31 = new Class[]{MappedClasses.Fe};
                    Class clazz51 = MappedClasses.FJ;
                    boolean bl36 = true;
                    String string45 = "getAttribute";
                    MEntityLivingBase mEntityLivingBase45 = this;
                    this.V = this.Y(string45, bl36, clazz51, classArray31);
                }
                Class[] classArray32 = new Class[]{};
                Class<Boolean> clazz52 = Boolean.TYPE;
                String string46 = "canStandOnFluid";
                MEntityLivingBase mEntityLivingBase46 = this;
                this.D = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string46, clazz52, classArray32).setParameterTypesForVersion(ForgeVersion.MC_1_20_6.b(), MappedClasses.VR).setNameForVersion(ForgeVersion.MC_1_20_6.b(), "func_230285_a_")).setMappedMemberForVersion(ForgeVersion.MC_1_20_6.b(), Wrapper.isNativeAvailable)).setParameterTypesForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.Dw).buildMethod();
                if (ForgeVersion.MC_1_17.d()) {
                    Class[] classArray33 = new Class[]{MappedClasses.u3};
                    Class<Boolean> clazz53 = Boolean.TYPE;
                    boolean bl37 = ForgeVersion.MC_1_20_6.d();
                    String string47 = "m_7292_";
                    MEntityLivingBase mEntityLivingBase47 = this;
                    this.z = this.Y(string47, bl37, clazz53, classArray33);
                } else {
                    Class[] classArray34 = new Class[]{MappedClasses.u3};
                    Class<Boolean> clazz54 = Boolean.TYPE;
                    boolean bl38 = true;
                    String string48 = "addPotionEffect";
                    MEntityLivingBase mEntityLivingBase48 = this;
                    this.z = this.Y(string48, bl38, clazz54, classArray34);
                }
                Class[] classArray35 = new Class[]{};
                Class<Void> clazz55 = Void.TYPE;
                boolean bl39 = true;
                String string49 = "jumpFromGround";
                MEntityLivingBase mEntityLivingBase49 = this;
                this.K = this.Y(string49, bl39, clazz55, classArray35);
                Class<Boolean> clazz56 = Boolean.TYPE;
                boolean bl40 = true;
                String string50 = "jumping";
                MEntityLivingBase mEntityLivingBase50 = this;
                this.W = this.J(string50, bl40, clazz56);
                Class clazz57 = MappedClasses.Ya;
                boolean bl41 = true;
                String string51 = "attributes";
                MEntityLivingBase mEntityLivingBase51 = this;
                this.V_ = this.J(string51, bl41, clazz57);
                Class[] classArray36 = new Class[]{Double.TYPE, Boolean.TYPE, MappedClasses.qP};
                Class clazz58 = MappedClasses.qP;
                String string52 = "getFluidFallingAdjustedMovement";
                MEntityLivingBase mEntityLivingBase52 = this;
                this.A = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string52, clazz58, classArray36).setNameForVersion(ForgeVersion.MC_1_20_6.b(), "func_233626_a_")).setMappedMemberForVersion(ForgeVersion.MC_1_20_6.b(), Wrapper.isNativeAvailable)).buildMethod();
                Class[] classArray37 = new Class[]{};
                Class<Float> clazz59 = Float.TYPE;
                boolean bl42 = true;
                String string53 = "getWaterSlowDown";
                MEntityLivingBase mEntityLivingBase53 = this;
                this.o = this.Y(string53, bl42, clazz59, classArray37);
                Class[] classArray38 = new Class[]{};
                Class<Double> clazz60 = Double.TYPE;
                String string54 = "getAttributeValue";
                MEntityLivingBase mEntityLivingBase54 = this;
                this.B = this.methodBuilder(string54, clazz60, classArray38).setParameterTypesForVersion(ForgeVersion.MC_1_20_6.b(), MappedClasses.Fe).setParameterTypesForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.Vo).buildMethod();
            } else {
                Class[] classArray = new Class[]{MappedClasses.FR};
                Class<Boolean> clazz61 = Boolean.TYPE;
                boolean bl43 = true;
                String string55 = "isPotionActive";
                MEntityLivingBase mEntityLivingBase55 = this;
                this.s = this.Y(string55, bl43, clazz61, classArray);
                Class[] classArray39 = new Class[]{MappedClasses.FR};
                Class clazz62 = MappedClasses.u3;
                boolean bl44 = true;
                String string56 = "getActivePotionEffect";
                MEntityLivingBase mEntityLivingBase56 = this;
                this.q = this.Y(string56, bl44, clazz62, classArray39);
                Class[] classArray40 = new Class[]{};
                Class clazz63 = MappedClasses.Ya;
                boolean bl45 = true;
                String string57 = "getAttributeMap";
                MEntityLivingBase mEntityLivingBase57 = this;
                this.J = this.Y(string57, bl45, clazz63, classArray40);
                Class[] classArray41 = new Class[]{};
                Class<Void> clazz64 = Void.TYPE;
                boolean bl46 = true;
                String string58 = "onLivingUpdate";
                MEntityLivingBase mEntityLivingBase58 = this;
                this.U = this.Y(string58, bl46, clazz64, classArray41);
                Class[] classArray42 = new Class[]{MappedClasses.Fe};
                Class clazz65 = MappedClasses.FJ;
                boolean bl47 = true;
                String string59 = "getEntityAttribute";
                MEntityLivingBase mEntityLivingBase59 = this;
                this.V = this.Y(string59, bl47, clazz65, classArray42);
                Class[] classArray43 = new Class[]{MappedClasses.u3};
                Class<Void> clazz66 = Void.TYPE;
                boolean bl48 = true;
                String string60 = "addPotionEffect";
                MEntityLivingBase mEntityLivingBase60 = this;
                this.z = this.Y(string60, bl48, clazz66, classArray43);
                Class[] classArray44 = new Class[]{};
                Class<Void> clazz67 = Void.TYPE;
                boolean bl49 = true;
                String string61 = "jump";
                MEntityLivingBase mEntityLivingBase61 = this;
                this.K = this.Y(string61, bl49, clazz67, classArray44);
                Class<Boolean> clazz68 = Boolean.TYPE;
                boolean bl50 = true;
                String string62 = "isJumping";
                MEntityLivingBase mEntityLivingBase62 = this;
                this.W = this.J(string62, bl50, clazz68);
                Class clazz69 = MappedClasses.Ya;
                boolean bl51 = true;
                String string63 = "attributeMap";
                MEntityLivingBase mEntityLivingBase63 = this;
                this.V_ = this.J(string63, bl51, clazz69);
            }
            if (ForgeVersion.MC_1_20_6.d()) {
                Class clazz70 = MappedClasses.VB;
                boolean bl52 = true;
                String string64 = "walkAnimation";
                MEntityLivingBase mEntityLivingBase64 = this;
                this.N = this.J(string64, bl52, clazz70);
            } else {
                Class<Float> clazz71 = Float.TYPE;
                boolean bl53 = true;
                String string65 = "limbSwing";
                MEntityLivingBase mEntityLivingBase65 = this;
                this.P = this.J(string65, bl53, clazz71);
                Class<Float> clazz72 = Float.TYPE;
                boolean bl54 = true;
                String string66 = "limbSwingAmount";
                MEntityLivingBase mEntityLivingBase66 = this;
                this.Y = this.J(string66, bl54, clazz72);
                Class<Float> clazz73 = Float.TYPE;
                boolean bl55 = true;
                String string67 = "prevLimbSwingAmount";
                MEntityLivingBase mEntityLivingBase67 = this;
                this.X = this.J(string67, bl55, clazz73);
                Class[] classArray = new Class[]{};
                Class clazz74 = MappedClasses.O;
                boolean bl56 = true;
                String string68 = "getCreatureAttribute";
                MEntityLivingBase mEntityLivingBase68 = this;
                this.c = this.Y(string68, bl56, clazz74, classArray);
            }
            Class<Float> clazz75 = Float.TYPE;
            boolean bl57 = true;
            String string69 = "rotationYawHead";
            MEntityLivingBase mEntityLivingBase69 = this;
            this.p = this.J(string69, bl57, clazz75);
            Class<Boolean> clazz76 = Boolean.TYPE;
            boolean bl58 = true;
            String string70 = "isSwingInProgress";
            MEntityLivingBase mEntityLivingBase70 = this;
            this.i = this.J(string70, bl58, clazz76);
            Class<Integer> clazz77 = Integer.TYPE;
            boolean bl59 = true;
            String string71 = "deathTime";
            MEntityLivingBase mEntityLivingBase71 = this;
            this.Vo = this.J(string71, bl59, clazz77);
            Class[] classArray = new Class[]{};
            Class<Float> clazz78 = Float.TYPE;
            boolean bl60 = true;
            String string72 = "getHealth";
            MEntityLivingBase mEntityLivingBase72 = this;
            this.k = this.Y(string72, bl60, clazz78, classArray);
            Class[] classArray45 = new Class[]{};
            Class<Float> clazz79 = Float.TYPE;
            boolean bl61 = true;
            String string73 = "getAbsorptionAmount";
            MEntityLivingBase mEntityLivingBase73 = this;
            this.Vz = this.Y(string73, bl61, clazz79, classArray45);
            Class[] classArray46 = new Class[]{MappedClasses.zc};
            Class<Boolean> clazz80 = Boolean.TYPE;
            boolean bl62 = true;
            String string74 = "canEntityBeSeen";
            MEntityLivingBase mEntityLivingBase74 = this;
            this.G = this.Y(string74, bl62, clazz80, classArray46);
            Class[] classArray47 = new Class[]{};
            Class<Collection> clazz81 = Collection.class;
            boolean bl63 = true;
            String string75 = "getActivePotionEffects";
            MEntityLivingBase mEntityLivingBase75 = this;
            this.E = this.Y(string75, bl63, clazz81, classArray47);
            Class[] classArray48 = new Class[]{};
            Class<Void> clazz82 = Void.TYPE;
            boolean bl64 = true;
            String string76 = "updatePotionEffects";
            MEntityLivingBase mEntityLivingBase76 = this;
            this.Vy = this.Y(string76, bl64, clazz82, classArray48);
            Class[] classArray49 = new Class[]{};
            Class<Boolean> clazz83 = Boolean.TYPE;
            boolean bl65 = true;
            String string77 = "isOnLadder";
            MEntityLivingBase mEntityLivingBase77 = this;
            this.a = this.Y(string77, bl65, clazz83, classArray49);
            Class[] classArray50 = new Class[]{};
            Class<Float> clazz84 = Float.TYPE;
            boolean bl66 = true;
            String string78 = "getMaxHealth";
            MEntityLivingBase mEntityLivingBase78 = this;
            this.g = this.Y(string78, bl66, clazz84, classArray50);
            if (ForgeVersion.MC_1_20_6.d()) {
                Class[] classArray51 = new Class[]{};
                Class<Float> clazz85 = Float.TYPE;
                boolean bl67 = true;
                String string79 = "getFlyingSpeed";
                MEntityLivingBase mEntityLivingBase79 = this;
                this.T = this.Y(string79, bl67, clazz85, classArray51);
            }
            Class<Float> clazz86 = Float.TYPE;
            String string80 = "jumpMovementFactor";
            MEntityLivingBase mEntityLivingBase80 = this;
            this.f = ((MappingFieldBuilder)this.fieldBuilder(string80, clazz86).setNameForVersion(ForgeVersion.MC_1_20_6.n(), "speed")).buildField();
            Class<Float> clazz87 = Float.TYPE;
            boolean bl68 = true;
            String string81 = "renderYawOffset";
            MEntityLivingBase mEntityLivingBase81 = this;
            this.Vh = this.J(string81, bl68, clazz87);
            Class<Integer> clazz88 = Integer.TYPE;
            boolean bl69 = true;
            String string82 = "jumpTicks";
            MEntityLivingBase mEntityLivingBase82 = this;
            this.Vc = this.J(string82, bl69, clazz88);
            Class<Integer> clazz89 = Integer.TYPE;
            boolean bl70 = true;
            String string83 = "hurtTime";
            MEntityLivingBase mEntityLivingBase83 = this;
            this.V5 = this.J(string83, bl70, clazz89);
            Class<Integer> clazz90 = Integer.TYPE;
            boolean bl71 = true;
            String string84 = "swingProgressInt";
            MEntityLivingBase mEntityLivingBase84 = this;
            this.w = this.J(string84, bl71, clazz90);
            if (ForgeVersion.MC_1_8_9.B()) {
                Class[] classArray52 = new Class[]{Float.TYPE};
                Class<Float> clazz91 = Float.TYPE;
                boolean bl72 = true;
                String string85 = "getSwingProgress";
                MEntityLivingBase mEntityLivingBase85 = this;
                this.I = this.Y(string85, bl72, clazz91, classArray52);
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_20_6.v()) {
                    Class[] classArray53 = new Class[]{Float.TYPE};
                    Class<Void> clazz92 = Void.TYPE;
                    boolean bl73 = Wrapper.isNativeAvailable;
                    String string86 = "func_70659_e";
                    MEntityLivingBase mEntityLivingBase86 = this;
                    this.VB = this.Y(string86, bl73, clazz92, classArray53);
                } else {
                    Class[] classArray54 = new Class[]{Float.TYPE};
                    Class<Void> clazz93 = Void.TYPE;
                    boolean bl74 = true;
                    String string87 = "setAIMoveSpeed";
                    MEntityLivingBase mEntityLivingBase87 = this;
                    this.VB = this.Y(string87, bl74, clazz93, classArray54);
                }
            } else {
                Class[] classArray55 = new Class[]{Float.TYPE};
                Class<Void> clazz94 = Void.TYPE;
                boolean bl75 = true;
                String string88 = "setAIMoveSpeed";
                MEntityLivingBase mEntityLivingBase88 = this;
                this.VB = this.Y(string88, bl75, clazz94, classArray55);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MEntity.U(++n);
            }
            return;
        }
        Class<Float> clazz95 = Float.TYPE;
        boolean bl76 = true;
        String string89 = "moveForward";
        MEntityLivingBase mEntityLivingBase89 = this;
        this.v = this.J(string89, bl76, clazz95);
        Class<Float> clazz96 = Float.TYPE;
        boolean bl77 = true;
        String string90 = "moveStrafing";
        MEntityLivingBase mEntityLivingBase90 = this;
        this.y = this.J(string90, bl77, clazz96);
        Class[] classArray = new Class[]{Integer.TYPE};
        Class<Void> clazz97 = Void.TYPE;
        boolean bl78 = true;
        String string91 = "removePotionEffect";
        MEntityLivingBase mEntityLivingBase91 = this;
        this.u = this.Y(string91, bl78, clazz97, classArray);
        if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray56 = new Class[]{Float.TYPE};
            Class clazz98 = MappedClasses.qP;
            boolean bl79 = true;
            String string92 = "getLook";
            Class clazz99 = MappedClasses.zc;
            MEntityLivingBase mEntityLivingBase92 = this;
            this.O = this.registerInstanceMethodForOwner(clazz99, string92, bl79, clazz98, classArray56);
            Class[] classArray57 = new Class[]{Float.TYPE};
            Class clazz100 = MappedClasses.qP;
            boolean bl80 = true;
            String string93 = "getEyePosition";
            Class clazz101 = MappedClasses.zc;
            MEntityLivingBase mEntityLivingBase93 = this;
            mEntityLivingBase93.registerInstanceMethodForOwner(clazz101, string93, bl80, clazz100, classArray57);
            Class[] classArray58 = new Class[]{Double.TYPE, Float.TYPE, Boolean.TYPE};
            Class clazz102 = MappedClasses.DT;
            boolean bl81 = true;
            String string94 = "pick";
            Class clazz103 = MappedClasses.zc;
            MEntityLivingBase mEntityLivingBase94 = this;
            mEntityLivingBase94.registerInstanceMethodForOwner(clazz103, string94, bl81, clazz102, classArray58);
        }
        Class[] classArray59 = new Class[]{Float.TYPE};
        Class clazz104 = MappedClasses.qP;
        boolean bl82 = true;
        String string95 = "getLook";
        Class clazz105 = MappedClasses.zc;
        MEntityLivingBase mEntityLivingBase95 = this;
        this.O = this.registerInstanceMethodForOwner(clazz105, string95, bl82, clazz104, classArray59);
        Class[] classArray60 = new Class[]{Float.TYPE};
        Class clazz106 = MappedClasses.qP;
        boolean bl83 = true;
        String string96 = "getPositionEyes";
        Class clazz107 = MappedClasses.zc;
        MEntityLivingBase mEntityLivingBase96 = this;
        this.Vp = this.registerInstanceMethodForOwner(clazz107, string96, bl83, clazz106, classArray60);
        Class[] classArray61 = new Class[]{Double.TYPE, Float.TYPE};
        Class clazz108 = MappedClasses.DT;
        boolean bl84 = true;
        String string97 = "rayTrace";
        Class clazz109 = MappedClasses.zc;
        MEntityLivingBase mEntityLivingBase97 = this;
        this.r = this.registerInstanceMethodForOwner(clazz109, string97, bl84, clazz108, classArray61);
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray62 = new Class[]{MappedClasses.Vo};
            Class clazz110 = MappedClasses.FJ;
            boolean bl85 = true;
            String string98 = "getAttribute";
            MEntityLivingBase mEntityLivingBase98 = this;
            mEntityLivingBase98.Y(string98, bl85, clazz110, classArray62);
        }
        Class[] classArray63 = new Class[]{MappedClasses.Fe};
        Class clazz111 = MappedClasses.FJ;
        boolean bl86 = true;
        String string99 = "getAttribute";
        MEntityLivingBase mEntityLivingBase99 = this;
        mEntityLivingBase99.Y(string99, bl86, clazz111, classArray63);
        Class[] classArray64 = new Class[]{};
        Class<Boolean> clazz112 = Boolean.TYPE;
        String string100 = "canStandOnFluid";
        MEntityLivingBase mEntityLivingBase100 = this;
        ((MappingMethodBuilder)((MappingMethodBuilder)mEntityLivingBase100.methodBuilder(string100, clazz112, classArray64).setParameterTypesForVersion(ForgeVersion.MC_1_20_6.b(), MappedClasses.VR).setNameForVersion(ForgeVersion.MC_1_20_6.b(), "func_230285_a_")).setMappedMemberForVersion(ForgeVersion.MC_1_20_6.b(), Wrapper.isNativeAvailable)).setParameterTypesForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.Dw).buildMethod();
        Class[] classArray65 = new Class[]{};
        Class<Void> clazz113 = Void.TYPE;
        boolean bl87 = true;
        String string101 = "jumpFromGround";
        MEntityLivingBase mEntityLivingBase101 = this;
        this.K = this.Y(string101, bl87, clazz113, classArray65);
        Class<Boolean> clazz114 = Boolean.TYPE;
        boolean bl88 = true;
        String string102 = "jumping";
        MEntityLivingBase mEntityLivingBase102 = this;
        this.W = this.J(string102, bl88, clazz114);
        Class clazz115 = MappedClasses.Ya;
        boolean bl89 = true;
        String string103 = "attributes";
        MEntityLivingBase mEntityLivingBase103 = this;
        this.V_ = this.J(string103, bl89, clazz115);
        Class[] classArray66 = new Class[]{Double.TYPE, Boolean.TYPE, MappedClasses.qP};
        Class clazz116 = MappedClasses.qP;
        String string104 = "getFluidFallingAdjustedMovement";
        MEntityLivingBase mEntityLivingBase104 = this;
        this.A = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string104, clazz116, classArray66).setNameForVersion(ForgeVersion.MC_1_20_6.b(), "func_233626_a_")).setMappedMemberForVersion(ForgeVersion.MC_1_20_6.b(), Wrapper.isNativeAvailable)).buildMethod();
        Class[] classArray67 = new Class[]{};
        Class<Float> clazz117 = Float.TYPE;
        boolean bl90 = true;
        String string105 = "getWaterSlowDown";
        MEntityLivingBase mEntityLivingBase105 = this;
        this.o = this.Y(string105, bl90, clazz117, classArray67);
        Class[] classArray68 = new Class[]{};
        Class<Double> clazz118 = Double.TYPE;
        String string106 = "getAttributeValue";
        MEntityLivingBase mEntityLivingBase106 = this;
        this.B = this.methodBuilder(string106, clazz118, classArray68).setParameterTypesForVersion(ForgeVersion.MC_1_20_6.b(), MappedClasses.Fe).setParameterTypesForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.Vo).buildMethod();
        Class[] classArray69 = new Class[]{MappedClasses.FR};
        Class<Boolean> clazz119 = Boolean.TYPE;
        boolean bl91 = true;
        String string107 = "isPotionActive";
        MEntityLivingBase mEntityLivingBase107 = this;
        this.s = this.Y(string107, bl91, clazz119, classArray69);
        Class[] classArray70 = new Class[]{MappedClasses.FR};
        Class clazz120 = MappedClasses.u3;
        boolean bl92 = true;
        String string108 = "getActivePotionEffect";
        MEntityLivingBase mEntityLivingBase108 = this;
        this.q = this.Y(string108, bl92, clazz120, classArray70);
        Class[] classArray71 = new Class[]{};
        Class clazz121 = MappedClasses.Ya;
        boolean bl93 = true;
        String string109 = "getAttributeMap";
        MEntityLivingBase mEntityLivingBase109 = this;
        this.J = this.Y(string109, bl93, clazz121, classArray71);
        Class[] classArray72 = new Class[]{};
        Class<Void> clazz122 = Void.TYPE;
        boolean bl94 = true;
        String string110 = "onLivingUpdate";
        MEntityLivingBase mEntityLivingBase110 = this;
        this.U = this.Y(string110, bl94, clazz122, classArray72);
        Class[] classArray73 = new Class[]{MappedClasses.Fe};
        Class clazz123 = MappedClasses.FJ;
        boolean bl95 = true;
        String string111 = "getEntityAttribute";
        MEntityLivingBase mEntityLivingBase111 = this;
        this.V = this.Y(string111, bl95, clazz123, classArray73);
        Class[] classArray74 = new Class[]{MappedClasses.u3};
        Class<Void> clazz124 = Void.TYPE;
        boolean bl96 = true;
        String string112 = "addPotionEffect";
        MEntityLivingBase mEntityLivingBase112 = this;
        this.z = this.Y(string112, bl96, clazz124, classArray74);
        Class[] classArray75 = new Class[]{};
        Class<Void> clazz125 = Void.TYPE;
        boolean bl97 = true;
        String string113 = "jump";
        MEntityLivingBase mEntityLivingBase113 = this;
        this.K = this.Y(string113, bl97, clazz125, classArray75);
        Class<Boolean> clazz126 = Boolean.TYPE;
        boolean bl98 = true;
        String string114 = "isJumping";
        MEntityLivingBase mEntityLivingBase114 = this;
        this.W = this.J(string114, bl98, clazz126);
        Class clazz127 = MappedClasses.Ya;
        boolean bl99 = true;
        String string115 = "attributeMap";
        MEntityLivingBase mEntityLivingBase115 = this;
        this.V_ = this.J(string115, bl99, clazz127);
        if (ForgeVersion.MC_1_20_6.d()) {
            Class clazz128 = MappedClasses.VB;
            boolean bl100 = true;
            String string116 = "walkAnimation";
            MEntityLivingBase mEntityLivingBase116 = this;
            this.N = this.J(string116, bl100, clazz128);
        }
        Class<Float> clazz129 = Float.TYPE;
        boolean bl101 = true;
        String string117 = "limbSwing";
        MEntityLivingBase mEntityLivingBase117 = this;
        this.P = this.J(string117, bl101, clazz129);
        Class<Float> clazz130 = Float.TYPE;
        boolean bl102 = true;
        String string118 = "limbSwingAmount";
        MEntityLivingBase mEntityLivingBase118 = this;
        this.Y = this.J(string118, bl102, clazz130);
        Class<Float> clazz131 = Float.TYPE;
        boolean bl103 = true;
        String string119 = "prevLimbSwingAmount";
        MEntityLivingBase mEntityLivingBase119 = this;
        this.X = this.J(string119, bl103, clazz131);
        Class[] classArray76 = new Class[]{};
        Class clazz132 = MappedClasses.O;
        boolean bl104 = true;
        String string120 = "getCreatureAttribute";
        MEntityLivingBase mEntityLivingBase120 = this;
        this.c = this.Y(string120, bl104, clazz132, classArray76);
        Class<Float> clazz133 = Float.TYPE;
        boolean bl105 = true;
        String string121 = "rotationYawHead";
        MEntityLivingBase mEntityLivingBase121 = this;
        this.p = this.J(string121, bl105, clazz133);
        Class<Boolean> clazz134 = Boolean.TYPE;
        boolean bl106 = true;
        String string122 = "isSwingInProgress";
        MEntityLivingBase mEntityLivingBase122 = this;
        this.i = this.J(string122, bl106, clazz134);
        Class<Integer> clazz135 = Integer.TYPE;
        boolean bl107 = true;
        String string123 = "deathTime";
        MEntityLivingBase mEntityLivingBase123 = this;
        this.Vo = this.J(string123, bl107, clazz135);
        Class[] classArray77 = new Class[]{};
        Class<Float> clazz136 = Float.TYPE;
        boolean bl108 = true;
        String string124 = "getHealth";
        MEntityLivingBase mEntityLivingBase124 = this;
        this.k = this.Y(string124, bl108, clazz136, classArray77);
        Class[] classArray78 = new Class[]{};
        Class<Float> clazz137 = Float.TYPE;
        boolean bl109 = true;
        String string125 = "getAbsorptionAmount";
        MEntityLivingBase mEntityLivingBase125 = this;
        this.Vz = this.Y(string125, bl109, clazz137, classArray78);
        Class[] classArray79 = new Class[]{MappedClasses.zc};
        Class<Boolean> clazz138 = Boolean.TYPE;
        boolean bl110 = true;
        String string126 = "canEntityBeSeen";
        MEntityLivingBase mEntityLivingBase126 = this;
        this.G = this.Y(string126, bl110, clazz138, classArray79);
        Class[] classArray80 = new Class[]{};
        Class<Collection> clazz139 = Collection.class;
        boolean bl111 = true;
        String string127 = "getActivePotionEffects";
        MEntityLivingBase mEntityLivingBase127 = this;
        this.E = this.Y(string127, bl111, clazz139, classArray80);
        Class[] classArray81 = new Class[]{};
        Class<Void> clazz140 = Void.TYPE;
        boolean bl112 = true;
        String string128 = "updatePotionEffects";
        MEntityLivingBase mEntityLivingBase128 = this;
        this.Vy = this.Y(string128, bl112, clazz140, classArray81);
        Class[] classArray82 = new Class[]{};
        Class<Boolean> clazz141 = Boolean.TYPE;
        boolean bl113 = true;
        String string129 = "isOnLadder";
        MEntityLivingBase mEntityLivingBase129 = this;
        this.a = this.Y(string129, bl113, clazz141, classArray82);
        Class[] classArray83 = new Class[]{};
        Class<Float> clazz142 = Float.TYPE;
        boolean bl114 = true;
        String string130 = "getMaxHealth";
        MEntityLivingBase mEntityLivingBase130 = this;
        this.g = this.Y(string130, bl114, clazz142, classArray83);
        if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray84 = new Class[]{Float.TYPE};
            Class<Void> clazz143 = Void.TYPE;
            boolean bl115 = Wrapper.isNativeAvailable;
            String string131 = "func_70659_e";
            MEntityLivingBase mEntityLivingBase131 = this;
            this.VB = this.Y(string131, bl115, clazz143, classArray84);
        }
        Class[] classArray85 = new Class[]{Float.TYPE};
        Class<Void> clazz144 = Void.TYPE;
        boolean bl116 = true;
        String string132 = "setAIMoveSpeed";
        MEntityLivingBase mEntityLivingBase132 = this;
        this.VB = this.Y(string132, bl116, clazz144, classArray85);
        Class[] classArray86 = new Class[]{Float.TYPE};
        Class<Void> clazz145 = Void.TYPE;
        boolean bl117 = true;
        String string133 = "setAIMoveSpeed";
        MEntityLivingBase mEntityLivingBase133 = this;
        this.VB = this.Y(string133, bl117, clazz145, classArray86);
        if (GuiComponent.getLegacyComponentState() == null) {
            MEntity.U(++n);
        }
        this.Q = null;
        this.l = null;
        this.S = null;
        this.Vh = null;
        this.Vc = null;
        this.V5 = null;
    }

    private Object S$src$Ljava_lang_Object_$mij01c(Object object, Object object2) {
        return this.q.invokeObject(object, object2);
    }

    private void K(Object object, float f) {
        this.f.setFloat(object, f);
    }

    public static void w$src$V$rgd406(MEntityLivingBase mEntityLivingBase, Object object) {
        mEntityLivingBase.c$src$V$85q5i8(object);
    }

    private boolean m(Object object, Object object2) {
        return this.D.invokeBoolean(object, object2);
    }

    public static Object z$src$Ljava_lang_Object_$rov6uz(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.A(object);
    }

    private Object f$src$Ljava_lang_Object_$1h86rmp(Object object) {
        return this.V6 == null ? null : this.V6.invokeObject(object, new Object[0]);
    }

    public static boolean N$src$Z$dl1vrt(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.l$src$Z$1fdenfp(object);
    }

    private float P(Object object) {
        return this.m.getFloat(object);
    }

    private int H(Object object) {
        return this.Vc.getInt(object);
    }

    public static Object f(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        return mEntityLivingBase.q(object, object2);
    }

    public Object u(Object object) {
        return this.N.getObject(object);
    }

    public static void d(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.r(object, f);
    }

    private boolean l$src$Z$1fdenfp(Object object) {
        return this.C.invokeBoolean(object, new Object[0]);
    }

    public static float O(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.o(object);
    }

    public static double n(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.x(object);
    }

    private double x(Object object) {
        return this.e.invokeDouble(object, new Object[0]);
    }

    private float l(Object object) {
        return this.j.invokeFloat(object, new Object[0]);
    }

    public static void A$src$V$299c5s(MEntityLivingBase mEntityLivingBase, Object object) {
        mEntityLivingBase.a(object);
    }

    public Object r(Object object) {
        return this.H.invokeObject(object, new Object[0]);
    }

    public float f(Object object) {
        return this.Vz.invokeFloat(object, new Object[0]);
    }

    public static void W(MEntityLivingBase mEntityLivingBase, Object object, int n) {
        mEntityLivingBase.Y(object, n);
    }

    public int U(Object object) {
        return this.w.getInt(object);
    }

    private void w(Object object) {
        this.VZ.invokeVoidNoArgs(object);
    }

    public static void F(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.e(object, f);
    }

    public static Object G(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        return mEntityLivingBase.G(object, object2);
    }

    public static float o(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.l(object);
    }

    public static void H(MEntityLivingBase mEntityLivingBase, Object object, int n) {
        mEntityLivingBase.U(object, n);
    }

    public static float A(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.F(object);
    }

    private boolean z(Object object) {
        return this.W.getBoolean(object);
    }

    public void G(Object object, boolean bl) {
        this.i.setBoolean(object, bl);
    }

    private void e(Object object, float f) {
        this.Y.setFloat(object, f);
    }

    private void L(Object object, Object object2) {
        this.V_.setObject(object, object2);
    }

    public static void q(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.o(object, f);
    }

    private Object I(Object object, Object object2) {
        return this.b.invokeObject(object, object2);
    }

    private boolean w$src$Z$1sq6c4w(Object object) {
        return this.i.getBoolean(object);
    }

    private float N(Object object) {
        return this.k.invokeFloat(object, new Object[0]);
    }

    public static boolean o$src$Z$10vin0a(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.N$src$Z$21aemf(object);
    }

    public static void J(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.c(object, f);
    }

    public static Object P(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.f$src$Ljava_lang_Object_$1h86rmp(object);
    }

    public Object v(Object object, double d, float f) {
        return this.r.invokeObject(object, d, Float.valueOf(f));
    }

    private void k(Object object, int n) {
        this.u.invokeVoid(object, n);
    }

    private void l(Object object, Object object2) {
        this.u.invokeVoid(object, object2);
    }

    public static boolean c$src$Z$uftdxu(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        return mEntityLivingBase.m(object, object2);
    }

    private void o(Object object, float f) {
        this.m.setFloat(object, f);
    }

    public static void L(MEntityLivingBase mEntityLivingBase, Object object, float f) {
        mEntityLivingBase.H(object, f);
    }

    private float M(Object object) {
        return this.Y.getFloat(object);
    }

    public static float L(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.G(object);
    }

    public static boolean c(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.w$src$Z$1sq6c4w(object);
    }

    public static float N(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.B(object);
    }

    public static Object R(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        return mEntityLivingBase.I(object, object2);
    }

    private Object q(Object object, double d, boolean bl, Object object2) {
        return this.A.invokeObject(object, d, bl, object2);
    }

    private float F(Object object) {
        return this.X.getFloat(object);
    }

    private void E(Object object, float f) {
        this.L.setFloat(object, f);
    }

    private float c(Object object) {
        return this.g.invokeFloat(object, new Object[0]);
    }

    private double S(Object object, Object object2) {
        return this.B.invokeDouble(object, object2);
    }

    public int Y(Object object) {
        return this.Vf.getInt(object);
    }

    public static float v(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.c(object);
    }

    public static float Y(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.P(object);
    }

    public static float I(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.N(object);
    }

    public static double c(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        return mEntityLivingBase.S(object, object2);
    }

    public static void P(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        mEntityLivingBase.L(object, object2);
    }

    public static boolean m(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        return mEntityLivingBase.G$src$Z$4wgqw4(object, object2);
    }

    public static float p(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.M(object);
    }

    public float C(Object object) {
        return this.M.getFloat(object);
    }

    private void U(Object object, int n) {
        this.V5.setInt(object, n);
    }

    public void d(Object object, int n) {
        this.w.setInt(object, n);
    }

    public static int Z(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.x$src$I$ea5w6a(object);
    }

    private void k(Object object) {
        this.Vy.invokeVoidNoArgs(object);
    }

    private float i$src$F$1on7to6(Object object) {
        return this.v.getFloat(object);
    }

    private void E(Object object, Object object2) {
        this.S.invokeVoid(object, object2);
    }

    private void Z(Object object, boolean bl) {
        this.W.setBoolean(object, bl);
    }

    private void H(Object object, float f) {
        this.v.setFloat(object, f);
    }

    private boolean N$src$Z$21aemf(Object object) {
        return this.Z.invokeBoolean(object, new Object[0]);
    }

    public void F(Object object, Object object2) {
        this.VJ.invokeVoid(object, object2);
    }

    public static void a(MEntityLivingBase mEntityLivingBase, Object object, Object object2) {
        mEntityLivingBase.E(object, object2);
    }

    private int x$src$I$ea5w6a(Object object) {
        return this.V5.getInt(object);
    }

    private void l(Object object, float f) {
        this.M.setFloat(object, f);
    }

    public static Object g(MEntityLivingBase mEntityLivingBase, Object object, double d, boolean bl, Object object2) {
        return mEntityLivingBase.q(object, d, bl, object2);
    }

    private void r(Object object, float f) {
        this.X.setFloat(object, f);
    }

    public static float t(MEntityLivingBase mEntityLivingBase, Object object) {
        return mEntityLivingBase.I(object);
    }

    public float X(Object object) {
        return this.T.invokeFloat(object, new Object[0]);
    }

    private void U(Object object, float f) {
        this.Vh.setFloat(object, f);
    }

    public static void m(MEntityLivingBase mEntityLivingBase, Object object, int n) {
        mEntityLivingBase.k(object, n);
    }

    private void I(Object object, float f) {
        this.P.setFloat(object, f);
    }
}

