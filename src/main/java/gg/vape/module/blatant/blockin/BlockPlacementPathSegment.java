package gg.vape.module.blatant.blockin;

import gg.vape.utils.Vec3d;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.wrapper.impl.Vec3;
import java.util.ArrayList;
import java.util.Vector;

public class BlockPlacementPathSegment {
    private static final String LEFT_SUFFIX = " (Left: ";

    public final BlockCoordinate startCoordinate;
    public final ArrayList<Vec3d> simulatedPositions;
    public BlockCoordinate targetCoordinate;
    public String failureReason;
    public final Vector<BlockInTargetRotationState> rotationStates = new Vector<>();
    public int simulationTickLimit;
    public Vec3 hitVector;
    public BlockPlacementPathSegmentState placementState;

    public BlockPlacementPathSegment(BlockCoordinate startCoordinate, BlockCoordinate targetCoordinate,
                                     ArrayList<Vec3d> simulatedPositions) {
        this.startCoordinate = startCoordinate;
        this.targetCoordinate = targetCoordinate;
        this.simulatedPositions = simulatedPositions;
    }

    public int getPendingPlacementCount() {
        return this.placementState != null ? this.placementState.pendingTargets.size() : 0;
    }

    public boolean hasFailed() {
        return this.failureReason != null;
    }

    public int getExpectedPlacementCount() {
        return this.placementState != null ? this.placementState.getExpectedCount() : 0;
    }

    public void fail(String reason) {
        if (this.failureReason == null) {
            this.failureReason = reason + LEFT_SUFFIX + this.getPendingPlacementCount() + ")";
            this.hitVector = null;
            this.placementState = null;
        }
    }
}
