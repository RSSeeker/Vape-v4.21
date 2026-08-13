package gg.vape.event.impl;

import gg.vape.event.impl.EventEntityRendererMouseUpdateBase;
import gg.vape.wrapper.impl.DeltaTracker;

public class EventEntityRendererMouseUpdate
extends EventEntityRendererMouseUpdateBase {
    private final float partialTicks;

    public EventEntityRendererMouseUpdate(Object deltaTrackerHandle) {
        this.partialTicks = new DeltaTracker(deltaTrackerHandle).getGameTimeDeltaPartialTick(true);
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
