package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;

public class EventLivingUpdate
extends Event {
    private final Entity entity;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public EventLivingUpdate(Entity entity) {
        this.entity = entity;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
