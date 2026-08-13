package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.EntityPlayerSP;

public class EventLocalPlayerTickBase
extends Event {
    private final EntityPlayerSP player;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    EventLocalPlayerTickBase(Object playerHandle) {
        this.player = new EntityPlayerSP(playerHandle);
    }

    public EntityPlayerSP getPlayer() {
        return this.player;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}
