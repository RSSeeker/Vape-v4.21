package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MMatrixStack;
import gg.vape.wrapper.Wrapper;

public class MatrixStack
extends Wrapper {
    public void H() {
        if (this.isInstance(MappedClasses.VH)) {
            new Matrix4fTransformBridge(this.I).J();
            return;
        }
        MMatrixStack.o(MatrixStack.vapeInstance.getMappingsMapperCompat().qn, this.I);
    }

    public MatrixStackEntry F() {
        return new MatrixStackEntry(MMatrixStack.S(MatrixStack.vapeInstance.getMappingsMapperCompat().qn, this.I));
    }

    public void y() {
        if (this.isInstance(MappedClasses.VH)) {
            new Matrix4fTransformBridge(this.I).X();
            return;
        }
        MMatrixStack.b(MatrixStack.vapeInstance.getMappingsMapperCompat().qn, this.I);
    }

    public void i(Quaternion jd_12) {
        MMatrixStack.r(MatrixStack.vapeInstance.getMappingsMapperCompat().qn, this.I, jd_12.getObject());
    }

    public void i(Matrix4f tl_12) {
        if (this.isInstance(MappedClasses.VH)) {
            new Matrix4fTransformBridge(this.I).a(tl_12);
            return;
        }
        MMatrixStack.o(MatrixStack.vapeInstance.getMappingsMapperCompat().qn, this.I, tl_12.getObject());
    }

    public void V(double d, double d2, double d3) {
        if (this.isInstance(MappedClasses.VH)) {
            new Matrix4fTransformBridge(this.I).N((float)d, (float)d2, (float)d3);
            return;
        }
        MMatrixStack.d(MatrixStack.vapeInstance.getMappingsMapperCompat().qn, this.I, d, d2, d3);
    }


    public void U() {
        if (this.isInstance(MappedClasses.VH)) {
            new Matrix4fTransformBridge(this.I).h();
            return;
        }
        MMatrixStack.u(MatrixStack.vapeInstance.getMappingsMapperCompat().qn, this.I);
    }

    public void S(float f, float f2, float f3) {
        if (this.isInstance(MappedClasses.VH)) {
            new Matrix4fTransformBridge(this.I).d(f, f2, f3);
            return;
        }
        MMatrixStack.o(MatrixStack.vapeInstance.getMappingsMapperCompat().qn, this.I, f, f2, f3);
    }

    public MatrixStack(Object object) {
        super(object);
    }

    public void s(double d, double d2, double d3) {
        this.S((float)d, (float)d2, (float)d3);
    }

    public static MatrixStack A() {
        return new MatrixStack(MMatrixStack.U(MatrixStack.vapeInstance.getMappingsMapperCompat().qn));
    }
}

