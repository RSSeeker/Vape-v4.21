package gg.vape.event.impl;

import gg.vape.event.impl.EventStep;

public class EventStepSnapshot
extends EventStep {
    public EventStepSnapshot(Object entityHandle) {
        super(entityHandle);
        EventStep.storeOriginalStepHeight(this.getEntity().u());
    }
}
