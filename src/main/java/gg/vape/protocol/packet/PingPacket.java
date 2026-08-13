package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.PingResponsePacket;
import gg.vape.protocol.packet.PingTargetData;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class PingPacket
extends ZeusTrackedPacket<PingResponsePacket> {
    private PingTargetData L;
    private static String J;

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        this.L.n(gx_12);
    }

    public PingPacket(PingTargetData pingTargetData) {
        this.L = pingTargetData;
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.L = new PingTargetData(gx_12);
    }

    public PingTargetData i() {
        return this.L;
    }

    public static void O(String string) {
        J = string;
    }

    public static String F() {
        return J;
    }

    public PingPacket() {
    }

    static {
        if (PingPacket.F() != null) {
            PingPacket.O("cFpSzb");
        }
    }
}

