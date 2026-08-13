package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBlockLayerOverrideLegacy;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class BlockLayerOverrideEventMappingTask
extends JavassistMappingTask {
    public BlockLayerOverrideEventMappingTask() {
        super(MappedClasses.ZU);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().qg.S;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockLayerOverrideLegacy.class);
        eventInjectionSpec.setConstructorArguments("$0");
        eventInjectionSpec.setReturnExpression("($r) $event.getBlockLayer()");
        this.registerEventInjection(eventInjectionSpec);
    }
}
