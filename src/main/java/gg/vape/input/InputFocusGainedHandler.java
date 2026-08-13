package gg.vape.input;

import gg.vape.input.InputEventDispatcher;
import gg.vape.input.InputEventHandler;

public class InputFocusGainedHandler
implements InputEventHandler {
    @Override
    public boolean handle(long windowHandle, long focusState) {
        InputEventDispatcher.getInstance().getFocusState().markFocused();
        return false;
    }
}
