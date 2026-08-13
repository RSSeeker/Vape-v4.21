package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MNetHandlerPlayClient;

public class NetHandlerPlayClient
extends NetworkPacketHandle {
    public void handleEntityTeleport(SPacketEntity teleportPacket) {
        MNetHandlerPlayClient.handleEntityTeleport(NetHandlerPlayClient.vapeInstance.getMappingsMapperCompat().netHandlerPlayClient, this.I, teleportPacket.getObject());
    }

    public NetHandlerPlayClient(Object handle) {
        super(handle);
    }
}
