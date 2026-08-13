package gg.vape.friend.activity;

import gg.vape.protocol.ZeusPacketBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityHealthData {
    private final float absorptionAmount;
    private final float maxHealth;
    private final int hurtTime;
    private final Map<Short, Integer> potionAmplifiers;
    private final float health;

    public int getHurtTime() {
        return this.hurtTime;
    }

    public float getHealth() {
        return this.health;
    }

    public float getMaxHealth() {
        return this.maxHealth;
    }

    public Map<Short, Integer> getPotionAmplifiers() {
        return this.potionAmplifiers;
    }

    public ActivityHealthData(float health, float maxHealth, float absorptionAmount, int hurtTime, Map<Short, Integer> potionAmplifiers) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.absorptionAmount = absorptionAmount;
        this.hurtTime = hurtTime;
        this.potionAmplifiers = potionAmplifiers;
    }

    ActivityHealthData(ZeusPacketBuffer buffer) {
        this.health = buffer.readFloat();
        this.maxHealth = buffer.readFloat();
        this.absorptionAmount = buffer.readFloat();
        this.hurtTime = buffer.readInt();
        this.potionAmplifiers = new LinkedHashMap<Short, Integer>();
        int potionCount = buffer.readVarInt();
        for (int index = 0; index < potionCount; ++index) {
            short potionId = buffer.readShort();
            int amplifier = buffer.readVarInt();
            this.potionAmplifiers.put(potionId, amplifier);
        }
    }

    public void writeTo(ZeusPacketBuffer buffer) {
        buffer.writeFloat(this.health);
        buffer.writeFloat(this.maxHealth);
        buffer.writeFloat(this.absorptionAmount);
        buffer.writeInt(this.hurtTime);
        buffer.writeVarInt(this.potionAmplifiers.size());
        for (Map.Entry<Short, Integer> entry : this.potionAmplifiers.entrySet()) {
            buffer.writeShort(entry.getKey());
            buffer.writeVarInt(entry.getValue());
        }
    }

    public float getAbsorptionAmount() {
        return this.absorptionAmount;
    }
}

