package gg.vape.utils;

import gg.vape.utils.Vec3d;

public class EntityMotionDeviationUtil {
    public static final double LEGACY_DEVIATION_THRESHOLD = 2.6;

    public static double calculateDeviation(Vec3d sourcePosition, double sourceYOffset, Vec3d direction, double targetX, double targetY, double targetZ, double horizontalSize, double verticalSize, double padding) {
        return EntityMotionDeviationUtil.calculateDeviation(sourcePosition.getX(), sourcePosition.getY() + sourceYOffset, sourcePosition.getZ(), direction.getX(), direction.getY(), direction.getZ(), targetX, targetY, targetZ, horizontalSize, verticalSize, padding);
    }

    public static double calculateDeviation(double sourceX, double sourceY, double sourceZ, double directionX, double directionY, double directionZ, double targetX, double targetY, double targetZ, double horizontalSize, double verticalSize, double padding) {
        double directionLength = Math.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
        if (directionLength == 0.0) {
            directionLength = 1.0;
        }
        double deltaX = targetX - sourceX;
        double deltaY = targetY - sourceY;
        double deltaZ = targetZ - sourceZ;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        double projectedX = distance * directionX / directionLength;
        double projectedY = distance * directionY / directionLength;
        double projectedZ = distance * directionZ / directionLength;
        double deviation = 0.0;
        deviation += Math.max(Math.abs(deltaX - projectedX) - (horizontalSize / 2.0 + padding), 0.0);
        deviation += Math.max(Math.abs(deltaZ - projectedZ) - (horizontalSize / 2.0 + padding), 0.0);
        if ((deviation += Math.max(Math.abs(deltaY - projectedY) - (verticalSize / 2.0 + padding), 0.0)) > 1.0) {
            deviation = Math.sqrt(deviation);
        }
        return deviation;
    }
}
