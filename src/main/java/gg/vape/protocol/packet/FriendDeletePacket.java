package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.FriendDeleteResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class FriendDeletePacket
extends ZeusTrackedPacket<FriendDeleteResponsePacket> {
    private UserModel user;

    public FriendDeletePacket() {
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.user = new UserModel(zeusPacketBuffer);
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        this.user.writeTo(zeusPacketBuffer);
    }

    public FriendDeletePacket(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return this.user;
    }
}
