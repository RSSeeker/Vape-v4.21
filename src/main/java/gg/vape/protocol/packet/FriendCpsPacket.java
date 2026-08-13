package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class FriendCpsPacket
implements ZeusSerializablePacket {
    private long userId;
    private int clicksPerSecond;

    public long getUserId() {
        return this.userId;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
        this.clicksPerSecond = zeusPacketBuffer.readVarInt();
    }

    public FriendCpsPacket(UserModel userModel, int clicksPerSecond) {
        this.userId = userModel.getId();
        this.clicksPerSecond = clicksPerSecond;
    }

    public FriendCpsPacket() {
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
        zeusPacketBuffer.writeVarInt(this.clicksPerSecond);
    }

    public int getClicksPerSecond() {
        return this.clicksPerSecond;
    }
}
