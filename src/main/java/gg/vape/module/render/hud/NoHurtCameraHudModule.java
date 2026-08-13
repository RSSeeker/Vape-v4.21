package gg.vape.module.render.hud;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;

public class NoHurtCameraHudModule
extends HudModule {
    private int savedHurtTime;


    @EventHandler
    public void onPreRenderTick(EventPreRenderTick event) {
        if (event.getThePlayer().isNull()) {
            return;
        }
        if (event.getThePlayer().c$src$I$15a9iwo() > 0) {
            this.savedHurtTime = event.getThePlayer().c$src$I$15a9iwo();
            event.getThePlayer().I(0);
        }
    }

    public NoHurtCameraHudModule() {
        super("NoHurtCam", HudModuleGroup.GAME, "legitmodeicon");
        this.setSuffix("Disables the hurt camera shaking effect");
    }

    @EventHandler
    public void onPostRenderTick(EventPostRenderTick event) {
        if (event.getThePlayer().isNull()) {
            return;
        }
        if (this.savedHurtTime > 0) {
            event.getThePlayer().I(this.savedHurtTime);
            this.savedHurtTime = 0;
        }
    }
}

