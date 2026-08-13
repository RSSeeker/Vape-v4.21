package gg.vape.input;

import gg.vape.input.CharacterInputHandler;
import gg.vape.input.ExtendedMouseButtonPressInputHandler;
import gg.vape.input.ExtendedMouseButtonReleaseInputHandler;
import gg.vape.input.InputEventHandler;
import gg.vape.input.InputFocusGainedHandler;
import gg.vape.input.InputFocusLostHandlerPayload;
import gg.vape.input.InputFocusState;
import gg.vape.input.KeyboardInputState;
import gg.vape.input.KeyboardPressInputHandler;
import gg.vape.input.KeyboardReleaseInputHandler;
import gg.vape.input.MouseButtonPressInputHandler;
import gg.vape.input.MouseButtonReleaseInputHandler;
import gg.vape.input.MouseInputState;
import gg.vape.input.MouseMoveInputHandler;
import gg.vape.input.MouseWheelInputHandler;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderTypeBuffer;
import java.util.HashMap;

public class InputEventDispatcher {
    public HashMap<Integer, InputEventHandler> handlers = new HashMap();
    private InputFocusState focusState;
    private static InputEventDispatcher instance;
    private MouseInputState mouseState;
    private KeyboardInputState keyboardState = new KeyboardInputState();
    private long windowHandle;
    private static int[] legacyState;


    public static void setLegacyState(int[] state) {
        legacyState = state;
    }

    public KeyboardInputState getKeyboardState() {
        return this.keyboardState;
    }

    public static InputEventDispatcher getInstance() {
        if (instance == null) {
            instance = new InputEventDispatcher();
        }
        return instance;
    }

    public InputEventDispatcher() {
        this.mouseState = new MouseInputState();
        this.focusState = new InputFocusState();
    }

    public InputFocusState getFocusState() {
        return this.focusState;
    }

    public void flushRenderBufferBeforeInput() {
        if (ForgeVersion.MC_1_21_10.v()) {
            return;
        }
        RenderTypeBuffer framerateLimitTracker = Minecraft.getFramerateLimitTracker();
        if (framerateLimitTracker.isNull()) {
            return;
        }
        if (!this.focusState.isFocused()) {
            return;
        }
        framerateLimitTracker.onInputReceived();
    }

    public boolean dispatch(int notificationCode, long firstArgument, long secondArgument) {
        InputEventHandler handler = this.handlers.get(notificationCode);
        if (handler != null) {
            this.flushRenderBufferBeforeInput();
            return handler.handle(firstArgument, secondArgument);
        }
        return false;
    }

    public void registerHandlers() {
        int[] legacyState = InputEventDispatcher.getLegacyState();
        this.handlers.put(258, new CharacterInputHandler());
        this.handlers.put(256, new KeyboardPressInputHandler());
        this.handlers.put(257, new KeyboardReleaseInputHandler());
        this.handlers.put(260, new KeyboardPressInputHandler());
        this.handlers.put(261, new KeyboardReleaseInputHandler());
        this.handlers.put(513, new MouseButtonPressInputHandler(0));
        this.handlers.put(516, new MouseButtonPressInputHandler(1));
        this.handlers.put(519, new MouseButtonPressInputHandler(2));
        this.handlers.put(514, new MouseButtonReleaseInputHandler(0));
        this.handlers.put(517, new MouseButtonReleaseInputHandler(1));
        this.handlers.put(520, new MouseButtonReleaseInputHandler(2));
        this.handlers.put(523, new ExtendedMouseButtonPressInputHandler());
        this.handlers.put(524, new ExtendedMouseButtonReleaseInputHandler());
        this.handlers.put(522, new MouseWheelInputHandler());
        this.handlers.put(512, new MouseMoveInputHandler());
        this.handlers.put(7, new InputFocusGainedHandler());
        this.handlers.put(8, new InputFocusLostHandlerPayload());
        if (legacyState != null) {
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
        }
    }

    public static int[] getLegacyState() {
        return legacyState;
    }

    public long getWindowHandle() {
        return this.windowHandle;
    }

    public void setWindowHandle(long windowHandle) {
        this.windowHandle = windowHandle;
        this.focusState.markFocused();
    }

    public MouseInputState getMouseState() {
        return this.mouseState;
    }

    static {
        if (InputEventDispatcher.getLegacyState() != null) {
            InputEventDispatcher.setLegacyState(new int[1]);
        }
    }
}

