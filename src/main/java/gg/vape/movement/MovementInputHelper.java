package gg.vape.movement;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayList;
import java.util.Arrays;

public class MovementInputHelper {
    public static void releaseAllInput() {
        releaseInput(true);
    }

    public static void applyDirectionalMovement(double targetOffsetX, double targetOffsetZ,
            ArrayList<KeyBinding> excludedKeys) {
        EntityPlayerSP player = Minecraft.thePlayer();
        float movementYaw = RotationUtil.c();
        float targetYaw = (float)Math.toDegrees(Math.atan2(targetOffsetZ, targetOffsetX)) - 90.0f;
        float relativeYaw = MathUtil.wrapAngleTo180(MathUtil.wrapAngleTo180(targetYaw) - movementYaw);
        float relativeYawRadians = relativeYaw * ((float)Math.PI / 180.0f);
        float forwardFactor = (float)Math.cos(relativeYawRadians);
        float strafeFactor = (float)Math.sin(relativeYawRadians);
        boolean moveForward = forwardFactor >= 0.0f;
        boolean moveRight = strafeFactor >= 0.0f;
        boolean moveLeft = strafeFactor <= 0.0f;
        boolean moveBackward = forwardFactor <= 0.0f;
        GameSettings settings = Minecraft.gameSettings();
        double movementStep = getMovementStep(player, settings.Y());

        double forwardInput = moveForward ? 1.0 : (moveBackward ? -1.0 : 0.0);
        double strafeInput = moveRight ? -1.0 : (moveLeft ? 1.0 : 0.0);
        float targetSin = MathUtil.sin(targetYaw * (float)Math.PI / 180.0f);
        float targetCos = MathUtil.cos(targetYaw * (float)Math.PI / 180.0f);
        double[] forwardDelta = new double[]{
                -((double)forwardFactor * forwardInput * movementStep) * targetSin,
                (double)forwardFactor * forwardInput * movementStep * targetCos
        };
        double[] projectedPosition = new double[]{player.z() + player.t(), player.h() + player.T()};
        double currentDistance = distanceToTargetAfterMove(player, projectedPosition,
                new double[]{0.0, 0.0}, targetOffsetX, targetOffsetZ);
        double forwardDistance = distanceToTargetAfterMove(player, projectedPosition,
                forwardDelta, targetOffsetX, targetOffsetZ);
        if (forwardDistance < currentDistance) {
            settings.Y().setPressed(moveForward && !excludedKeys.contains(settings.Y()));
            settings.s().setPressed(moveBackward && !excludedKeys.contains(settings.s()));
        }

        float strafeSin = MathUtil.sin((targetYaw + 90.0f) * (float)Math.PI / 180.0f);
        float strafeCos = MathUtil.cos((targetYaw + 90.0f) * (float)Math.PI / 180.0f);
        double[] strafeDelta = new double[]{
                (double)strafeFactor * strafeInput * movementStep * strafeCos,
                -((double)strafeFactor * strafeInput * movementStep) * strafeSin
        };
        double strafeDistance = distanceToTargetAfterMove(player, projectedPosition,
                strafeDelta, targetOffsetX, targetOffsetZ);
        Vape.debugLog("Vector " + strafeDelta[0] + " " + strafeDelta[1]
                + " | Potential Dist: " + strafeDistance + " | Final Dist: " + currentDistance
                + " | " + relativeYaw + " " + targetYaw + " " + EnumFacing.p(targetYaw)
                + " " + forwardFactor + " " + strafeFactor);
        if (strafeDistance < currentDistance) {
            KeyBinding leftKey = settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg();
            KeyBinding rightKey = settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3();
            leftKey.setPressed(moveLeft && !excludedKeys.contains(leftKey));
            rightKey.setPressed(moveRight && !excludedKeys.contains(rightKey));
        }
    }

    public static void setJumpPressed(boolean pressed) {
        synchronizeKeyState(Minecraft.gameSettings().O(), pressed);
    }

    public static void synchronizeKeyState(KeyBinding keyBinding, boolean pressed) {
        if (pressed) {
            if (!keyBinding.isKeyDown()) {
                setKeyPressed(keyBinding, true);
            }
        } else if (keyBinding.isKeyDown() || keyBinding.V() > 0) {
            setKeyPressed(keyBinding, false);
        }
    }

