package gg.vape.module.combat.silentaura;

import gg.vape.Vape;
import gg.vape.click.ClickButton;
import gg.vape.click.ClickEngine;
import gg.vape.module.Category;
import gg.vape.module.combat.ClickerMod;
import gg.vape.module.combat.SilentAura;
import gg.vape.module.render.Animations;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.EntityPlayerSP;

public class SilentAuraClicker
extends ClickerMod {
    private static final String CLICKER_NAME = "auraClicker";
    private final SilentAura silentAura;

    @Override
    public boolean isClickCycleBlocked() {
        if (!this.silentAura.isEnabled()) {
            return true;
        }
        return !this.silentAura.canClickAttack();
    }

    @Override
    public boolean shouldSimulateBlockHit(ClickEngine clickEngine, EntityPlayerSP player) {
        Animations animations = Vape.INSTANCE.getModManager().getMod(Animations.class);
        return animations != null && animations.shouldBlock();
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    public SilentAuraClicker(SilentAura silentAura) {
        super(CLICKER_NAME, 0, Category.NONE);
        this.silentAura = silentAura;
        ClickEngine clickEngine = new ClickEngine(ClickButton.LEFT, silentAura.getAttackRate(), silentAura.getLimitToItems(), silentAura.getAllowedItems(), silentAura.getRequireMouseDown(), null, new BooleanValue((Object)null, "", false), this);
        this.setClickEngine(clickEngine);
        this.setEnabled(true);
    }
}
