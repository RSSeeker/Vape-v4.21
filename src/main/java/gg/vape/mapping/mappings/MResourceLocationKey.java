package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MResourceLocationKey
extends Mapping {
    private static boolean Y;
    private static final String b;
    private final MappingField I;

    public static boolean m() {
        boolean bl = MResourceLocationKey.A();
        return !bl;
    }

    public MResourceLocationKey() {
        this(MResourceLocationKey.m());
    }

    private MResourceLocationKey(boolean bl) {
        super(MappedClasses.qJ);
        if (bl) {
            Class clazz = MappedClasses.qJ;
            boolean bl2 = true;
            String string = b;
            MResourceLocationKey mResourceLocationKey = this;
            this.I = mResourceLocationKey.registerStaticField(string, bl2, clazz);
            if (GuiComponent.getLegacyComponentState() == null) {
                MResourceLocationKey.L(false);
            }
            return;
        }
        Class clazz = MappedClasses.qJ;
        boolean bl3 = true;
        String string = b;
        MResourceLocationKey mResourceLocationKey = this;
        this.I = mResourceLocationKey.registerStaticField(string, bl3, clazz);
        if (GuiComponent.getLegacyComponentState() == null) {
            MResourceLocationKey.L(true);
        }
    }

    public static boolean A() {
        return Y;
    }


    public static void L(boolean bl) {
        Y = bl;
    }

    public Object r() {
        return this.I.getObject(null);
    }

    static {
        MResourceLocationKey.L(false);
        b = "CRASH";
    }
}

