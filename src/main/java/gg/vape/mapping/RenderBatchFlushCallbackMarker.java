package gg.vape.mapping;

import gg.vape.mapping.InsertedCallbackMarker;
import gg.vape.utils.render.RenderBatchManager;

public class RenderBatchFlushCallbackMarker
extends InsertedCallbackMarker {
    public static void call() {
        RenderBatchManager.getInstance().refreshTargetFramebuffer();
    }
}

