package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTextureObject;
import gg.vape.wrapper.Wrapper;

public class TextureObject
extends Wrapper {
    public void setFilter(boolean blur, boolean mipmap) {
        MTextureObject.setFilter(TextureObject.vapeInstance.getMappingsMapperCompat().textureObject, this.I, blur, mipmap);
    }

    public TextureObject(Object object) {
        super(object);
    }

    public int getId() {
        if (ForgeVersion.MC_1_21_6.d()) {
            TextureObjectHandle textureHandle = new TextureObjectHandle(MTextureObject.getTexture(TextureObject.vapeInstance.getMappingsMapperCompat().textureObject, this.I));
            return textureHandle.getId();
        }
        return MTextureObject.getGlTextureId(TextureObject.vapeInstance.getMappingsMapperCompat().textureObject, this.I);
    }
}
