package gg.vape.mapping.mappings;

import com.google.common.collect.ImmutableList;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MPotion
extends Mapping {
    private MappingField a;
    private MappingField T;
    private final MappingMethod e;
    private static int[] G;
    private MappingField F;
    private MappingField p;
    private MappingField H;
    private MappingMethod k;
    private MappingField I;
    private MappingField J;
    private MappingMethod i;
    private MappingMethod O;
    private MappingMethod X;
    private MappingMethod h;
    private MappingMethod x;
    private MappingField v;
    private MappingField l;
    private MappingField P;
    private MappingField d;
    private MappingField M;
    private MappingField S;
    private MappingField W;
    private MappingField q;
    private MappingField K;
    private MappingField z;
    private MappingField j;
    private MappingField r;

    private Object n(String string) {
        return this.X.invokeObject(null, string);
    }

    private Object o() {
        return this.K.getObject(null);
    }

    public static void U(int[] nArray) {
        G = nArray;
    }

    private int n(Object object) {
        return this.i.invokeInt(object, new Object[0]);
    }

    public MPotion() {
        this(MPotion.K());
    }

    private MPotion(int[] nArray) {
        super(MappedClasses.FR);
        int[] nArray2 = nArray;
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_20_6.d()) {
                    Class clazz = MappedClasses.Fk;
                    boolean bl = true;
                    String string = "POTION";
                    Class clazz2 = MappedClasses.R;
                    MPotion mPotion = this;
                    this.K = mPotion.registerStaticFieldForOwner(clazz2, string, bl, clazz);
                } else {
                    Class clazz = MappedClasses.lz;
                    boolean bl = true;
                    String string = "POTION";
                    Class clazz3 = MappedClasses.Fk;
                    MPotion mPotion = this;
                    this.K = mPotion.registerStaticFieldForOwner(clazz3, string, bl, clazz);
                    Class[] classArray = new Class[]{String.class};
                    Class clazz4 = MappedClasses.FR;
                    boolean bl2 = true;
                    String string2 = "getPotionTypeForName";
                    MPotion mPotion2 = this;
                this.X = this.registerStaticMethod(string2, bl2, clazz4, classArray);
                    Class[] classArray2 = new Class[]{MappedClasses.D3};
                    Class<Integer> clazz5 = Integer.TYPE;
                    boolean bl3 = true;
                    String string3 = "getId";
                    Class clazz6 = MappedClasses.D3;
                    MPotion mPotion3 = this;
                    this.x = this.registerStaticMethodForOwner(clazz6, string3, bl3, clazz5, classArray2);
                }
                Class<String> clazz = String.class;
                boolean bl = true;
                String string = "baseName";
                MPotion mPotion = this;
                this.j = mPotion.J(string, bl, clazz);
            } else {
                Class clazz = MappedClasses.zz;
                boolean bl = true;
                String string = "REGISTRY";
                MPotion mPotion = this;
                this.K = mPotion.registerStaticField(string, bl, clazz);
                Class[] classArray = new Class[]{};
                Class<String> clazz7 = String.class;
                boolean bl4 = true;
                String string4 = "getName";
                MPotion mPotion4 = this;
                this.k = this.Y(string4, bl4, clazz7, classArray);
                Class[] classArray3 = new Class[]{MappedClasses.FR};
                Class<Integer> clazz8 = Integer.TYPE;
                boolean bl5 = true;
                String string5 = "getIdFromPotion";
                MPotion mPotion5 = this;
        this.x = this.registerStaticMethod(string5, bl5, clazz8, classArray3);
                Class[] classArray4 = new Class[]{Integer.TYPE};
                Class clazz9 = MappedClasses.FR;
                boolean bl6 = true;
                String string6 = "getPotionById";
                MPotion mPotion6 = this;
        this.h = this.registerStaticMethod(string6, bl6, clazz9, classArray4);
            }
        } else {
            Class clazz = MappedClasses.FR;
            boolean bl = true;
            String string = "fireResistance";
            MPotion mPotion = this;
            this.v = mPotion.registerStaticField(string, bl, clazz);
            Class clazz10 = MappedClasses.FR;
            boolean bl7 = true;
            String string7 = "regeneration";
            MPotion mPotion7 = this;
            this.p = this.registerStaticField(string7, bl7, clazz10);
            Class clazz11 = MappedClasses.FR;
            boolean bl8 = true;
            String string8 = "resistance";
            MPotion mPotion8 = this;
            this.F = this.registerStaticField(string8, bl8, clazz11);
            Class clazz12 = MappedClasses.FR;
            boolean bl9 = true;
            String string9 = "damageBoost";
            MPotion mPotion9 = this;
            this.l = this.registerStaticField(string9, bl9, clazz12);
            Class clazz13 = MappedClasses.FR;
            boolean bl10 = true;
            String string10 = "moveSpeed";
            MPotion mPotion10 = this;
            this.S = this.registerStaticField(string10, bl10, clazz13);
            Class clazz14 = MappedClasses.FR;
            boolean bl11 = true;
            String string11 = "blindness";
            MPotion mPotion11 = this;
            this.J = this.registerStaticField(string11, bl11, clazz14);
            Class clazz15 = MappedClasses.FR;
            boolean bl12 = true;
            String string12 = "moveSlowdown";
            MPotion mPotion12 = this;
            this.W = this.registerStaticField(string12, bl12, clazz15);
            Class clazz16 = MappedClasses.FR;
            boolean bl13 = true;
            String string13 = "jump";
            MPotion mPotion13 = this;
            this.P = this.registerStaticField(string13, bl13, clazz16);
            Class clazz17 = MappedClasses.FR;
            boolean bl14 = true;
            String string14 = "heal";
            MPotion mPotion14 = this;
            this.M = this.registerStaticField(string14, bl14, clazz17);
            Class clazz18 = MappedClasses.FR;
            boolean bl15 = true;
            String string15 = "digSpeed";
            MPotion mPotion15 = this;
            this.d = this.registerStaticField(string15, bl15, clazz18);
            Class clazz19 = MappedClasses.FR;
            boolean bl16 = true;
            String string16 = "digSlowdown";
            MPotion mPotion16 = this;
            this.r = this.registerStaticField(string16, bl16, clazz19);
            Class clazz20 = MappedClasses.FR;
            boolean bl17 = true;
            String string17 = "confusion";
            MPotion mPotion17 = this;
            this.H = this.registerStaticField(string17, bl17, clazz20);
            Class<?> clazz21 = DescUtils.getArrayType(MappedClasses.FR);
            boolean bl18 = true;
            String string18 = "potionTypes";
            MPotion mPotion18 = this;
            this.a = this.registerStaticField(string18, bl18, clazz21);
            Class<Integer> clazz22 = Integer.TYPE;
            boolean bl19 = true;
            String string19 = "id";
            MPotion mPotion19 = this;
            this.q = this.J(string19, bl19, clazz22);
            Class[] classArray = new Class[]{};
            Class<String> clazz23 = String.class;
            boolean bl20 = true;
            String string20 = "getName";
            MPotion mPotion20 = this;
            this.k = this.Y(string20, bl20, clazz23, classArray);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_20_6.d()) {
                Class<List> clazz = List.class;
                boolean bl = true;
                String string = "effects";
                MPotion mPotion = this;
                this.T = mPotion.J(string, bl, clazz);
            } else {
                Class<ImmutableList> clazz = ImmutableList.class;
                boolean bl = true;
                String string = "effects";
                MPotion mPotion = this;
                this.T = mPotion.J(string, bl, clazz);
            }
            Class[] classArray = new Class[]{MappedClasses.zm, MappedClasses.Ya, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "removeAttributesModifiersFromEntity";
            Class clazz24 = MappedClasses.D3;
            MPotion mPotion = this;
            this.e = mPotion.registerInstanceMethodForOwner(clazz24, string, bl, clazz, classArray);
            Class<Boolean> clazz25 = Boolean.TYPE;
            boolean bl21 = true;
            String string21 = "showIcon";
            Class clazz26 = MappedClasses.u3;
            MPotion mPotion21 = this;
            this.I = this.registerInstanceFieldForOwner(clazz26, string21, bl21, clazz25);
        } else {
            Class<Boolean> clazz = Boolean.TYPE;
            boolean bl = true;
            String string = "isBadEffect";
            MPotion mPotion = this;
            this.z = mPotion.J(string, bl, clazz);
            Class[] classArray = new Class[]{MappedClasses.zm, MappedClasses.Ya, Integer.TYPE};
            Class<Void> clazz27 = Void.TYPE;
            boolean bl22 = true;
            String string22 = "removeAttributesModifiersFromEntity";
            MPotion mPotion22 = this;
            this.e = this.Y(string22, bl22, clazz27, classArray);
            Class[] classArray5 = new Class[]{};
            Class<Boolean> clazz28 = Boolean.TYPE;
            boolean bl23 = true;
            String string23 = "hasStatusIcon";
            MPotion mPotion23 = this;
            this.O = this.Y(string23, bl23, clazz28, classArray5);
            Class[] classArray6 = new Class[]{};
            Class<Integer> clazz29 = Integer.TYPE;
            boolean bl24 = true;
            String string24 = "getStatusIconIndex";
            MPotion mPotion24 = this;
            this.i = this.Y(string24, bl24, clazz29, classArray6);
        }
    }

    public static Object x(MPotion mPotion) {
        return mPotion.o();
    }

    private Object l() {
        return this.S.getObject(null);
    }


    public static int g(MPotion mPotion, Object object) {
        return mPotion.L(object);
    }

    private Object q() {
        return this.H.getObject(null);
    }

    private Object[] O() {
        return this.a.getObjectArray(null);
    }

    private Object I() {
        return this.v.getObject(null);
    }

    static {
        MPotion.U(null);
    }

    private int L(Object object) {
        return this.q.getInt(object);
    }

    public static int H(MPotion mPotion, Object object) {
        return mPotion.n(object);
    }

    public Object E$src$Ljava_lang_Object_$2lonps() {
        return this.r.getObject(null);
    }

    public static boolean u(MPotion mPotion, Object object) {
        return mPotion.b(object);
    }

    private int Y(Object object) {
        return this.x.invokeInt(null, object);
    }

    public List W(Object object) {
        return (List)this.T.getObject(object);
    }

    private boolean b(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.I.getBoolean(object);
        }
        return this.O.invokeBoolean(object, new Object[0]);
    }

    private Object f() {
        return this.J.getObject(null);
    }

    private Object n() {
        return this.W.getObject(null);
    }

    private Object a(int n) {
        return this.h.invokeObject(null, n);
    }

    private Object p() {
        return this.d.getObject(null);
    }

    private Object x() {
        return this.P.getObject(null);
    }

    private Object m() {
        return this.p.getObject(null);
    }

    public void b(Object object, Object object2, Object object3, int n) {
        if (ForgeVersion.MC_1_20_6.d()) {
            this.e.invokeVoid(object, object3);
            return;
        }
        this.e.invokeVoid(object, object2, object3, n);
    }

    private Object y() {
        return this.F.getObject(null);
    }

    public static int R(MPotion mPotion, Object object) {
        return mPotion.Y(object);
    }

    public boolean E(Object object) {
        return this.z.getBoolean(object);
    }

    public static String q(MPotion mPotion, Object object) {
        return mPotion.W$src$Ljava_lang_String_$16q9s18(object);
    }

    public static Object[] i(MPotion mPotion) {
        return mPotion.O();
    }

    public Object S() {
        return this.l.getObject(null);
    }

    private Object B() {
        return this.M.getObject(null);
    }

    private String W$src$Ljava_lang_String_$16q9s18(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return (String)this.j.getObject(object);
        }
        return (String)this.k.invokeObject(object, new Object[0]);
    }

    public static Object Q(MPotion mPotion, int n) {
        return mPotion.a(n);
    }

    public static int[] K() {
        return G;
    }
}

