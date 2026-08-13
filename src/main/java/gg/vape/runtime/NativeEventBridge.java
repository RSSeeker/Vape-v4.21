package gg.vape.runtime;

import gg.vape.Vape;

public class NativeEventBridge {
    public static void reg(Class<?> eventClass, int eventId) {
    }

    public static void call(Object event) {
        if (Vape.INSTANCE == null) {
            return;
        }
    }

}

