package gg.vape.event.impl;

import gg.vape.event.impl.EventBlockLayerOverride;

public class EventBlockLayerOverrideLegacy
extends EventBlockLayerOverride {
    @Override
    public boolean fire() {
        return super.fire();
    }

    public EventBlockLayerOverrideLegacy(Object blockHandle) {
        super(blockHandle);
    }
}
