package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MResourceKey;
import gg.vape.wrapper.Wrapper;

public class ResourceKey
extends Wrapper {
    public ResourceKey(Object wrappedObject) {
        super(wrappedObject);
    }

    public ResourceLocation getLocation() {
        return new ResourceLocation(MResourceKey.getLocation(ResourceKey.vapeInstance.getMappingsMapperCompat().P, this.I));
    }
}
