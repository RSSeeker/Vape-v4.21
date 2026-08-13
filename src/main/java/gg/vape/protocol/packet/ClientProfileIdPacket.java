package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ClientProfileIdPacket
implements ZeusSerializablePacket {
    private long profileId;

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.profileId = zeusPacketBuffer.readLong();
    }

    public long getProfileId() {
        return this.profileId;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.profileId);
    }

    public ClientProfileIdPacket(long profileId) {
        this.profileId = profileId;
    }

    public ClientProfileIdPacket() {
    }
}
