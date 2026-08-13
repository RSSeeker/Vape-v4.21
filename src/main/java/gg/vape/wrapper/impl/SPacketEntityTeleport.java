package gg.vape.wrapper.impl;

public class SPacketEntityTeleport
extends Packet {
    public SPacketEntityTeleport(Object handle) {
        super(handle);
    }

    public int getZ() {
        return SPacketEntityTeleport.vapeInstance.getMappings().qH.getZ(this.I);
    }

    public byte getYaw() {
        return SPacketEntityTeleport.vapeInstance.getMappings().qH.getYaw(this.I);
    }

    public byte getPitch() {
        return SPacketEntityTeleport.vapeInstance.getMappings().qH.getPitch(this.I);
    }

    public int getY() {
        return SPacketEntityTeleport.vapeInstance.getMappings().qH.getY(this.I);
    }

    public int getEntityId() {
        return SPacketEntityTeleport.vapeInstance.getMappings().qH.getEntityId(this.I);
    }

    public int getX() {
        return SPacketEntityTeleport.vapeInstance.getMappings().qH.getX(this.I);
    }
}
