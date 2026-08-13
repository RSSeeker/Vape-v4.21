package gg.vape.input;

import gg.vape.input.InputEventDispatcher;
import gg.vape.input.InputEventHandler;
import gg.vape.input.KeyboardCodeUtil;

public class ExtendedMouseButtonPressInputHandler
implements InputEventHandler {
    @Override
    public boolean handle(long buttonMetadata, long secondArgument) {
        return InputEventDispatcher.getInstance().getMouseState().setButtonState(KeyboardCodeUtil.decodeExtendedMouseButton(buttonMetadata), true);
    }
}
