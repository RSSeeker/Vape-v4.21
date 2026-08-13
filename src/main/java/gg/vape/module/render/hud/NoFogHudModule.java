package gg.vape.module.render.hud;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventFogDensity;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;

public class NoFogHudModule
extends HudModule {
    @EventHandler
    public void onFogDensity(EventFogDensity event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        if (!player.h$src$Z$ftwoya()) {
            return;
        }
        event.setCancelled(true);
        if (ForgeVersion.MC_1_16_5.d()) {
            event.setDensity(0.01f);
        }
    }


    public NoFogHudModule() {
        super("Clear Water", HudModuleGroup.GAME, "clearwater");
        this.setSuffix("Makes water clear when under water");
    }
}

