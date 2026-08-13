package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class BlockStateContainerBridge
extends Wrapper {
    public GpuTextureView getTextureView() {
        return new GpuTextureView(BlockStateContainerBridge.vapeInstance.getMappingsMapperCompat().Ds.getTextureView(this.I));
    }

    public Object getOrUpdate(TextComponentTranslation renderState) {
        return BlockStateContainerBridge.vapeInstance.getMappingsMapperCompat().Ds.getOrUpdate(this.I, renderState.getObject());
    }

    public BlockStateContainerBridge(Object handle) {
        super(handle);
    }

    public int getTextureId() {
        GpuTextureView textureView = this.getTextureView();
        return textureView.isNull() ? -1 : textureView.getTextureId();
    }

}

