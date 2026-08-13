package gg.vape.module.render.hud;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreTick;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;

public class NoClickDelayHudModule
extends HudModule {
    public NoClickDelayHudModule() {
        super("NoClickDelay", HudModuleGroup.GAME, "no_click_delay2");
        this.setSuffix("Removes the click delay that normally occurs after missing an attack");
    }

    @EventHandler
    public void onTick(EventPreTick event) {
        if (ForgeVersion.MC_1_8_9.d() && ClientSettings.isAttackButtonDown() && Minecraft.currentScreen().isNull()) {
            Minecraft.r(0);
        }
    }

}

