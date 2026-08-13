package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.EnumWorldBlockLayer;

public class EventBlockLayerRender
extends Event {
    private final EnumWorldBlockLayer blockLayer;
    private final Block block;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private boolean shouldRender;

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    @Override
    public boolean fire() {
        XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onBlockRenderLayer(this);
        return super.isCanceled();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    public boolean shouldRender() {
        return this.shouldRender;
    }

    public EventBlockLayerRender(Object blockHandle, Object blockLayerHandle) {
        this.block = new Block(blockHandle);
        this.blockLayer = new EnumWorldBlockLayer(blockLayerHandle);
    }

    public EnumWorldBlockLayer getEnumWorldBlockLayer() {
        return this.blockLayer;
    }

    public Block getBlock() {
        return this.block;
    }
}

