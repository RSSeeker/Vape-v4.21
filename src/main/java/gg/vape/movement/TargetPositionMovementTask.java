package gg.vape.movement;

import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class TargetPositionMovementTask
extends PlayerMovementTask {
    public double targetX;
    public double targetZ;


    public void setTarget(double targetX, double targetZ) {
        this.targetX = targetX;
        this.targetZ = targetZ;
    }

    @Override
    public boolean hasReachedTarget() {
        EntityPlayerSP player = Minecraft.thePlayer();
        this.remainingX = this.shouldIgnoreX() ? 0.0 : this.targetX - player.z();
        this.remainingZ = this.shouldIgnoreZ() ? 0.0 : this.targetZ - player.h();
        if (this.shouldSneakNearTarget()) {
            return Math.abs(this.remainingX) <= 0.1 && Math.abs(this.remainingZ) <= 0.1;
        }
        return Math.abs(this.remainingX) <= this.getCompletionTolerance()
                && Math.abs(this.remainingZ) <= this.getCompletionTolerance();
    }

    public TargetPositionMovementTask(double targetX, double targetZ) {
        this.targetX = targetX;
        this.targetZ = targetZ;
    }
}

