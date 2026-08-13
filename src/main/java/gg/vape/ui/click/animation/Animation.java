package gg.vape.ui.click.animation;


public abstract class Animation<T> {
    private boolean i;
    private long b = 0L;
    private long p = Long.MAX_VALUE;
    private long m;
    private static boolean n;
    private long D = 0L;

    public double X() {
        return -(Math.cos(Math.PI * (double)this.s()) - 1.0) / 2.0;
    }

    public boolean N() {
        return this.q() == 100.0;
    }

    public void u(boolean bl) {
        double d = this.q();
        if (bl) {
            if (d == 0.0) {
                this.c();
                return;
            }
            if (d == 100.0) {
                return;
            }
        } else if (d == 100.0) {
            this.Z();
            return;
        }
    }

    public boolean n() {
        return this.i;
    }

    public Animation(double d) {
        this.m = (long)(d * 1000.0);
    }

    public static boolean Z$src$Z$cdkyx1() {
        boolean bl = Animation.R$src$Z$c96m65();
        return true;
    }

    public void g(double d) {
        this.b = (long)(1000.0 * d);
    }

    public static boolean R$src$Z$c96m65() {
        return n;
    }

    public float s() {
        return (float)(this.q() / 100.0);
    }

    public void O() {
        this.p = 0L;
        this.i = true;
    }

    public boolean I$src$Z$c48gtw() {
        return !this.i && this.q() != 0.0;
    }

    public Animation(double d, double d2) {
        this.b = this.D = (long)(d2 * 1000.0);
        this.m = (long)(d * 1000.0);
    }

    public void U(double d) {
        this.m = (long)(d * 1000.0);
    }

    public long f() {
        return this.m;
    }

    public boolean l() {
        double d = this.q();
        return d != 0.0 && d != 100.0;
    }

    public double q() {
        long l = this.i ? this.b : this.D;
        long l2 = System.currentTimeMillis() - this.p - l;
        boolean bl = l > 0L && l2 + l >= 0L;
        double d = Math.min((double)l2 / (double)this.m * 100.0, 100.0);
        if (d < 0.0) {
            d = bl ? 0.1 : 0.0;
        }
        if (this.i) {
            return 100.0 - d;
        }
        return d;
    }

    public Animation(double d, double d2, double d3) {
        this.m = (long)(d * 1000.0);
        this.D = (long)(d2 * 1000.0);
        this.b = (long)(d3 * 1000.0);
    }

    public static void K(boolean bl) {
        n = bl;
    }

    public void P(double d) {
        this.D = (long)(1000.0 * d);
    }

    public abstract T R();

    public void Z() {
        this.p = System.currentTimeMillis();
        this.i = true;
    }

    public void C() {
        this.p = 0L;
        this.i = false;
    }

    public long T() {
        return this.b;
    }


    static {
        if (Animation.R$src$Z$c96m65()) {
            Animation.K(true);
        }
    }

    public void c() {
        this.p = System.currentTimeMillis();
        this.i = false;
    }

    public double I() {
        return this.D / 1000L;
    }

    public void J() {
        double d = this.q();
        if (d == 0.0) {
            this.c();
            return;
        }
        if (d == 100.0) {
            this.Z();
            return;
        }
        long l = System.currentTimeMillis() - this.p;
        long l2 = this.m - l;
        this.p = System.currentTimeMillis() - l2;
        if (this.i) {
            this.i = false;
            return;
        }
        this.i = true;
    }
}

