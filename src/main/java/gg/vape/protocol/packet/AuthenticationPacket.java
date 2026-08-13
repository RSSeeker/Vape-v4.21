package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.AuthenticationResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import java.util.UUID;

public class AuthenticationPacket
extends ZeusTrackedPacket<AuthenticationResponsePacket> {
    private UUID I;
    private String E;
    private String V;

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.E = zeusPacketBuffer.readString(255);
        this.I = zeusPacketBuffer.readUuid();
        this.V = zeusPacketBuffer.readString(16);
    }

    public UUID i() {
        return this.I;
    }

    public String W() {
        return this.V;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeString(this.E);
        zeusPacketBuffer.writeUuid(this.I);
        zeusPacketBuffer.writeString(this.V);
    }

    public AuthenticationPacket() {
    }

    public AuthenticationPacket(String string, UUID uUID, String string2) {
        this.E = string;
        this.I = uUID;
        this.V = string2;
    }

    public String q() {
        return this.E;
    }
}

