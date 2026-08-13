package gg.vape.wrapper.impl;

public class SEntityPacket
extends Packet {
    public int getDeltaY() {
        return SEntityPacket.vapeInstance.getMappingsMapperCompat().qC.getDeltaY(this.I);
    }

    public int getDeltaZ() {
        return SEntityPacket.vapeInstance.getMappingsMapperCompat().qC.getDeltaZ(this.I);
    }

    public Entity getEntity(World world) {
        return new Entity(SEntityPacket.vapeInstance.getMappingsMapperCompat().qC.getEntity(this.I, world.getObject()));
    }

    public SEntityPacket(Object handle) {
        super(handle);
    }

    public int getDeltaX() {
        return SEntityPacket.vapeInstance.getMappingsMapperCompat().qC.getDeltaX(this.I);
    }
}

