package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MEnumHandBridge
extends Mapping {
    private static final String b;
    private static int d;
    private MappingField s;

    public static Object c(MEnumHandBridge mEnumHandBridge) {
        return mEnumHandBridge.h();
    }

    private Object h() {
        return this.s.getObject(null);
    }


    public MEnumHandBridge() {
        this(MEnumHandBridge.V());
    }

    private MEnumHandBridge(int n) {
        super(MappedClasses.YF);
        int n2 = n;
        Class clazz = MappedClasses.YF;
        boolean bl = true;
        String string = b;
        MEnumHandBridge mEnumHandBridge = this;
        this.s = this.registerStaticField(string, bl, clazz);
        if (GuiComponent.getLegacyComponentState() == null) {
            MEnumHandBridge.q(++n2);
        }
    }

    public static void q(int n) {
        d = n;
    }

    static {
        MEnumHandBridge.q(0);
        b = "QUADS";
    }

    public static int s() {
        return d;
    }

    public static int V() {
        int n = MEnumHandBridge.s();
        if (n == 0) {
            return 92;
        }
        return 0;
    }
}

