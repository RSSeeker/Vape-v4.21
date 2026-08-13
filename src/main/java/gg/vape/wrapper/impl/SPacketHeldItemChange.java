package gg.vape.wrapper.impl;

public class SPacketHeldItemChange
extends Packet {
    public SPacketHeldItemChange(Object handle) {
        super(handle);
    }

    public static SPacketHeldItemChange createCloseWindowPacket(int windowId) {
        return new SPacketHeldItemChange(SPacketHeldItemChange.vapeInstance.getMappingsMapperCompat().C6.createCloseWindowPacket(windowId));
    }
}
