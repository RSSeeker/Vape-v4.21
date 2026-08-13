package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class OpenedChestTypeSentinel
extends Wrapper {
    public OpenedChestTypeSentinel(Object wrappedObject) {
        super(wrappedObject);
    }

    public static OpenedChestTypeSentinel basic() {
        return new OpenedChestTypeSentinel(OpenedChestTypeSentinel.vapeInstance.getMappingsMapperCompat().CD.getBasic());
    }
}
