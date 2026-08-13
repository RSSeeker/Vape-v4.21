package gg.vape.module.utility.autopearl;

import gg.vape.module.utility.AutoPearl;
import gg.vape.rotation.PointRotationController;
import gg.vape.wrapper.impl.Vec3;

public class AutoPearlPointRotationController
extends PointRotationController {
    final Float pitch;
    final AutoPearl module;

    public AutoPearlPointRotationController(AutoPearl autoPearl, Vec3 vec3, Float pitch) {
        super(vec3);
        this.module = autoPearl;
        this.pitch = pitch;
    }

    @Override
    public void setTargetRotation(float yaw, float pitch) {
        super.setTargetRotation(yaw, this.pitch.floatValue());
    }
}
