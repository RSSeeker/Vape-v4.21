package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnumParticleTypes;
import gg.vape.wrapper.Wrapper;

public class EnumParticleTypes
extends Wrapper {
    public String K() {
        if (ForgeVersion.MC_1_20_6.d()) {
            Registry jn_12 = BuiltInRegistries.a();
            return jn_12.W(this.I).getResourcePath();
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return MEnumParticleTypes.i(EnumParticleTypes.vapeInstance.getMappingsMapperCompat().Rp, this.I);
        }
        return MEnumParticleTypes.I(EnumParticleTypes.vapeInstance.getMappingsMapperCompat().Rp, this.I);
    }

    public EnumParticleTypes(Object object) {
        super(object);
    }

}

