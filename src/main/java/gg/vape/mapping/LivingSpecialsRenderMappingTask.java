package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPreRenderLivingSpecials;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.ForgeVersion;

public class LivingSpecialsRenderMappingTask
extends JavassistMappingTask {
    public LivingSpecialsRenderMappingTask() {
        super(MappedClasses.uP);
    }


    @Override
    public void transform() {
        if (ForgeVersion.c() == ForgeVersion.MC_1_8_9.i()) {
            this.O(Vape.INSTANCE.getMappings().U.V, EventPreRenderLivingSpecials.class, "$1", "");
            return;
        }
        if (ForgeVersion.c() != ForgeVersion.MC_1_21_11.i()) {
            return;
        }
        this.O(Vape.INSTANCE.getMappings().U.d, EventPreRenderLivingSpecials.class, "$2", "0");
    }
}

