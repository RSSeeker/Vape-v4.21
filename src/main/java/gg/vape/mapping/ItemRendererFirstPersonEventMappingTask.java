package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventRenderItemInFirstPerson;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class ItemRendererFirstPersonEventMappingTask
extends JavassistMappingTask {
    private static final String c = "$0, $1";

    @Override
    public void transform() {
        this.c(Vape.INSTANCE.getMappings().qE.S, EventRenderItemInFirstPerson.class, c);
    }

    public ItemRendererFirstPersonEventMappingTask() {
        super(MappedClasses.zN);
    }
}
