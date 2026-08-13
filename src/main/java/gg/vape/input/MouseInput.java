package gg.vape.input;

import gg.vape.input.InputEventDispatcher;
import gg.vape.input.MouseInputState;
import gg.vape.wrapper.impl.Minecraft;

public class MouseInput {
    private static MouseInputState cachedState;

    public static int getMouseX() {
        return MouseInput.getState().getMouseX();
    }

    public static boolean isButtonDown(int button) {
        return MouseInput.getState().isButtonDown(button);
    }


    public static int getInvertedMouseY() {
        return Minecraft.h() - MouseInput.getState().getMouseY();
    }

    public static MouseInputState getState() {
        if (cachedState == null) {
            cachedState = InputEventDispatcher.getInstance().getMouseState();
        }
        return cachedState;
    }

    public static int getScrollDelta() {
        return MouseInput.getState().getScrollDelta();
    }

    public static boolean wasHandledByGui() {
        return MouseInput.getState().wasHandledByGui();
    }

    public static long getLastChangeTime() {
        return MouseInput.getState().getLastChangeTime();
    }

    public static boolean isLastButtonDown() {
        return MouseInput.getState().isLastButtonDown();
    }

    public static int getLastButton() {
        return MouseInput.getState().getLastButton();
    }

    public static /* synthetic */ int getLastButtonBridge() {
        return MouseInput.getLastButton();
    }

    public static /* synthetic */ long getLastChangeTimeBridge() {
        return MouseInput.getLastChangeTime();
    }
}

