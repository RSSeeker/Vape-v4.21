package gg.vape.module.utility.clutch;

import gg.vape.module.blatant.blockin.BlockPlacementPathSegmentState;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.EnumFacing;
import java.util.ArrayList;
import java.util.HashSet;

public class BlockPlacementNode {
    public final ArrayList<BlockPlacementPathSegmentState> candidatePaths = new ArrayList<>();
    public final ArrayList<EnumFacing> blockedBaseFacings = new ArrayList<>();
    public final ArrayList<EnumFacing> blockedSupportFacings = new ArrayList<>();
    public EnumFacing baseFacing;
    public final HashSet<BlockData> occupiedBlocks = new HashSet<>();
    public final BlockData supportBlock;
    public final EnumFacing supportFacing;
    public int retryState = 0;
    public final BlockData baseBlock;

    public void blockSupportFacing(EnumFacing facing) {
        this.blockedSupportFacings.add(EnumFacing.t()[facing.Y()]);
        if (this.supportBlock != null) {
            this.occupiedBlocks.add(this.supportBlock.R(facing));
        }
    }

    public BlockPlacementNode(BlockData blockData, EnumFacing enumFacing, boolean useSupportBlock) {
        this.baseBlock = blockData;
        this.supportFacing = enumFacing;
        this.supportBlock = useSupportBlock ? blockData.y(0, 1, 0) : null;
    }


    public boolean clearCandidatePaths() {
        this.candidatePaths.forEach(BlockPlacementPathSegmentState::clearPendingTargets);
        this.candidatePaths.clear();
        return true;
    }

    public boolean isBaseFacingBlocked(EnumFacing facing) {
        return this.blockedBaseFacings.contains(EnumFacing.t()[facing.Y()]);
    }

    public void blockBaseFacing(EnumFacing facing) {
        this.blockedBaseFacings.add(EnumFacing.t()[facing.Y()]);
        this.occupiedBlocks.add(this.baseBlock.R(facing));
    }

    public BlockPlacementNode(BlockData blockData) {
        this(blockData, null, false);
    }

    public boolean hasSupportBlock() {
        return this.supportBlock != null;
    }

    public BlockPlacementNode(BlockData blockData, EnumFacing enumFacing) {
        this(blockData, enumFacing, true);
    }

    public boolean isSupportFacingBlocked(EnumFacing facing) {
        return this.blockedSupportFacings.contains(EnumFacing.t()[facing.Y()]);
    }
}

