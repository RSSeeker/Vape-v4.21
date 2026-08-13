package gg.vape.friend.activity;

import gg.vape.friend.activity.ActivityHealthData;
import gg.vape.friend.activity.ActivityPositionData;
import gg.vape.friend.activity.ActivityTargetData;
import gg.vape.protocol.ZeusPacketBuffer;
import org.jetbrains.annotations.Nullable;

public class ActivitySnapshotPayload {
    @Nullable
    private ActivityTargetData target;
    @Nullable
    private ActivityHealthData health;
    @Nullable
    private ActivityPositionData position;

    @Nullable
    public ActivityPositionData getPosition() {
        return this.position;
    }

    public ActivitySnapshotPayload(ZeusPacketBuffer zeusPacketBuffer) {
        if (zeusPacketBuffer.readBoolean()) {
            this.position = new ActivityPositionData(zeusPacketBuffer);
        }
        if (zeusPacketBuffer.readBoolean()) {
            this.health = new ActivityHealthData(zeusPacketBuffer);
        }
        if (zeusPacketBuffer.readBoolean()) {
            this.target = new ActivityTargetData(zeusPacketBuffer);
        }
    }

    @Nullable
    public ActivityHealthData getHealth() {
        return this.health;
    }

    public void writeTo(ZeusPacketBuffer zeusPacketBuffer) {
        ZeusPacketBuffer positionBuffer = zeusPacketBuffer;
        boolean hasPosition = this.position != null;
        positionBuffer.writeBoolean(hasPosition);
        if (this.position != null) {
            this.position.writeTo(zeusPacketBuffer);
        }
        ZeusPacketBuffer healthBuffer = zeusPacketBuffer;
        boolean hasHealth = this.health != null;
        healthBuffer.writeBoolean(hasHealth);
        if (this.health != null) {
            this.health.writeTo(zeusPacketBuffer);
        }
        ZeusPacketBuffer targetBuffer = zeusPacketBuffer;
        boolean hasTarget = this.target != null;
        targetBuffer.writeBoolean(hasTarget);
        if (this.target != null) {
            this.target.writeTo(zeusPacketBuffer);
        }
    }


    @Nullable
    public ActivityTargetData getTarget() {
        return this.target;
    }

    public ActivitySnapshotPayload(@Nullable ActivityPositionData activityPositionData, @Nullable ActivityHealthData activityHealthData, @Nullable ActivityTargetData activityTargetData) {
        this.position = activityPositionData;
        this.health = activityHealthData;
        this.target = activityTargetData;
    }
}

