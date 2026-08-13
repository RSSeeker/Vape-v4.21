package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ResourceLocation
extends Wrapper {
    public static ResourceLocation create(String location) {
        return new ResourceLocation(ResourceLocation.vapeInstance.getMappingsMapperCompat().resourceLocation.create(location));
    }

    public String getResourcePath() {
        return ResourceLocation.vapeInstance.getMappingsMapperCompat().resourceLocation.getPath(this.I);
    }

    public ResourceLocation(Object resourceLocationHandle) {
        super(resourceLocationHandle);
    }
}
