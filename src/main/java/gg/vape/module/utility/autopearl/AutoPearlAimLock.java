package gg.vape.module.utility.autopearl;

import gg.vape.module.utility.autopearl.AutoPearlRotationController;
import gg.vape.module.utility.autopearl.AutoPearlTrackedPearl;
import gg.vape.rotation.FixedRotationController;
import gg.vape.wrapper.impl.EntityEnderPearl;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.Vec3;
import org.jetbrains.annotations.Nullable;

public class AutoPearlAimLock
extends AutoPearlTrackedPearl {
    @Nullable
    private Vec3 landingPos = null;
    private final FixedRotationController rotationController;

    public Vec3 getLandingPosition() {
        return this.landingPos;
    }

    public FixedRotationController getRotationController() {
        return this.rotationController;
    }

    public AutoPearlAimLock(EntityEnderPearl pearl, EntityPlayer owner, FixedRotationController rotationController, Vec3 landingPosition, AutoPearlRotationController ignoredController) {
        this(pearl, owner, rotationController, landingPosition);
    }

    public void setLandingPosition(Vec3 landingPosition) {
        this.landingPos = landingPosition;
    }

    private AutoPearlAimLock(EntityEnderPearl pearl, EntityPlayer owner, FixedRotationController rotationController, Vec3 landingPosition) {
        this(pearl, owner, rotationController);
        this.setLandingPosition(landingPosition);
    }

    private AutoPearlAimLock(EntityEnderPearl pearl, EntityPlayer owner, FixedRotationController rotationController) {
        super(pearl, owner, null);
        this.rotationController = rotationController;
    }
}

