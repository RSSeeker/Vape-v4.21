package gg.vape.module.utility.mlg;


public class MLGImpactState {
    public static final MLGImpactState HOTBAR = new MLGImpactState(36, 44);
    public static final MLGImpactState MAIN_INVENTORY = new MLGImpactState(9, 35);
    public static final MLGImpactState INVENTORY_AND_HOTBAR = new MLGImpactState(9, 44);
    private final int lastSlot;
    private final int firstSlot;

    private MLGImpactState(int firstSlot, int lastSlot) {
        this.firstSlot = firstSlot;
        this.lastSlot = lastSlot;
    }

    public int getFirstSlot() {
        return this.firstSlot;
    }

    public int getLastSlot() {
        return this.lastSlot;
    }
}

