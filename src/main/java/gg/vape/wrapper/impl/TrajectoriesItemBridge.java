package gg.vape.wrapper.impl;

public class TrajectoriesItemBridge
extends Item {
    public static boolean isCharged(ItemStack itemStack) {
        return TrajectoriesItemBridge.vapeInstance.getMappingsMapperCompat().RX.isCharged(itemStack.getObject());
    }

    public TrajectoriesItemBridge(Object handle) {
        super(handle);
    }
}
