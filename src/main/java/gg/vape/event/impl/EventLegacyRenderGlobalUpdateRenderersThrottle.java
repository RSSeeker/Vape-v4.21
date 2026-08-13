package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Minecraft;

public class EventLegacyRenderGlobalUpdateRenderersThrottle
extends Event {
    static float lastPartialTicks = 0.0f;
    private static final EventListeners EVENT_LISTENERS;
    static float callsSincePartialTickChange;
    private static boolean obfuscationState;

    @Override
    public boolean fire() {
        float partialTicks = Minecraft.getTimer().renderPartialTicks();
        callsSincePartialTickChange += 1.0f;
        if (partialTicks != lastPartialTicks && callsSincePartialTickChange >= 10.0f) {
            callsSincePartialTickChange = 0.0f;
        }
        if (callsSincePartialTickChange > 0.0f) {
            this.setCancelled(true);
        }
        lastPartialTicks = partialTicks;
        return super.fire();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static void setThrottleObfuscationState(boolean state) {
        obfuscationState = state;
    }

    public static boolean getThrottleObfuscationState() {
        return obfuscationState;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    static {
        callsSincePartialTickChange = 0.0f;
        EVENT_LISTENERS = new EventListeners();
        EventLegacyRenderGlobalUpdateRenderersThrottle.setThrottleObfuscationState(false);
    }


    public static boolean getObfuscationConstant() {
        boolean state = EventLegacyRenderGlobalUpdateRenderersThrottle.getThrottleObfuscationState();
        return true;
    }
}

