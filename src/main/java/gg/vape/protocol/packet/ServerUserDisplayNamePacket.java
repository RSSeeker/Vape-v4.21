package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerUserDisplayNamePacket
implements ZeusSerializablePacket {
    private long userId;
    private String displayName;

    public ServerUserDisplayNamePacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
        this.displayName = zeusPacketBuffer.readString(16);
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public long getUserId() {
        return this.userId;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
        zeusPacketBuffer.writeString(this.displayName);
    }

    public ServerUserDisplayNamePacket(UserModel userModel, String displayName) {
        this.userId = userModel.getId();
        this.displayName = displayName;
    }
}
