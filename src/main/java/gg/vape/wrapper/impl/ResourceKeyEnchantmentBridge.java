package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MResourceKeyEnchantmentBridge;
import gg.vape.wrapper.Wrapper;

public class ResourceKeyEnchantmentBridge
extends Wrapper {
    public static ResourceKey enchantment() {
        return new ResourceKey(MResourceKeyEnchantmentBridge.getEnchantment(ResourceKeyEnchantmentBridge.vapeInstance.getMappingsMapperCompat().enchantmentRegistryKey));
    }

    public ResourceKeyEnchantmentBridge(Object handle) {
        super(handle);
    }
}
