package func.skidline;

import gg.vape.ui.click.MousePosition;
import gg.vape.utils.MathUtil;
import java.awt.Point;

public class RectData {
    private static final String b;
    private double Z;
    private double d;
    private double c;
    private static int l;
    private double Y;


    public boolean Z(MousePosition mousePosition) {
        return this.J(mousePosition.O, mousePosition.H);
    }

    public boolean q(RectData rectData) {
        return this.c(rectData) > 0.0;
    }

    public static int B() {
        int n = RectData.X();
        return 78;
    }

    static {
        RectData.b(0);
        b = "[%s, %s, %s, %s]";
    }

    public void A(double d) {
        this.Y = d;
    }

    public double c(RectData rectData) {
        double d = Math.max(0.0, Math.min(this.o() + this.e(), rectData.o() + rectData.e()) - Math.max(this.o(), rectData.o()));
        double d2 = Math.max(0.0, Math.min(this.W() + this.R(), rectData.W() + rectData.R()) - Math.max(this.W(), rectData.W()));
        return d * d2;
    }

    public void M(double d) {
        this.d = d;
    }

    public RectData d() {
        return new RectData(this.o(), this.W(), this.e(), this.R());
    }

    public boolean z(double d, double d2, double d3) {
        if (MathUtil.O(d, d2, d3, this.d, this.Z) || MathUtil.O(d, d2, d3, this.d + this.Y, this.Z) || MathUtil.O(d, d2, d3, this.d + this.Y, this.Z + this.c) || MathUtil.O(d, d2, d3, this.d, this.Z + this.c)) {
            return MathUtil.H(d, d2, d3, this.d, this.Z) || MathUtil.H(d, d2, d3, this.d + this.Y, this.Z) || MathUtil.H(d, d2, d3, this.d + this.Y, this.Z + this.c) || MathUtil.H(d, d2, d3, this.d, this.Z + this.c);
        }
        return false;
    }

    public double e() {
        return this.Y;
    }

    public double o() {
        return this.d;
    }

    public double W() {
        return this.Z;
    }

    public String toString() {
        return String.format(b, this.o(), this.W(), this.e(), this.R());
    }

    public RectData x(double d, double d2) {
        return new RectData(d + this.o(), d2 + this.W(), this.e(), this.R());
    }

    public static int X() {
        return l;
    }

    public RectData(double d, double d2, double d3, double d4) {
        this.d = d;
        this.Z = d2;
        this.Y = d3;
        this.c = d4;
    }

    public boolean J(double d, double d2) {
        return d >= this.o() && d <= this.o() + this.e() && d2 >= this.W() && d2 <= this.W() + this.R();
    }

    public void O(double d) {
        this.Z = d;
    }

    public static void b(int n) {
        l = n;
    }

    public RectData y(double d, double d2) {
        return new RectData(this.o() - d, this.W() - d2, this.e() + d * 2.0, this.R() + d2 * 2.0);
    }

    public boolean R(Point point) {
        return this.J(point.x, point.y);
    }

    public double R() {
        return this.c;
    }

    public void U(double d) {
        this.c = d;
    }
}

