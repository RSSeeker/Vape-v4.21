package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderTypeBufferBridge;
import gg.vape.wrapper.Wrapper;

public class RenderTypeBuffer
extends Wrapper {
    public RenderTypeBuffer(Object handle) {
        super(handle);
    }

    public void onInputReceived() {
        MRenderTypeBufferBridge.onInputReceived(RenderTypeBuffer.vapeInstance.getMappingsMapperCompat().framerateLimitTracker, this.getObject());
    }
}
