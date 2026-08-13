package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ResourceLocationKey
extends Wrapper {
    public static ResourceLocationKey L() {
        return new ResourceLocationKey(ResourceLocationKey.vapeInstance.getMappingsMapperCompat().qK.r());
    }

    public ResourceLocationKey(Object object) {
        super(object);
    }
}

