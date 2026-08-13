package gg.vape.utils;

import gg.vape.utils.Vec3d;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class ProjectilePitchUtil {
    private static final double DEFAULT_PROJECTILE_SPEED = 1.5;
    private static final double DEFAULT_GRAVITY = 0.03;

    public static float calculatePitch(Vec3d targetPosition, double projectileSpeed) {
        EntityPlayerSP player = Minecraft.thePlayer();
        double gravity = 0.05f;
        double deltaX = targetPosition.getX() - player.z();
        double deltaZ = targetPosition.getZ() - player.h();
        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double playerY = player.N();
        float eyeHeight = player.X();
        double boundingBoxMinY = player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY();
        double verticalDistance = targetPosition.getY() - player.U();
        double verticalTerm = 2.0 * verticalDistance * (projectileSpeed * projectileSpeed);
        double horizontalTerm = gravity * (horizontalDistance * horizontalDistance);
        double trajectoryTerm = horizontalTerm + verticalTerm;
        double speedFourthPower = projectileSpeed * projectileSpeed * projectileSpeed * projectileSpeed;
        double gravityTerm = gravity * trajectoryTerm;
        double discriminant = speedFourthPower - gravityTerm;
        double root = Math.sqrt(discriminant);
        double highNumerator = projectileSpeed * projectileSpeed + root;
        double lowNumerator = projectileSpeed * projectileSpeed - root;
        double highAngle = Math.atan2(highNumerator, gravity * horizontalDistance);
        double lowAngle = Math.atan2(lowNumerator, gravity * horizontalDistance);
        float pitch = (float)(-Math.toDegrees(Math.min(highAngle, lowAngle)));
        return pitch;
    }

    public static float calculatePitch(double sourceX, double sourceY, double sourceZ, double targetX, double targetY, double targetZ) {
        double verticalDistance;
        double selectedAngle;
        double speedSquared = DEFAULT_PROJECTILE_SPEED * DEFAULT_PROJECTILE_SPEED;
        double deltaX = targetX - sourceX;
        double deltaZ = targetZ - sourceZ;
        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double discriminant = speedSquared * speedSquared - DEFAULT_GRAVITY * (DEFAULT_GRAVITY * (horizontalDistance * horizontalDistance) + 2.0 * (verticalDistance = targetY - sourceY) * speedSquared);
        if (discriminant < 0.0) {
            return Float.NaN;
        }
        double root = Math.sqrt(discriminant);
        double highAngle = Math.atan((speedSquared + root) / (DEFAULT_GRAVITY * horizontalDistance));
        double lowAngle = selectedAngle = Math.atan((speedSquared - root) / (DEFAULT_GRAVITY * horizontalDistance));
        float currentPitch = Minecraft.thePlayer().V();
        double highPitch = Math.toDegrees(-highAngle);
        double lowPitch = Math.toDegrees(-selectedAngle);
        float highPitchDifference = (float)Math.abs(highPitch - (double)currentPitch);
        float lowPitchDifference = (float)Math.abs(lowPitch - (double)currentPitch);
        boolean preferHighTrajectory = false;
        float pitch = (float)Math.toDegrees(-lowAngle);
        return pitch;
    }

}

