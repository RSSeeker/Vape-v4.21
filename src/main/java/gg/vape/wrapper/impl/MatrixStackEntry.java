package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MMatrixStackEntry;
import gg.vape.wrapper.Wrapper;

public class MatrixStackEntry
extends Wrapper {
    public Matrix4f getMatrix() {
        return new Matrix4f(MMatrixStackEntry.getMatrix(MatrixStackEntry.vapeInstance.getMappingsMapperCompat().CK, this.I));
    }

    public MatrixStackEntry(Object wrappedObject) {
        super(wrappedObject);
    }
}
