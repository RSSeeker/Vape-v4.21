package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class RegistryAccess
extends Wrapper {
    public RegistryAccess(Object wrappedObject) {
        super(wrappedObject);
    }

    public Registry lookupOrThrow(ResourceKey resourceKey) {
        return new Registry(RegistryAccess.vapeInstance.getMappingsMapperCompat().qs.lookupOrThrow(this.I, resourceKey.getObject()));
    }
}
