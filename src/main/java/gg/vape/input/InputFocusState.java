package gg.vape.input;


public class InputFocusState {
    private boolean focused = true;
    private static int legacyState;

    public void markFocused() {
        this.focused = true;
    }

    public static int getLegacySentinelResult() {
        int state = InputFocusState.getLegacyState();
        return 0;
    }

    public boolean isFocused() {
        return this.focused;
    }

    public static void setLegacyState(int state) {
        legacyState = state;
    }


    public void markUnfocused() {
        this.focused = false;
    }

    public static int getLegacyState() {
        return legacyState;
    }

    static {
        if (InputFocusState.getLegacyState() == 0) {
            InputFocusState.setLegacyState(49);
        }
    }
}

