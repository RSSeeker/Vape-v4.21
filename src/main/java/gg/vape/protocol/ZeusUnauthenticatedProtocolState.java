package gg.vape.protocol;

import gg.vape.protocol.ZeusPacketDirection;
import gg.vape.protocol.ZeusProtocolState;
import gg.vape.protocol.packet.AuthenticationPacket;
import gg.vape.protocol.packet.AuthenticationResponsePacket;
import gg.vape.protocol.packet.HandshakePacket;
import gg.vape.protocol.packet.HandshakeResponsePacket;

final class ZeusUnauthenticatedProtocolState {
    private final ZeusProtocolState state;

    private ZeusUnauthenticatedProtocolState(ZeusProtocolState state) {
        this.state = state;
        this.registerPacket(ZeusPacketDirection.SERVER, HandshakePacket.class);
        this.registerPacket(ZeusPacketDirection.CLIENT, HandshakeResponsePacket.class);
        this.registerPacket(ZeusPacketDirection.SERVER, AuthenticationPacket.class);
        this.registerPacket(ZeusPacketDirection.CLIENT, AuthenticationResponsePacket.class);
    }

    static void register(ZeusProtocolState state) {
        new ZeusUnauthenticatedProtocolState(state);
    }

    private void registerPacket(ZeusPacketDirection direction, Class<? extends gg.vape.protocol.packet.ZeusSerializablePacket> packetClass) {
        this.state.registerPacket(direction, packetClass);
    }
}
