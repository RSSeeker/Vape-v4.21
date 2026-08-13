package gg.vape.protocol;

import gg.vape.protocol.ZeusPacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class ZeusFrameEncoder
extends MessageToByteEncoder<ByteBuf> {
    private static int configuredFrameLengthLimit;

    private static IllegalArgumentException propagateIllegalArgument(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }

    public static void setFrameLengthLimit(int frameLengthLimit) {
        configuredFrameLengthLimit = frameLengthLimit;
    }

    static {
        ZeusFrameEncoder.setFrameLengthLimit(67);
    }

    public static int getReservedFrameValue() {
        int frameLengthLimit = ZeusFrameEncoder.getFrameLengthLimit();
        return 0;
    }

    @Override
    protected void encode(ChannelHandlerContext context, ByteBuf input, ByteBuf output) {
        this.encodeFrame(context, input, output);
    }

    protected void encodeFrame(ChannelHandlerContext channelHandlerContext, ByteBuf input, ByteBuf output) {
        int payloadLength = input.readableBytes();
        int prefixLength = ZeusPacketBuffer.getVarIntSize(payloadLength);
        if (prefixLength > 3) {
            throw new IllegalArgumentException("unable to fit " + payloadLength + " into 3");
        }
        ZeusPacketBuffer zeusPacketBuffer = new ZeusPacketBuffer(output);
        zeusPacketBuffer.ensureWritable(prefixLength + payloadLength);
        zeusPacketBuffer.writeVarInt(payloadLength);
        zeusPacketBuffer.writeBytes(input, input.readerIndex(), payloadLength);
    }

    public static int getFrameLengthLimit() {
        return configuredFrameLengthLimit;
    }
}
