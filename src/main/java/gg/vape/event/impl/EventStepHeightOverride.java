package gg.vape.event.impl;

import gg.vape.event.impl.EventStep;

public class EventStepHeightOverride
extends EventStep {
    private final float realHeight;

    public EventStepHeightOverride(Object entityHandle, double heightOffset) {
        super(entityHandle);
        this.realHeight = (float)(1.0 + heightOffset);
    }

    @Override
    public double getRealHeight() {
        return this.realHeight;
    }
}
