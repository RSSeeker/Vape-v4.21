package gg.vape.rotation;

import gg.vape.rotation.MouseRotationController;
import gg.vape.rotation.WorldPointRotationTarget;
import gg.vape.utils.RotationUtil;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Vec3;

public class WorldPointMouseRotationController
extends MouseRotationController
implements WorldPointRotationTarget {
    private double targetZ;
    private double targetY;
    private double targetX;

    public void setTargetY(double targetY) {
        this.targetY = targetY;
    }

    public WorldPointMouseRotationController(double targetX, double targetY, double targetZ) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    public void setTargetX(double targetX) {
        this.targetX = targetX;
    }

    @Override
    public boolean updateYaw() {
        double yawDifference = RotationUtil.T(Minecraft.thePlayer(), this.targetX, this.targetZ);
        if (yawDifference > (double)this.tolerance) {
            this.pendingYawDelta = RotationUtil.k(Minecraft.thePlayer(), this.targetX, this.targetZ)
                    ? (this.pendingYawDelta -= this.getSpeed()) : (this.pendingYawDelta += this.getSpeed());
            return false;
        }
        return true;
    }


    public double getTargetZ() {
        return this.targetZ;
    }

    @Override
    public void setTarget(Vec3 target) {
        this.targetX = target.getX();
        this.targetY = target.getY();
        this.targetZ = target.getZ();
    }

    @Override
    public Vec3 getTarget() {
        return Vec3.create(this.getTargetX(), this.getTargetY(), this.getTargetZ());
    }

    public double getTargetX() {
        return this.targetX;
    }

    @Override
    public boolean updatePitch() {
        double pitchDifference = RotationUtil.H(Minecraft.thePlayer(), this.targetX, this.targetY, this.targetZ);
        if (pitchDifference > (double)this.tolerance || pitchDifference < (double)(-this.tolerance)) {
            this.pendingPitchDelta = RotationUtil.h$src$Z$kozqzr(Minecraft.thePlayer(), this.targetX, this.targetY, this.targetZ)
                    ? (this.pendingPitchDelta += this.getSpeed()) : (this.pendingPitchDelta -= this.getSpeed());
            return false;
        }
        return true;
    }

    public double getTargetY() {
        return this.targetY;
    }

    public void setTargetZ(double targetZ) {
        this.targetZ = targetZ;
    }
}

