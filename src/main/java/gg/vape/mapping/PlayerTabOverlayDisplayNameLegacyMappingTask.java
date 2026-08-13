package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPlayerTabOverlayDisplayNameLegacy;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class PlayerTabOverlayDisplayNameLegacyMappingTask
extends JavassistMappingTask {
    public PlayerTabOverlayDisplayNameLegacyMappingTask() {
        super(MappedClasses.lF);
    }

    @Override
    public void transform() {
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Vape.INSTANCE.getMappings().hP.O, EventPlayerTabOverlayDisplayNameLegacy.class);
        eventInjectionSpec.setConstructorArguments("$0, $1");
        eventInjectionSpec.setReturnExpression("$event.getDisplayName()");
        this.registerEventInjection(eventInjectionSpec);
    }
}
