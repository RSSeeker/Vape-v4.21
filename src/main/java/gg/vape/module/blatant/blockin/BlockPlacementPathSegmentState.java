package gg.vape.module.blatant.blockin;

import gg.vape.Vape;
import gg.vape.module.utility.clutch.PlacementTarget;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.EnumFacing;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;
import org.jetbrains.annotations.NotNull;

public class BlockPlacementPathSegmentState {
    private static final String DEBUG_PREFIX = "isFirst: ";

    public EnumFacing placementFacing;
    public Vector<PlacementTarget> pendingTargets;
    private int expectedCount;
    public HashSet<Long> targetPositionKeys;

    public BlockPlacementPathSegmentState(@NotNull EnumFacing placementFacing,
                                          @NotNull Vector<PlacementTarget> pendingTargets) {
        this.placementFacing = placementFacing;
        this.pendingTargets = pendingTargets;
        this.targetPositionKeys = new HashSet<>();
        for (PlacementTarget target : pendingTargets) {
            this.targetPositionKeys.add(this.positionKey(target.getPlacedBlock()));
        }
        this.expectedCount = pendingTargets.size();
    }

    public boolean contains(BlockData blockData) {
        return this.targetPositionKeys.contains(this.positionKey(blockData));
    }

    private long positionKey(BlockData blockData) {
        return BlockPos.f(blockData.D(), blockData.B(), blockData.G());
    }

    public void addTargets(@NotNull Collection<PlacementTarget> targets) {
        this.pendingTargets.addAll(targets);
        for (PlacementTarget target : targets) {
            this.targetPositionKeys.add(this.positionKey(target.getPlacedBlock()));
        }
        this.expectedCount += targets.size();
    }

    public void clearPendingTargets() {
        this.pendingTargets.clear();
    }

    public boolean isComplete() {
        Vape.debugLog(DEBUG_PREFIX + this.pendingTargets.size() + " " + this.expectedCount);
        return this.pendingTargets.size() == this.expectedCount;
    }

    public boolean containsPosition(int x, int y, int z) {
        return this.targetPositionKeys.contains(BlockPos.f(x, y, z));
    }

    public int getExpectedCount() {
        return this.expectedCount;
    }
}
