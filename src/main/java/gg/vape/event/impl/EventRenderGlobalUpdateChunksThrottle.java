package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class EventRenderGlobalUpdateChunksThrottle
extends Event {
    private static final EventListeners EVENT_LISTENERS;
    static float callsSinceReset;
    private static String obfuscationState;

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        if ((callsSinceReset += 1.0f) >= 100.0f) {
            callsSinceReset = 0.0f;
        }
        if (callsSinceReset > 0.0f) {
            this.setCancelled(true);
        }
        return super.fire();
    }


    public static void setObfuscationState(String state) {
        obfuscationState = state;
    }

    public static String getObfuscationState() {
        return obfuscationState;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    static {
        callsSinceReset = 0.0f;
        EventRenderGlobalUpdateChunksThrottle.setObfuscationState("p3vFd");
        EVENT_LISTENERS = new EventListeners();
    }
}

