package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MEnchantments
extends Mapping {
    private static String M;
    private final MappingField v;
    private final MappingField L;
    private final MappingField I;
    private final MappingField Q;
    private final MappingField c;
    private final MappingField D;
    private final MappingField X;
    private final MappingField B;
    private final MappingField d;
    private final MappingField Y;
    private final MappingField a;
    private final MappingField l;
    private final MappingField V;


    private Object y() {
        return this.B.getObject(null);
    }

    public static Object W(MEnchantments mEnchantments) {
        return mEnchantments.g();
    }

    public static Object S(MEnchantments mEnchantments) {
        return mEnchantments.z();
    }

    public static void b(String string) {
        M = string;
    }

    public static Object i(MEnchantments mEnchantments) {
        return mEnchantments.Z();
    }

    private Object g() {
        return this.X.getObject(null);
    }

    public static Object U(MEnchantments mEnchantments) {
        return mEnchantments.I();
    }

    public static Object l(MEnchantments mEnchantments) {
        return mEnchantments.i();
    }

    public static Object F(MEnchantments mEnchantments) {
        return mEnchantments.Q();
    }

    public static Object j(MEnchantments mEnchantments) {
        return mEnchantments.y();
    }

    public static Object r(MEnchantments mEnchantments) {
        return mEnchantments.r();
    }

    private Object r() {
        return this.d.getObject(null);
    }

    public static Object x(MEnchantments mEnchantments) {
        return mEnchantments.t();
    }

    private Object V() {
        return this.Q.getObject(null);
    }

    public static String u() {
        return M;
    }

    static {
        MEnchantments.b(null);
    }

    private Object I() {
        return this.V.getObject(null);
    }

    public static Object I(MEnchantments mEnchantments) {
        return mEnchantments.T();
    }

    private Object z() {
        return this.v.getObject(null);
    }

    private Object N() {
        return this.Y.getObject(null);
    }

    private Object T() {
        return this.a.getObject(null);
    }

    public static Object f(MEnchantments mEnchantments) {
        return mEnchantments.N();
    }

    public MEnchantments() {
        this(MEnchantments.u());
    }

    private MEnchantments(String string) {
        super(MappedClasses.V2);
        Class clazz = MappedClasses.qB;
        boolean bl = true;
        String string2 = "DEPTH_STRIDER";
        MEnchantments mEnchantments = this;
        this.D = this.registerStaticField(string2, bl, clazz);
        Class clazz2 = MappedClasses.qB;
        boolean bl2 = true;
        String string3 = "PROTECTION";
        MEnchantments mEnchantments2 = this;
        this.I = this.registerStaticField(string3, bl2, clazz2);
        Class clazz3 = MappedClasses.qB;
        boolean bl3 = true;
        String string4 = "BLAST_PROTECTION";
        MEnchantments mEnchantments3 = this;
        this.L = this.registerStaticField(string4, bl3, clazz3);
        Class clazz4 = MappedClasses.qB;
        boolean bl4 = true;
        String string5 = "FIRE_PROTECTION";
        MEnchantments mEnchantments4 = this;
        this.Y = this.registerStaticField(string5, bl4, clazz4);
        Class clazz5 = MappedClasses.qB;
        boolean bl5 = true;
        String string6 = "FEATHER_FALLING";
        MEnchantments mEnchantments5 = this;
        this.c = this.registerStaticField(string6, bl5, clazz5);
        if (string != null) {
            Class clazz6 = MappedClasses.qB;
            boolean bl6 = true;
            String string7 = "PROJECTILE_PROTECTION";
            MEnchantments mEnchantments6 = this;
            this.v = this.registerStaticField(string7, bl6, clazz6);
            Class clazz7 = MappedClasses.qB;
            boolean bl7 = true;
            String string8 = "RESPIRATION";
            MEnchantments mEnchantments7 = this;
            this.d = this.registerStaticField(string8, bl7, clazz7);
            Class clazz8 = MappedClasses.qB;
            boolean bl8 = true;
            String string9 = "AQUA_AFFINITY";
            MEnchantments mEnchantments8 = this;
            this.l = this.registerStaticField(string9, bl8, clazz8);
            Class clazz9 = MappedClasses.qB;
            boolean bl9 = true;
            String string10 = "THORNS";
            MEnchantments mEnchantments9 = this;
            this.Q = this.registerStaticField(string10, bl9, clazz9);
            Class clazz10 = MappedClasses.qB;
            boolean bl10 = true;
            String string11 = "FROST_WALKER";
            MEnchantments mEnchantments10 = this;
            this.V = this.registerStaticField(string11, bl10, clazz10);
            Class clazz11 = MappedClasses.qB;
            boolean bl11 = true;
            String string12 = "BINDING_CURSE";
            MEnchantments mEnchantments11 = this;
            this.B = this.registerStaticField(string12, bl11, clazz11);
            Class clazz12 = MappedClasses.qB;
            boolean bl12 = true;
            String string13 = "SOUL_SPEED";
            MEnchantments mEnchantments12 = this;
            this.a = this.registerStaticField(string13, bl12, clazz12);
            Class clazz13 = MappedClasses.qB;
            boolean bl13 = true;
            String string14 = "SWIFT_SNEAK";
            MEnchantments mEnchantments13 = this;
            this.X = this.registerStaticField(string14, bl13, clazz13);
            GuiComponent.setLegacyComponentState(new GuiComponent[3]);
            return;
        }
        Class clazz14 = MappedClasses.qB;
        boolean bl14 = true;
        String string15 = "PROJECTILE_PROTECTION";
        MEnchantments mEnchantments14 = this;
        this.v = this.registerStaticField(string15, bl14, clazz14);
        Class clazz15 = MappedClasses.qB;
        boolean bl15 = true;
        String string16 = "RESPIRATION";
        MEnchantments mEnchantments15 = this;
        this.d = this.registerStaticField(string16, bl15, clazz15);
        Class clazz16 = MappedClasses.qB;
        boolean bl16 = true;
        String string17 = "AQUA_AFFINITY";
        MEnchantments mEnchantments16 = this;
        this.l = this.registerStaticField(string17, bl16, clazz16);
        Class clazz17 = MappedClasses.qB;
        boolean bl17 = true;
        String string18 = "THORNS";
        MEnchantments mEnchantments17 = this;
        this.Q = this.registerStaticField(string18, bl17, clazz17);
        Class clazz18 = MappedClasses.qB;
        boolean bl18 = true;
        String string19 = "FROST_WALKER";
        MEnchantments mEnchantments18 = this;
        this.V = this.registerStaticField(string19, bl18, clazz18);
        Class clazz19 = MappedClasses.qB;
        boolean bl19 = true;
        String string20 = "BINDING_CURSE";
        MEnchantments mEnchantments19 = this;
        this.B = this.registerStaticField(string20, bl19, clazz19);
        Class clazz20 = MappedClasses.qB;
        boolean bl20 = true;
        String string21 = "SOUL_SPEED";
        MEnchantments mEnchantments20 = this;
        this.a = this.registerStaticField(string21, bl20, clazz20);
        Class clazz21 = MappedClasses.qB;
        boolean bl21 = true;
        String string22 = "SWIFT_SNEAK";
        MEnchantments mEnchantments21 = this;
        this.X = this.registerStaticField(string22, bl21, clazz21);
    }

    private Object t() {
        return this.I.getObject(null);
    }

    private Object Q() {
        return this.l.getObject(null);
    }

    public static Object T(MEnchantments mEnchantments) {
        return mEnchantments.V();
    }

    private Object l() {
        return this.c.getObject(null);
    }

    public static Object N(MEnchantments mEnchantments) {
        return mEnchantments.l();
    }

    private Object i() {
        return this.D.getObject(null);
    }

    private Object Z() {
        return this.L.getObject(null);
    }
}

