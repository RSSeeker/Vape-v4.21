package gg.vape.module.blatant.scaffold;

import gg.vape.module.blatant.Scaffold;
import gg.vape.rotation.PointRotationController;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.GuiScreen;

public class ScaffoldPointRotationController
extends PointRotationController {
    private final int direction;
    private boolean speedInitialized;
    private final double[] placementPosition;
    private final Scaffold scaffold;


    public ScaffoldPointRotationController(Scaffold scaffold, double targetX, double targetY,
                                           double targetZ, int direction,
                                           double[] placementPosition) {
        super(targetX, targetY, targetZ);
        this.scaffold = scaffold;
        this.direction = direction;
        this.placementPosition = placementPosition;
        this.speedInitialized = false;
    }

    @Override
    public void update(EntityPlayerSP player, GuiScreen currentScreen) {
        if (currentScreen.isNotNull()) {
            return;
        }
        this.updateTargetRotation();
        this.updatePitch();
        EnumFacing facing = this.scaffold.getFacingForDirection(this.direction);
        if (RotationUtil.p(facing, new BlockCoordinate(MathUtil.floor(this.placementPosition[0]), MathUtil.floor(this.placementPosition[1]), MathUtil.floor(this.placementPosition[2])))) {
            if (!this.speedInitialized) {
                this.setSpeed(this.scaffold.getDirectionRotationSpeed(this.direction));
                this.speedInitialized = true;
            }
            this.updateYaw();
        }
    }
}
