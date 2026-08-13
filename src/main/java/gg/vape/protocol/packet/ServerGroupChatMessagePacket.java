package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerGroupChatMessagePacket
implements ZeusSerializablePacket {
    private String message;
    private long messageTimestamp;
    private long senderUserId;

    public long getSenderUserId() {
        return this.senderUserId;
    }

    public ServerGroupChatMessagePacket(long senderUserId, String message) {
        this.senderUserId = senderUserId;
        this.message = message;
        this.messageTimestamp = System.currentTimeMillis();
    }

    public long getMessageTimestamp() {
        return this.messageTimestamp;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.senderUserId);
        zeusPacketBuffer.writeString(this.message);
        zeusPacketBuffer.writeLong(this.messageTimestamp);
    }

    public String getMessage() {
        return this.message;
    }

    public ServerGroupChatMessagePacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.senderUserId = zeusPacketBuffer.readLong();
        this.message = zeusPacketBuffer.readString(255);
        this.messageTimestamp = zeusPacketBuffer.readLong();
    }
}
