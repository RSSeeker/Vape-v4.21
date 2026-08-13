package gg.vape.mapping;

import gg.vape.event.impl.EventPostTick;
import gg.vape.mapping.InsertedTickCallbackBase;

public class EventPostTickCallback
extends InsertedTickCallbackBase {
    public static void call() {
        new EventPostTick().fire();
    }
}

