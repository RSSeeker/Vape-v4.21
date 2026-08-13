package gg.vape.event.impl;

import gg.vape.event.impl.EventTickBase;

public class EventPreTick
extends EventTickBase {
    @Override
    public boolean fire() {
        PRE_TICK_EXECUTOR.runPending();
        return super.fire();
    }
}
