package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerGroupInviteRemovedPacket
implements ZeusSerializablePacket {
    private UserModel d;

    public ServerGroupInviteRemovedPacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.d = new UserModel(zeusPacketBuffer);
    }

    public ServerGroupInviteRemovedPacket(UserModel userModel) {
        this.d = userModel;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.d.writeTo(zeusPacketBuffer);
    }

    public UserModel G() {
        return this.d;
    }
}

