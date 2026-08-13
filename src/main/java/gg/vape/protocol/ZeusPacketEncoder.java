package gg.vape.protocol;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.ZeusPacketDirection;
import gg.vape.protocol.ZeusProtocolConstants;
import gg.vape.protocol.ZeusProtocolState;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ZeusPacketEncoder
extends MessageToByteEncoder<ZeusSerializablePacket> {
    private static final String UNKNOWN_PACKET_MESSAGE;
    private final ZeusPacketDirection packetDirection;
    private final boolean failOnUnknownPacket;
    private static boolean diagnosticsEnabled;

    public static void setDiagnosticsEnabled(boolean enabled) {
        diagnosticsEnabled = enabled;
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    static {
        ZeusPacketEncoder.setDiagnosticsEnabled(false);
        UNKNOWN_PACKET_MESSAGE = "Failed to recognize packet ";
    }

    public ZeusPacketEncoder(ZeusPacketDirection zeusPacketDirection, boolean bl) {
        this.packetDirection = zeusPacketDirection;
        this.failOnUnknownPacket = bl;
    }

    public static boolean X() {
        boolean diagnostics = ZeusPacketEncoder.isDiagnosticsEnabled();
        return true;
    }

    public static boolean isDiagnosticsEnabled() {
        return diagnosticsEnabled;
    }

    @Override
    protected void encode(ChannelHandlerContext context, ZeusSerializablePacket packet, ByteBuf output) throws Exception {
        this.encodePacket(context, packet, output);
    }

    protected void encodePacket(ChannelHandlerContext channelHandlerContext, ZeusSerializablePacket zeusSerializablePacket, ByteBuf byteBuf) throws Exception {
        ZeusProtocolState zeusProtocolState = (ZeusProtocolState)((Object)channelHandlerContext.channel().attr(ZeusProtocolConstants.Q).get());
        int packetId = zeusProtocolState.getPacketId(this.packetDirection, zeusSerializablePacket);
        if (packetId == -1) {
            if (this.failOnUnknownPacket) {
                throw new RuntimeException(UNKNOWN_PACKET_MESSAGE + zeusSerializablePacket.getClass().getName());
            }
            return;
        }
        ZeusPacketBuffer zeusPacketBuffer = new ZeusPacketBuffer(byteBuf);
        zeusPacketBuffer.writeVarInt(packetId);
        try {
            zeusSerializablePacket.o(zeusPacketBuffer);
        }
        catch (Exception exception) {
            if (this.failOnUnknownPacket) {
                exception.printStackTrace();
            }
            throw exception;
        }
    }
}
