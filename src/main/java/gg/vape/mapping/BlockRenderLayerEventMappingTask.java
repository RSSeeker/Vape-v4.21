package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBlockLayerOverride;
import gg.vape.event.impl.EventBlockRenderLayerGate;
import gg.vape.event.impl.EventBlockShouldRender;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class BlockRenderLayerEventMappingTask
extends JavassistMappingTask {
    public BlockRenderLayerEventMappingTask() {
        super(MappedClasses.Zk);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().qg.J;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockShouldRender.class);
        eventInjectionSpec.setConstructorArguments("$0");
        eventInjectionSpec.setReturnExpression("true");
        this.registerEventInjection(eventInjectionSpec);
        MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().qg.S;
        EventInjectionSpec eventInjectionSpec2 = new EventInjectionSpec(mappingMethod2, EventBlockLayerOverride.class);
        eventInjectionSpec2.setConstructorArguments("$0");
        eventInjectionSpec2.setReturnExpression("($r) $event.getBlockLayer()");
        this.registerEventInjection(eventInjectionSpec2);
        MappingMethod mappingMethod3 = Vape.INSTANCE.getMappings().qg.M;
        EventInjectionSpec eventInjectionSpec3 = new EventInjectionSpec(mappingMethod3, EventBlockRenderLayerGate.class);
        eventInjectionSpec3.setInsertBefore(true);
        eventInjectionSpec3.setReturnExpression("($r) 1");
        this.registerEventInjection(eventInjectionSpec3);
    }
}
