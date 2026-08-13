package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MChannel;
import gg.vape.wrapper.Wrapper;

public class Channel
extends Wrapper {
    public boolean usesBlockLight() {
        return MChannel.usesBlockLight(Channel.vapeInstance.getMappingsMapperCompat().bakedModel, this.I);
    }

    public Channel(Object handle) {
        super(handle);
    }
}
