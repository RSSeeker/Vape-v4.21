package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.SoundEvent;

@Deprecated
public class EventPlaySoundAtEntity
extends Event {
    private final Entity entity;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final String name;

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public Entity getEntity() {
        return this.entity;
    }

    public String getName() {
        return this.name;
    }

    public EventPlaySoundAtEntity(Entity entity, Object soundHandleOrName) {
        this.entity = entity;
        if (ForgeVersion.MC_1_16_5.d()) {
            SoundEvent soundEvent = new SoundEvent(soundHandleOrName);
            this.name = soundEvent.V().getResourcePath();
        } else {
            this.name = (String)soundHandleOrName;
        }
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
