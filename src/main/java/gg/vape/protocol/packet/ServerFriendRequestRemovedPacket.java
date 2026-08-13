package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerFriendRequestRemovedPacket
implements ZeusSerializablePacket {
    private long userId;

    public long getUserId() {
        return this.userId;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
    }

    public ServerFriendRequestRemovedPacket() {
    }

    public ServerFriendRequestRemovedPacket(long userId) {
        this.userId = userId;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
    }
}
