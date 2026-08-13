package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPreRenderPlayerSpec;
import gg.vape.event.impl.EventRenderPlayerPost;
import gg.vape.event.impl.EventRenderPlayerPre;
import gg.vape.event.impl.EventSetArmorModel;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class RenderPlayerEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().renderPlayer.renderMethod;
        if (!ForgeVersion.MC_1_21_0.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.c(mappingMethod, EventRenderPlayerPre.class, "$0, $1, $3, $4");
                this.k(mappingMethod, EventRenderPlayerPost.class, "$0, $1, $3, $4");
            } else {
                this.c(mappingMethod, EventRenderPlayerPre.class, "$0, $1, $2, $3, $4, $6");
                this.k(mappingMethod, EventRenderPlayerPost.class, "$0, $1, $6");
                if (ForgeVersion.MC_1_7_10.L()) {
                    MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().renderPlayer.shouldRenderPassMethod;
                    EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod2, EventSetArmorModel.class);
                    eventInjectionSpec.setConstructorArguments("$1, $2, $3");
                    eventInjectionSpec.setReturnExpression("($r) $event.getResult()");
                    this.registerEventInjection(eventInjectionSpec);
                    MappingMethod mappingMethod3 = Vape.INSTANCE.getMappings().renderPlayer.renderEquippedItemsMethod;
                    this.c(mappingMethod3, EventPreRenderPlayerSpec.class, "$1, $2");
                }
            }
        }
    }

    public RenderPlayerEventMappingTask() {
        super(MappedClasses.D0);
    }

}

