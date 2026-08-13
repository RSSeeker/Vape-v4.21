package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class BlockReaderBridge
extends Wrapper {
    public BlockReaderBridge(Object handle) {
        super(handle);
    }

    public EntityFishHook getShape(BlockReader blockReader, BlockPos blockPosition) {
        return new EntityFishHook(BlockReaderBridge.vapeInstance.getMappingsMapperCompat().CJ.getShape(this.I, blockReader.getObject(), blockPosition.getObject()));
    }

    public boolean isSuffocating(Object blockReader, Object blockPosition) {
        return BlockReaderBridge.vapeInstance.getMappingsMapperCompat().CJ.isSuffocating(this.I, blockReader, blockPosition);
    }
}
