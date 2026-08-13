package gg.vape.wrapper.impl;

public class CPacketHeldItemChange
extends Packet {
    public static CPacketHeldItemChange create(int selectedSlot) {
        return new CPacketHeldItemChange(CPacketHeldItemChange.vapeInstance.getMappingsMapperCompat().Ck.createPacket(selectedSlot));
    }

    public CPacketHeldItemChange(Object handle) {
        super(handle);
    }
}
