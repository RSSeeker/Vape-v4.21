package gg.vape.ui.font;

import gg.vape.ui.font.FontFamily;

public class FontFamilySwitchMap {
    public static final int[] d = new int[FontFamily.values().length];

    FontFamilySwitchMap() {
    }

    static {
        try {
            FontFamilySwitchMap.d[FontFamily.PROXIMA.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FontFamilySwitchMap.d[FontFamily.ARIAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FontFamilySwitchMap.d[FontFamily.NOTO.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FontFamilySwitchMap.d[FontFamily.MINECRAFT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

