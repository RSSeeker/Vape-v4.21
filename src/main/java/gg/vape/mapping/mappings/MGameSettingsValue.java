package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MGameSettingsValue
extends Mapping {
    private MappingMethod d;
    private static boolean r;
    private MappingField W;
    private MappingMethod B;

    public static boolean m() {
        return r;
    }

    public static Object U(MGameSettingsValue mGameSettingsValue, Object object) {
        return mGameSettingsValue.X(object);
    }

    public static void U(boolean bl) {
        r = bl;
    }

    public MGameSettingsValue() {
        this(MGameSettingsValue.X());
    }

    private MGameSettingsValue(boolean bl) {
        super(MappedClasses.l4);
        Class<Object> clazz = Object.class;
        boolean bl2 = true;
        String string = "value";
        MGameSettingsValue mGameSettingsValue = this;
        this.W = this.J(string, bl2, clazz);
        Class[] classArray = new Class[]{};
        Class<Object> clazz2 = Object.class;
        boolean bl3 = true;
        String string2 = "get";
        MGameSettingsValue mGameSettingsValue2 = this;
        this.B = this.Y(string2, bl3, clazz2, classArray);
        if (bl) {
            Class[] classArray2 = new Class[]{Object.class};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl4 = true;
            String string3 = "set";
            MGameSettingsValue mGameSettingsValue3 = this;
            this.d = this.Y(string3, bl4, clazz3, classArray2);
            return;
        }
        Class[] classArray3 = new Class[]{Object.class};
        Class<Void> clazz4 = Void.TYPE;
        boolean bl5 = true;
        String string4 = "set";
        MGameSettingsValue mGameSettingsValue4 = this;
        this.d = this.Y(string4, bl5, clazz4, classArray3);
        GuiComponent.setLegacyComponentState(new GuiComponent[4]);
    }


    private Object X(Object object) {
        return this.B.invokeObject(object, new Object[0]);
    }

    public static boolean X() {
        boolean bl = MGameSettingsValue.m();
        return !bl;
    }

    static {
        MGameSettingsValue.U(false);
    }

    public static void S(MGameSettingsValue mGameSettingsValue, Object object, Object object2) {
        mGameSettingsValue.d(object, object2);
    }

    public void b(Object object, Object object2) {
        this.W.setObject(object, object2);
    }

    private void d(Object object, Object object2) {
        this.d.invokeVoid(object, object2);
    }
}

