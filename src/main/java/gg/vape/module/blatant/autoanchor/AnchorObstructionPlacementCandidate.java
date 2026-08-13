package gg.vape.module.blatant.autoanchor;

import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.EnumFacing;

public class AnchorObstructionPlacementCandidate {
    private final BlockData block;
    private final BlockData obstructionBlock;
    private final EnumFacing facing;

    public AnchorObstructionPlacementCandidate(BlockData block, BlockData obstructionBlock,
                                               EnumFacing facing) {
        this.block = block;
        this.obstructionBlock = obstructionBlock;
        this.facing = facing;
    }

    public EnumFacing getFacing() {
        return this.facing;
    }

    public BlockData getBlock() {
        return this.block;
    }

    public BlockData getObstructionBlock() {
        return this.obstructionBlock;
    }
}

