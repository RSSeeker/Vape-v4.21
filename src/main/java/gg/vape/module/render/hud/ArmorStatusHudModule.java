package gg.vape.module.render.hud;

import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.frame.impl.hud.ArmorStatusHudFrame;
import gg.vape.value.BooleanValue;

public class ArmorStatusHudModule
extends HudModule {
    public final BooleanValue compact = BooleanValue.create(this, "Compact", false);

    public ArmorStatusHudModule() {
        super("Armor Status", HudModuleGroup.HUD, "armor_status", ArmorStatusHudFrame.class);
        this.setSuffix("Shows your currently equipped armor, and its durability");
        this.addValue(this.compact);
    }
}
