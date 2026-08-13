package gg.vape.protocol.packet;

import gg.vape.protocol.PresenceState;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class PresenceStateUpdatePacket
implements ZeusSerializablePacket {
    private PresenceState presenceState;

    @Override
    public void S(ZeusPacketBuffer packetBuffer) {
        this.presenceState = packetBuffer.readEnum(PresenceState.class);
    }

    public PresenceStateUpdatePacket() {
    }

    public PresenceStateUpdatePacket(PresenceState presenceState) {
        this.presenceState = presenceState;
    }

    @Override
    public void o(ZeusPacketBuffer packetBuffer) {
        packetBuffer.writeEnum(this.presenceState);
    }

    public PresenceState getPresenceState() {
        return this.presenceState;
    }
}
