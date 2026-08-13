package gg.vape.module.render.hud;

import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.frame.impl.hud.PotionEffectsHudFrame;
import gg.vape.value.BooleanValue;

public class PotionEffectsHudModule
extends HudModule {
    public final BooleanValue showPositiveEffects = BooleanValue.create(this, "Show Positive Effects", true);
    public final BooleanValue showNegativeEffects = BooleanValue.create(this, "Show Negative Effects", true);

    public PotionEffectsHudModule() {
        super("Potion Status", HudModuleGroup.HUD, "potion_status", PotionEffectsHudFrame.class);
        this.setSuffix("Shows your currently active potion effects");
        this.addValue(this.showPositiveEffects, this.showNegativeEffects);
    }
}
