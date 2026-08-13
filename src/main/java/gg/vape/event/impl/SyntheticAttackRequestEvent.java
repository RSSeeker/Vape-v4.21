package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.Mod;

public class SyntheticAttackRequestEvent
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Mod source;

    public Mod getSource() {
        return this.source;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public SyntheticAttackRequestEvent(Mod mod) {
        this.source = mod;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
