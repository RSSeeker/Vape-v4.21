package gg.vape.wrapper.impl;

public class ByteStatePacketBridge
extends Packet {
    public ByteStatePacketBridge(Object handle) {
        super(handle);
    }

    public byte getHeadYaw() {
        return ByteStatePacketBridge.vapeInstance.getMappingsMapperCompat().hy.getHeadYaw(this.I);
    }
}
