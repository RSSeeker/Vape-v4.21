package gg.vape.wrapper;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import java.util.HashMap;
import java.util.Map;

public class Wrapper {
    private static final Map<String, Integer> wrapperIdCache;
    public static boolean nativeDisabled;
    private static final String nullInstanceText;
    private static GuiComponent[] obfuscationState;
    public static boolean isNativeAvailable;
    protected Object I;
    public static Vape vapeInstance;

    public String toString() {
        if (this.I == null) {
            return nullInstanceText;
        }
        try {
            return this.I.toString();
        }
        catch (Exception exception) {
            return "";
        }
    }

    public boolean isInstance(Class clazz) {
        return this.isNotNull() && clazz != null && clazz.isInstance(this.I);
    }

    public Object getObject() {
        return this.I;
    }

    public boolean isNull() {
        return this.I == null;
    }

    public Wrapper(Object object) {
        if (object instanceof Wrapper) {
            Wrapper wrapper = (Wrapper)object;
            this.I = wrapper.getObject();
            return;
        }
        this.I = object;
    }

    static {
        Wrapper.setObfuscationState(null);
        nullInstanceText = "Null instance";
        vapeInstance = Vape.INSTANCE;
        isNativeAvailable = vapeInstance.isForgeAbsent();
        nativeDisabled = false;
        wrapperIdCache = new HashMap<String, Integer>();
    }

    public static GuiComponent[] getObfuscationState() {
        return obfuscationState;
    }

    public boolean isNotNull() {
        return !this.isNull();
    }

    public static void setObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }

    public boolean equals(Object object) {
        if (object == null || this.I == null) {
            return false;
        }
        if (object instanceof Wrapper) {
            Wrapper wrapper = (Wrapper)object;
            return this.I.equals(wrapper.I);
        }
        return super.equals(object);
    }
}
