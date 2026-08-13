package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;

public class EventBlockFluidRender
extends Event {
    private final int z;
    private final int x;
    private final Object blockHandle;
    private final int y;
    private final Object renderBlocksHandle;
    private boolean result;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public boolean isResult() {
        return this.result;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventBlockFluidRender(Object renderBlocksHandle, Object blockHandle, int x, int y, int z) {
        this.renderBlocksHandle = renderBlocksHandle;
        this.blockHandle = blockHandle;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean fire() {
        XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onBlockFluidRender(this);
        if (this.isCanceled()) {
            this.result = Vape.INSTANCE.getMappingsMapperCompat().renderBlocks
                    .renderStandardBlockWithColorMultiplierMethod.invokeBoolean(
                            this.renderBlocksHandle, this.blockHandle, this.x, this.y, this.z,
                            Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f));
        }
        return this.isCanceled();
    }


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}

