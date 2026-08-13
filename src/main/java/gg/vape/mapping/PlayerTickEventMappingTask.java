package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPostPlayerTick;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.PlayerNameFormatCallback;
import gg.vape.wrapper.impl.ForgeVersion;

public class PlayerTickEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Rr.V;
        this.c(mappingMethod, EventPrePlayerTick.class, "$0");
        this.k(mappingMethod, EventPostPlayerTick.class, "$0");
        if (ForgeVersion.MC_1_21_10.v()) {
            MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().Rr.jO;
            EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod2, PlayerNameFormatCallback.class);
            eventInjectionSpec.setConstructorArguments("$0");
            eventInjectionSpec.setReturnExpression("($r) $event.getRawDisplayName()");
            this.registerEventInjection(eventInjectionSpec);
        }
    }

    public PlayerTickEventMappingTask() {
        super(MappedClasses.Yl);
    }
}
