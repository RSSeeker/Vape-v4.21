package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventFogDensity;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import javassist.CtBehavior;

public class FogDensityEventMappingTask
extends JavassistMappingTask {
    private static final String c = "0.1F";

    @Override
    public void transform() {
        CtBehavior ctBehavior = this.F(Vape.INSTANCE.getMappings().fogRenderer.setupFogMethod);
        this.H(ctBehavior, EventFogDensity.class, c, "", "");
    }

    public FogDensityEventMappingTask() {
        super(MappedClasses.FOG_RENDERER);
    }
}
