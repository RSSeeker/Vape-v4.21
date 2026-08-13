package gg.vape.mapping;

import gg.vape.mapping.InsertedCallbackMarker;
import gg.vape.mapping.MappedClasses;

public class AbstractEventRenderPlayerCallback
extends InsertedCallbackMarker {
    private static String s;

    private static boolean isPlayerInstance(Object object) {
        boolean bl = MappedClasses.Yl.isInstance(object) || MappedClasses.z5.isInstance(object);
        return bl;
    }

    public static String n() {
        return s;
    }

    public static void D(String string) {
        s = string;
    }


    public static boolean access$000(Object object) {
        return AbstractEventRenderPlayerCallback.isPlayerInstance(object);
    }

    static {
        if (AbstractEventRenderPlayerCallback.n() == null) {
            AbstractEventRenderPlayerCallback.D("LTsfgb");
        }
    }
}

