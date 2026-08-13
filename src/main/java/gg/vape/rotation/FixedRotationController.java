package gg.vape.rotation;

import gg.vape.event.impl.EventPreEntityRendererMouseUpdate;
import gg.vape.rotation.MouseRotationController;
import gg.vape.rotation.RotationAngles;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class FixedRotationController
extends MouseRotationController {
    public float targetPitch;
    private boolean clampStepToRemaining;
    public float targetYaw;
    private boolean scaleAxesProportionally;
    private boolean restoreCapturedRotation;
    private boolean cubicAcceleration;
    private boolean linearAcceleration;
    private boolean angleBasedAcceleration;
    public static final float UNSET_ROTATION = -999.0f;

    public boolean isAxisScalingEnabled() {
        return this.scaleAxesProportionally;
    }

    public void setRestoreCapturedRotation(boolean restoreCapturedRotation) {
        this.restoreCapturedRotation = restoreCapturedRotation;
    }

    public boolean isCubicAccelerationEnabled() {
        return this.cubicAcceleration;
    }

    public void setAngleBasedAcceleration(boolean enabled) {
        this.angleBasedAcceleration = enabled;
    }

    public boolean isLinearAccelerationEnabled() {
        return this.linearAcceleration;
    }

    public float getTargetYaw() {
        return this.targetYaw;
    }

    public FixedRotationController(RotationAngles rotationAngles) {
        this.targetYaw = rotationAngles.getYaw();
        this.targetPitch = rotationAngles.getPitch();
    }

    public void setCubicAcceleration(boolean enabled) {
        this.cubicAcceleration = enabled;
    }

    public float getCurrentPitch() {
        return Minecraft.F().V();
    }

    public void setClampStepToRemaining(boolean enabled) {
        this.clampStepToRemaining = enabled;
    }

    public boolean shouldRestoreCapturedRotation() {
        return this.restoreCapturedRotation;
    }

    @Override
    public boolean updateYaw() {
        if (this.targetYaw == UNSET_ROTATION) {
            return true;
        }
        float mouseScale = this.getMouseScale();
        float predictedYaw = this.getCurrentYaw() + (float)(int)this.pendingYawDelta * mouseScale * 0.15f;
        float predictedPitch = this.getCurrentPitch() - (float)(int)(-this.pendingPitchDelta) * mouseScale * 0.15f;
        double yawError = MathUtil.wrapAngleTo180((this.targetYaw - predictedYaw) % 360.0f);
        double pitchError = MathUtil.wrapAngleTo180((this.targetPitch - predictedPitch) % 360.0f);
        double absoluteYawError = Math.abs(yawError);
        if (!this.isOutsideTolerance(absoluteYawError)) {
            return true;
        }
        double step = this.getSpeed() * 0.25;
        double axisRatio = absoluteYawError / Math.abs(pitchError);
        if (this.scaleAxesProportionally && axisRatio < 1.0) {
            step *= axisRatio;
        }
        step = this.applyYawAcceleration(step, absoluteYawError);
        this.pendingYawDelta = this.addPendingStep(this.pendingYawDelta, yawError, step);
        return false;
    }

    @Override
    public void onPreMouseUpdate(EventPreEntityRendererMouseUpdate event) {
        if (this.shouldRestoreCapturedRotation()) {
            EntityPlayerSP player = event.getThePlayer();
            if (player.J() != this.savedYaw) {
                player.H(this.savedYaw);
            }
            if (player.j() != this.savedPreviousYaw) {
                player.D(this.savedPreviousYaw);
            }
            if (player.V() != this.savedPitch) {
                player.C(this.savedPitch);
            }
            if (player.D() != this.savedPreviousPitch) {
                player.l(this.savedPreviousPitch);
            }
        }
    }

    public void setTargetRotation(float yaw, float pitch) {
        this.targetYaw = yaw;
        this.targetPitch = pitch;
        this.setComplete(false);
    }

    @Override
    public boolean updatePitch() {
        if (this.targetPitch == UNSET_ROTATION) {
            return true;
        }
        float currentPitch = this.getCurrentPitch() == -90.0f ? -89.99f : this.getCurrentPitch();
        float mouseScale = this.getMouseScale();
        float predictedYaw = this.getCurrentYaw() + (float)(int)this.pendingYawDelta * mouseScale * 0.15f;
        float predictedPitch = currentPitch - (float)(int)(-this.pendingPitchDelta) * mouseScale * 0.15f;
        double yawError = MathUtil.wrapAngleTo180((this.targetYaw - predictedYaw) % 360.0f);
        double pitchError = MathUtil.wrapAngleTo180((this.targetPitch - predictedPitch) % 360.0f);
        double absolutePitchError = Math.abs(pitchError);
        if (!this.isOutsideTolerance(absolutePitchError)) {
            return true;
        }
        double step = this.getSpeed() * 0.25;
        double axisRatio = absolutePitchError / Math.abs(yawError);
        if (this.scaleAxesProportionally && axisRatio < 1.0) {
            step *= axisRatio;
        }
        step = this.applyPitchAcceleration(step, absolutePitchError);
        this.pendingPitchDelta = this.addPendingStep(this.pendingPitchDelta, pitchError, step);
        return false;
    }

    protected float getMouseScale() {
        float sensitivityBase = RotationManager.INSTANCE.getMouseSensitivity() * 0.6f + 0.2f;
        return sensitivityBase * sensitivityBase * sensitivityBase * 8.0f;
    }

    protected boolean isOutsideTolerance(double absoluteError) {
        float rotationPerStep = this.getMouseScale() * 0.15f;
        return Math.round(absoluteError / rotationPerStep) > Math.max(Math.round(this.tolerance / rotationPerStep), 0L);
    }

    protected double applyYawAcceleration(double step, double absoluteError) {
        if (this.angleBasedAcceleration) {
            return step * (225.0 + absoluteError) / 180.0;
        }
        if (this.linearAcceleration) {
            return step + absoluteError * 0.05;
        }
        if (!this.cubicAcceleration) {
            return step;
        }
        double normalizedError = absoluteError / 100.0;
        double shiftedError = normalizedError + 0.7;
        double multiplier = 0.4 + 2.0 * Math.pow(shiftedError, 3.0) + Math.pow(shiftedError, 2.0);
        return step * Math.min(Math.max(1.0, multiplier), 4.0);
    }

    protected double applyPitchAcceleration(double step, double absoluteError) {
        if (this.angleBasedAcceleration) {
            return step * (135.0 + absoluteError) / 90.0;
        }
        if (this.linearAcceleration) {
            return step + absoluteError * 0.05;
        }
        if (!this.cubicAcceleration) {
            return step;
        }
        double normalizedError = absoluteError / 75.0;
        double shiftedError = normalizedError + 0.7;
        double multiplier = 0.4 + 2.0 * Math.pow(shiftedError, 3.0) + Math.pow(shiftedError, 2.0);
        return step * Math.max(1.0, multiplier);
    }

    protected float addPendingStep(float pendingDelta, double error, double step) {
        if (!this.clampStepToRemaining) {
            return (float)(error > 0.0 ? pendingDelta + step : pendingDelta - step);
        }
        double remainingSteps = Math.abs(error / (this.getMouseScale() * 0.15f));
        double appliedStep = Math.min(step, remainingSteps);
        return (float)(error > 0.0 ? pendingDelta + appliedStep : pendingDelta - appliedStep);
    }

    public void setTargetRotation(RotationAngles rotationAngles) {
        this.setTargetRotation(rotationAngles.getYaw(), rotationAngles.getPitch());
    }

    public FixedRotationController(float yaw, float pitch) {
        this.targetYaw = yaw;
        this.targetPitch = pitch;
    }

    public float getTargetPitch() {
        return this.targetPitch;
    }

    public boolean isStepClampedToRemaining() {
        return this.clampStepToRemaining;
    }

    public float getCurrentYaw() {
        return Minecraft.F().J();
    }

    public void setLinearAcceleration(boolean enabled) {
        this.linearAcceleration = enabled;
    }


    public void setScaleAxesProportionally(boolean enabled) {
        this.scaleAxesProportionally = enabled;
    }

    public boolean isAngleBasedAccelerationEnabled() {
        return this.angleBasedAcceleration;
    }
}
