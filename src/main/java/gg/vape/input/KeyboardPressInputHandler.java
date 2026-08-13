package gg.vape.input;

import gg.vape.input.InputEventDispatcher;
import gg.vape.input.InputEventHandler;
import gg.vape.input.KeyboardCodeUtil;
import gg.vape.input.KeyboardInputState;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.click.component.GuiKeyTypedDispatcher;
import java.util.HashSet;
import java.util.Set;

public class KeyboardPressInputHandler
implements InputEventHandler {
    private final Set<Integer> navigationKeys = new HashSet<Integer>();


    public KeyboardPressInputHandler() {
        this.navigationKeys.add(37);
        this.navigationKeys.add(39);
        this.navigationKeys.add(36);
        this.navigationKeys.add(35);
    }

    @Override
    public boolean handle(long virtualKey, long keyMetadata) {
        int translatedCharacter = NativeBridge.mvk((int)virtualKey, 2);
        if (this.navigationKeys.contains((int)virtualKey)) {
            GuiKeyTypedDispatcher.dispatch((char)translatedCharacter, (int)virtualKey);
        }
        int keyCode = KeyboardCodeUtil.resolveModifierKey((int)virtualKey, (int)keyMetadata);
        KeyboardInputState keyboardState = InputEventDispatcher.getInstance().getKeyboardState();
        keyboardState.setKeyState(keyCode, true);
        return keyboardState.isCanceled();
    }
}

