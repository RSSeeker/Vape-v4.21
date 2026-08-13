package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;

public class EventBlockRenderLayerGate
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
        return xRay != null && xRay.boolean_r();
    }
}
