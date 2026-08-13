package gg.vape.ui.font;

public class SmoothFontGlyph {
    public float w;
    public float t;
    public float B;
    public float X;
    public float G;
    private static String[] p;
    public float J;
    public float S;
    public float a;
    public float g;

    public SmoothFontGlyph(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.X = f;
        this.g = f3;
        this.a = f2;
        this.G = f4;
        this.t = f5;
        this.w = f7;
        this.J = f6;
        this.B = f8;
        this.S = f9;
    }

    public static String[] N() {
        return p;
    }

    public static void m(String[] stringArray) {
        p = stringArray;
    }

    static {
        if (SmoothFontGlyph.N() == null) {
            SmoothFontGlyph.m(new String[1]);
        }
    }
}

