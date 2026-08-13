package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import java.util.function.BiConsumer;
import java.util.function.Function;

public enum GroupOption {
    OPEN_INVITES(Boolean.class, false, GroupOption::lambda$static$0, ZeusPacketBuffer::readBoolean);

    private static final GroupOption[] valuesCache;
    private final Class<?> valueType;
    private final BiConsumer<ZeusPacketBuffer, Object> valueWriter;
    private final Function<ZeusPacketBuffer, Object> valueReader;
    private final Object defaultValue;

    static {
        String string = "OPEN_INVITES";

        valuesCache = new GroupOption[]{OPEN_INVITES};
    }

    public BiConsumer<ZeusPacketBuffer, Object> getValueWriter() {
        return this.valueWriter;
    }

    public Class<?> getValueType() {
        return this.valueType;
    }

    private static String a(byte[] byArray) {
        int n = 0;
        int n2 = byArray.length;
        char[] cArray = new char[n2];
        for (int i = 0; i < n2; ++i) {
            char c;
            int n3 = 0xFF & byArray[i];
            if (n3 < 192) {
                cArray[n++] = (char)n3;
                continue;
            }
            if (n3 < 224) {
                c = (char)((char)(n3 & 0x1F) << 6);
                n3 = byArray[++i];
                c = (char)(c | (char)(n3 & 0x3F));
                cArray[n++] = c;
                continue;
            }
            if (i >= n2 - 2) continue;
            c = (char)((char)(n3 & 0xF) << 12);
            n3 = byArray[++i];
            c = (char)(c | (char)(n3 & 0x3F) << 6);
            n3 = byArray[++i];
            c = (char)(c | (char)(n3 & 0x3F));
            cArray[n++] = c;
        }
        return new String(cArray, 0, n);
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    private GroupOption(Class<?> valueType, Object defaultValue, BiConsumer<ZeusPacketBuffer, Object> valueWriter, Function<ZeusPacketBuffer, Object> valueReader) {
        this.valueType = valueType;
        this.defaultValue = defaultValue;
        this.valueWriter = valueWriter;
        this.valueReader = valueReader;
    }

    public Function<ZeusPacketBuffer, Object> getValueReader() {
        return this.valueReader;
    }

    private static void lambda$static$0(ZeusPacketBuffer zeusPacketBuffer, Object object) {
        zeusPacketBuffer.writeBoolean((Boolean)object);
    }
}
