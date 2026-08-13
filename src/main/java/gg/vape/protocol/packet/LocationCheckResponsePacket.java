package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class LocationCheckResponsePacket
implements ZeusSerializablePacket {
    private boolean locationValid;

    public boolean isLocationValid() {
        return this.locationValid;
    }

    public LocationCheckResponsePacket(boolean locationValid) {
        this.locationValid = locationValid;
    }

    public LocationCheckResponsePacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.locationValid = zeusPacketBuffer.readBoolean();
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeBoolean(this.locationValid);
    }
}
