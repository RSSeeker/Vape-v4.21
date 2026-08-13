package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MRenderWorldLastEvent;
import gg.vape.wrapper.Wrapper;

public class RenderWorldLastEvent
extends Wrapper {

    public static float getRenderResolutionMultiplier() {
        if (!Vape.renderReady) {
            return 1.0f;
        }
        return MRenderWorldLastEvent.getRenderResolutionMultiplier(RenderWorldLastEvent.vapeInstance.getMappingsMapperCompat().shadersConfig);
    }

    public RenderWorldLastEvent(Object handle) {
        super(handle);
    }
}

