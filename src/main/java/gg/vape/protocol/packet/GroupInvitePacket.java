package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupInviteResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class GroupInvitePacket
extends ZeusTrackedPacket<GroupInviteResponsePacket> {
    private UserModel invitedUser;

    public UserModel getInvitedUser() {
        return this.invitedUser;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.invitedUser = new UserModel(zeusPacketBuffer);
    }

    public GroupInvitePacket() {
    }

    public GroupInvitePacket(UserModel invitedUser) {
        this.invitedUser = invitedUser;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        this.invitedUser.writeTo(zeusPacketBuffer);
    }
}
