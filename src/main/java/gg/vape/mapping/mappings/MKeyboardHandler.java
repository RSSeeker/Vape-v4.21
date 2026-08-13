package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MGameSettingsValue;
import gg.vape.ui.click.component.GuiComponent;

public class MKeyboardHandler
extends Mapping {
    private MappingMethod a;
    private MappingMethod f;

    private void z(Object object, String string) {
        this.f.invokeVoid(object, string);
    }

    public static String X(MKeyboardHandler mKeyboardHandler, Object object) {
        return mKeyboardHandler.H(object);
    }

    private String H(Object object) {
        return (String)this.a.invokeObject(object, new Object[0]);
    }

    public static void f(MKeyboardHandler mKeyboardHandler, Object object, String string) {
        mKeyboardHandler.z(object, string);
    }

    public MKeyboardHandler() {
        this(MGameSettingsValue.m());
    }

    private MKeyboardHandler(boolean bl) {
        super(MappedClasses.un);
        if (bl) {
            Class[] classArray = new Class[]{String.class};
            Class<Void> clazz = Void.TYPE;
            boolean bl2 = true;
            String string = "setClipboard";
            MKeyboardHandler mKeyboardHandler = this;
            this.f = mKeyboardHandler.Y(string, bl2, clazz, classArray);
            Class[] classArray2 = new Class[]{};
            Class<String> clazz2 = String.class;
            boolean bl3 = true;
            String string2 = "getClipboard";
            MKeyboardHandler mKeyboardHandler2 = this;
            this.a = this.Y(string2, bl3, clazz2, classArray2);
            if (GuiComponent.getLegacyComponentState() == null) {
                MGameSettingsValue.U(false);
            }
            return;
        }
        Class[] classArray = new Class[]{String.class};
        Class<Void> clazz = Void.TYPE;
        boolean bl4 = true;
        String string = "setClipboard";
        MKeyboardHandler mKeyboardHandler = this;
        this.f = mKeyboardHandler.Y(string, bl4, clazz, classArray);
        Class[] classArray3 = new Class[]{};
        Class<String> clazz3 = String.class;
        boolean bl5 = true;
        String string3 = "getClipboard";
        MKeyboardHandler mKeyboardHandler3 = this;
        this.a = this.Y(string3, bl5, clazz3, classArray3);
        if (GuiComponent.getLegacyComponentState() == null) {
            MGameSettingsValue.U(true);
        }
    }

}

