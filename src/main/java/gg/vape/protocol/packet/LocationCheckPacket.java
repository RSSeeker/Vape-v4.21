package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class LocationCheckPacket
implements ZeusSerializablePacket {
    private int a;
    private int H;
    private int j;

    public int L() {
        return this.j;
    }

    @Override
    public void S(ZeusPacketBuffer gx_12) {
        this.H = gx_12.readInt();
        this.j = gx_12.readInt();
        this.a = gx_12.readInt();
    }

    @Override
    public void o(ZeusPacketBuffer gx_12) {
        gx_12.writeInt(this.H);
        gx_12.writeInt(this.j);
        gx_12.writeInt(this.a);
    }

    public int f() {
        return this.H;
    }

    public LocationCheckPacket(int n, int n2, int n3) {
        this.H = n;
        this.j = n2;
        this.a = n3;
    }

    public LocationCheckPacket() {
    }

    public int r() {
        return this.a;
    }
}

