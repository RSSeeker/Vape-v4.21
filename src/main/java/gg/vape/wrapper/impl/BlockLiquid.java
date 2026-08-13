package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Block;

public class BlockLiquid
extends Block {
    public BlockLiquid(Object object) {
        super(object);
    }

    public static float getLiquidHeightPercent(int n) {
        if (n >= 8) {
            n = 0;
        }
        return (float)(n + 1) / 9.0f;
    }
}

