package gg.vape.input;

import gg.vape.input.InputEventDispatcher;
import gg.vape.input.InputEventHandler;
import gg.vape.input.Win32InputConstants;

public class MouseWheelInputHandler
implements InputEventHandler {
    @Override
    public boolean handle(long packedWheelMetadata, long secondArgument) {
        short scrollDelta = Win32InputConstants.extractHighWord(packedWheelMetadata);
        return InputEventDispatcher.getInstance().getMouseState().setScrollDelta(scrollDelta);
    }
}
