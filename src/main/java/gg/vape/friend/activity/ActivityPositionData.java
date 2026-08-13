package gg.vape.friend.activity;

import gg.vape.protocol.ZeusPacketBuffer;

public class ActivityPositionData {
    private final double x;
    private final double y;
    private final double z;

    ActivityPositionData(ZeusPacketBuffer buffer) {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    public void writeTo(ZeusPacketBuffer buffer) {
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);
    }

    public double getZ() {
        return this.z;
    }

    public double getY() {
        return this.y;
    }

    public double getX() {
        return this.x;
    }

    public ActivityPositionData(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

