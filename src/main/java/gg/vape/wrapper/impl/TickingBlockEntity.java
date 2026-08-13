package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTickingBlockEntity;
import gg.vape.wrapper.Wrapper;

public class TickingBlockEntity
extends Wrapper {
    public BlockPos getPos() {
        return new BlockPos(MTickingBlockEntity.getPos(TickingBlockEntity.vapeInstance.getMappingsMapperCompat().tickingBlockEntity, this.I));
    }

    public TickingBlockEntity(Object wrappedObject) {
        super(wrappedObject);
    }
}
