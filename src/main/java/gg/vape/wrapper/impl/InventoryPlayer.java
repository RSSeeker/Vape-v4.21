package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MInventoryPlayer;
import gg.vape.wrapper.Wrapper;

import java.util.AbstractList;
import java.util.ArrayList;

public class InventoryPlayer
extends Wrapper {
    public void g(int n) {
        InventoryPlayer.vapeInstance.getMappings().v.A(this.I, n);
    }

    public ItemStack A() {
        return new ItemStack(MInventoryPlayer.N(InventoryPlayer.vapeInstance.getMappings().v, this.I));
    }

    public Object[] i() {
        if (ForgeVersion.MC_1_21_6.d()) {
            ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
            for (Object e : InventoryListBridge.armor().getSlots()) {
                EquipmentSlotSet equipmentSlotSet = new EquipmentSlotSet(e);
                arrayList.add(this.c(equipmentSlotSet.d()));
            }
            return arrayList.toArray();
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            AbstractList abstractList = MInventoryPlayer.S(InventoryPlayer.vapeInstance.getMappings().v, this.I);
            return abstractList.toArray();
        }
        return MInventoryPlayer.i(InventoryPlayer.vapeInstance.getMappings().v, this.I);
    }

    public InventoryPlayer(Object object) {
        super(object);
    }

    public Object[] M() {
        if (ForgeVersion.MC_1_12_2.d()) {
            AbstractList abstractList = InventoryPlayer.vapeInstance.getMappings().v.T(this.I);
            return abstractList.toArray();
        }
        return InventoryPlayer.vapeInstance.getMappings().v.X(this.I);
    }

    public ItemStack c(int n) {
        return new ItemStack(InventoryPlayer.vapeInstance.getMappings().v.N(this.I, n));
    }

    public int v() {
        return InventoryPlayer.vapeInstance.getMappings().v.s(this.I);
    }
}
