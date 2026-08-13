package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import org.jetbrains.annotations.Nullable;

public class ClientServerAddressPacket
implements ZeusSerializablePacket {
    private String serverAddress;

    @Nullable
    public String getServerAddress() {
        return this.serverAddress;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        ZeusPacketBuffer zeusPacketBuffer2 = zeusPacketBuffer;
        boolean hasServerAddress = this.serverAddress != null;
        zeusPacketBuffer2.writeBoolean(hasServerAddress);
        if (this.serverAddress != null) {
            zeusPacketBuffer.writeString(this.serverAddress);
        }
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        if (zeusPacketBuffer.readBoolean()) {
            this.serverAddress = zeusPacketBuffer.readString(255);
        }
    }

    public ClientServerAddressPacket() {
    }

    public ClientServerAddressPacket(String serverAddress) {
        this.serverAddress = serverAddress;
    }

}
