package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MIInventory;
import gg.vape.wrapper.Wrapper;

public class Inventory
extends Wrapper {
    public int getSizeInventory() {
        return MIInventory.N(Inventory.vapeInstance.getMappingsMapperCompat().X, this.I);
    }

    public ItemStack getStackInSlot(int n) {
        return new ItemStack(MIInventory.W(Inventory.vapeInstance.getMappingsMapperCompat().X, this.I, n));
    }

    public Inventory(Object object) {
        super(object);
    }

    public boolean hasCustomInventoryName() {
        if (ForgeVersion.MC_1_7_10.Y()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                return false;
            }
            return new WorldNameable(this.I).hasCustomName();
        }
        return MIInventory.e(Inventory.vapeInstance.getMappingsMapperCompat().X, this.I);
    }


    public String getName() {
        if (ForgeVersion.MC_1_7_10.Y()) {
            return new WorldNameable(this.I).getDisplayName().a();
        }
        return MIInventory.F(Inventory.vapeInstance.getMappingsMapperCompat().X, this.I);
    }
}

