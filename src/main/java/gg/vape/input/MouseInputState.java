package gg.vape.input;

import gg.vape.event.impl.EventMouseButton;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiScreenNativeCallbackBridge;
import java.util.HashMap;

public class MouseInputState {
    private HashMap<Integer, Boolean> buttonStates = new HashMap();
    private boolean handledByGui;
    private int scrollDelta;
    private boolean lastButtonDown;
    private int lastButton;
    private long lastChangeTime = System.nanoTime();
    private int mouseX;
    private int mouseY;

    private boolean dispatchButtonChange(int button, boolean buttonDown) {
        this.lastChangeTime = System.nanoTime();
        this.lastButtonDown = buttonDown;
        this.lastButton = button;
        if (!ClientSettings.INSTANCE.inputEnabled) {
            if (buttonDown) {
                GuiScreenNativeCallbackBridge.mouseClicked(null, this.mouseX, this.mouseY, button);
            }
            this.handledByGui = true;
            return true;
        }
        return new EventMouseButton(button, buttonDown).fire();
    }

    public boolean setButtonState(int button, boolean buttonDown) {
        boolean previousState = this.buttonStates.getOrDefault(button, false);
        boolean canceled = false;
        if (previousState != buttonDown) {
            canceled = this.dispatchButtonChange(button, buttonDown);
        }
        this.buttonStates.put(button, buttonDown);
        return canceled;
    }

    public boolean updateCursorPosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        if (!ClientSettings.INSTANCE.inputEnabled) {
            GuiScreenNativeCallbackBridge.handleMouseInput(null);
        }
        return false;
    }

    public int getLastButton() {
        return this.lastButton;
    }


    public boolean getButtonState(int button) {
        return this.buttonStates.getOrDefault(button, false);
    }

    public long getLastChangeTime() {
        return this.lastChangeTime;
    }

    public boolean isButtonDown(int button) {
        return this.buttonStates.getOrDefault(button, false);
    }

    public boolean setScrollDelta(int scrollDelta) {
        if (!ClientSettings.INSTANCE.inputEnabled) {
            this.scrollDelta = scrollDelta;
            return true;
        }
        return false;
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    public boolean wasHandledByGui() {
        return this.handledByGui;
    }

    public boolean isLastButtonDown() {
        return this.lastButtonDown;
    }

    public void prepareFrame() {
    }

    public void resetScrollDelta() {
        this.scrollDelta = 0;
    }

    public int getScrollDelta() {
        return this.scrollDelta;
    }
}

