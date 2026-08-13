package gg.vape.module.render.hud;

import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.frame.impl.hud.HudModuleCategoryConfigFrame;

public class HudModuleCategoryEntry
extends HudModule {
    public HudModuleCategoryEntry() {
        super("Cooldown", HudModuleGroup.HUD, "cooldowns", HudModuleCategoryConfigFrame.class);
    }
}
