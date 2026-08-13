package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class GlStateManager$TextureState
extends Wrapper {
    public GlStateManager$TextureState(Object textureStateHandle) {
        super(textureStateHandle);
    }

    public int getTextureName() {
        return GlStateManager$TextureState.vapeInstance.getMappingsMapperCompat().glTextureState.getTextureName(this.I);
    }

    public void setTextureName(int textureName) {
        GlStateManager$TextureState.vapeInstance.getMappingsMapperCompat().glTextureState
                .setTextureName(this.I, textureName);
    }
}
