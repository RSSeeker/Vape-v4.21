package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventChunkRenderRebuild;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class ChunkRenderRebuildEventMappingTask
extends JavassistMappingTask {
    private static final String c = "($r) 1";

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().qg.w;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventChunkRenderRebuild.class);
        eventInjectionSpec.setReturnExpression(c);
        this.registerEventInjection(eventInjectionSpec);
    }

    public ChunkRenderRebuildEventMappingTask() {
        super(MappedClasses.Zk);
    }
}
