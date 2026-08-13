package gg.vape.friend.activity;

import gg.vape.protocol.ZeusPacketBuffer;
import java.util.UUID;

public class ActivityTargetData {
    private final UUID uuid;
    private final String name;

    public UUID getUuid() {
        return this.uuid;
    }

    public ActivityTargetData(ZeusPacketBuffer buffer) {
        this.uuid = buffer.readUuid();
        this.name = buffer.readString(16);
    }

    public ActivityTargetData(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public void writeTo(ZeusPacketBuffer buffer) {
        buffer.writeUuid(this.uuid);
        buffer.writeString(this.name);
    }

    public String getName() {
        return this.name;
    }
}

