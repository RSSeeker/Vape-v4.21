package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ItemCameraTransformType
extends Wrapper {
    public ItemCameraTransformBase getItems() {
        return new ItemCameraTransformBase(ItemCameraTransformType.vapeInstance.getMappingsMapperCompat().repairable.getItems(this.I));
    }

    public ItemCameraTransformType(Object handle) {
        super(handle);
    }
}
