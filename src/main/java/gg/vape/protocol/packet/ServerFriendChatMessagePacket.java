package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerFriendChatMessagePacket
implements ZeusSerializablePacket {
    private UserModel sender;
    private String message;
    private long messageTimestamp;

    public ServerFriendChatMessagePacket() {
    }

    public String getMessage() {
        return this.message;
    }

    public long getMessageTimestamp() {
        return this.messageTimestamp;
    }

    public UserModel getSender() {
        return this.sender;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.sender = new UserModel(zeusPacketBuffer);
        this.message = zeusPacketBuffer.readString(255);
        this.messageTimestamp = zeusPacketBuffer.readLong();
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.sender.writeTo(zeusPacketBuffer);
        zeusPacketBuffer.writeString(this.message);
        zeusPacketBuffer.writeLong(this.messageTimestamp);
    }

    public ServerFriendChatMessagePacket(UserModel sender, String message) {
        this.sender = sender;
        this.message = message;
        this.messageTimestamp = System.currentTimeMillis();
    }
}
