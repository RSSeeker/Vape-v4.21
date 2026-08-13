package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.Mod;

public class EventModStateChange
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final boolean enabled;
    private final Mod module;

    public EventModStateChange(Mod module, boolean enabled) {
        this.module = module;
        this.enabled = enabled;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public Mod getModule() {
        return this.module;
    }
}
