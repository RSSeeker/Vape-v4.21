package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupInviteStateResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class GroupInviteStatePacket
extends ZeusTrackedPacket<GroupInviteStateResponsePacket> {
    private boolean acceptInvite;
    private long userId;

    public GroupInviteStatePacket() {
    }

    public long getUserId() {
        return this.userId;
    }

    public boolean isAcceptInvite() {
        return this.acceptInvite;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
        zeusPacketBuffer.writeBoolean(this.acceptInvite);
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
        this.acceptInvite = zeusPacketBuffer.readBoolean();
    }

    public GroupInviteStatePacket(UserModel userModel, boolean acceptInvite) {
        this.userId = userModel.getId();
        this.acceptInvite = acceptInvite;
    }
}
