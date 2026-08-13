package gg.vape.ui.font.stb;

import gg.vape.ui.font.SmoothFontGlyph;

public class StbGlyphCacheEntry {
    private static String[] A;
    public final int y;
    public final float i;
    public final SmoothFontGlyph d;
    public final boolean u;

    public static String[] f() {
        return A;
    }

    public StbGlyphCacheEntry(SmoothFontGlyph hv_22, int n, float f, boolean bl) {
        this.d = hv_22;
        this.y = n;
        this.i = f;
        this.u = bl;
    }

    public static void R(String[] stringArray) {
        A = stringArray;
    }

    static {
        if (StbGlyphCacheEntry.f() != null) {
            StbGlyphCacheEntry.R(new String[5]);
        }
    }
}

