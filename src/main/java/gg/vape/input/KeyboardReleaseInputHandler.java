package gg.vape.input;

import gg.vape.input.InputEventDispatcher;
import gg.vape.input.InputEventHandler;
import gg.vape.input.KeyboardCodeUtil;

public class KeyboardReleaseInputHandler
implements InputEventHandler {
    private static boolean legacyState;


    public static void setLegacyState(boolean state) {
        legacyState = state;
    }

    @Override
    public boolean handle(long virtualKey, long keyMetadata) {
        int keyCode = KeyboardCodeUtil.resolveModifierKey((int)virtualKey, (int)keyMetadata);
        InputEventDispatcher.getInstance().getKeyboardState().setKeyState(keyCode, false);
        return false;
    }

    public static boolean isLegacyStateDisabled() {
        return !KeyboardReleaseInputHandler.getLegacyState();
    }

    public static boolean getLegacyState() {
        return legacyState;
    }

    static {
        if (!KeyboardReleaseInputHandler.isLegacyStateDisabled()) {
            KeyboardReleaseInputHandler.setLegacyState(true);
        }
    }
}

