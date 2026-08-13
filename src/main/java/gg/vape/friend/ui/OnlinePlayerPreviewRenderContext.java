package gg.vape.friend.ui;

import gg.vape.render.OffscreenRenderContext;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class OnlinePlayerPreviewRenderContext
extends OffscreenRenderContext {
    private boolean levelView;

    public OnlinePlayerPreviewRenderContext() {
        super(true);
    }

    @Override
    public void renderOffscreenFrame() {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        this.captureInterpolatedCameraPosition(entityPlayerSP);
        this.cameraYaw = entityPlayerSP.J() + 180.0f;
        this.cameraPitch = this.levelView ? 0.0f : -entityPlayerSP.V();
        super.renderOffscreenFrame();
    }


    public void setLevelView(boolean levelView) {
        this.levelView = levelView;
    }
}

