package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;

public class EventStep
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Entity entity;
    private static float originalStepHeight;

    static float storeOriginalStepHeight(float stepHeight) {
        originalStepHeight = stepHeight;
        return originalStepHeight;
    }

    public double getStepHeight() {
        return this.entity.u();
    }

    public void setStepHeight(double stepHeight) {
        this.entity.K((float)stepHeight);
    }

    static float getOriginalStepHeight() {
        return originalStepHeight;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public double getRealHeight() {
        return originalStepHeight;
    }

    EventStep(Object entityHandle) {
        this.entity = new Entity(entityHandle);
    }
}
