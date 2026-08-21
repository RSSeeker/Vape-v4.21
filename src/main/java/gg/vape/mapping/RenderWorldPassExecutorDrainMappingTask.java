package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventRenderWorldPassExecutorDrain;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class RenderWorldPassExecutorDrainMappingTask
extends JavassistMappingTask {
    private static final String c;

    void V() {
        this.c(Vape.INSTANCE.getMappings().RY.J, EventRenderWorldPassExecutorDrain.class, c);
    }

    public RenderWorldPassExecutorDrainMappingTask() {
        super(MappedClasses.FW);
    }

    @Override
    public void transform() {
        Vape.debugLog("RWP: target=" + this.E + " J=" + Vape.INSTANCE.getMappings().RY.J
                + " resolved=" + (Vape.INSTANCE.getMappings().RY.J == null ? "null"
                        : Vape.INSTANCE.getMappings().RY.J.getResolvedName()));
        this.V();
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
