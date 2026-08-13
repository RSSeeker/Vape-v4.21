package gg.vape.wrapper.impl;

import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.Wrapper;

public class GlStateManager$BooleanState
extends Wrapper {
    public void m$src$V$17py9xa() {
        this.O(true);
    }


    public void z() {
        this.O(false);
    }

    public GlStateManager$BooleanState(Object object) {
        super(object);
    }

    public void O(boolean bl) {
        boolean bl2 = GlStateManager$BooleanState.vapeInstance.getMappings().he.J(this.I);
        if (bl != bl2) {
            GlStateManager$BooleanState.vapeInstance.getMappings().he.T(this.I, bl);
            if (bl) {
                OpenGlBackendHolder.backend.enableCapability(GlStateManager$BooleanState.vapeInstance.getMappings().he.n(this.I));
            } else {
                OpenGlBackendHolder.backend.disableCapability(GlStateManager$BooleanState.vapeInstance.getMappings().he.n(this.I));
            }
        }
    }
}

