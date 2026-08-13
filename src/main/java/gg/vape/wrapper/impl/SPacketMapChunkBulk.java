package gg.vape.wrapper.impl;

import java.util.ArrayList;
import java.util.List;

public class SPacketMapChunkBulk
extends Packet {
    public int[] getZPositions() {
        return SPacketMapChunkBulk.vapeInstance.getMappingsMapperCompat().Rg.getZPositions(this.I);
    }

    public List<SPacketChunkDataExtracted> getChunksData() {
        Object[] chunkDataHandles = SPacketMapChunkBulk.vapeInstance.getMappingsMapperCompat().Rg.getChunksData(this.I);
        ArrayList<SPacketChunkDataExtracted> chunksData = new ArrayList<SPacketChunkDataExtracted>();
        for (Object chunkDataHandle : chunkDataHandles) {
            chunksData.add(new SPacketChunkDataExtracted(chunkDataHandle));
        }
        return chunksData;
    }

    public SPacketMapChunkBulk(Object handle) {
        super(handle);
    }

    public int[] getXPositions() {
        return SPacketMapChunkBulk.vapeInstance.getMappingsMapperCompat().Rg.getXPositions(this.I);
    }
}
