package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class EventRightClickMouse
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public boolean fire() {
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}
