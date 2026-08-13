package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ModelManager
extends Wrapper {
    public TextureAtlas getAtlas(ResourceLocation location) {
        return new TextureAtlas(ModelManager.vapeInstance.getMappingsMapperCompat().CZ.getAtlas(this.I, location.getObject()));
    }

    public ModelManager(Object object) {
        super(object);
    }
}
