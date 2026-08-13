package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class MutableBoundingBox
extends Wrapper {
    public static MutableBoundingBox create(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        Object boundingBoxHandle = MutableBoundingBox.vapeInstance.getMappingsMapperCompat().mutableBoundingBox
                .create(minX, minY, minZ, maxX, maxY, maxZ);
        return new MutableBoundingBox(boundingBoxHandle);
    }

    public MutableBoundingBox(Object boundingBoxHandle) {
        super(boundingBoxHandle);
    }

    public boolean intersects(MutableBoundingBox other) {
        return MutableBoundingBox.vapeInstance.getMappingsMapperCompat().mutableBoundingBox
                .intersects(this.I, other.getObject());
    }
}
