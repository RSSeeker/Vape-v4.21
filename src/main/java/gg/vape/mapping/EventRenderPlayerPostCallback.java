package gg.vape.mapping;

import gg.vape.event.impl.EventRenderPlayerPost;
import gg.vape.mapping.AbstractEventRenderPlayerCallback;

public class EventRenderPlayerPostCallback
extends AbstractEventRenderPlayerCallback {
    public static void call(Object object, Object object2, Object object3) {
        if (!AbstractEventRenderPlayerCallback.access$000(object)) {
            return;
        }
        new EventRenderPlayerPost(object, object2, object3).fire();
    }

}

