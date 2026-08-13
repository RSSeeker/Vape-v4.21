package gg.vape.rotation;

public enum MouseButtonActionState {
    NONE,
    PRESS,
    RELEASE;

    private static final MouseButtonActionState[] cachedValues;

    static {
        String[] stateNames = new String[]{"NONE", "RELEASE", "PRESS"};



        cachedValues = new MouseButtonActionState[]{NONE, PRESS, RELEASE};
    }

}
