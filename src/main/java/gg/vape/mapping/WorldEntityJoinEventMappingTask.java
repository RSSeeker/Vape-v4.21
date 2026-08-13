package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventEntityJoinWorld;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class WorldEntityJoinEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Cy.q;
        String string = ForgeVersion.MC_1_20_6.d() ? "$1" : "$2";
        this.c(mappingMethod, EventEntityJoinWorld.class, string);
    }

    public WorldEntityJoinEventMappingTask() {
        super(MappedClasses.Z);
    }

}

