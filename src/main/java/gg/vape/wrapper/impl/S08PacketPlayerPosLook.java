package gg.vape.wrapper.impl;

public class S08PacketPlayerPosLook
extends Packet {
    public void setPitch(float pitch) {
        S08PacketPlayerPosLook.vapeInstance.getMappingsMapperCompat().q9.setPitch(this.I, pitch);
    }

    public void setYaw(float yaw) {
        S08PacketPlayerPosLook.vapeInstance.getMappingsMapperCompat().q9.setYaw(this.I, yaw);
    }

    public S08PacketPlayerPosLook(Object handle) {
        super(handle);
    }

    public float getPitch() {
        return S08PacketPlayerPosLook.vapeInstance.getMappingsMapperCompat().q9.getPitch(this.I);
    }

    public float getYaw() {
        return S08PacketPlayerPosLook.vapeInstance.getMappingsMapperCompat().q9.getYaw(this.I);
    }
}
