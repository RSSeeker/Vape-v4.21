package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MBiome;
import gg.vape.wrapper.Wrapper;

import java.util.Optional;

public class Biome
extends Wrapper {
    private static final String b = "Unknown";

    public String n() {
        if (ForgeVersion.MC_1_20_6.d()) {
            Optional optional;
            ResourceKey resourceKey;
            if (this.isInstance(MappedClasses.Vo) && !(resourceKey = new ResourceKey((optional = new Holder(this.I).f()).orElse(null))).isNull()) {
                return resourceKey.getLocation().getResourcePath();
            }
            return b;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            BiomeRegistryName biomeRegistryName = new BiomeRegistryName(MBiome.getCategory(Biome.vapeInstance.getMappings().Rm, this.I));
            return biomeRegistryName.n();
        }
        return Biome.vapeInstance.getMappings().Rm.getBiomeName(this.I);
    }

    public Biome(Object object) {
        super(object);
    }

}

