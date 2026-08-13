package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ItemRendererBridge
extends Wrapper {
    public static ItemRendererBridge firstPerson() {
        return new ItemRendererBridge(ItemRendererBridge.vapeInstance.getMappingsMapperCompat().legacyItemCameraTransformType.getFirstPerson());
    }

    public ItemRendererBridge(Object handle) {
        super(handle);
    }
}
