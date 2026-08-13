package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MBlockHorizontal;

public class BlockHorizontal
extends Block {
    public BlockHorizontal(Object wrappedObject) {
        super(wrappedObject);
    }

    public static BlockProperty facing() {
        return new BlockProperty(MBlockHorizontal.getFacing(BlockHorizontal.vapeInstance.getMappingsMapperCompat().a));
    }
}
