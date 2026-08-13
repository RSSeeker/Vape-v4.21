package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.HandshakePacket;
import gg.vape.protocol.packet.HandshakeStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class HandshakeResponsePacket
extends ZeusTrackedPacket<HandshakePacket> {
    private static boolean B;
    private HandshakeStatus f;

    public HandshakeResponsePacket() {
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.f = gx_12.readEnum(HandshakeStatus.class);
    }

    public HandshakeResponsePacket(HandshakePacket tz_02, HandshakeStatus eG) {
        super(tz_02);
        this.f = eG;
    }

    public static boolean V() {
        return B;
    }

    public HandshakeStatus Q() {
        return this.f;
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.f);
    }

    public static void i(boolean bl) {
        B = bl;
    }

    public static boolean w() {
        boolean bl = HandshakeResponsePacket.V();
        return true;
    }


    static {
        if (HandshakeResponsePacket.V()) {
            HandshakeResponsePacket.i(true);
        }
    }
}

