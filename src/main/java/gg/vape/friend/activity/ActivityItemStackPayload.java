package gg.vape.friend.activity;

import gg.vape.protocol.ZeusPacketBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityItemStackPayload {
    private final int metadata;
    private final int count;
    private static String obfuscationToken;
    private final Map<Short, Short> enchantments;
    private final int itemId;

    public ActivityItemStackPayload(ZeusPacketBuffer zeusPacketBuffer) {
        this.itemId = zeusPacketBuffer.readInt();
        this.count = zeusPacketBuffer.readInt();
        this.metadata = zeusPacketBuffer.readInt();
        LinkedHashMap<Short, Short> decodedEnchantments = new LinkedHashMap<Short, Short>();
        int enchantmentCount = zeusPacketBuffer.readVarInt();
        for (int index = 0; index < enchantmentCount; ++index) {
            short enchantmentId = zeusPacketBuffer.readShort();
            short level = zeusPacketBuffer.readShort();
            decodedEnchantments.put(enchantmentId, level);
        }
        this.enchantments = decodedEnchantments;
    }

    public int getItemId() {
        return this.itemId;
    }

    public ActivityItemStackPayload(int itemId, int count, int metadata, Map<Short, Short> enchantments) {
        this.itemId = itemId;
        this.count = count;
        this.metadata = metadata;
        this.enchantments = enchantments;
    }

    public Map<Short, Short> getEnchantments() {
        return this.enchantments;
    }

    public void writeTo(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeInt(this.itemId);
        zeusPacketBuffer.writeInt(this.count);
        zeusPacketBuffer.writeInt(this.metadata);
        zeusPacketBuffer.writeVarInt(this.enchantments.size());
        for (Map.Entry<Short, Short> entry : this.enchantments.entrySet()) {
            zeusPacketBuffer.writeShort(entry.getKey());
            zeusPacketBuffer.writeShort(entry.getValue());
        }
    }

    public int getCount() {
        return this.count;
    }

    public int getMetadata() {
        return this.metadata;
    }

    public static String getObfuscationToken() {
        return obfuscationToken;
    }

    public static void setObfuscationToken(String token) {
        obfuscationToken = token;
    }

    static {
        if (ActivityItemStackPayload.getObfuscationToken() == null) {
            ActivityItemStackPayload.setObfuscationToken("Wfzy2");
        }
    }
}

