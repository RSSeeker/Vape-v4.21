package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBlockLayerOverrideFallback;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class BlockLayerOverrideFallbackEventMappingTask
extends JavassistMappingTask {
    public BlockLayerOverrideFallbackEventMappingTask() {
        super(MappedClasses.lA);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().N.gcMethod;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockLayerOverrideFallback.class);
        eventInjectionSpec.setInsertBefore(true);
        this.registerEventInjection(eventInjectionSpec);
    }
}
