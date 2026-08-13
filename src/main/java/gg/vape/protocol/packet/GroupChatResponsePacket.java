package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupChatPacket;
import gg.vape.protocol.packet.GroupChatStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GroupChatResponsePacket
extends ZeusTrackedPacket<GroupChatPacket> {
    private GroupChatStatus status;
    private long messageTimestamp;
    @Nullable
    private String responseMessage;

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.status);
        zeusPacketBuffer.writeString(this.responseMessage);
        zeusPacketBuffer.writeLong(this.messageTimestamp);
    }

    public GroupChatResponsePacket(@Nullable GroupChatPacket groupChatPacket, GroupChatStatus status) {
        super(groupChatPacket);
        this.status = status;
    }

    public GroupChatStatus getStatus() {
        return this.status;
    }

    public GroupChatResponsePacket() {
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.status = zeusPacketBuffer.readEnum(GroupChatStatus.class);
        this.responseMessage = zeusPacketBuffer.readString(255);
        this.messageTimestamp = zeusPacketBuffer.readLong();
    }

    @Nullable
    public String getResponseMessage() {
        return this.responseMessage;
    }

    public long getMessageTimestamp() {
        return this.messageTimestamp;
    }

    public GroupChatResponsePacket(@Nullable GroupChatPacket groupChatPacket, @NotNull String string) {
        this(groupChatPacket, GroupChatStatus.SUCCESS);
        this.responseMessage = string;
        this.messageTimestamp = System.currentTimeMillis();
    }
}
