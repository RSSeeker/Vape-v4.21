package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MEmptyDataComponentMap;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MDataComponents
extends Mapping {
    private MappingField y;
    private MappingField Z;
    private MappingField Q;
    private MappingField x;
    private MappingField B;
    private MappingField c;
    private MappingField m;
    private MappingField O;
    private MappingField E;
    private MappingField D;
    private MappingField T;
    private MappingField w;
    private MappingField C;
    private MappingField q;

    public static Object E(MDataComponents mDataComponents) {
        return mDataComponents.q();
    }

    private Object w() {
        return this.O.getObject(null);
    }

    public static Object z(MDataComponents mDataComponents) {
        return mDataComponents.g();
    }

    private Object M() {
        return this.C.getObject(null);
    }

    public static Object I(MDataComponents mDataComponents) {
        return mDataComponents.Q();
    }

    private Object l() {
        return this.q.getObject(null);
    }

    public static Object O(MDataComponents mDataComponents) {
        return mDataComponents.J();
    }

    private Object S() {
        return this.D.getObject(null);
    }

    public static Object F(MDataComponents mDataComponents) {
        return mDataComponents.V();
    }

    private Object g() {
        return this.Z.getObject(null);
    }

    public static Object y(MDataComponents mDataComponents) {
        return mDataComponents.B();
    }

    public static Object T(MDataComponents mDataComponents) {
        return mDataComponents.Z();
    }

    public static Object b(MDataComponents mDataComponents) {
        return mDataComponents.S();
    }

    private Object J() {
        return this.x.getObject(null);
    }


    private Object q() {
        return this.c.getObject(null);
    }

    private Object Q() {
        return this.E.getObject(null);
    }

    public static Object a(MDataComponents mDataComponents) {
        return mDataComponents.M();
    }

    public static Object m(MDataComponents mDataComponents) {
        return mDataComponents.o();
    }

    private Object B() {
        return this.T.getObject(null);
    }

    private Object Z() {
        return this.y == null ? null : this.y.getObject(null);
    }

    public static Object e(MDataComponents mDataComponents) {
        return mDataComponents.h();
    }

    private Object V() {
        return this.w.getObject(null);
    }

    public static Object M(MDataComponents mDataComponents) {
        return mDataComponents.w();
    }

    public static Object L(MDataComponents mDataComponents) {
        return mDataComponents.C();
    }

    private Object o() {
        return this.m.getObject(null);
    }

    private Object h() {
        return this.B.getObject(null);
    }

    private Object C() {
        return this.Q.getObject(null);
    }

    public MDataComponents() {
        super(MappedClasses.Zr);
        Class clazz = MappedClasses.Fz;
        boolean bl = true;
        String string = "POTION_CONTENTS";
        MDataComponents mDataComponents = this;
        this.O = this.registerStaticField(string, bl, clazz);
        Class clazz2 = MappedClasses.Fz;
        boolean bl2 = true;
        String string2 = "ENCHANTMENTS";
        MDataComponents mDataComponents2 = this;
        this.Z = this.registerStaticField(string2, bl2, clazz2);
        Class clazz3 = MappedClasses.Fz;
        boolean bl3 = true;
        String string3 = "TOOL";
        MDataComponents mDataComponents3 = this;
        this.T = this.registerStaticField(string3, bl3, clazz3);
        Class clazz4 = MappedClasses.Fz;
        boolean bl4 = true;
        String string4 = "ATTRIBUTE_MODIFIERS";
        MDataComponents mDataComponents4 = this;
        this.c = this.registerStaticField(string4, bl4, clazz4);
        Class clazz5 = MappedClasses.Fz;
        boolean bl5 = true;
        String string5 = "EQUIPPABLE";
        MDataComponents mDataComponents5 = this;
        this.w = this.registerStaticField(string5, bl5, clazz5);
        Class clazz6 = MappedClasses.Fz;
        boolean bl6 = true;
        String string6 = "REPAIRABLE";
        MDataComponents mDataComponents6 = this;
        this.B = this.registerStaticField(string6, bl6, clazz6);
        Class clazz7 = MappedClasses.Fz;
        boolean bl7 = true;
        String string7 = "FOOD";
        MDataComponents mDataComponents7 = this;
        this.C = this.registerStaticField(string7, bl7, clazz7);
        Class clazz8 = MappedClasses.Fz;
        boolean bl8 = true;
        String string8 = "CONSUMABLE";
        MDataComponents mDataComponents8 = this;
        this.x = this.registerStaticField(string8, bl8, clazz8);
        Class clazz9 = MappedClasses.Fz;
        boolean bl9 = true;
        String string9 = "DAMAGE";
        MDataComponents mDataComponents9 = this;
        this.E = this.registerStaticField(string9, bl9, clazz9);
        if (MEmptyDataComponentMap.getControlFlowState() != null) {
            Class clazz10 = MappedClasses.Fz;
            boolean bl10 = true;
            String string10 = "ENCHANTABLE";
            MDataComponents mDataComponents10 = this;
            this.D = this.registerStaticField(string10, bl10, clazz10);
            Class clazz11 = MappedClasses.Fz;
            boolean bl11 = true;
            String string11 = "DAMAGE_RESISTANT";
            MDataComponents mDataComponents11 = this;
            this.m = this.registerStaticField(string11, bl11, clazz11);
            Class clazz12 = MappedClasses.Fz;
            boolean bl12 = true;
            String string12 = "CAN_PLACE_ON";
            MDataComponents mDataComponents12 = this;
            this.q = this.registerStaticField(string12, bl12, clazz12);
            Class clazz13 = MappedClasses.Fz;
            boolean bl13 = true;
            String string13 = "CAN_BREAK";
            MDataComponents mDataComponents13 = this;
            this.y = this.registerStaticField(string13, bl13, clazz13);
            GuiComponent.setLegacyComponentState(new GuiComponent[1]);
            return;
        }
        Class clazz14 = MappedClasses.Fz;
        boolean bl14 = true;
        String string14 = "ENCHANTABLE";
        MDataComponents mDataComponents14 = this;
        this.D = this.registerStaticField(string14, bl14, clazz14);
        Class clazz15 = MappedClasses.Fz;
        boolean bl15 = true;
        String string15 = "DAMAGE_RESISTANT";
        MDataComponents mDataComponents15 = this;
        this.m = this.registerStaticField(string15, bl15, clazz15);
        Class clazz16 = MappedClasses.Fz;
        boolean bl16 = true;
        String string16 = "CAN_PLACE_ON";
        MDataComponents mDataComponents16 = this;
        this.q = this.registerStaticField(string16, bl16, clazz16);
        Class clazz17 = MappedClasses.Fz;
        boolean bl17 = true;
        String string17 = "CAN_BREAK";
        MDataComponents mDataComponents17 = this;
        this.Q = this.registerStaticField(string17, bl17, clazz17);
        if (ForgeVersion.MC_1_21_11.d()) {
            Class clazz18 = MappedClasses.Fz;
            boolean bl18 = true;
            String string18 = "ATTACK_RANGE";
            MDataComponents mDataComponents18 = this;
            this.y = this.registerStaticField(string18, bl18, clazz18);
        }
    }

    public static Object h(MDataComponents mDataComponents) {
        return mDataComponents.l();
    }
}

