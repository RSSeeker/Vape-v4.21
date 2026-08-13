package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import java.util.UUID;

public class ClientMinecraftProfilePacket
implements ZeusSerializablePacket {
    private UUID r;
    private String O;

    @Override
    public void o(ZeusPacketBuffer gx_12) {
        gx_12.writeUuid(this.r);
        gx_12.writeString(this.O);
    }

    public ClientMinecraftProfilePacket() {
    }

    public String y() {
        return this.O;
    }

    public ClientMinecraftProfilePacket(UUID uUID, String string) {
        this.r = uUID;
        this.O = string;
    }

    @Override
    public void S(ZeusPacketBuffer gx_12) {
        this.r = gx_12.readUuid();
        this.O = gx_12.readString(16);
    }

    public UUID e() {
        return this.r;
    }
}

