package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MSoundEventRegistryName;
import gg.vape.wrapper.Wrapper;

public class SoundEventRegistryName
extends Wrapper {
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(MSoundEventRegistryName.getName(SoundEventRegistryName.vapeInstance.getMappingsMapperCompat().h9, this.I));
    }

    public SoundEventRegistryName(Object wrappedObject) {
        super(wrappedObject);
    }
}
