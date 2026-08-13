package gg.vape.config;

import gg.vape.module.blatant.Backtrack;
import gg.vape.module.combat.AimAssist;
import gg.vape.module.combat.LeftClicker;
import gg.vape.module.combat.Sprint;
import gg.vape.module.combat.JumpReset;
import gg.vape.module.combat.HitSelect;
import gg.vape.wrapper.impl.ForgeVersion;

public class BuiltinProfileState
extends BuiltinProfile {
    private static final String PROFILE_NAME = "Classic PVP";

    @Override
    protected void configureModules() {
        this.selectModule(AimAssist.class);
        this.selectModule(LeftClicker.class);
        this.selectModule(JumpReset.class);
        this.selectModule(HitSelect.class);
        this.selectModule(Sprint.class);
        this.selectModule(Backtrack.class);
    }

    @Override
    public boolean isApplicable() {
        return !ForgeVersion.MC_1_21_0.d();
    }

    public BuiltinProfileState() {
        super(PROFILE_NAME);
    }

}

