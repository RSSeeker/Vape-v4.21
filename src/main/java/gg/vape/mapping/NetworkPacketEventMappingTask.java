package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class NetworkPacketEventMappingTask
extends JavassistMappingTask {
    public NetworkPacketEventMappingTask() {
        super(MappedClasses.FO);
    }

    @Override
    public void transform() {
        this.k();
    }


    private void k() {
        EventInjectionSpec eventInjectionSpec;
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Do.a;
        MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().Do.O;
        if (mappingMethod != null && !mappingMethod.hasResolutionFailed()) {
            eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventPacketReceive.class);
            eventInjectionSpec.setConstructorArguments("$0, $2");
            eventInjectionSpec.setAfterCode("$2 = (" + MappedClasses.Fm.getName() + ") $event.getPacketInstance();");
            this.registerEventInjection(eventInjectionSpec);
        }
        eventInjectionSpec = new EventInjectionSpec(mappingMethod2, EventPacketSend.class);
        eventInjectionSpec.setConstructorArguments("$0, $1");
        eventInjectionSpec.setAfterCode("$1 = (" + MappedClasses.Fm.getName() + ") $event.getPacketInstance();");
        this.registerEventInjection(eventInjectionSpec);
    }
}

