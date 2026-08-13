package gg.vape.wrapper.impl;

public class CPacketUseEntityOne
extends Packet {
    public CPacketUseEntityOne(Object handle) {
        super(handle);
    }

    public static CPacketUseEntityOne create(ModelPlayer playerCapabilities) {
        return new CPacketUseEntityOne(CPacketUseEntityOne.vapeInstance.getMappingsMapperCompat().RE.createPlayerAbilitiesPacket(playerCapabilities.getObject()));
    }
}
