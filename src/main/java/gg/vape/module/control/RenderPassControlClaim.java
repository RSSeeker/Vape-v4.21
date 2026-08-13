package gg.vape.module.control;

public class RenderPassControlClaim
extends ModuleControlClaim {
    public boolean isRenderBlocked() {
        return this.isClaimed();
    }

    public void blockRender() {
        this.setClaimed(true);
    }
}
