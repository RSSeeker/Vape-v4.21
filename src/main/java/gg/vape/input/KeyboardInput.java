package gg.vape.input;

import gg.vape.input.InputEventDispatcher;
import gg.vape.input.KeyboardInputState;
import gg.vape.wrapper.impl.ForgeVersion;
import org.lwjgl.input.Keyboard;

public class KeyboardInput {
    private static KeyboardInputState cachedState;

    public static boolean isKeyDown(int keyCode) {
        return KeyboardInput.getState().isKeyDown(keyCode);
    }

    public static String getKeyName(int keyCode) {
        if (keyCode < 0) {
            int mouseButton = keyCode + 100;
            return "M" + mouseButton;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return String.valueOf((char)keyCode);
        }
        return Keyboard.getKeyName((int)keyCode);
    }


    public static KeyboardInputState getState() {
        if (cachedState == null) {
            cachedState = InputEventDispatcher.getInstance().getKeyboardState();
        }
        return cachedState;
    }
}

