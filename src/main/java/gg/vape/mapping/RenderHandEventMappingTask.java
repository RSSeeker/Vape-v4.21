package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPostRenderHand;
import gg.vape.event.impl.EventPreRenderHand;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class RenderHandEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        if (Vape.INSTANCE.getMappings().RY.x != null && !Vape.INSTANCE.getMappings().RY.x.hasResolutionFailed()) {
            this.c(Vape.INSTANCE.getMappings().RY.x, EventPreRenderHand.class, "");
            this.k(Vape.INSTANCE.getMappings().RY.x, EventPostRenderHand.class, "");
        }
    }

    public RenderHandEventMappingTask() {
        super(MappedClasses.lt);
    }

}

