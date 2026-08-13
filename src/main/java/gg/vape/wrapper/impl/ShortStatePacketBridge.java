package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.wrapper.impl.Packet;

public class ShortStatePacketBridge
extends Packet {
    public short getTransactionId() {
        return Vape.INSTANCE.getMappingsMapperCompat().F.getTransactionId(this.I);
    }

    public ShortStatePacketBridge(Object handle) {
        super(handle);
    }
}
