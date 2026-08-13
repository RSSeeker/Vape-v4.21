package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.HeartbeatResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class HeartbeatPacket
extends ZeusTrackedPacket<HeartbeatResponsePacket> {
    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
    }
}

