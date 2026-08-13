package gg.vape.event.impl;

import gg.vape.event.impl.EventRenderTickBase;
import gg.vape.utils.render.shader.ShaderProgram;
import gg.vape.wrapper.impl.DeltaTracker;

public class EventPreRenderTick
extends EventRenderTickBase {
    @Override
    public boolean fire() {
        ShaderProgram.setCurrentProgramId(-1);
        return super.fire();
    }

    public EventPreRenderTick(Object deltaTrackerHandle) {
        super(new DeltaTracker(deltaTrackerHandle));
    }

    public EventPreRenderTick(float f) {
        super(f);
    }
}
