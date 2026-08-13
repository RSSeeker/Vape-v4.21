package gg.vape.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ZeusPacketBufferUtil {
    public static int readVarInt(ByteBuf byteBuf) {
        byte by;
        int n = 0;
        int n2 = 0;
        do {
            by = byteBuf.readByte();
            n |= (by & 0x7F) << n2++ * 7;
            if (n2 <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((by & 0x80) == 128);
        return n;
    }

    public static void writeVarInt(ByteBuf byteBuf, int value) {
        while ((value & 0xFFFFFF80) != 0) {
            byteBuf.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
        byteBuf.writeByte(value);
    }

    public static <E extends Enum<E>> E readEnum(ByteBuf byteBuf, Class<E> enumType) {
        return (E)((Enum[])enumType.getEnumConstants())[ZeusPacketBufferUtil.readVarInt(byteBuf)];
    }

    public static void writeString(ByteBuf byteBuf, String value) {
        byte[] byArray = value.getBytes(StandardCharsets.UTF_8);
        if (byArray.length > Short.MAX_VALUE) {
            throw new EncoderException("String too big (was " + value.length() + " bytes encoded, max " + Short.MAX_VALUE + ")");
        }
        ZeusPacketBufferUtil.writeVarInt(byteBuf, byArray.length);
        byteBuf.writeBytes(byArray);
    }

    public static UUID readUuid(ByteBuf byteBuf) {
        return new UUID(byteBuf.readLong(), byteBuf.readLong());
    }

    public static void writeUuid(ByteBuf byteBuf, UUID uuid) {
        byteBuf.writeLong(uuid.getMostSignificantBits());
        byteBuf.writeLong(uuid.getLeastSignificantBits());
    }

    public static String readString(ByteBuf byteBuf, int maxCharacters) {
        int n2 = ZeusPacketBufferUtil.readVarInt(byteBuf);
        if (n2 > maxCharacters * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + n2 + " > " + maxCharacters * 4 + ")");
        }
        if (n2 < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        byte[] byArray = new byte[n2];
        byteBuf.readBytes(byArray);
        String string = new String(byArray, StandardCharsets.UTF_8);
        if (string.length() > maxCharacters) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + n2 + " > " + maxCharacters + ")");
        }
        return string;
    }

    public static void writeEnum(ByteBuf byteBuf, Enum<?> value) {
        ZeusPacketBufferUtil.writeVarInt(byteBuf, value.ordinal());
    }

    private static RuntimeException preserveRuntimeException(RuntimeException runtimeException) {
        return runtimeException;
    }
}

