package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MSlot;
import gg.vape.wrapper.Wrapper;

public class Slot
extends Wrapper {
    public Slot(Object object) {
        super(object);
    }


    public int getSlotIndex() {
        return MSlot.getSlotIndex(Slot.vapeInstance.getMappingsMapperCompat().slot, this.getObject());
    }

    public Inventory getInventory() {
        return new Inventory(MSlot.getInventoryOrContainer(Slot.vapeInstance.getMappingsMapperCompat().slot, this.getObject()));
    }

    public int getSlotNumber() {
        return MSlot.getSlotNumber(Slot.vapeInstance.getMappingsMapperCompat().slot, this.getObject());
    }

    public ItemStack getStack() {
        return new ItemStack(Slot.vapeInstance.getMappingsMapperCompat().slot.getStack(this.getObject()));
    }

    public boolean hasStack() {
        if (ForgeVersion.MC_1_21_11.d()) {
            return !this.getStack().isNull();
        }
        return Slot.vapeInstance.getMappingsMapperCompat().slot.getStack(this.getObject()) != null;
    }
}

