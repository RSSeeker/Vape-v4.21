package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;
import java.util.function.BiConsumer;

public class MItemStack
extends Mapping {
    private MappingMethod M;
    private MappingMethod o;
    public final MappingMethod U;
    private MappingMethod O;
    private MappingMethod H;
    private MappingMethod V;
    private MappingMethod Q;
    private final MappingMethod X;
    private final MappingMethod K;
    private final MappingField E;
    private MappingMethod x;
    private MappingField c;
    public final MappingMethod v;
    private final MappingMethod e;
    public MappingMethod P;
    private static int S;
    public final MappingMethod A;
    private final MappingMethod B;
    private MappingMethod T;
    private final MappingMethod r;
    private final MappingMethod i;
    private MappingMethod N;
    private MappingMethod Y;
    private final MappingMethod z;
    public final MappingMethod J;
    private MappingMethod G;

    public MItemStack() {
        this(MItemStack.f());
    }

    private MItemStack(int n) {
        super(MappedClasses.VK);
        int n2 = n;
        Class[] classArray = new Class[]{};
        Class clazz = MappedClasses.lb;
        boolean bl = true;
        String string = "getItem";
        MItemStack mItemStack = this;
        this.U = this.Y(string, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{};
        Class<Integer> clazz2 = Integer.TYPE;
        boolean bl2 = true;
        String string2 = "getMaxStackSize";
        MItemStack mItemStack2 = this;
        this.X = this.Y(string2, bl2, clazz2, classArray2);
        if (ForgeVersion.MC_1_17.d()) {
            if (ForgeVersion.MC_26_1.v()) {
                Class[] classArray3 = new Class[]{MappedClasses.lb};
                Class<Boolean> clazz3 = Boolean.TYPE;
                boolean bl3 = ForgeVersion.MC_1_20_6.d();
                String string3 = "m_150930_";
                MItemStack mItemStack3 = this;
                this.z = this.Y(string3, bl3, clazz3, classArray3);
            } else {
                this.z = null;
            }
        } else {
            Class[] classArray4 = new Class[]{MappedClasses.VK};
            Class<Boolean> clazz4 = Boolean.TYPE;
            boolean bl4 = true;
            String string4 = "isItemEqual";
            MItemStack mItemStack4 = this;
            this.z = this.Y(string4, bl4, clazz4, classArray4);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            if (ForgeVersion.MC_1_21_10.d() && Vape.INSTANCE.isFabricMinecraftPresent()) {
                Class[] classArray5 = new Class[]{MappedClasses.Fz};
                Class<Object> clazz5 = Object.class;
                boolean bl5 = true;
                String string5 = "get";
                Class clazz6 = MappedClasses.uS;
                MItemStack mItemStack5 = this;
                this.M = this.registerInstanceMethodForOwner(clazz6, string5, bl5, clazz5, classArray5);
            } else {
                Class[] classArray6 = new Class[]{MappedClasses.Fz};
                Class<Object> clazz7 = Object.class;
                boolean bl6 = true;
                String string6 = "get";
                Class clazz8 = MappedClasses.qu;
                MItemStack mItemStack6 = this;
                this.M = this.registerInstanceMethodForOwner(clazz8, string6, bl6, clazz7, classArray6);
            }
            Class[] classArray7 = new Class[]{MappedClasses.FY, BiConsumer.class};
            Class<Void> clazz9 = Void.TYPE;
            boolean bl7 = true;
            String string7 = "forEachModifier";
            MItemStack mItemStack7 = this;
            this.N = this.Y(string7, bl7, clazz9, classArray7);
            Class[] classArray8 = new Class[]{};
            Class<Integer> clazz10 = Integer.TYPE;
            boolean bl8 = true;
            String string8 = "getMaxDamage";
            MItemStack mItemStack8 = this;
            this.G = this.Y(string8, bl8, clazz10, classArray8);
        } else {
            Class[] classArray9 = new Class[]{};
            Class clazz11 = MappedClasses.qt;
            boolean bl9 = true;
            String string9 = "getEnchantmentTagList";
            MItemStack mItemStack9 = this;
            this.P = this.Y(string9, bl9, clazz11, classArray9);
        }
        Class[] classArray10 = new Class[]{MappedClasses.lR, Integer.TYPE};
        Class<Void> clazz12 = Void.TYPE;
        boolean bl10 = true;
        String string10 = "addEnchantment";
        MItemStack mItemStack10 = this;
        this.i = this.Y(string10, bl10, clazz12, classArray10);
        Class[] classArray11 = new Class[]{};
        Class clazz13 = MappedClasses.VK;
        boolean bl11 = true;
        String string11 = "copy";
        MItemStack mItemStack11 = this;
        this.r = this.Y(string11, bl11, clazz13, classArray11);
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray12 = new Class[]{MappedClasses.Zl};
                Class<Float> clazz14 = Float.TYPE;
                boolean bl12 = true;
                String string12 = "getDestroySpeed";
                MItemStack mItemStack12 = this;
                this.K = this.Y(string12, bl12, clazz14, classArray12);
                Class[] classArray13 = new Class[]{MappedClasses.Yl, MappedClasses.Zx};
                Class<List> clazz15 = List.class;
                boolean bl13 = Wrapper.isNativeAvailable;
                String string13 = "func_82840_a";
                MItemStack mItemStack13 = this;
                this.J = this.Y(string13, bl13, clazz15, classArray13);
            } else {
                Class[] classArray14 = new Class[]{MappedClasses.Vv};
                Class<Float> clazz16 = Float.TYPE;
                boolean bl14 = Wrapper.isNativeAvailable;
                String string14 = "func_150997_a";
                MItemStack mItemStack14 = this;
                this.K = this.Y(string14, bl14, clazz16, classArray14);
                Class[] classArray15 = new Class[]{MappedClasses.Yl, MappedClasses.Zx};
                Class<List> clazz17 = List.class;
                boolean bl15 = true;
                String string15 = "getTooltip";
                MItemStack mItemStack15 = this;
                this.J = this.Y(string15, bl15, clazz17, classArray15);
            }
            if (ForgeVersion.MC_1_20_6.v()) {
                Class[] classArray16 = new Class[]{MappedClasses.FY};
                Class clazz18 = MappedClasses.Yb;
                boolean bl16 = true;
                String string16 = "getAttributeModifiers";
                MItemStack mItemStack16 = this;
                this.T = this.Y(string16, bl16, clazz18, classArray16);
            }
        } else {
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                Class[] classArray17 = new Class[]{MappedClasses.Zk};
                Class<Float> clazz19 = Float.TYPE;
                boolean bl17 = true;
                String string17 = "getStrVsBlock";
                MItemStack mItemStack17 = this;
                this.K = this.Y(string17, bl17, clazz19, classArray17);
            } else {
                Class[] classArray18 = new Class[]{MappedClasses.Zk};
                Class<Float> clazz20 = Float.TYPE;
                boolean bl18 = Wrapper.isNativeAvailable;
                String string18 = "func_150997_a";
                MItemStack mItemStack18 = this;
                this.K = this.Y(string18, bl18, clazz20, classArray18);
            }
            Class[] classArray19 = new Class[]{};
            Class clazz21 = MappedClasses.Yb;
            boolean bl19 = true;
            String string19 = "getAttributeModifiers";
            MItemStack mItemStack19 = this;
            this.T = this.Y(string19, bl19, clazz21, classArray19);
            Class[] classArray20 = new Class[]{MappedClasses.Yl, Boolean.TYPE};
            Class<List> clazz22 = List.class;
            boolean bl20 = true;
            String string20 = "getTooltip";
            MItemStack mItemStack20 = this;
            this.J = this.Y(string20, bl20, clazz22, classArray20);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_12_2.v()) {
                Class[] classArray21 = new Class[]{};
                Class<String> clazz23 = String.class;
                boolean bl21 = true;
                String string21 = "getTranslationKey";
                MItemStack mItemStack21 = this;
                this.Y = this.Y(string21, bl21, clazz23, classArray21);
            }
            Class clazz24 = MappedClasses.Yg;
            boolean bl22 = true;
            String string22 = "tag";
            MItemStack mItemStack22 = this;
            this.c = this.J(string22, bl22, clazz24);
            Class[] classArray22 = new Class[]{MappedClasses.Du};
            Class<Void> clazz25 = Void.TYPE;
            boolean bl23 = false;
            String string23 = "<init>";
            MItemStack mItemStack23 = this;
            this.A = this.Y(string23, bl23, clazz25, classArray22);
            if (ForgeVersion.MC_26_1.d()) {
                Class[] classArray23 = new Class[]{MappedClasses.Vo, Integer.TYPE};
                Class<Void> clazz26 = Void.TYPE;
                boolean bl24 = false;
                String string24 = "<init>";
                MItemStack mItemStack24 = this;
                this.x = this.Y(string24, bl24, clazz26, classArray23);
                Class[] classArray24 = new Class[]{MappedClasses.Vo, Integer.TYPE, MappedClasses.DP};
                Class<Void> clazz27 = Void.TYPE;
                boolean bl25 = false;
                String string25 = "<init>";
                MItemStack mItemStack25 = this;
                this.V = this.Y(string25, bl25, clazz27, classArray24);
            }
            Class<Integer> clazz28 = Integer.TYPE;
            boolean bl26 = true;
            String string26 = "count";
            MItemStack mItemStack26 = this;
            this.E = this.J(string26, bl26, clazz28);
            Class[] classArray25 = new Class[]{};
            Class clazz29 = MappedClasses.Yr;
            boolean bl27 = true;
            String string27 = "getDisplayName";
            MItemStack mItemStack27 = this;
            this.v = this.Y(string27, bl27, clazz29, classArray25);
            Class[] classArray26 = new Class[]{};
            Class<Integer> clazz30 = Integer.TYPE;
            boolean bl28 = true;
            String string28 = "getDamage";
            MItemStack mItemStack28 = this;
            this.B = this.Y(string28, bl28, clazz30, classArray26);
            Class[] classArray27 = new Class[]{Integer.TYPE};
            Class<Void> clazz31 = Void.TYPE;
            boolean bl29 = true;
            String string29 = "setDamage";
            MItemStack mItemStack29 = this;
            this.e = this.Y(string29, bl29, clazz31, classArray27);
        } else {
            Class[] classArray28 = new Class[]{MappedClasses.lb};
            Class<Void> clazz32 = Void.TYPE;
            boolean bl30 = false;
            String string30 = "<init>";
            MItemStack mItemStack30 = this;
            this.A = this.Y(string30, bl30, clazz32, classArray28);
            Class<Integer> clazz33 = Integer.TYPE;
            boolean bl31 = true;
            String string31 = "stackSize";
            MItemStack mItemStack31 = this;
            this.E = this.J(string31, bl31, clazz33);
            Class clazz34 = MappedClasses.Yg;
            boolean bl32 = true;
            String string32 = "stackTagCompound";
            MItemStack mItemStack32 = this;
            this.c = this.J(string32, bl32, clazz34);
            Class[] classArray29 = new Class[]{};
            Class<String> clazz35 = String.class;
            boolean bl33 = true;
            String string33 = "getUnlocalizedName";
            MItemStack mItemStack33 = this;
            this.Y = this.Y(string33, bl33, clazz35, classArray29);
            Class[] classArray30 = new Class[]{};
            Class<String> clazz36 = String.class;
            boolean bl34 = true;
            String string34 = "getDisplayName";
            MItemStack mItemStack34 = this;
            this.v = this.Y(string34, bl34, clazz36, classArray30);
            if (ForgeVersion.MC_1_7_10.L() && Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                Class[] classArray31 = new Class[]{};
                Class<Integer> clazz37 = Integer.TYPE;
                boolean bl35 = true;
                String string35 = "getCurrentDurability";
                MItemStack mItemStack35 = this;
                this.B = this.Y(string35, bl35, clazz37, classArray31);
                Class[] classArray32 = new Class[]{Integer.TYPE};
                Class<Void> clazz38 = Void.TYPE;
                boolean bl36 = true;
                String string36 = "setMetadata";
                MItemStack mItemStack36 = this;
                this.e = this.Y(string36, bl36, clazz38, classArray32);
            } else {
                Class[] classArray33 = new Class[]{};
                Class<Integer> clazz39 = Integer.TYPE;
                boolean bl37 = true;
                String string37 = "getItemDamage";
                MItemStack mItemStack37 = this;
                this.B = this.Y(string37, bl37, clazz39, classArray33);
                Class[] classArray34 = new Class[]{Integer.TYPE};
                Class<Void> clazz40 = Void.TYPE;
                boolean bl38 = true;
                String string38 = "setItemDamage";
                MItemStack mItemStack38 = this;
                this.e = this.Y(string38, bl38, clazz40, classArray34);
            }
        }
        if (ForgeVersion.MC_1_21_11.d()) {
            Class[] classArray35 = new Class[]{};
            Class<Boolean> clazz41 = Boolean.TYPE;
            boolean bl39 = true;
            String string39 = "isEmpty";
            MItemStack mItemStack39 = this;
            this.o = this.Y(string39, bl39, clazz41, classArray35);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray36 = new Class[]{MappedClasses.lq};
            Class<Boolean> clazz42 = Boolean.TYPE;
            boolean bl40 = true;
            String string40 = "isItemEnabled";
            MItemStack mItemStack40 = this;
            this.Q = this.Y(string40, bl40, clazz42, classArray36);
            Class[] classArray37 = new Class[]{MappedClasses.YU, MappedClasses.Yl, MappedClasses.Yf};
            Class clazz43 = MappedClasses.zr;
            boolean bl41 = true;
            String string41 = "use";
            MItemStack mItemStack41 = this;
            this.O = this.Y(string41, bl41, clazz43, classArray37);
        }
        if (ForgeVersion.MC_1_21_11.d() && ForgeVersion.MC_26_1.v()) {
            Class[] classArray38 = new Class[]{};
            Class clazz44 = MappedClasses.Vo;
            boolean bl42 = true;
            String string42 = "getItemHolder";
            MItemStack mItemStack42 = this;
            this.H = this.Y(string42, bl42, clazz44, classArray38);
        }
    }

    public boolean Y(Object object, Object object2) {
        return this.Q.invokeBoolean(object, object2);
    }

    public static Object X(MItemStack mItemStack, Object object, Object object2, boolean bl) {
        return mItemStack.l(object, object2, bl);
    }

    private void r(Object object, Object object2, BiConsumer biConsumer) {
        this.N.invokeVoid(object, object2, biConsumer);
    }

    private int E(Object object) {
        return this.X.invokeInt(object, new Object[0]);
    }

    public static Object g(MItemStack mItemStack, Object object, Object object2) {
        return mItemStack.D$src$Ljava_lang_Object_$i3u5lb(object, object2);
    }

    public static float u(MItemStack mItemStack, Object object, Object object2) {
        return mItemStack.p(object, object2);
    }

    public Object p(Object object) {
        return this.A.newInstance(object);
    }

    private Object f(Object object) {
        if (this.H == null) {
            return null;
        }
        return this.H.invokeObject(object, new Object[0]);
    }

    public static void b(MItemStack mItemStack, Object object, int n) {
        mItemStack.s(object, n);
    }

    public Object n(Object object, Object object2, Object object3, Object object4) {
        return this.O.invokeObject(object, object2, object3, object4);
    }

    private String a$src$Ljava_lang_String_$12fy1ag(Object object) {
        if (object == null) {
            return "";
        }
        return (String)this.v.invokeObject(object, new Object[0]);
    }

    public Object r(Object object) {
        return this.c.getObject(object);
    }

    public static Object c(MItemStack mItemStack, Object object) {
        return mItemStack.U(object);
    }

    private float p(Object object, Object object2) {
        return this.K.invokeFloat(object, object2);
    }

    public static Object g(MItemStack mItemStack, Object object, Object object2, Object object3) {
        return mItemStack.b(object, object2, object3);
    }

    public static Object J(MItemStack mItemStack, Object object) {
        return mItemStack.P(object);
    }

    public static void E(int n) {
        S = n;
    }

    public static String k(MItemStack mItemStack, Object object) {
        return mItemStack.a$src$Ljava_lang_String_$12fy1ag(object);
    }

    public String F(Object object) {
        return this.Y.invokeObject(object, new Object[0]).toString();
    }

    private Object l(Object object, Object object2, boolean bl) {
        return this.J.invokeObject(object, object2, bl);
    }


    private void S(Object object, Object object2, int n) {
        this.i.invokeVoid(object, object2, n);
    }

    private Object b(Object object, Object object2, Object object3) {
        return this.J.invokeObject(object, object2, object3);
    }

    private Object L(Object object, Object object2) {
        return this.M.invokeObject(object, object2);
    }

    private Object Y(Object object) {
        return this.P.invokeObject(object, new Object[0]);
    }

    private int a(Object object) {
        return this.B.invokeInt(object, new Object[0]);
    }

    public Object j(Object object, int n, Object object2) {
        return this.V.newInstance(object, n, object2);
    }

    public static int f() {
        return S;
    }

    public int n(Object object) {
        return this.E.getInt(object);
    }

    private Object P(Object object) {
        return this.r.invokeObject(object, new Object[0]);
    }

    static {
        MItemStack.E(19);
    }

    private int k(Object object) {
        return this.G.invokeInt(object, new Object[0]);
    }

    private void s(Object object, int n) {
        this.e.invokeVoid(object, n);
    }

    public static Object H(MItemStack mItemStack, Object object) {
        return mItemStack.Y(object);
    }

    public void k(Object object, int n) {
        this.E.setInt(object, n);
    }

    private Object U(Object object) {
        return this.v.invokeObject(object, new Object[0]);
    }

    public static void Q(MItemStack mItemStack, Object object, Object object2, BiConsumer biConsumer) {
        mItemStack.r(object, object2, biConsumer);
    }

    public static Object A(MItemStack mItemStack, Object object, Object object2) {
        return mItemStack.L(object, object2);
    }

    public Object O(Object object, int n) {
        return this.x.newInstance(object, n);
    }

    public static int v(MItemStack mItemStack, Object object) {
        return mItemStack.a(object);
    }

    public static Object M(MItemStack mItemStack, Object object) {
        return mItemStack.f(object);
    }

    public boolean e(Object object, Object object2) {
        if (ForgeVersion.MC_26_1.d()) {
            return this.U.invokeObject(object, new Object[0]) == object2;
        }
        return this.z.invokeBoolean(object, object2);
    }

    public boolean j(Object object) {
        return this.o.invokeBoolean(object, new Object[0]);
    }

    public static Object A$src$Ljava_lang_Object_$2ove6c(MItemStack mItemStack, Object object) {
        return mItemStack.k$src$Ljava_lang_Object_$12vnjos(object);
    }

    public Object V(Object object) {
        return this.U.invokeObject(object, new Object[0]);
    }

    private Object D$src$Ljava_lang_Object_$i3u5lb(Object object, Object object2) {
        return this.T.invokeObject(object, object2);
    }

    public void D(Object object, Object object2) {
        this.c.setObject(object, object2);
    }

    public static void r(MItemStack mItemStack, Object object, Object object2, int n) {
        mItemStack.S(object, object2, n);
    }

    public static int A(MItemStack mItemStack, Object object) {
        return mItemStack.k(object);
    }

    public static int l(MItemStack mItemStack, Object object) {
        return mItemStack.E(object);
    }

    public static int M() {
        int n = MItemStack.f();
        if (n == 0) {
            return 86;
        }
        return 0;
    }

    private Object k$src$Ljava_lang_Object_$12vnjos(Object object) {
        return this.T.invokeObject(object, new Object[0]);
    }
}

