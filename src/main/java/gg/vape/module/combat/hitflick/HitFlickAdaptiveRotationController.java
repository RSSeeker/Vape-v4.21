package gg.vape.module.combat.hitflick;

import gg.vape.module.combat.HitFlick;
import gg.vape.module.combat.silentaura.SilentAuraAdaptiveRotationEntry;
import gg.vape.rotation.AdaptiveRotationController;

public class HitFlickAdaptiveRotationController
extends AdaptiveRotationController {
    private final HitFlick targetingModule;


    public HitFlickAdaptiveRotationController(HitFlick targetingModule) {
        this.targetingModule = targetingModule;
    }

    @Override
    public float getSpeed() {
        switch (SilentAuraAdaptiveRotationEntry.MODE_ORDINALS[this.targetingModule.getRotationMode().ordinal()]) {
            case 1: {
                return this.targetingModule.getAttackRotationSpeed();
            }
            case 2: {
                return this.targetingModule.getFlickAwayRotationSpeed();
            }
        }
        return 48.0f;
    }
}

