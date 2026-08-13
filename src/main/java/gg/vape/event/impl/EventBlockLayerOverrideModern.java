package gg.vape.event.impl;

import gg.vape.event.impl.EventBlockLayerOverride;

public class EventBlockLayerOverrideModern
extends EventBlockLayerOverride {
    @Override
    public boolean fire() {
        return super.fire();
    }

    public EventBlockLayerOverrideModern(Object blockHandle) {
        super(blockHandle);
    }
}
