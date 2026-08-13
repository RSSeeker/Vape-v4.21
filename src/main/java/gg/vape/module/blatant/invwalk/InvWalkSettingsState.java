package gg.vape.module.blatant.invwalk;

import gg.vape.config.ClientSettings;
import gg.vape.input.KeyBindingHelper;
import gg.vape.wrapper.impl.KeyBinding;

public class InvWalkSettingsState {
    private static int keyState;

    public static int getInitializationSentinel() {
        int state = InvWalkSettingsState.getKeyState();
        if (state == 0) {
            return 116;
        }
        return 0;
    }

    public static void setKeyState(int state) {
        keyState = state;
    }

    public static boolean isPhysicallyPressed(KeyBinding keyBinding) {
        return ClientSettings.isPhysicalKeyDown(keyBinding);
    }


    public static void synchronizeKey(KeyBinding keyBinding) {
        if (InvWalkSettingsState.isPhysicallyPressed(keyBinding)) {
            InvWalkSettingsState.setPressed(keyBinding, true);
        } else if (keyBinding.u()) {
            InvWalkSettingsState.setPressed(keyBinding, false);
        }
    }

    public static void setPressed(KeyBinding keyBinding, boolean pressed) {
        KeyBindingHelper.setPressedAndTick(keyBinding, pressed);
    }

    public static int getKeyState() {
        return keyState;
    }

    static {
        if (InvWalkSettingsState.getInitializationSentinel() != 0) {
            InvWalkSettingsState.setKeyState(62);
        }
    }
}

