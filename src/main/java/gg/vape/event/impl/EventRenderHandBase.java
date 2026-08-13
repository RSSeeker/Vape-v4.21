package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class EventRenderHandBase
extends Event {
    private static String primaryObfuscationState;
    private static final EventListeners EVENT_LISTENERS;
    private static String secondaryObfuscationState;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    static {
        secondaryObfuscationState = null;
        primaryObfuscationState = null;
        EVENT_LISTENERS = new EventListeners();
    }
}
