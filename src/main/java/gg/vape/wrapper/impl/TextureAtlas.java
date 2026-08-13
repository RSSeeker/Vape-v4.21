package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTextureAtlasSpriteInfo;
import gg.vape.wrapper.Wrapper;

public class TextureAtlas
extends Wrapper {
    public TextureAtlasSprite getSprite(ResourceLocation location) {
        return new TextureAtlasSprite(MTextureAtlasSpriteInfo.getSprite(TextureAtlas.vapeInstance.getMappings().textureAtlas, this.I, location.getObject()));
    }

    public TextureAtlas(Object object) {
        super(object);
    }

    public static ResourceLocation getBlocksAtlasLocation() {
        return new ResourceLocation(MTextureAtlasSpriteInfo.getBlocksAtlasLocation(TextureAtlas.vapeInstance.getMappings().textureAtlas));
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(MTextureAtlasSpriteInfo.getTextureLocation(TextureAtlas.vapeInstance.getMappings().textureAtlas, this.I));
    }
}
