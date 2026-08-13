package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientGroupLeaderKickResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ClientGroupLeaderKickPacket
extends ZeusTrackedPacket<ClientGroupLeaderKickResponsePacket> {
    private long f;

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.f);
    }

    public ClientGroupLeaderKickPacket() {
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.f = zeusPacketBuffer.readLong();
    }

    public long H() {
        return this.f;
    }

    public ClientGroupLeaderKickPacket(UserModel userModel) {
        this.f = userModel.getId();
    }
}

