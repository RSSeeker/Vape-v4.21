package gg.vape.module.blatant.clutch;

import gg.vape.module.blatant.Clutch;
import gg.vape.rotation.ThresholdFixedRotationController;
import gg.vape.wrapper.impl.EntityPlayerSP;

public class ClutchThresholdRotationController
extends ThresholdFixedRotationController {
    private final Clutch clutch;

    @Override
    public void setComplete(boolean complete) {
        super.setComplete(complete);
        if (complete) {
            if (this.clutch.getRotationClaim().release(this.clutch)) {
                // empty if block
            }
            this.clutch.setSavedYaw(-999.0);
        }
    }


    public ClutchThresholdRotationController(Clutch clutch, EntityPlayerSP player,
                                             float targetYaw, float targetPitch) {
        super(player, targetYaw, targetPitch);
        this.clutch = clutch;
    }
}
