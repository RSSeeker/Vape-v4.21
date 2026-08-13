package gg.vape.module.utility.clutch;

import gg.vape.module.utility.BlockIn;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.World;
import java.util.Vector;

public class ClutchSolidBlockPathSearchStrategy
implements BlockPathSearchStrategy<BlockPlacementNode> {
    final World world;
    final BlockIn blockIn;

    @Override
    public int scorePath(Vector<BlockPlacementNode> path) {
        return this.blockIn.computePathCost(this.world, path);
    }

    @Override
    public boolean isValidBlock(BlockData blockData) {
        Block block = this.world.getBlockByPos(blockData.D(), blockData.B(), blockData.G());
        return BlockUtil.f(block);
    }

    public ClutchSolidBlockPathSearchStrategy(BlockIn blockIn, World world) {
        this.blockIn = blockIn;
        this.world = world;
    }

    @Override
    public int getMaxDepth() {
        return 4;
    }
}

