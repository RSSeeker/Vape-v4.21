package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPostRenderWorldPass;
import gg.vape.event.impl.EventPreRenderWorldPass;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class RenderWorldPassEventMappingTask
extends JavassistMappingTask {
    public RenderWorldPassEventMappingTask() {
        super(ForgeVersion.MC_26_2.d() ? MappedClasses.LEVEL_EXTRACTOR : MappedClasses.zs);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_21_4.d()) {
            try {
                MappingMethod mappingMethod = null;
                if (ForgeVersion.MC_1_21_10.d()) {
                    mappingMethod = Vape.INSTANCE.getMappings().renderGlobal.extractVisibleEntitiesMethod;
                } else if (ForgeVersion.MC_1_21_4.d()) {
                    mappingMethod = Vape.INSTANCE.getMappings().renderGlobal.renderEntitiesMethod;
                }
                this.c(mappingMethod, EventPreRenderWorldPass.class, "");
                this.k(mappingMethod, EventPostRenderWorldPass.class, "");
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
    }
}
