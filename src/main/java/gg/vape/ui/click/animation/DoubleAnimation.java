package gg.vape.ui.click.animation;

import gg.vape.ui.click.animation.Animation;

public class DoubleAnimation
extends Animation<Double> {
    private double f;
    private double A;
    private double S;

    public Double getInterpolatedValue() {
        if (this.f() == 0L) {
            return this.S;
        }
        double d = this.q();
        if (d == 0.0) {
            return this.f;
        }
        if (d == 100.0) {
            return this.S;
        }
        return this.f + this.q() * this.A / 100.0;
    }

    @Override
    public Double R() {
        return this.getInterpolatedValue();
    }

    public double getEndValue() {
        return this.S;
    }

    public DoubleAnimation(double d, double d2, double d3, double d4) {
        super(d, d2);
        this.f = d3;
        this.S = d4;
        this.A = d4 - d3;
    }

    public DoubleAnimation(double d, double d2, double d3) {
        super(d);
        this.f = d2;
        this.S = d3;
        this.A = d3 - d2;
    }

    public DoubleAnimation(double d, double d2, double d3, double d4, double d5) {
        super(d, d2, d3);
        this.f = d4;
        this.S = d5;
        this.A = d5 - d4;
    }

    public void setEndValue(double d) {
        this.S = d;
        this.A = d - this.f;
    }

    public double getStartValue() {
        return this.f;
    }

}
