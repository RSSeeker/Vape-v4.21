package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class LegacyStringRenderPayloadEvent
extends Event {
    private final Object fontRendererHandle;
    private final String text;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final int color;
    private final boolean dropShadow;
    private final float x;
    private final float y;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public int getX() {
        return (int)this.x;
    }

    public LegacyStringRenderPayloadEvent(Object fontRendererHandle, String text, float x, float y, int color, boolean dropShadow) {
        this.fontRendererHandle = fontRendererHandle;
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}
