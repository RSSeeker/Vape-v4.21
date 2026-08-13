package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MLightTexture;
import gg.vape.wrapper.Wrapper;

public class LightTexture
extends Wrapper {
    public void Z(float f) {
        MLightTexture.B(LightTexture.vapeInstance.getMappingsMapperCompat().hF, this.I, f);
    }

    public void V() {
        if (ForgeVersion.MC_1_21_11.d()) {
            return;
        }
        MLightTexture.f(LightTexture.vapeInstance.getMappingsMapperCompat().hF, this.I);
    }

    public LightTexture(Object object) {
        super(object);
    }


    public void X() {
        if (ForgeVersion.MC_1_21_11.d()) {
            return;
        }
        MLightTexture.z(LightTexture.vapeInstance.getMappingsMapperCompat().hF, this.I);
    }
}

