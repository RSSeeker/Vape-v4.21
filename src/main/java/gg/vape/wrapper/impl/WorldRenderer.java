package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MWorldRenderer;
import gg.vape.wrapper.Wrapper;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class WorldRenderer
extends Wrapper {
    public WorldRenderer(Object object) {
        super(object);
    }

    public void Q(boolean bl) {
        MWorldRenderer.X(WorldRenderer.vapeInstance.getMappings().qZ, this.I, bl);
    }


    public IntBuffer O() {
        if (ForgeVersion.MC_1_21_0.d()) {
            Vape.notifyNativeStackTrace();
            return null;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            ByteBuffer byteBuffer = (ByteBuffer)MWorldRenderer.t(WorldRenderer.vapeInstance.getMappings().qZ, this.I);
            return byteBuffer.asIntBuffer();
        }
        return (IntBuffer)MWorldRenderer.v(WorldRenderer.vapeInstance.getMappings().qZ, this.I);
    }

    public int o(int n) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Vape.notifyNativeStackTrace();
            return -1;
        }
        return MWorldRenderer.G(WorldRenderer.vapeInstance.getMappings().qZ, this.I, n);
    }
}

