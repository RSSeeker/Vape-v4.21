package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ClientCpsPacket
implements ZeusSerializablePacket {
    private int clicksPerSecond;

    public ClientCpsPacket(int clicksPerSecond) {
        this.clicksPerSecond = clicksPerSecond;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeVarInt(this.clicksPerSecond);
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.clicksPerSecond = zeusPacketBuffer.readVarInt();
    }

    public int getClicksPerSecond() {
        return this.clicksPerSecond;
    }

    public ClientCpsPacket() {
    }
}
