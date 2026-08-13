package gg.vape.mapping;

import gg.vape.event.impl.EventThreadBoundPreTick;
import gg.vape.event.impl.EventTickBase;
import gg.vape.mapping.ThreadBoundTickCallbackBase;

public class ThreadBoundEventPreTickCallback
extends ThreadBoundTickCallbackBase {
    public static void call() {
        if (Thread.currentThread().equals(EventTickBase.PRE_TICK_EXECUTOR.getOwnerThread())) {
            new EventThreadBoundPreTick().fire();
        }
    }

}

