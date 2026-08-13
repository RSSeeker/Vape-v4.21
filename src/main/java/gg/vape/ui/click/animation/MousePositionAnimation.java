package gg.vape.ui.click.animation;

import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.animation.Animation;

public class MousePositionAnimation
extends Animation<MousePosition> {
    private MousePosition X;
    private MousePosition e;
    private int G;
    private int w;

    public MousePositionAnimation(double d, MousePosition mousePosition, MousePosition mousePosition2) {
        super(d);
        this.X = mousePosition;
        this.e = mousePosition2;
        this.G = mousePosition2.O - mousePosition.O;
        this.w = mousePosition2.H - mousePosition.H;
    }

    public void I(MousePosition mousePosition) {
        this.X = mousePosition;
        this.G = this.e.O - mousePosition.O;
        this.w = this.e.H - mousePosition.H;
    }


    public MousePosition k() {
        double d = this.q();
        if (d == 0.0) {
            return this.X;
        }
        if (d == 100.0) {
            return this.e;
        }
        int n = this.X.O + (int)(this.q() * (double)this.G / 100.0);
        int n2 = this.X.H + (int)(this.q() * (double)this.w / 100.0);
        return new MousePosition(n, n2);
    }

    @Override
    public MousePosition R() {
        return this.k();
    }

    public MousePosition Y() {
        return this.e;
    }

    public void B(MousePosition mousePosition) {
        this.e = mousePosition;
        this.G = mousePosition.O - this.X.O;
        this.w = mousePosition.H - this.X.H;
    }

    public MousePosition o() {
        return this.X;
    }
}
