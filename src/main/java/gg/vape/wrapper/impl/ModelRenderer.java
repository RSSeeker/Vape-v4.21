package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MModelRenderer;
import gg.vape.wrapper.Wrapper;

public class ModelRenderer
extends Wrapper {
    public float j() {
        return MModelRenderer.h(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public float s() {
        return MModelRenderer.k(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public void n(boolean bl) {
        MModelRenderer.r(ModelRenderer.vapeInstance.getMappings().Rz, this.I, bl);
    }

    public ModelRenderer(Object object) {
        super(object);
    }

    public float M() {
        return MModelRenderer.i(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public float B() {
        return MModelRenderer.v(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public float q() {
        return MModelRenderer.b(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public float getRotateAngleY() {
        return MModelRenderer.s(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public float getRotateAngleZ() {
        return MModelRenderer.U(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public void S(boolean bl) {
        MModelRenderer.j(ModelRenderer.vapeInstance.getMappings().Rz, this.I, bl);
    }

    public float getRotateAngleX() {
        return MModelRenderer.c(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public int s$src$I$x0ut69() {
        return MModelRenderer.z(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public float F() {
        return MModelRenderer.t(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }

    public int C() {
        return MModelRenderer.j(ModelRenderer.vapeInstance.getMappings().Rz, this.I);
    }
}

