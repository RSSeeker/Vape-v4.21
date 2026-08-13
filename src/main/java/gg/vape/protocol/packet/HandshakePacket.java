package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.HandshakeResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class HandshakePacket
extends ZeusTrackedPacket<HandshakeResponsePacket> {
    private int t;
    private static int[] K;

    public HandshakePacket(int n) {
        this.t = n;
    }

    public HandshakePacket() {
    }

    public static int[] P() {
        return K;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeVarInt(this.t);
    }

    public static void C(int[] nArray) {
        K = nArray;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.t = zeusPacketBuffer.readVarInt();
    }

    public int E() {
        return this.t;
    }

    static {
        if (HandshakePacket.P() != null) {
            HandshakePacket.C(new int[5]);
        }
    }
}

