package gg.vape.protocol;

import gg.vape.protocol.ZeusPacketBufferUtil;
import gg.vape.ui.click.component.GuiComponent;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.util.UUID;

public class ZeusPacketBuffer {
    private static GuiComponent[] guiComponents;
    private final ByteBuf byteBuf;

    public void writeDouble(double value) {
        this.byteBuf.writeDouble(value);
    }

    public short readShort() {
        return this.byteBuf.readShort();
    }

    public void writeBoolean(boolean value) {
        this.byteBuf.writeBoolean(value);
    }

    public ByteBuf writeBytes(byte[] source, int sourceIndex, int length) {
        return this.byteBuf.writeBytes(source, sourceIndex, length);
    }

    public void writeUuid(UUID uuid) {
        ZeusPacketBufferUtil.writeUuid(this.byteBuf, uuid);
    }

    public static int getVarIntSize(int value) {
        for (int i = 1; i < 5; ++i) {
            if ((value & -1 << i * 7) != 0) continue;
            return i;
        }
        return 5;
    }

    public void writeFloat(float value) {
        this.byteBuf.writeFloat(value);
    }

    public boolean readBoolean() {
        return this.byteBuf.readBoolean();
    }

    public void writeVarInt(int value) {
        ZeusPacketBufferUtil.writeVarInt(this.byteBuf, value);
    }

    public void writeShort(short value) {
        this.byteBuf.writeShort((int)value);
    }

    public ByteBuf writeBytes(ByteBuf source, int length) {
        return this.byteBuf.writeBytes(source, length);
    }

    public ByteBuf writeBytes(ByteBuffer source) {
        return this.byteBuf.writeBytes(source);
    }

    public static void setGuiComponents(GuiComponent[] components) {
        guiComponents = components;
    }

    public String readString(int maxLength) {
        return ZeusPacketBufferUtil.readString(this.byteBuf, maxLength);
    }

    public void writeLong(long value) {
        this.byteBuf.writeLong(value);
    }

    public boolean release(int decrement) {
        return this.byteBuf.release(decrement);
    }

    public int readVarInt() {
        return ZeusPacketBufferUtil.readVarInt(this.byteBuf);
    }

    public UUID readUuid() {
        return ZeusPacketBufferUtil.readUuid(this.byteBuf);
    }

    public ByteBuf writeBytes(ByteBuf source) {
        return this.byteBuf.writeBytes(source);
    }

    public int readInt() {
        return this.byteBuf.readInt();
    }

    public ByteBuf writeBytes(byte[] source) {
        return this.byteBuf.writeBytes(source);
    }

    public ByteBuf getByteBuf() {
        return this.byteBuf;
    }

    public ByteBuf ensureWritable(int minWritableBytes) {
        return this.byteBuf.ensureWritable(minWritableBytes);
    }

    public <E extends Enum<E>> E readEnum(Class<E> enumType) {
        return ZeusPacketBufferUtil.readEnum(this.byteBuf, enumType);
    }

    public void writeString(String value) {
        ZeusPacketBufferUtil.writeString(this.byteBuf, value);
    }

    public void writeInt(int value) {
        this.byteBuf.writeInt(value);
    }

    public long readLong() {
        return this.byteBuf.readLong();
    }

    public float readFloat() {
        return this.byteBuf.readFloat();
    }

    public double readDouble() {
        return this.byteBuf.readDouble();
    }


    public void writeEnum(Enum<?> value) {
        ZeusPacketBufferUtil.writeEnum(this.byteBuf, value);
    }

    public ByteBuf writeBytes(ByteBuf source, int sourceIndex, int length) {
        return this.byteBuf.writeBytes(source, sourceIndex, length);
    }

    public static GuiComponent[] getGuiComponents() {
        return guiComponents;
    }

    public ZeusPacketBuffer(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public boolean releaseBuffer() {
        return this.byteBuf.release();
    }

    static {
        if (ZeusPacketBuffer.getGuiComponents() != null) {
            ZeusPacketBuffer.setGuiComponents(new GuiComponent[3]);
        }
    }

}

