package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

import java.util.List;

public class ShaderGroup
extends Wrapper {
    public ShaderGroup(Object object) {
        super(object);
    }

    public void resize(int n, int n2) {
        ShaderGroup.vapeInstance.getMappingsMapperCompat().Qv.G(this.I, n, n2);
    }

    public static ShaderGroup create(TextureManager textureManager, ShaderGroupState shaderGroupState, Framebuffer framebuffer, ResourceLocation resourceLocation) {
        return new ShaderGroup(ShaderGroup.vapeInstance.getMappingsMapperCompat().Qv.k(textureManager.getObject(), shaderGroupState.getObject(), framebuffer.getObject(), resourceLocation.getObject()));
    }

    public List getFramebuffers() {
        return ShaderGroup.vapeInstance.getMappingsMapperCompat().Qv.h(this.I);
    }
}

