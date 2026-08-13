package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientGroupLeaderPromoteResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ClientGroupLeaderPromotePacket
extends ZeusTrackedPacket<ClientGroupLeaderPromoteResponsePacket> {
    private long U;

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.U = zeusPacketBuffer.readLong();
    }

    public ClientGroupLeaderPromotePacket(UserModel userModel) {
        this.U = userModel.getId();
    }

    public ClientGroupLeaderPromotePacket() {
    }

    public long d() {
        return this.U;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.U);
    }
}

