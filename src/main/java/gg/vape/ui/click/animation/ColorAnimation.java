package gg.vape.ui.click.animation;

import gg.vape.ui.click.animation.Animation;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class ColorAnimation
extends Animation<Color> {
    private int C;
    private Color a;
    private static boolean c;
    private int j;
    private int g;
    private int M;
    private int h;
    private int P;
    private int v;
    private Color s;
    private int G;

    public static ColorAnimation Y(@Nullable Color color) {
        return new ColorAnimation(0.0, color, color);
    }

    static {
        if (!ColorAnimation.k()) {
            ColorAnimation.U(true);
        }
    }

    public static boolean b() {
        return c;
    }

    public Color getStartColor() {
        return this.a;
    }

    public static void U(boolean bl) {
        c = bl;
    }

    public static boolean k() {
        boolean bl = ColorAnimation.b();
        return !bl;
    }

    public Color getEndColor() {
        return this.s;
    }

    public Color getInterpolatedColor() {
        double d = this.q();
        if (d == 0.0) {
            return this.a;
        }
        if (d == 100.0) {
            return this.s;
        }
        int n = this.g + (int)(this.q() * (double)this.G / 100.0);
        int n2 = this.v + (int)(this.q() * (double)this.j / 100.0);
        int n3 = this.M + (int)(this.q() * (double)this.h / 100.0);
        int n4 = this.P + (int)(this.q() * (double)this.C / 100.0);
        return new Color(n, n2, n3, n4);
    }

    @Override
    public Color R() {
        return this.getInterpolatedColor();
    }


    public void setEndColor(Color color) {
        this.s = color;
        this.G = color.getRed() - this.g;
        this.j = color.getGreen() - this.v;
        this.h = color.getBlue() - this.M;
        this.C = color.getAlpha() - this.P;
    }

    public ColorAnimation(double d, @Nullable Color color, @Nullable Color color2) {
        super(d);
        this.a = color != null ? color : new Color(0, 0, 0, 0);
        this.s = color2 != null ? color2 : new Color(0, 0, 0, 0);
        this.g = this.a.getRed();
        this.v = this.a.getGreen();
        this.M = this.a.getBlue();
        this.P = this.a.getAlpha();
        this.G = this.s.getRed() - this.g;
        this.j = this.s.getGreen() - this.v;
        this.h = this.s.getBlue() - this.M;
        this.C = this.s.getAlpha() - this.P;
    }

    public void setStartColor(Color color) {
        this.a = color;
        this.g = color.getRed();
        this.v = color.getGreen();
        this.M = color.getBlue();
        this.P = color.getAlpha();
        this.G = this.s.getRed() - this.g;
        this.j = this.s.getGreen() - this.v;
        this.h = this.s.getBlue() - this.M;
        this.C = this.s.getAlpha() - this.P;
    }
}
