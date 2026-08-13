package gg.vape.module.blatant.blockin;

import gg.vape.module.blatant.blockin.BlockPlacementGraph;

public interface BlockInMovementController {
    void setInput(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean sneak);

    void applySnapshot(BlockPlacementGraph snapshot);

    void tick();

    void initialize();
}
