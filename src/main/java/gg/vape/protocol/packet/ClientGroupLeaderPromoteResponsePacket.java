package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientGroupLeaderPromotePacket;
import gg.vape.protocol.packet.ClientGroupLeaderPromoteStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ClientGroupLeaderPromoteResponsePacket
extends ZeusTrackedPacket<ClientGroupLeaderPromotePacket> {
    private ClientGroupLeaderPromoteStatus H;

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.H);
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.H = gx_12.readEnum(ClientGroupLeaderPromoteStatus.class);
    }

    public ClientGroupLeaderPromoteStatus M() {
        return this.H;
    }

    public ClientGroupLeaderPromoteResponsePacket(ClientGroupLeaderPromotePacket gl_02, ClientGroupLeaderPromoteStatus vp_12) {
        super(gl_02);
        this.H = vp_12;
    }

    public ClientGroupLeaderPromoteResponsePacket() {
    }
}

