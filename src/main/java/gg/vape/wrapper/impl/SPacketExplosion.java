package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class SPacketExplosion
extends Wrapper {
    public void setPosition(float position) {
        SPacketExplosion.vapeInstance.getMappingsMapperCompat().hQ.setPosition(this.I, position);
    }

    public void setPreviousSpeed(float previousSpeed) {
        SPacketExplosion.vapeInstance.getMappingsMapperCompat().hQ.setPreviousSpeed(this.I, previousSpeed);
    }

    public float getSpeed() {
        return SPacketExplosion.vapeInstance.getMappingsMapperCompat().hQ.getSpeed(this.I);
    }

    public float getPreviousSpeed() {
        return SPacketExplosion.vapeInstance.getMappingsMapperCompat().hQ.getPreviousSpeed(this.I);
    }

    public SPacketExplosion(Object handle) {
        super(handle);
    }

    public float getPosition() {
        return SPacketExplosion.vapeInstance.getMappingsMapperCompat().hQ.getPosition(this.I);
    }
}
