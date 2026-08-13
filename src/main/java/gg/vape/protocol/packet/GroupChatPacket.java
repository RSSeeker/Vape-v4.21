package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupChatResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class GroupChatPacket
extends ZeusTrackedPacket<GroupChatResponsePacket> {
    private String message;

    public GroupChatPacket(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public GroupChatPacket() {
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.message = gx_12.readString(255);
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeString(this.message);
    }
}
