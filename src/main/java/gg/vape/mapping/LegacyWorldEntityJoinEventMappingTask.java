package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventEntityJoinWorld;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class LegacyWorldEntityJoinEventMappingTask
extends JavassistMappingTask {
    private static final String c;

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return;
        }
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Cy.q;
        this.c(mappingMethod, EventEntityJoinWorld.class, c);
    }


    public LegacyWorldEntityJoinEventMappingTask() {
        super(MappedClasses.YU);
    }

    static {
        try {
            c = "$1";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}

