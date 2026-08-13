package gg.vape.module.none;

import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;

public class MouseDelayFix
extends HudModule {
    public MouseDelayFix() {
        super("MouseDelayFix", HudModuleGroup.GAME, "mouse_delay_fix");
        this.setSuffix("Fixes the bug on version 1.8.x that causes aiming/hit registration to be less accurate.\nThe fix makes combat more similar to 1.7");
    }
}
