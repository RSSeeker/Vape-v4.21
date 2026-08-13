package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.FriendRequestResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class FriendRequestPacket
extends ZeusTrackedPacket<FriendRequestResponsePacket> {
    private String username;

    public FriendRequestPacket() {
    }

    public String getUsername() {
        return this.username;
    }

    public FriendRequestPacket(String username) {
        this();
        this.username = username;
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.username = gx_12.readString(16);
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeString(this.username);
    }
}
