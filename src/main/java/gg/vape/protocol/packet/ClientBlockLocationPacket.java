package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ClientBlockLocationPacket
implements ZeusSerializablePacket {
    private int x;
    private int y;
    private int z;
    private long userId;

    public int getZ() {
        return this.z;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
        this.x = zeusPacketBuffer.readInt();
        this.y = zeusPacketBuffer.readInt();
        this.z = zeusPacketBuffer.readInt();
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
        zeusPacketBuffer.writeInt(this.x);
        zeusPacketBuffer.writeInt(this.y);
        zeusPacketBuffer.writeInt(this.z);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public ClientBlockLocationPacket(long userId, int x, int y, int z) {
        this.userId = userId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ClientBlockLocationPacket() {
    }

    public long getUserId() {
        return this.userId;
    }
}
