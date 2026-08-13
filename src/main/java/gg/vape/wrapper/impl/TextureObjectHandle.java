package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class TextureObjectHandle
extends Wrapper {
    public int resolveFramebufferId(int depthTextureId) {
        return TextureObjectHandle.vapeInstance.getMappingsMapperCompat().gpuTexture.resolveFramebufferId(this.I, depthTextureId);
    }

    public int getId() {
        return TextureObjectHandle.vapeInstance.getMappingsMapperCompat().gpuTexture.getTextureId(this.I);
    }

    public TextureObjectHandle(Object object) {
        super(object);
    }
}
