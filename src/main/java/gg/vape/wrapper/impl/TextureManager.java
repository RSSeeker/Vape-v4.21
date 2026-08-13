package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTextureManager;
import gg.vape.wrapper.Wrapper;

public class TextureManager
extends Wrapper {

    public TextureManager(Object object) {
        super(object);
    }

    public void bindTexture(ResourceLocation location) {
        if (ForgeVersion.MC_1_21_0.d()) {
            return;
        }
        MTextureManager.bindTexture(TextureManager.vapeInstance.getMappingsMapperCompat().textureManager, this.I, location.getObject());
    }

    public TextureObject getTexture(ResourceLocation location) {
        return new TextureObject(MTextureManager.getTexture(TextureManager.vapeInstance.getMappingsMapperCompat().textureManager, this.I, location.getObject()));
    }
}

