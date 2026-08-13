package gg.vape.module.blatant;

import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.blatant.phase.HypixelPhase;
import gg.vape.value.SubModuleValue;
import java.awt.Color;

@Deprecated
public class Phase
extends Mod {
    private final SubModuleValue antiCheatSubModule = new HypixelPhase(this, "AntiCheat").getSelectionValue();

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    public Phase() {
        super("Phase", new Color(73, 208, 176).getRGB(), Category.OTHER, "Phase/Clip through walls.");
    }
}
