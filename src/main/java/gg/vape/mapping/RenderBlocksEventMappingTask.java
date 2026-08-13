package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBlockFluidRender;
import gg.vape.event.impl.EventBlockRenderBounds;
import gg.vape.event.impl.EventLegacyXRayRenderFaceXNeg;
import gg.vape.event.impl.EventLegacyXRayRenderFaceXPos;
import gg.vape.event.impl.EventLegacyXRayRenderFaceYNeg;
import gg.vape.event.impl.EventLegacyXRayRenderFaceYPos;
import gg.vape.event.impl.EventLegacyXRayRenderFaceZNeg;
import gg.vape.event.impl.EventLegacyXRayRenderFaceZPos;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class RenderBlocksEventMappingTask
extends JavassistMappingTask {
    public RenderBlocksEventMappingTask() {
        super(MappedClasses.q5);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().renderBlocks.renderBlockByRenderTypeMethod;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockRenderBounds.class);
        eventInjectionSpec.setConstructorArguments("$0, $1");
        eventInjectionSpec.setReturnExpression("false");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Vape.INSTANCE.getMappings().renderBlocks.renderStandardBlockMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockFluidRender.class);
        eventInjectionSpec.setConstructorArguments("$0, $1, $2, $3, $4");
        eventInjectionSpec.setReturnExpression("$event.isResult()");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Vape.INSTANCE.getMappings().renderBlocks.renderFaceXNegMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceYNeg.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Vape.INSTANCE.getMappings().renderBlocks.renderFaceXPosMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceYPos.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Vape.INSTANCE.getMappings().renderBlocks.renderFaceYNegMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceZNeg.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Vape.INSTANCE.getMappings().renderBlocks.renderFaceYPosMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceZPos.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Vape.INSTANCE.getMappings().renderBlocks.renderFaceZNegMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceXNeg.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Vape.INSTANCE.getMappings().renderBlocks.renderFaceZPosMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceXPos.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
    }
}
