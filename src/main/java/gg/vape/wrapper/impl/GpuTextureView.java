package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class GpuTextureView
extends Wrapper {
    public int getTextureId() {
        Object texture = this.getTexture();
        if (texture == null) {
            return -1;
        }
        TextureObjectHandle textureHandle = new TextureObjectHandle(texture);
        return textureHandle.getId();
    }

    public int getBaseMipLevel() {
        return GpuTextureView.vapeInstance.getMappingsMapperCompat().gpuTextureView.getBaseMipLevel(this.I);
    }

    public boolean isClosed() {
        return GpuTextureView.vapeInstance.getMappingsMapperCompat().gpuTextureView.isClosed(this.I);
    }

    public GpuTextureView(Object wrappedObject) {
        super(wrappedObject);
    }

    public int getMipLevels() {
        return GpuTextureView.vapeInstance.getMappingsMapperCompat().gpuTextureView.getMipLevels(this.I);
    }

    public Object getTexture() {
        return GpuTextureView.vapeInstance.getMappingsMapperCompat().gpuTextureView.getTexture(this.I);
    }

    public int getWidth(int mipLevel) {
        return GpuTextureView.vapeInstance.getMappingsMapperCompat().gpuTextureView.getWidth(this.I, mipLevel);
    }

    public int getHeight(int mipLevel) {
        return GpuTextureView.vapeInstance.getMappingsMapperCompat().gpuTextureView.getHeight(this.I, mipLevel);
    }
}
