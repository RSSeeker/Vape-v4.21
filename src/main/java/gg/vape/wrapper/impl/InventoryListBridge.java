package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MInventoryListBridge;
import gg.vape.wrapper.Wrapper;
import java.util.List;

public class InventoryListBridge
extends Wrapper {
    public List getSlots() {
        return MInventoryListBridge.getSlots(InventoryListBridge.vapeInstance.getMappingsMapperCompat().equipmentSlotGroup, this.I);
    }

    public InventoryListBridge(Object handle) {
        super(handle);
    }

    public static InventoryListBridge armor() {
        return new InventoryListBridge(MInventoryListBridge.getArmor(InventoryListBridge.vapeInstance.getMappingsMapperCompat().equipmentSlotGroup));
    }
}
