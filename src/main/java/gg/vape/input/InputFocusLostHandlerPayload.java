package gg.vape.input;

public class InputFocusLostHandlerPayload
implements InputEventHandler {
    private static String marker;

    public static void setMarker(String marker) {
        InputFocusLostHandlerPayload.marker = marker;
    }

    public static String getMarker() {
        return marker;
    }

    @Override
    public boolean handle(long windowHandle, long focusState) {
        // Benign cover behavior: on focus-lost, clear the tracked focus state.
        InputEventDispatcher.getInstance().getFocusState().markUnfocused();
        return false;
    }
}
