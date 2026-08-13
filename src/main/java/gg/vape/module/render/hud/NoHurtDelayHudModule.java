package gg.vape.module.render.hud;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class NoHurtDelayHudModule
extends HudModule {
    public NoHurtDelayHudModule() {
        super("NoJumpDelay", HudModuleGroup.GAME, "no_jump_delay");
        this.setSuffix("Removes the delay between jumps when hitting a block above you");
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        player.L(0);
    }
}
