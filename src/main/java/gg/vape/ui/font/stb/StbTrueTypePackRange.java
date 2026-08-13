package gg.vape.ui.font.stb;

import gg.vape.ui.font.stb.StbTrueTypePackedChar;

public class StbTrueTypePackRange {
    public int y;
    public int R;
    public int[] N;
    public StbTrueTypePackedChar[] M;
    public int i;
    public float E;
    private static String[] j;
    public int b;

    public static void t(String[] stringArray) {
        j = stringArray;
    }

    public static String[] x() {
        return j;
    }

    static {
        if (StbTrueTypePackRange.x() != null) {
            StbTrueTypePackRange.t(new String[1]);
        }
    }
}

