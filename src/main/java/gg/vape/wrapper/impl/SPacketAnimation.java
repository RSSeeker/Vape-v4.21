package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class SPacketAnimation
extends Wrapper {
    public int getEntityId() {
        return SPacketAnimation.vapeInstance.getMappingsMapperCompat().DF.getEntityId(this.I);
    }

    public int getAnimationType() {
        return SPacketAnimation.vapeInstance.getMappingsMapperCompat().DF.getAnimationType(this.I);
    }

    public SPacketAnimation(Object handle) {
        super(handle);
    }
}
