package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderBufferBridge;
import gg.vape.wrapper.Wrapper;

import java.util.Map;

public class RenderBufferBridge
extends Wrapper {
    public RenderBufferBridge(Object object) {
        super(object);
    }

    public TextureObjectHandle F() {
        if (ForgeVersion.MC_26_1.d()) {
            BlockStateContainerBridge blockStateContainerBridge = this.P();
            if (blockStateContainerBridge.isNull()) {
                return new TextureObjectHandle(null);
            }
            GpuTextureView textureView = blockStateContainerBridge.getTextureView();
            return textureView.isNull() ? new TextureObjectHandle(null) : new TextureObjectHandle(textureView.getTexture());
        }
        return new TextureObjectHandle(MRenderBufferBridge.P(RenderBufferBridge.vapeInstance.getMappings().Ca, this.I));
    }

    public int L(int n) {
        return MRenderBufferBridge.J(RenderBufferBridge.vapeInstance.getMappings().Ca, this.I, n);
    }

    public void P(Object object) {
        RenderBufferBridge.vapeInstance.getMappings().Ca.o(this.I, object);
    }

    public BlockStateContainerBridge P() {
        if (ForgeVersion.MC_26_1.v()) {
            return new BlockStateContainerBridge(null);
        }
        return new BlockStateContainerBridge(MRenderBufferBridge.V(RenderBufferBridge.vapeInstance.getMappings().Ca, this.I));
    }


    public void j() {
        MRenderBufferBridge.v(RenderBufferBridge.vapeInstance.getMappings().Ca, this.I);
    }

    public Map p() {
        return MRenderBufferBridge.P$src$Ljava_util_Map_$14yo47i(RenderBufferBridge.vapeInstance.getMappings().Ca, this.I);
    }
}

