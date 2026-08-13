package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.PingAudience;
import gg.vape.protocol.packet.PingTargetData;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerPingPacket
implements ZeusSerializablePacket {
    private PingTargetData c;
    private PingAudience n;
    private long C;
    private static int A;

    public static int q() {
        int n = ServerPingPacket.D();
        if (n == 0) {
            return 28;
        }
        return 0;
    }

    public ServerPingPacket() {
    }

    public ServerPingPacket(PingAudience pingAudience, long l, PingTargetData pingTargetData) {
        this.n = pingAudience;
        this.C = l;
        this.c = pingTargetData;
    }

    public long s() {
        return this.C;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.n = zeusPacketBuffer.readEnum(PingAudience.class);
        this.C = zeusPacketBuffer.readLong();
        this.c = new PingTargetData(zeusPacketBuffer);
    }


    public static void s(int n) {
        A = n;
    }

    public static int D() {
        return A;
    }

    public PingTargetData m() {
        return this.c;
    }

    public PingAudience R() {
        return this.n;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.n);
        zeusPacketBuffer.writeLong(this.C);
        this.c.n(zeusPacketBuffer);
    }

    static {
        if (ServerPingPacket.q() != 0) {
            ServerPingPacket.s(84);
        }
    }
}

