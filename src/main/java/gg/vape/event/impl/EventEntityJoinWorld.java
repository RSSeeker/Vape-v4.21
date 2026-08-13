package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;

public class EventEntityJoinWorld
extends Event {
    private static final EventListeners EVENT_LISTENERS;
    private static final String SERVER_THREAD_NAME;
    private final Entity entity;

    public EventEntityJoinWorld(Object entityHandle) {
        this.entity = new Entity(entityHandle);
    }

    @Override
    public boolean fire() {
        if (Thread.currentThread().getName().contains(SERVER_THREAD_NAME)) {
            return false;
        }
        return super.fire();
    }

    static {
        SERVER_THREAD_NAME = "Server";
        EVENT_LISTENERS = new EventListeners();
    }

    public Entity getEntity() {
        return this.entity;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

}

