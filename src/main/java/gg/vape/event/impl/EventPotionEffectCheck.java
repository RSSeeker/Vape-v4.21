package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.Potion;

public class EventPotionEffectCheck
extends Event {
    private final Object potionHandle;
    private final Object entityHandle;
    private Potion potion;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private boolean active;
    private Entity entity;

    public EventPotionEffectCheck(Object entityHandle, Object potionHandle) {
        this.entityHandle = entityHandle;
        this.potionHandle = potionHandle;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public boolean isActive() {
        return this.active;
    }

    public Potion getPotion() {
        if (this.potion == null) {
            this.potion = new Potion(this.potionHandle);
        }
        return this.potion;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Entity getEntity() {
        if (this.entity == null) {
            this.entity = new Entity(this.entityHandle);
        }
        return this.entity;
    }
}

