package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class EventKeyBindingState
extends Event {
    private final Object keyBinding;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final int keyCode;
    private static String[] obfuscationState;
    private final boolean pressed;

    public static void setKeyBindingObfuscationState(String[] state) {
        obfuscationState = state;
    }

    public EventKeyBindingState(Object keyBinding, boolean pressed) {
        this.keyBinding = keyBinding;
        this.keyCode = 0;
        this.pressed = pressed;
    }

    public Object getKeyBinding() {
        return this.keyBinding;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public EventKeyBindingState(int keyCode, boolean pressed) {
        this.keyBinding = null;
        this.keyCode = keyCode;
        this.pressed = pressed;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public boolean isPressed() {
        return this.pressed;
    }

    public static String[] getKeyBindingObfuscationState() {
        return obfuscationState;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    static {
        EventKeyBindingState.setKeyBindingObfuscationState(null);
    }
}
