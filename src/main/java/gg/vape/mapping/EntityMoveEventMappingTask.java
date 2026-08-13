package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPostMove;
import gg.vape.event.impl.EventPreMove;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class EntityMoveEventMappingTask
extends JavassistMappingTask {
    private void k() {
        if (ForgeVersion.MC_1_16_5.d()) {
            MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Rr.jG;
            String string = "$2";
            String string2 = "$2 = ( " + MappedClasses.qP.getName() + ") $event.getVector();";
            EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventPreMove.class);
            eventInjectionSpec.setConstructorArguments(string);
            eventInjectionSpec.setAfterCode(string2);
            eventInjectionSpec.setInstanceType(MappedClasses.z5);
            this.registerEventInjection(eventInjectionSpec);
            EventInjectionSpec eventInjectionSpec2 = new EventInjectionSpec(mappingMethod, EventPostMove.class);
            eventInjectionSpec2.setInsertBefore(false);
            eventInjectionSpec2.setConstructorArguments(string);
            eventInjectionSpec2.setInstanceType(MappedClasses.z5);
            this.registerEventInjection(eventInjectionSpec2);
            return;
        }
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Rr.j0;
        String string = ForgeVersion.MC_1_12_2.d() ? "$2, $3, $4" : "$1, $2, $3";
        String string3 = ForgeVersion.MC_1_12_2.d() ? "$2 = $event.getX();\n $3 = $event.getY();\n $4 = $event.getZ();" : "$1 = $event.getX();\n $2 = $event.getY();\n $3 = $event.getZ();";
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventPreMove.class);
        eventInjectionSpec.setConstructorArguments(string);
        eventInjectionSpec.setAfterCode(string3);
        eventInjectionSpec.setInstanceType(MappedClasses.z5);
        this.registerEventInjection(eventInjectionSpec);
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventPostMove.class);
        eventInjectionSpec.setInsertBefore(false);
        eventInjectionSpec.setConstructorArguments(string);
        eventInjectionSpec.setInstanceType(MappedClasses.z5);
        this.registerEventInjection(eventInjectionSpec);
    }

    private void F() {
    }

    @Override
    public void transform() {
        this.k();
    }

    public EntityMoveEventMappingTask() {
        super(MappedClasses.zc);
    }

}

