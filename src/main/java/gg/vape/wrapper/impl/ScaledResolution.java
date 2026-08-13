package gg.vape.wrapper.impl;

import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.Minecraft;

public class ScaledResolution {
    private final double scaledHeight;
    private static int G;
    private int scaleFactor = 1;
    private int scaledWidth = Minecraft.J();
    private int scaledHeightPixels = Minecraft.h();
    private final double scaledWidthDouble;


    public double getScaledHeightDouble() {
        return this.scaledHeight;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    static {
        if (ScaledResolution.q() != 0) {
            ScaledResolution.r(123);
        }
    }

    public static void r(int n) {
        G = n;
    }

    public int getScaleFactor() {
        return this.scaleFactor;
    }

    public int getScaledHeight() {
        return this.scaledHeightPixels;
    }

    public static int q() {
        return G;
    }

    public double getScaledWidthDouble() {
        return this.scaledWidthDouble;
    }

    public ScaledResolution() {
        boolean bl = false;
        int n = Minecraft.gameSettings().T();
        if (n == 0) {
            n = 1000;
        }
        while (this.scaleFactor < n && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeightPixels / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        this.scaledWidthDouble = (double)this.scaledWidth / (double)this.scaleFactor;
        this.scaledHeight = (double)this.scaledHeightPixels / (double)this.scaleFactor;
        this.scaledWidth = MathUtil.ceil(this.scaledWidthDouble);
        this.scaledHeightPixels = MathUtil.ceil(this.scaledHeight);
    }

    public static int W() {
        int n = ScaledResolution.q();
        if (n == 0) {
            return 80;
        }
        return 0;
    }
}

