package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTextureAtlasSprite;
import gg.vape.wrapper.Wrapper;

public class TextureAtlasSprite
extends Wrapper {
    public ResourceLocation getAtlasLocation() {
        return new ResourceLocation(MTextureAtlasSprite.getAtlasLocation(TextureAtlasSprite.vapeInstance.getMappingsMapperCompat().textureAtlasSprite, this.I));
    }

    public void setU0(float u0) {
        MTextureAtlasSprite.setU0(TextureAtlasSprite.vapeInstance.getMappingsMapperCompat().textureAtlasSprite, this.I, u0);
    }

    public void setU1(float u1) {
        MTextureAtlasSprite.setU1(TextureAtlasSprite.vapeInstance.getMappingsMapperCompat().textureAtlasSprite, this.I, u1);
    }

    public TextureAtlasSprite(Object object) {
        super(object);
    }

    public void setSecondaryPosition(int position) {
        MTextureAtlasSprite.setSecondaryPosition(TextureAtlasSprite.vapeInstance.getMappingsMapperCompat().textureAtlasSprite, this.I, position);
    }

    public void setV0(float v0) {
        MTextureAtlasSprite.setV0(TextureAtlasSprite.vapeInstance.getMappingsMapperCompat().textureAtlasSprite, this.I, v0);
    }

    public void setV1(float v1) {
        MTextureAtlasSprite.setV1(TextureAtlasSprite.vapeInstance.getMappingsMapperCompat().textureAtlasSprite, this.I, v1);
    }

    public float[] getTextureCoordinates() {
        return MTextureAtlasSprite.getTextureCoordinates(TextureAtlasSprite.vapeInstance.getMappingsMapperCompat().textureAtlasSprite, this.I);
    }

    public Object getContentsOrAtlasTexture() {
        return MTextureAtlasSprite.getContentsOrAtlasTexture(TextureAtlasSprite.vapeInstance.getMappingsMapperCompat().textureAtlasSprite, this.I);
    }

    public void setPrimaryPosition(int position) {
        MTextureAtlasSprite.setPrimaryPosition(TextureAtlasSprite.vapeInstance.getMappingsMapperCompat().textureAtlasSprite, this.I, position);
    }
}
