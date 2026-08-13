package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientGroupLeaderKickPacket;
import gg.vape.protocol.packet.ClientGroupLeaderKickStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ClientGroupLeaderKickResponsePacket
extends ZeusTrackedPacket<ClientGroupLeaderKickPacket> {
    private ClientGroupLeaderKickStatus K;

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.K = gx_12.readEnum(ClientGroupLeaderKickStatus.class);
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.K);
    }

    public ClientGroupLeaderKickResponsePacket() {
    }

    public ClientGroupLeaderKickResponsePacket(ClientGroupLeaderKickPacket gq_02, ClientGroupLeaderKickStatus iH) {
        super(gq_02);
        this.K = iH;
    }

    public ClientGroupLeaderKickStatus P() {
        return this.K;
    }
}

