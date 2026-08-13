package gg.vape.mapping;

import gg.vape.event.impl.EventThreadBoundPostTick;
import gg.vape.event.impl.EventTickBase;
import gg.vape.mapping.ThreadBoundTickCallbackBase;

public class ThreadBoundEventPostTickCallback
extends ThreadBoundTickCallbackBase {
    public static void call() {
        if (Thread.currentThread().equals(EventTickBase.PRE_TICK_EXECUTOR.getOwnerThread())) {
            new EventThreadBoundPostTick().fire();
        }
    }

}

