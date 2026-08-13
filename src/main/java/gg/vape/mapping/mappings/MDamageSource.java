package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MDamageSource
extends Mapping {
    private final MappingField A;
    private MappingField z;
    private MappingField Y;
    private final MappingField a;
    private static int[] f;

    public static void f(int[] nArray) {
        f = nArray;
    }

    public static Object s(MDamageSource mDamageSource) {
        return mDamageSource.L();
    }

    public static int[] r() {
        return f;
    }

    private Object L() {
        return this.Y.getObject(null);
    }

    public MDamageSource() {
        this(MDamageSource.r());
    }

    private MDamageSource(int[] nArray) {
        super(MappedClasses.DW);
        if (nArray != null) {
            Class clazz = MappedClasses.Di;
            boolean bl = true;
            String string = "potionitem";
            MDamageSource mDamageSource = this;
            this.a = mDamageSource.registerStaticField(string, bl, clazz);
            if (GuiComponent.getLegacyComponentState() == null) {
                MDamageSource.f(new int[1]);
            }
            this.A = null;
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            Class clazz = MappedClasses.lb;
            boolean bl = true;
            String string = "WOODEN_SWORD";
            MDamageSource mDamageSource = this;
            this.A = mDamageSource.registerStaticField(string, bl, clazz);
            if (ForgeVersion.MC_1_16_5.d()) {
                Class clazz2 = MappedClasses.lb;
                boolean bl2 = true;
                String string2 = "POTION";
                MDamageSource mDamageSource2 = this;
                this.a = this.registerStaticField(string2, bl2, clazz2);
            } else {
                Class clazz3 = MappedClasses.Di;
                boolean bl3 = true;
                String string3 = "POTIONITEM";
                MDamageSource mDamageSource3 = this;
                this.a = this.registerStaticField(string3, bl3, clazz3);
            }
            Class clazz4 = MappedClasses.lb;
            String string4 = "END_CRYSTAL";
            MDamageSource mDamageSource4 = this;
            this.z = ((MappingFieldBuilder)this.fieldBuilder(string4, clazz4).setStaticMember(true)).buildField();
            Class clazz5 = MappedClasses.lb;
            String string5 = "TOTEM_OF_UNDYING";
            MDamageSource mDamageSource5 = this;
            this.Y = ((MappingFieldBuilder)this.fieldBuilder(string5, clazz5).setStaticMember(true)).buildField();
        } else {
            Class clazz = MappedClasses.lb;
            boolean bl = true;
            String string = "wooden_sword";
            MDamageSource mDamageSource = this;
            this.A = mDamageSource.registerStaticField(string, bl, clazz);
            Class clazz6 = MappedClasses.Di;
            boolean bl4 = true;
            String string6 = "potionitem";
            MDamageSource mDamageSource6 = this;
            this.a = this.registerStaticField(string6, bl4, clazz6);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MDamageSource.f(new int[1]);
        }
    }

    private Object M() {
        return this.A.getObject(null);
    }

    public static Object D(MDamageSource mDamageSource) {
        return mDamageSource.W();
    }

    private Object Q() {
        return this.z.getObject(null);
    }

    public static Object I(MDamageSource mDamageSource) {
        return mDamageSource.M();
    }


    static {
        MDamageSource.f(null);
    }

    public static Object T(MDamageSource mDamageSource) {
        return mDamageSource.Q();
    }

    private Object W() {
        return this.a.getObject(null);
    }
}

