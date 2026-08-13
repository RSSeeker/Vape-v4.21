package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class SelectionContext
extends Wrapper {
    public SelectionContext(Object handle) {
        super(handle);
    }

    public static SelectionContext forEntity(Entity entity) {
        return new SelectionContext(SelectionContext.vapeInstance.getMappingsMapperCompat().selectionContext.forEntity(entity.getObject()));
    }
}
