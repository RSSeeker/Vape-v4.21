package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Vec3i;

public class InlineVec3i
extends Vec3i {
    private final int x;
    private final int y;
    private final int z;

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    public InlineVec3i(int x, int y, int z) {
        super(null);
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
