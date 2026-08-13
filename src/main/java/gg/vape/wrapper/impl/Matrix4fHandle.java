package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MMatrix4fHandle;
import gg.vape.wrapper.Wrapper;

public class Matrix4fHandle
extends Wrapper {
    public Matrix4fHandle o() {
        return new Matrix4fHandle(MMatrix4fHandle.C(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I));
    }

    public Matrix4fHandle(Object object) {
        super(object);
    }

    public void K(float f) {
        MMatrix4fHandle.t(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I, f);
    }

    public Object a(float f, float f2) {
        return MMatrix4fHandle.x(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I, f, f2);
    }

    public void b(float f) {
        MMatrix4fHandle.F(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I, f);
    }

    public void T(float f) {
        MMatrix4fHandle.j(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I, f);
    }

    public void z(float f) {
        MMatrix4fHandle.L(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I, f);
    }

    public Object z(double d, double d2) {
        return this.I((float)d, (float)d2);
    }

    public Object I(float f, float f2) {
        return MMatrix4fHandle.j(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I, f, f2);
    }

    public Matrix4fHandle N() {
        return new Matrix4fHandle(MMatrix4fHandle.u(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I));
    }

    public void p(float f) {
        MMatrix4fHandle.a(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I, f);
    }

    public void m(float f) {
        MMatrix4fHandle.U(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, this.I, f);
    }

    public Object j(double d, double d2) {
        return this.a((float)d, (float)d2);
    }

    public static Matrix4fHandle b(int n) {
        return new Matrix4fHandle(MMatrix4fHandle.R(Matrix4fHandle.vapeInstance.getMappingsMapperCompat().D8, n));
    }
}

