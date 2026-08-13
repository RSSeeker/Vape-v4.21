package gg.vape.ui.geometry;

public class MutableFloatRect {
    public float d;
    public float m;
    public float y;
    public float D;

    public void n(float f) {
        this.y = f;
    }

    public float f() {
        return this.m;
    }

    public void O(float f) {
        this.d = f;
    }

    public MutableFloatRect(float f, float f2, float f3, float f4) {
        this.D = f;
        this.d = f2;
        this.y = f3;
        this.m = f4;
    }

    public float F() {
        return this.D;
    }

    public void u(float f) {
        this.D = f;
    }

    public void q(float f) {
        this.m = f;
    }

    public float V() {
        return this.y;
    }

    public float E() {
        return this.d;
    }
}

