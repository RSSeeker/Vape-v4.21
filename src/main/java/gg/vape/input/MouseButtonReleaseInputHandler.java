package gg.vape.input;

import gg.vape.input.InputEventDispatcher;
import gg.vape.input.InputEventHandler;

public class MouseButtonReleaseInputHandler
implements InputEventHandler {
    int button;

    public MouseButtonReleaseInputHandler(int button) {
        this.button = button;
    }

    @Override
    public boolean handle(long firstArgument, long secondArgument) {
        return InputEventDispatcher.getInstance().getMouseState().setButtonState(this.button, false);
    }
}
