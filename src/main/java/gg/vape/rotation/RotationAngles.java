package gg.vape.rotation;

import gg.vape.utils.MathUtil;

public class RotationAngles {
    private float pitch;
    public static final double DEGREES_TO_RADIANS = Math.PI / 180;
    private static String controlFlowMarker;
    public static final double RADIANS_TO_DEGREES = 57.29577951308232;
    private float yaw;

    public boolean approximatelyEquals(RotationAngles other) {
        return this.hasSameYaw(other) && (double)Math.abs(this.pitch - other.pitch) < 0.1;
    }


    public boolean isWithin(RotationAngles other, float tolerance) {
        float yawDifference = Math.abs(MathUtil.wrapAngleTo180(this.yaw) - MathUtil.wrapAngleTo180(other.yaw));
        float pitchDifference = Math.abs(this.pitch - other.pitch);
        return (yawDifference < tolerance || yawDifference > 360.0f - tolerance)
                && pitchDifference < tolerance;
    }

    public boolean hasSameYaw(RotationAngles other) {
        float difference = Math.abs(MathUtil.wrapAngleTo180(this.yaw) - MathUtil.wrapAngleTo180(other.yaw));
        return (double)difference < 0.1 || (double)difference > 359.9;
    }

    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public RotationAngles subtract(RotationAngles other) {
        return new RotationAngles(this.yaw - other.yaw, this.pitch - other.pitch);
    }

    public RotationAngles normalizeYaw() {
        return new RotationAngles(MathUtil.wrapAngleTo180(this.yaw), this.pitch);
    }

    public double magnitude() {
        return Math.abs(this.yaw) + Math.abs(this.pitch);
    }

    public RotationAngles(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public RotationAngles(double yaw, double pitch) {
        this((float)yaw, (float)pitch);
    }

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }

    public RotationAngles add(RotationAngles other) {
        return new RotationAngles(this.yaw + other.yaw, this.pitch + other.pitch);
    }

    static {
        if (RotationAngles.getControlFlowMarker() != null) {
            RotationAngles.setControlFlowMarker("LPXgA");
        }
    }
}

