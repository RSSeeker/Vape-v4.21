package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class BlockReader
extends Wrapper {
    public BlockReader(Object object) {
        super(object);
    }

    public BlockStatePredicate f(BlockPos blockPos) {
        return new BlockStatePredicate(BlockReader.vapeInstance.getMappingsMapperCompat().y.getBlockState(this.I, blockPos.getObject()));
    }
}
