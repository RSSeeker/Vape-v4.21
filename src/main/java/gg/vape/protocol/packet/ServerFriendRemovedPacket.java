package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerFriendRemovedPacket
implements ZeusSerializablePacket {
    private UserModel M;

    public UserModel m() {
        return this.M;
    }

    public ServerFriendRemovedPacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.M = new UserModel(zeusPacketBuffer);
    }

    public ServerFriendRemovedPacket(UserModel userModel) {
        this.M = userModel;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.M.writeTo(zeusPacketBuffer);
    }
}

