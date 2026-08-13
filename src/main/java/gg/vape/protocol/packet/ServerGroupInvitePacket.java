package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerGroupInvitePacket
implements ZeusSerializablePacket {
    private UserModel H;

    public UserModel Z() {
        return this.H;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.H = new UserModel(zeusPacketBuffer);
    }

    public ServerGroupInvitePacket(UserModel userModel) {
        this.H = userModel;
    }

    public ServerGroupInvitePacket() {
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.H.writeTo(zeusPacketBuffer);
    }
}

