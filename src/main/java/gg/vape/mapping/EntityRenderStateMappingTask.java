package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventEntityRenderState;
import gg.vape.mapping.EventPreRenderEntityCallback;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class EntityRenderStateMappingTask
extends JavassistMappingTask {

    @Override
    public void transform() {
        if (!ForgeVersion.MC_1_7_10.L()) {
            MappingMethod mappingMethod = Vape.INSTANCE.getMappings().qe.p;
            if (ForgeVersion.MC_1_21_10.d()) {
                MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().qe.z;
                this.k(mappingMethod2, EventEntityRenderState.class, "$1, $2");
                return;
            }
            if (ForgeVersion.MC_1_21_0.d()) {
                return;
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                this.c(mappingMethod, EventPreRenderEntityCallback.class, "$1, $4");
                return;
            }
            if (!mappingMethod.hasResolutionFailed()) {
                this.c(mappingMethod, EventPreRenderEntityCallback.class, "$1, $2, $3, $4");
            }
        }
    }

    public EntityRenderStateMappingTask() {
        super(MappedClasses.VQ);
    }
}

