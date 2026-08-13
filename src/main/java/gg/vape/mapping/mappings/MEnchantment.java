package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEnchantments;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEnchantment
extends Mapping {
    private MappingField d;
    private MappingField j;
    private MappingMethod e;
    private MappingField g;
    private MappingField N;
    private MappingField J;
    private MappingField Y;
    private MappingField H;
    private MappingField A;
    private MappingField c;
    private MappingField I;
    private MappingField O;
    private MappingField x;
    private MappingField o;
    private MappingField S;
    private final MappingMethod M;
    private MappingMethod l;
    private MappingField b;
    private MappingField C;

    private Object P() {
        return this.A.getObject(null);
    }

    public static String E(MEnchantment mEnchantment, Object object, int n) {
        return mEnchantment.g(object, n);
    }

    private Object x(int n) {
        return this.e.invokeObject(null, n);
    }

    private int i(Object object) {
        return this.O.getInt(object);
    }

    public static Object[] x(MEnchantment mEnchantment) {
        return mEnchantment.a();
    }


    public MEnchantment() {
        this(MEnchantments.u());
    }

    private MEnchantment(String string) {
        super(MappedClasses.lR);
        String string2 = string;
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_20_6.d()) {
                    if (!ForgeVersion.MC_1_21_0.d()) {
                        Class clazz = MappedClasses.Fk;
                        boolean bl = true;
                        String string3 = "ENCHANTMENT";
                        Class clazz2 = MappedClasses.R;
                        MEnchantment mEnchantment = this;
                        this.A = mEnchantment.registerStaticFieldForOwner(clazz2, string3, bl, clazz);
                    }
                } else {
                    Class clazz = MappedClasses.Fk;
                    boolean bl = true;
                    String string4 = "ENCHANTMENT";
                    Class clazz3 = MappedClasses.Fk;
                    MEnchantment mEnchantment = this;
                    this.A = mEnchantment.registerStaticFieldForOwner(clazz3, string4, bl, clazz);
                }
            } else {
                Class[] classArray = new Class[]{Integer.TYPE};
                Class clazz = MappedClasses.lR;
                boolean bl = true;
                String string5 = "getEnchantmentByID";
                MEnchantment mEnchantment = this;
                this.e = mEnchantment.registerStaticMethod(string5, bl, clazz, classArray);
                Class[] classArray2 = new Class[]{MappedClasses.lR};
                Class<Integer> clazz4 = Integer.TYPE;
                boolean bl2 = true;
                String string6 = "getEnchantmentID";
                MEnchantment mEnchantment2 = this;
                this.l = this.registerStaticMethod(string6, bl2, clazz4, classArray2);
                Class clazz5 = MappedClasses.zz;
                boolean bl3 = true;
                String string7 = "REGISTRY";
                MEnchantment mEnchantment3 = this;
                this.A = this.registerStaticField(string7, bl3, clazz5);
            }
        } else {
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string8 = "effectId";
            MEnchantment mEnchantment = this;
            this.O = mEnchantment.J(string8, bl, clazz);
            Class clazz6 = MappedClasses.lR;
            String string9 = "enchantmentsList";
            MEnchantment mEnchantment4 = this;
            this.C = ((MappingFieldBuilder)this.fieldBuilder(string9, clazz6).setStaticMember(true)).setArrayDimensions(1).buildField();
        }
        if (ForgeVersion.MC_1_21_0.d()) {
            Class[] classArray = new Class[]{MappedClasses.Vo, Integer.TYPE};
            Class clazz = MappedClasses.Yr;
            boolean bl = true;
            String string10 = "getFullname";
            MEnchantment mEnchantment = this;
            this.M = mEnchantment.registerStaticMethod(string10, bl, clazz, classArray);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{Integer.TYPE};
            Class clazz = MappedClasses.Yr;
            boolean bl = true;
            String string11 = "getDisplayName";
            MEnchantment mEnchantment = this;
            this.M = mEnchantment.Y(string11, bl, clazz, classArray);
        } else {
            Class[] classArray = new Class[]{Integer.TYPE};
            Class<String> clazz = String.class;
            boolean bl = true;
            String string12 = "getTranslatedName";
            MEnchantment mEnchantment = this;
            this.M = mEnchantment.Y(string12, bl, clazz, classArray); 
        }
    }

    public static Object k(MEnchantment mEnchantment, int n) {
        return mEnchantment.x(n);
    }

    private int G(Object object) {
        return this.l.invokeInt(null, object);
    }

    public static Object O(MEnchantment mEnchantment, Object object, int n) {
        return mEnchantment.w(object, n);
    }

    private Object B(Object object, int n) {
        return this.M.invokeObject(object, n);
    }

    public static int m(MEnchantment mEnchantment, Object object) {
        return mEnchantment.G(object);
    }

    public static Object N(MEnchantment mEnchantment) {
        return mEnchantment.P();
    }

    private String g(Object object, int n) {
        return (String)this.M.invokeObject(object, n);
    }

    public static int o(MEnchantment mEnchantment, Object object) {
        return mEnchantment.i(object);
    }

    private Object[] a() {
        return this.C.getObjectArray(null);
    }

    private Object w(Object object, int n) {
        return this.M.invokeObject(null, object, n);
    }

    public static Object J(MEnchantment mEnchantment, Object object, int n) {
        return mEnchantment.B(object, n);
    }
}

