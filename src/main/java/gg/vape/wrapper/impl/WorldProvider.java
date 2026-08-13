package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class WorldProvider
extends Wrapper {
    public boolean hasNoSky() {
        return WorldProvider.vapeInstance.getMappingsMapperCompat().worldProvider.hasNoSky(this.I);
    }

    public WorldProvider(Object wrappedObject) {
        super(wrappedObject);
    }
}
