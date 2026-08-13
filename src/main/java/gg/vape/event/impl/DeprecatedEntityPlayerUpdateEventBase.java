package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.Entity;

@Deprecated
public class DeprecatedEntityPlayerUpdateEventBase
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Entity entity;

    public DeprecatedEntityPlayerUpdateEventBase(Object entityHandle) {
        this.entity = new Entity(entityHandle);
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


    @Override
    public boolean fire() {
        if (!this.entity.isInstance(MappedClasses.z5)) {
            return false;
        }
        return super.fire();
    }
}

