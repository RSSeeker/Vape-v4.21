package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MBiomeRegistryName;
import gg.vape.wrapper.Wrapper;

public class BiomeRegistryName
extends Wrapper {
    public String n() {
        return MBiomeRegistryName.getName(BiomeRegistryName.vapeInstance.getMappingsMapperCompat().hq, this.I);
    }

    public BiomeRegistryName(Object object) {
        super(object);
    }
}
