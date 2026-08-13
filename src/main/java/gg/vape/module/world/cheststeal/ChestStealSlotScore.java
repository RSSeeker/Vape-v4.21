package gg.vape.module.world.cheststeal;

public class ChestStealSlotScore {
    private double score;
    private int slotIndex;

    public ChestStealSlotScore() {
        this.reset();
    }

    public void updateIfHigher(int slotIndex, double score) {
        if (score > this.score) {
            this.slotIndex = slotIndex;
            this.score = score;
        }
    }

    public boolean hasSlot() {
        return this.slotIndex != -1;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public void reset() {
        this.slotIndex = -1;
        this.score = -1.0;
    }
}