    public static void setKeyPressed(KeyBinding keyBinding, boolean pressed) {
        if (ForgeVersion.MC_1_16_5.d()) {
            KeyBinding.setKeyBindState(keyBinding, pressed);
            keyBinding.onTick(1);
            if (pressed) {
                KeyBinding.onTick(keyBinding);
            }
        } else if (pressed) {
            KeyBinding.setKeyBindState(keyBinding, true);
            KeyBinding.onTick(keyBinding);
        } else {
            keyBinding.e();
        }
    }

    public static void applyMovementToward(double targetOffsetX, double targetOffsetZ,
            ArrayList<KeyBinding> excludedKeys, boolean requireSupportedMovement) {
        EntityPlayerSP player = Minecraft.thePlayer();
        double movementYaw = RotationUtil.c();
        ArrayList<Double> candidateHeadings = new ArrayList<Double>(Arrays.asList(
                movementYaw, (movementYaw + 90.0) % 360.0,
                (movementYaw + 180.0) % 360.0, (movementYaw + 270.0) % 360.0));
        ArrayList<KeyBinding> candidateKeys = new ArrayList<KeyBinding>(Arrays.asList(
                Minecraft.gameSettings().Y(),
                Minecraft.gameSettings().g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3(),
                Minecraft.gameSettings().s(),
                Minecraft.gameSettings().x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg()));
        ArrayList<Boolean> selectedKeys = new ArrayList<Boolean>();
        for (KeyBinding excludedKey : excludedKeys) {
            candidateHeadings.remove(candidateKeys.indexOf(excludedKey));
            candidateKeys.remove(excludedKey);
        }

        double[] projectedPosition = new double[]{player.z() + player.t(), player.h() + player.T()};
        double bestDistance = distanceToTargetAfterMove(player, projectedPosition,
                new double[]{0.0, 0.0}, targetOffsetX, targetOffsetZ);
        for (int index = 0; index < candidateHeadings.size(); ++index) {
            double[] movementDelta = calculateMovementVector(player,
                    candidateHeadings.get(index), candidateKeys.get(index));
            double candidateDistance = distanceToTargetAfterMove(player, projectedPosition,
                    movementDelta, targetOffsetX, targetOffsetZ);
            if (candidateDistance < bestDistance) {
                selectedKeys.add(true);
                projectedPosition = new double[]{
                        projectedPosition[0] + movementDelta[0],
                        projectedPosition[1] + movementDelta[1]
                };
                bestDistance = candidateDistance;
            } else {
                selectedKeys.add(false);
            }
        }

        if (requireSupportedMovement && !hasSupportingCollision(player, projectedPosition)) {
            for (int index = 0; index < selectedKeys.size(); ++index) {
                selectedKeys.set(index, false);
            }
        }

        for (int index = 0; index < selectedKeys.size(); ++index) {
            KeyBinding key = candidateKeys.get(index);
            boolean shouldPress = selectedKeys.get(index);
            if (shouldPress && !key.isKeyDown()) {
                setKeyPressed(key, true);
            } else if (!shouldPress && key.isKeyDown()) {
                setKeyPressed(key, false);
            }
        }
    }

    private static boolean hasSupportingCollision(EntityPlayerSP player, double[] projectedPosition) {
        double originalX = player.z();
        double originalZ = player.h();
        player.H(projectedPosition[0]);
        player.l(projectedPosition[1]);
        AxisAlignedBB bounds = ForgeVersion.MC_1_8_9.d()
                ? player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl()
                : player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().copy();
        double verticalOffset = ForgeVersion.MC_1_20_6.d() ? 1.0 : -1.0;
        AxisAlignedBB supportBounds = bounds.expand(-0.15, 0.0, -0.15)
                .k(player.t(), verticalOffset, player.T());
        boolean supported = !Minecraft.theWorld().i(player, supportBounds).isEmpty();
        player.H(originalX);
        player.l(originalZ);
        return supported;
    }

    public static boolean isPhysicalMovementInputActive() {
        GameSettings settings = Minecraft.gameSettings();
        return ClientSettings.isPhysicalKeyDown(settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg())
                || ClientSettings.isPhysicalKeyDown(settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3())
                || ClientSettings.isPhysicalKeyDown(settings.Y()) || ClientSettings.isPhysicalKeyDown(settings.s());
    }

