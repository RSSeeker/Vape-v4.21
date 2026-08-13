package gg.vape.rotation;

import gg.vape.rotation.FixedRotationController;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.GuiScreen;

public class ThresholdFixedRotationController
extends FixedRotationController {
    final double yawThreshold;
    final double pitchThreshold;

    @Override
    public void update(EntityPlayerSP player, GuiScreen screen) {
        if (screen.isNotNull()) {
            return;
        }
        boolean yawThresholdReached = false;
        boolean pitchThresholdReached = false;
        if ((double)this.getYawDistance() >= this.yawThreshold) {
            yawThresholdReached = true;
        }
        if ((double)this.getPitchDistance() >= this.pitchThreshold) {
            pitchThresholdReached = true;
        }
        boolean yawAligned = false;
        boolean pitchAligned = false;
        if (!yawThresholdReached && (yawAligned = this.updateYaw())) {
            yawThresholdReached = true;
        }
        if (!pitchThresholdReached && (pitchAligned = this.updatePitch())) {
            pitchThresholdReached = true;
        }
        if (yawAligned && pitchAligned && Math.abs(this.pendingYawDelta) < 1.0f
                && Math.abs(this.pendingPitchDelta) < 1.0f) {
            this.setComplete(true);
        }
        if (yawThresholdReached && pitchThresholdReached) {
            this.setComplete(true);
        }
    }

    public ThresholdFixedRotationController(EntityPlayerSP player, float yawDelta, float pitchDelta) {
        super(player.J() - yawDelta, player.V() - pitchDelta);
        this.yawThreshold = Math.abs(yawDelta);
        this.pitchThreshold = Math.abs(pitchDelta);
    }

}

