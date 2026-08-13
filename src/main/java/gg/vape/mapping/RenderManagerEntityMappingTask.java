package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPreRenderEntity;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class RenderManagerEntityMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod;
        if (ForgeVersion.MC_1_8_9.L() && !(mappingMethod = Vape.INSTANCE.getMappings().CA.W).hasResolutionFailed()) {
            this.O(mappingMethod, EventPreRenderEntity.class, "$1", "false");
        }
        if (ForgeVersion.MC_1_16_5.d() && !(mappingMethod = Vape.INSTANCE.getMappings().CA.W).hasResolutionFailed()) {
            this.c(mappingMethod, EventPreRenderEntity.class, "$1");
        }
    }


    public RenderManagerEntityMappingTask() {
        super(MappedClasses.Dc);
    }
}

