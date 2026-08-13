package gg.vape.utils;

import java.awt.Color;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;

public class MutableColor
extends Color {
    private int V;
    private int R;
    private static final Integer[] d = new Integer[5];
    private static final long[] c = new long[5];
    private int w;
    private static final long b = 0L;
    private static final Map e = new HashMap();
    private int G;
    private Color a;

    public MutableColor(int n, int n2) {
        super(n);
        this.a = new Color(n);
        this.R = super.getRed();
        this.w = super.getGreen();
        this.G = super.getBlue();
        this.V = n2;
        if (this.V == 0) {
            this.V = 1;
        }
    }

    public MutableColor(int n) {
        super(n);
        this.a = new Color(n);
        this.R = super.getRed();
        this.w = super.getGreen();
        this.G = super.getBlue();
        this.V = 255;
    }

    public MutableColor(Color color) {
        super(color.getRGB(), true);
        this.a = new Color(color.getRGB(), true);
        this.R = super.getRed();
        this.w = super.getGreen();
        this.G = super.getBlue();
        this.V = super.getAlpha();
        if (this.V == 0) {
            this.V = 1;
        }
    }

    public MutableColor(int n, int n2, int n3, int n4) {
        super(n, n2, n3, n4);
        this.a = new Color(super.getRGB(), true);
        this.R = super.getRed();
        this.w = super.getGreen();
        this.G = super.getBlue();
        this.V = super.getAlpha();
        if (this.V == 0) {
            this.V = 1;
        }
    }

    @Override
    public int getRGB() {
        return this.a.getRGB();
    }

    @Override
    public int getRed() {
        return this.R;
    }

    @Override
    public int getGreen() {
        return this.w;
    }

    @Override
    public int getBlue() {
        return this.G;
    }

    @Override
    public int getAlpha() {
        return this.V;
    }

    public int l() {
        return (this.V & 0xFF) << 24 | (this.R & 0xFF) << 16 | (this.w & 0xFF) << 8 | this.G & 0xFF;
    }

    public void setBlue(int n) {
        this.G = n;
        this.G();
    }

    public void setGreen(int n) {
        this.w = n;
        this.G();
    }

    public void setRed(int n) {
        this.R = n;
        this.G();
    }

    public MutableColor withAlpha(int n) {
        if (n == 0) {
            n = 1;
        }
        this.V = n;
        this.G();
        return this;
    }

    public void setColor(Color color) {
        this.R = color.getRed();
        this.w = color.getGreen();
        this.G = color.getBlue();
        this.V = color.getAlpha();
        this.G();
    }

    public void blend(Color color, float f) {
        if (f > 1.0f) {
            f = 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        float f2 = 1.0f - f;
        int n = this.getRGB();
        int n2 = color.getRGB();
        int n3 = n >> 24 & 0xFF;
        int n4 = (n & 0xFF0000) >> 16;
        int n5 = (n & 0xFF00) >> 8;
        int n6 = n & 0xFF;
        int n7 = n2 >> 24 & 0xFF;
        int n8 = (n2 & 0xFF0000) >> 16;
        int n9 = (n2 & 0xFF00) >> 8;
        int n10 = n2 & 0xFF;
        int n11 = (int)((float)n3 * f2 + (float)n7 * f);
        int n12 = (int)((float)n4 * f2 + (float)n8 * f);
        int n13 = (int)((float)n5 * f2 + (float)n9 * f);
        int n14 = (int)((float)n6 * f2 + (float)n10 * f);
        this.setRed(n12);
        this.setGreen(n13);
        this.setBlue(n14);
        this.withAlpha(n11);
    }

    public MutableColor F(float f) {
        int n = this.getRed();
        int n2 = this.getGreen();
        int n3 = this.getBlue();
        int n4 = this.getAlpha();
        int n5 = (int)(1.0 / (1.0 - (double)f));
        if (n == 0 && n2 == 0 && n3 == 0) {
            this.R = n5;
            this.w = n5;
            this.G = n5;
            this.V = n4;
            return this;
        }
        if (n > 0 && n < n5) {
            n = n5;
        }
        if (n2 > 0 && n2 < n5) {
            n2 = n5;
        }
        if (n3 > 0 && n3 < n5) {
            n3 = n5;
        }
        this.R = Math.min((int)((float)n / f), 255);
        this.w = Math.min((int)((float)n2 / f), 255);
        this.G = Math.min((int)((float)n3 / f), 255);
        this.V = n4;
        return this;
    }

    public MutableColor H(float f) {
        this.R = Math.max((int)((float)this.getRed() * f), 0);
        this.w = Math.max((int)((float)this.getGreen() * f), 0);
        this.G = Math.max((int)((float)this.getBlue() * f), 0);
        return this;
    }

    private void G() {
        if (this.V > 255) {
            this.a = new Color(this.R, this.w, this.G);
            return;
        }
        this.a = new Color(this.R, this.w, this.G, this.V);
    }

    private static int a(int n, long l) {
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x6E01;
        Integer n3 = n2 >= 0 && n2 < d.length ? d[n2] : null;
        Integer n4 = n3;
        if (n3 == null) {
            throw new IllegalStateException("removed MutableColor constant bootstrap");
        }
        return n3;
    }

    private static int a(MethodHandles.Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
        return MutableColor.a((Integer)objectArray[0], (Long)objectArray[1]);
    }

    private static CallSite a(MethodHandles.Lookup lookup, String string, MethodType methodType) {
        return new MutableCallSite(methodType);
    }

}

