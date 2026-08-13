package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.AuthenticationPacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class AuthenticationResponsePacket
extends ZeusTrackedPacket<AuthenticationPacket> {
    private UserModel E;

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        int n = gx_12.readVarInt();
        this.E = new UserModel(gx_12);
    }

    public AuthenticationResponsePacket() {
    }

    public UserModel s() {
        return this.E;
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeVarInt(1);
        this.E.writeTo(gx_12);
    }

    public AuthenticationResponsePacket(AuthenticationPacket authenticationPacket, UserModel oj_12) {
        this(authenticationPacket);
        this.E = oj_12;
    }

    public AuthenticationResponsePacket(AuthenticationPacket authenticationPacket) {
        super(authenticationPacket);
    }
}

