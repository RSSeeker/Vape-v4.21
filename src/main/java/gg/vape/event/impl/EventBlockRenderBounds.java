package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.RenderBlocks;

public class EventBlockRenderBounds
extends Event {
    private final RenderBlocks renderBlocks;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Block block;

    public RenderBlocks getRenderBlocks() {
        return this.renderBlocks;
    }

    public EventBlockRenderBounds(Object renderBlocksHandle, Object blockHandle) {
        this.renderBlocks = new RenderBlocks(renderBlocksHandle);
        this.block = new Block(blockHandle);
    }

    public Block getBlock() {
        return this.block;
    }

    @Override
    public boolean fire() {
        XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onAmbientOcclusion(this);
        return this.isCanceled();
    }


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}

