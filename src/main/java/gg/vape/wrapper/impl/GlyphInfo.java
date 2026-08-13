package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class GlyphInfo
extends Wrapper {
    public float M() {
        return GlyphInfo.vapeInstance.getMappings().qp.h(this.I);
    }

    public float s() {
        return GlyphInfo.vapeInstance.getMappings().qp.j(this.I);
    }

    public FontGlyphInfo z() {
        Object object = GlyphInfo.vapeInstance.getMappings().qp.H(this.I);
        return object != null ? new FontGlyphInfo(object) : null;
    }

    public GlyphInfo(Object object) {
        super(object);
    }

    public float S() {
        return GlyphInfo.vapeInstance.getMappings().qp.v(this.I);
    }

    public float E() {
        return this.s() - this.h();
    }

    public GpuTextureView getTextureView() {
        Object object = GlyphInfo.vapeInstance.getMappings().qp.r$src$Ljava_lang_Object_$1r6sqxs(this.I);
        return object != null ? new GpuTextureView(object) : null;
    }


    public float F(boolean bl) {
        FontGlyphInfo fontGlyphInfo = this.z();
        return fontGlyphInfo != null ? fontGlyphInfo.getAdvance(bl) : 0.0f;
    }

    public float j() {
        return GlyphInfo.vapeInstance.getMappings().qp.K(this.I);
    }

    public int q() {
        GpuTextureView textureView = this.getTextureView();
        return textureView != null ? textureView.getTextureId() : -1;
    }

    public float h() {
        return GlyphInfo.vapeInstance.getMappings().qp.r(this.I);
    }

    public float Q() {
        return this.j() - this.t();
    }

    public float x() {
        return GlyphInfo.vapeInstance.getMappings().qp.g(this.I);
    }

    public float H() {
        return GlyphInfo.vapeInstance.getMappings().qp.M(this.I);
    }

    public float t() {
        return GlyphInfo.vapeInstance.getMappings().qp.N(this.I);
    }
}

