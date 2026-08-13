package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class EventRenderWorldPassBase
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public boolean fire() {
        return super.fire();
    }

    public EntityPlayerSP getPlayer() {
        return Minecraft.a_xH_J();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}
