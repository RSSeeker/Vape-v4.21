package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ChunkProvider
extends Wrapper {
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return ChunkProvider.vapeInstance.getMappingsMapperCompat().chunkProvider
                .chunkExists(this.I, chunkX, chunkZ);
    }

    public ChunkProvider(Object chunkProviderHandle) {
        super(chunkProviderHandle);
    }
}
