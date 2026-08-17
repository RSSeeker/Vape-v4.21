package gg.vape.service.zeus;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

final class ZeusFrameDecoder extends ByteToMessageDecoder {
    private static final int MAXIMUM_FRAME_LENGTH = 1024 * 1024;

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf input, List<Object> output) {
        input.markReaderIndex();
        int length = 0;
        for (int index = 0; index < 3; index++) {
            if (!input.isReadable()) {
                input.resetReaderIndex();
                return;
            }
            byte current = input.readByte();
            length |= (current & 0x7f) << index * 7;
            if ((current & 0x80) == 0) {
                if (length < 0 || length > MAXIMUM_FRAME_LENGTH) {
                    throw new CorruptedFrameException("Invalid Zeus frame length: " + length);
                }
                if (input.readableBytes() < length) {
                    input.resetReaderIndex();
                    return;
                }
                output.add(input.readBytes(length));
                return;
            }
        }
        throw new CorruptedFrameException("Zeus frame length is wider than 21 bits");
    }
}
