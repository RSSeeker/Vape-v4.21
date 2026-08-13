package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class FriendVisibilityUpdatePacket
implements ZeusSerializablePacket {
    private boolean visible;
    private long userId;

    public long getUserId() {
        return this.userId;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
        zeusPacketBuffer.writeBoolean(this.visible);
    }

    public FriendVisibilityUpdatePacket() {
    }

    public FriendVisibilityUpdatePacket(UserModel userModel, boolean visible) {
        this.userId = userModel.getId();
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
        this.visible = zeusPacketBuffer.readBoolean();
    }
}
