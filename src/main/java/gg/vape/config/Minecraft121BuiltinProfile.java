package gg.vape.config;

import gg.vape.module.blatant.AutoAnchor;
import gg.vape.module.combat.AimAssist;
import gg.vape.module.combat.Triggerbot;
import gg.vape.module.combat.HitSwap;
import gg.vape.module.combat.CrystalAura;
import gg.vape.module.combat.Sprint;
import gg.vape.module.utility.AutoTotem;
import gg.vape.module.utility.WindCharge;
import gg.vape.wrapper.impl.ForgeVersion;

public class Minecraft121BuiltinProfile
extends BuiltinProfile {
    private static final String PROFILE_NAME = "Modern PVP";

    @Override
    protected void configureModules() {
        this.selectModule(Triggerbot.class);
        this.selectModule(AimAssist.class);
        this.selectModule(HitSwap.class);
        this.selectModule(Sprint.class);
        this.selectModule(CrystalAura.class);
        this.selectModule(AutoTotem.class);
        this.selectModule(AutoAnchor.class);
        this.selectModule(WindCharge.class);
    }

    public Minecraft121BuiltinProfile() {
        super(PROFILE_NAME);
    }

    @Override
    public boolean isApplicable() {
        return ForgeVersion.MC_1_21_0.d();
    }
}
