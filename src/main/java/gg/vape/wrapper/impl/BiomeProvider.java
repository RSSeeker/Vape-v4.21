package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MBiomeProviderBridge;
import gg.vape.wrapper.Wrapper;

public class BiomeProvider
extends Wrapper {
    public Iterable getAllEffects() {
        return MBiomeProviderBridge.getAllEffects(BiomeProvider.vapeInstance.getMappingsMapperCompat().K, this.I);
    }

    public BiomeProvider(Object wrappedObject) {
        super(wrappedObject);
    }
}
