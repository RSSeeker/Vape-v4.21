package gg.vape.module.combat.crystalaura;

public class CrystalAuraPlacementSearchState {
    public boolean lowEfficiency;
    public boolean noPlacement;
    public boolean outOfRange;
    public boolean blocked;

    public void markOutOfRange() {
        this.outOfRange = true;
    }

    public void markBlocked() {
        this.blocked = true;
    }

    public void markNoPlacement() {
        this.noPlacement = true;
    }

    public void markLowEfficiency() {
        this.lowEfficiency = true;
    }
}
