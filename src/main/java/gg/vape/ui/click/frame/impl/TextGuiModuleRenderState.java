package gg.vape.ui.click.frame.impl;

import func.skidline.RectData;
import gg.vape.module.Mod;

public class TextGuiModuleRenderState {
    private float T;
    private boolean W;
    private Mod o;
    private RectData k = null;
    private boolean B;

    public static RectData H(TextGuiModuleRenderState textGuiModuleRenderState, RectData rectData) {
        textGuiModuleRenderState.k = rectData;
        return textGuiModuleRenderState.k;
    }

    public void a(double d, double d2, double d3, double d4) {
        if (this.k == null) {
            this.k = new RectData(d, d2, d3, d4);
        }
        this.k.M(d);
        this.k.O(d2);
        this.k.A(d3);
        this.k.U(d4);
    }

    public void T(boolean bl) {
        this.W = bl;
    }

    public TextGuiModuleRenderState(Mod mod) {
        this.o = mod;
    }

    public void w(boolean bl) {
        this.B = bl;
    }


    public boolean W() {
        return this.W;
    }

    public static boolean a(TextGuiModuleRenderState textGuiModuleRenderState) {
        return textGuiModuleRenderState.B;
    }

    public static RectData J(TextGuiModuleRenderState textGuiModuleRenderState) {
        return textGuiModuleRenderState.k;
    }

    public float L() {
        return this.T;
    }

    public static float f(TextGuiModuleRenderState textGuiModuleRenderState, float f) {
        textGuiModuleRenderState.T = f;
        return textGuiModuleRenderState.T;
    }

    public static float G(TextGuiModuleRenderState textGuiModuleRenderState) {
        return textGuiModuleRenderState.T;
    }

    public void f(float f) {
        this.T = f;
    }

    public static boolean h(TextGuiModuleRenderState textGuiModuleRenderState) {
        return textGuiModuleRenderState.W;
    }
}

