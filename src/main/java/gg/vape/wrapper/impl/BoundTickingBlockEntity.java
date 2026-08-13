package gg.vape.wrapper.impl;

public class BoundTickingBlockEntity
extends TickingBlockEntity {
    public BoundTickingBlockEntity(Object wrappedObject) {
        super(wrappedObject);
    }

    public Object getBlockEntity() {
        return BoundTickingBlockEntity.vapeInstance.getMappingsMapperCompat().boundTickingBlockEntity.getBlockEntity(this.I);
    }
}
