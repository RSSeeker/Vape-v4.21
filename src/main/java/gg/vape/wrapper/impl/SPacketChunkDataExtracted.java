package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class SPacketChunkDataExtracted
extends Wrapper {
    public SPacketChunkDataExtracted(Object handle) {
        super(handle);
    }

    public int getDataSize() {
        return SPacketChunkDataExtracted.vapeInstance.getMappings().b.getDataSize(this.I);
    }

    public byte[] getData() {
        return SPacketChunkDataExtracted.vapeInstance.getMappings().b.getData(this.I);
    }
}
