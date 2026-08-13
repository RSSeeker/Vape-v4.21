package gg.vape.module.combat.crystalaura;

import gg.vape.module.combat.crystalaura.CrystalAuraActionState;

public class CrystalAuraActionStateSwitchMap {
    public static final int[] stateOrdinals = new int[CrystalAuraActionState.values().length];

    CrystalAuraActionStateSwitchMap() {
    }

    static {
        try {
            CrystalAuraActionStateSwitchMap.stateOrdinals[CrystalAuraActionState.BREAKING_CRYSTAL.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CrystalAuraActionStateSwitchMap.stateOrdinals[CrystalAuraActionState.PLACING_CRYSTAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CrystalAuraActionStateSwitchMap.stateOrdinals[CrystalAuraActionState.PLACING_OBSIDIAN.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

