package gg.vape.module.utility.clutch;

import gg.vape.utils.datas.BlockData;
import java.util.Vector;

public interface BlockPathSearchStrategy<T> {
    int getMaxDepth();

    default boolean canVisit(BlockData blockData) {
        return true;
    }

    boolean isValidBlock(BlockData blockData);

    default int scorePath(Vector<T> path, int depth) {
        return this.scorePath(path);
    }

    default int scorePath(Vector<T> path) {
        return path.size();
    }
}
