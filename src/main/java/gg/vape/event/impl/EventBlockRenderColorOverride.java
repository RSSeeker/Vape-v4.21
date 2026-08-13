package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.ForgeVersion;
import java.nio.FloatBuffer;

public class EventBlockRenderColorOverride
extends Event {
    private static boolean colorOverridePending;
    private static final EventListeners EVENT_LISTENERS;
    private static float alpha;
    private static float red;
    private static float green;
    private static float blue;

    public static void flip(FloatBuffer floatBuffer) {
        if (!colorOverridePending) {
            return;
        }
        floatBuffer.position(0);
        floatBuffer.put(red);
        floatBuffer.put(green);
        floatBuffer.put(blue);
        floatBuffer.put(alpha);
        colorOverridePending = false;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public EventBlockRenderColorOverride() {
        red = 1.0f;
        green = 0.0f;
        blue = 0.0f;
        alpha = ForgeVersion.MC_1_7_10.L() ? 0.4f : 0.3f;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }


    public static void setColor(float red, float green, float blue, float alpha) {
        EventBlockRenderColorOverride.red = red;
        EventBlockRenderColorOverride.green = green;
        EventBlockRenderColorOverride.blue = blue;
        EventBlockRenderColorOverride.alpha = alpha;
        colorOverridePending = true;
    }

    static {
        EVENT_LISTENERS = new EventListeners();
    }
}

