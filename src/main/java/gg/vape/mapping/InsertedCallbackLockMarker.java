package gg.vape.mapping;

import gg.vape.mapping.InsertedCallbackMarker;

public class InsertedCallbackLockMarker
extends InsertedCallbackMarker {
    private static int reservedStateSeed;
    private static int reservedCheckValue;
    private static boolean locked;

    public static void unlock() {
        locked = false;
    }

    public static void lock() {
        locked = true;
    }

    public static boolean check(int n, int n2) {
        return locked;
    }

    static {
        locked = false;
    }
}
