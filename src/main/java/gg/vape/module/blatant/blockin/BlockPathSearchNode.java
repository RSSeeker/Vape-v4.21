package gg.vape.module.blatant.blockin;

import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.wrapper.impl.BlockPos;

public class BlockPathSearchNode {
    public double pathCost;
    public final double z;
    public BlockPathSearchNode parent;
    public final double y;
    public double heuristicCost;
    public final double x;

    public BlockPathSearchNode(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockCoordinate getBlockCoordinate() {
        return new BlockCoordinate(MathUtil.floor(this.x), MathUtil.floor(this.y), MathUtil.floor(this.z));
    }

    public double getTotalCost() {
        return this.pathCost + this.heuristicCost;
    }

    @Override
    public String toString() {
        return "X: " + this.x + " Y: " + this.y + " Z: " + this.z;
    }

    @Override
    public int hashCode() {
        return (int)BlockPos.f(MathUtil.floor(this.x), MathUtil.floor(this.y), MathUtil.floor(this.z));
    }

    @Override
    public boolean equals(Object candidate) {
        if (!(candidate instanceof BlockPathSearchNode)) {
            return false;
        }
        BlockPathSearchNode other = (BlockPathSearchNode)candidate;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }
}
