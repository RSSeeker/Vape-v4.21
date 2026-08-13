package gg.vape.module.blatant.clutch;

import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.PlayerMouseRotationApplier;
import gg.vape.rotation.RotationAngles;
import gg.vape.wrapper.impl.EntityPlayer;

public class EntityFixedRotationController
extends FixedRotationController {
    private final EntityPlayer player;

    public EntityFixedRotationController(RotationAngles rotationAngles, EntityPlayer player) {
        super(rotationAngles);
        this.player = player;
    }

    @Override
    public void applyMouseDelta(float deltaX, float deltaY) {
        PlayerMouseRotationApplier.applyMouseDelta(this.player, deltaX, deltaY);
    }

    @Override
    public float getCurrentPitch() {
        return this.player.V();
    }

    @Override
    public float getCurrentYaw() {
        return this.player.J();
    }
}
