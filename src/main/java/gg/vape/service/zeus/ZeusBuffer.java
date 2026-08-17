package gg.vape.service.zeus;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

final class ZeusBuffer {
    private ZeusBuffer() {
    }

    static int readVarInt(ByteBuf buffer) {
        int result = 0;
        for (int index = 0; index < 5; index++) {
            byte current = buffer.readByte();
            result |= (current & 0x7f) << index * 7;
            if ((current & 0x80) == 0) {
                return result;
            }
        }
        throw new DecoderException("VarInt is wider than 35 bits");
    }

    static void writeVarInt(ByteBuf buffer, int value) {
        while ((value & 0xffffff80) != 0) {
            buffer.writeByte(value & 0x7f | 0x80);
            value >>>= 7;
        }
        buffer.writeByte(value);
    }

    static String readString(ByteBuf buffer, int maximumCharacters) {
        int byteLength = readVarInt(buffer);
        if (byteLength < 0 || byteLength > maximumCharacters * 4 || byteLength > buffer.readableBytes()) {
            throw new DecoderException("Invalid string length: " + byteLength);
        }
        byte[] bytes = new byte[byteLength];
        buffer.readBytes(bytes);
        String value = new String(bytes, StandardCharsets.UTF_8);
        if (value.length() > maximumCharacters) {
            throw new DecoderException("String exceeds " + maximumCharacters + " characters");
        }
        return value;
    }

    static void writeString(ByteBuf buffer, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeVarInt(buffer, bytes.length);
        buffer.writeBytes(bytes);
    }

    static UUID readUuid(ByteBuf buffer) {
        return new UUID(buffer.readLong(), buffer.readLong());
    }

    static void writeUuid(ByteBuf buffer, UUID value) {
        buffer.writeLong(value.getMostSignificantBits());
        buffer.writeLong(value.getLeastSignificantBits());
    }

    static int varIntSize(int value) {
        for (int size = 1; size < 5; size++) {
            if ((value & -1 << size * 7) == 0) {
                return size;
            }
        }
        return 5;
    }
}
