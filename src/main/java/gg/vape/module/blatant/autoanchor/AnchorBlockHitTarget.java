package gg.vape.module.blatant.autoanchor;

import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.EnumFacing;

public class AnchorBlockHitTarget {
    private final BlockPos hitPos;
    private final EnumFacing hitFace;

    public EnumFacing getHitFace() {
        return this.hitFace;
    }

    public AnchorBlockHitTarget(BlockPos hitPos, EnumFacing hitFace) {
        this.hitPos = hitPos;
        this.hitFace = hitFace;
    }

    public BlockPos getHitPos() {
        return this.hitPos;
    }
}

