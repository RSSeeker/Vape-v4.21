package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.PotionEntry;

public class PotionEntryResolveException
extends Exception {
    private final PotionEntry potionEntry;

    public PotionEntry getPotionEntry() {
        return this.potionEntry;
    }

    public PotionEntryResolveException(PotionEntry potionEntry) {
        this.potionEntry = potionEntry;
    }
}