    public static void releaseMovementKeys() {
        GameSettings settings = Minecraft.gameSettings();
        setKeyPressed(settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg(), false);
        setKeyPressed(settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3(), false);
        setKeyPressed(settings.Y(), false);
        setKeyPressed(settings.s(), false);
    }

    public static void synchronizeDirectionalInput(boolean forward, boolean backward,
            boolean left, boolean right) {
        GameSettings settings = Minecraft.gameSettings();
        ArrayList<KeyBinding> keys = new ArrayList<KeyBinding>(Arrays.asList(
                settings.Y(), settings.s(),
                settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg(),
                settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3()));
        ArrayList<Boolean> states = new ArrayList<Boolean>(Arrays.asList(forward, backward, left, right));
        for (int index = 0; index < states.size(); ++index) {
            synchronizeKeyState(keys.get(index), states.get(index));
        }
    }

    public static void restorePhysicalInput() {
        restorePhysicalInput(true);
    }

    public static void restorePhysicalInput(boolean includeSprint) {
        GameSettings settings = Minecraft.gameSettings();
        KeyBinding leftKey = settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg();
        KeyBinding rightKey = settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3();
        KeyBinding forwardKey = settings.Y();
        KeyBinding backwardKey = settings.s();
        KeyBinding sprintKey = settings.r();
        KeyBinding sneakKey = settings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0();
        KeyBinding jumpKey = settings.O();
        setKeyPressed(leftKey, ClientSettings.isPhysicalKeyDown(leftKey));
        setKeyPressed(rightKey, ClientSettings.isPhysicalKeyDown(rightKey));
        setKeyPressed(forwardKey, ClientSettings.isPhysicalKeyDown(forwardKey));
        setKeyPressed(backwardKey, ClientSettings.isPhysicalKeyDown(backwardKey));
        if (includeSprint) {
            setKeyPressed(sprintKey, ClientSettings.isPhysicalKeyDown(sprintKey));
        }
        setKeyPressed(sneakKey, ClientSettings.isPhysicalKeyDown(sneakKey));
        setKeyPressed(jumpKey, ClientSettings.isPhysicalKeyDown(jumpKey));
    }

    public static float getMovementYaw(EntityPlayer player) {
        double motionX = player.t();
        double motionZ = player.T();
        double yaw = Math.toDegrees(Math.atan2(motionZ, motionX));
        if (motionX != 0.0 || motionZ != 0.0) {
            yaw = MathUtil.wrapAngleTo180(yaw - 90.0);
        }
        return (float)(yaw - 180.0);
    }

    public static double[] calculateMovementVector(EntityPlayerSP player, double heading,
            KeyBinding keyBinding) {
        double step = getMovementStep(player, keyBinding);
        double radians = Math.toRadians(heading);
        return new double[]{step * -Math.sin(radians), step * Math.cos(radians)};
    }

    public static double distanceToTargetAfterMove(Entity entity, double[] currentPosition,
            double[] movementDelta, double targetOffsetX, double targetOffsetZ) {
        double targetX = entity.z() + targetOffsetX;
        double targetZ = entity.h() + targetOffsetZ;
        double candidateX = currentPosition[0] + movementDelta[0];
        double candidateZ = currentPosition[1] + movementDelta[1];
        return RotationUtil.y(targetX, 0.0, targetZ, candidateX, 0.0, candidateZ);
    }

    private static double getMovementStep(Entity entity, KeyBinding keyBinding) {
        double step = 0.2;
        if (entity.P() && entity.b$src$Z$fqlxe4()) {
            step = 0.06;
        } else if (entity.B$src$Z$f90iek() && keyBinding.equals(Minecraft.gameSettings().Y())) {
            step = 0.3;
        }
        if (!entity.b$src$Z$fqlxe4()) {
            step *= 0.02;
        }
        return step;
    }

    public static void releaseInput(boolean includeSprint) {
        GameSettings settings = Minecraft.gameSettings();
        setKeyPressed(settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg(), false);
        setKeyPressed(settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3(), false);
        setKeyPressed(settings.Y(), false);
        setKeyPressed(settings.s(), false);
        if (includeSprint) {
            setKeyPressed(settings.r(), false);
        }
        setKeyPressed(settings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0(), false);
        setKeyPressed(settings.O(), false);
    }
}
