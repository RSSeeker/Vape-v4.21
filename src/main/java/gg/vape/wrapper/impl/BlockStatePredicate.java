package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockState;

public class BlockStatePredicate
extends BlockState {
    public BlockStatePredicate(Object object) {
        super(object);
    }

    public boolean w(Block px_12) {
        return this.getBlock().equals(px_12);
    }
}

