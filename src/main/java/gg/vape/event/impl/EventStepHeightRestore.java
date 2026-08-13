package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.event.impl.EventStep;
import gg.vape.wrapper.impl.Entity;

public class EventStepHeightRestore
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Entity entity;

    public EventStepHeightRestore(Object entityHandle) {
        this.entity = new Entity(entityHandle);
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        this.entity.K(EventStep.getOriginalStepHeight());
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
