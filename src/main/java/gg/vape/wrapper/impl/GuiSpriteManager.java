package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class GuiSpriteManager
extends Wrapper {
    public TextureAtlasSprite getSprite(ResourceLocation resourceLocation) {
        return new TextureAtlasSprite(GuiSpriteManager.vapeInstance.getMappingsMapperCompat().h4.getSprite(this.I, resourceLocation.getObject()));
    }

    public GuiSpriteManager(Object handle) {
        super(handle);
    }
}
