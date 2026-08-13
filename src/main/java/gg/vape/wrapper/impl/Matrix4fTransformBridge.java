package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MMatrix4fTransformVariantBridge;

public class Matrix4fTransformBridge
extends Matrix4f {
    public void d(float f, float f2, float f3) {
        MMatrix4fTransformVariantBridge.L(Matrix4fTransformBridge.vapeInstance.getMappingsMapperCompat().Rq, this.I, f, f2, f3);
    }

    public Matrix4fTransformBridge J() {
        return new Matrix4fTransformBridge(MMatrix4fTransformVariantBridge.D(Matrix4fTransformBridge.vapeInstance.getMappingsMapperCompat().Rq, this.I));
    }

    public Matrix4fTransformBridge h() {
        return new Matrix4fTransformBridge(MMatrix4fTransformVariantBridge.B(Matrix4fTransformBridge.vapeInstance.getMappingsMapperCompat().Rq, this.I));
    }

    public void X() {
        MMatrix4fTransformVariantBridge.w(Matrix4fTransformBridge.vapeInstance.getMappingsMapperCompat().Rq, this.I);
    }

    public void Z(double d, double d2, double d3) {
        this.d((float)d, (float)d2, (float)d3);
    }

    public void N(float f, float f2, float f3) {
        MMatrix4fTransformVariantBridge.Q(Matrix4fTransformBridge.vapeInstance.getMappingsMapperCompat().Rq, this.I, f, f2, f3);
    }

    public Matrix4fTransformBridge(Object object) {
        super(object);
    }
}

