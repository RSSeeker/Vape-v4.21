package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;
import gg.vape.wrapper.impl.Block;

public class EventBlockShouldRender
extends Event {
    private final Block block;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public EventBlockShouldRender(Object blockHandle) {
        this.block = new Block(blockHandle);
    }

    @Override
    public boolean fire() {
        XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onBlockSideRender(this);
        return this.isCanceled();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public Block getBlock() {
        return this.block;
    }


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}

