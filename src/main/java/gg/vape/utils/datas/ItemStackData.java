package gg.vape.utils.datas;

import gg.vape.wrapper.impl.ItemStack;

public class ItemStackData {
    private boolean processed;
    private final ItemStack itemStack;
    private final int slot;

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public ItemStackData(int slot, ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isProcessed() {
        return this.processed;
    }

    public int getSlot() {
        return this.slot;
    }
}
