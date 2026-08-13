package gg.vape.module.render.hud;

import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.frame.impl.hud.CompassHudFrame;

public class CompassHudModule
extends HudModule {
    public CompassHudModule() {
        super("Compass", HudModuleGroup.HUD, "compass_active", CompassHudFrame.class);
        this.setSuffix("Shows a compass indicating your direction");
    }
}
