package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

import java.util.Optional;

public class ResourceKeyRegistryLookup
extends Wrapper {
    public ResourceKeyRegistryLookup(Object lookupHandle) {
        super(lookupHandle);
    }

    public EnchantmentHolder getOrThrow(ResourceKey resourceKey) {
        Object holderHandle = ResourceKeyRegistryLookup.vapeInstance.getMappingsMapperCompat()
                .resourceKeyRegistryLookup.getOrThrow(this.getObject(), resourceKey.getObject());
        return new EnchantmentHolder(holderHandle);
    }

    public Optional<Object> get(ResourceKey resourceKey) {
        return ResourceKeyRegistryLookup.vapeInstance.getMappingsMapperCompat()
                .resourceKeyRegistryLookup.get(this.getObject(), resourceKey.getObject());
    }
}
