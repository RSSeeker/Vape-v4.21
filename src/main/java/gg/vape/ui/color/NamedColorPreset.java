package gg.vape.ui.color;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public enum NamedColorPreset {
    RED("Red", new Color(255, 0, 0)),
    ORANGE("Orange", new Color(255, 165, 0)),
    YELLOW("Yellow", new Color(255, 255, 0)),
    GREEN("Green", new Color(0, 128, 0)),
    BLUE("Blue", new Color(0, 0, 255)),
    INDIGO("Indigo", new Color(75, 0, 130)),
    PINK("Pink", new Color(238, 130, 238)),
    BURGUNDY("Burgundy", new Color(128, 0, 32)),
    CARROT("Carrot", new Color(237, 145, 33)),
    GOLD("Gold", new Color(255, 215, 0)),
    FOREST_GREEN("Forest Green", new Color(34, 139, 34)),
    NAVY("Navy", new Color(0, 0, 128)),
    AUBERGINE("Aubergine", new Color(61, 48, 84)),
    PLUM("Plum", new Color(221, 160, 221)),
    SALMON("Salmon", new Color(250, 128, 114)),
    PEACH("Peach", new Color(255, 218, 185)),
    LEMON("Lemon", new Color(255, 247, 0)),
    MINT("Mint", new Color(152, 255, 152)),
    SKY_BLUE("Sky Blue", new Color(135, 206, 235)),
    LAVENDER("Lavender", new Color(230, 230, 250)),
    CYAN("Cyan", new Color(0, 255, 255)),
    MAGENTA("Magenta", new Color(255, 0, 255)),
    SILVER("Silver", new Color(192, 192, 192)),
    BRONZE("Bronze", new Color(205, 127, 50)),
    WHITE("White", new Color(255, 255, 255)),
    BLACK("Black", new Color(0, 0, 0)),
    GRAY("Gray", new Color(128, 128, 128)),
    IVORY("Ivory", new Color(255, 255, 240)),
    BEIGE("Beige", new Color(245, 245, 220)),
    OLIVE("Olive", new Color(128, 128, 0)),
    TEAL("Teal", new Color(0, 128, 128)),
    MAROON("Maroon", new Color(128, 0, 0)),
    CHARCOAL("Charcoal", new Color(54, 69, 79)),
    FUCHSIA("Fuchsia", new Color(255, 0, 255)),
    TURQUOISE("Turquoise", new Color(64, 224, 208)),
    RASPBERRY("Raspberry", new Color(227, 11, 92)),
    TANGERINE("Tangerine", new Color(242, 133, 0)),
    AMETHYST("Amethyst", new Color(153, 102, 204)),
    EMERALD("Emerald", new Color(80, 200, 120));

    private final String v;
    private final double[] H;
    public static final List<NamedColorPreset> VALUES;
    private static final /* synthetic */ NamedColorPreset[] n;
    private static String[] r;
    private final Color E;

    public static void S(String[] stringArray) {
        r = stringArray;
    }

    static {
        if (NamedColorPreset.V() != null) {
            NamedColorPreset.S(new String[4]);
        }
        String[] stringArray = new String[]{"Orange", "Pink", "Plum", "Fuchsia", "Yellow", "CYAN", "INDIGO", "Green", "Carrot", "Amethyst", "BURGUNDY", "Salmon", "MAROON", "Charcoal", "LAVENDER", "BLUE", "TANGERINE", "OLIVE", "RASPBERRY", "Lemon", "TEAL", "Tangerine", "Mint", "AMETHYST", "CARROT", "Raspberry", "IVORY", "Teal", "GRAY", "CHARCOAL", "Red", "GREEN", "Cyan", "NAVY", "BEIGE", "FUCHSIA", "PINK", "Lavender", "Bronze", "Aubergine", "BRONZE", "BLACK", "FOREST_GREEN", "SALMON", "WHITE", "White", "Turquoise", "PEACH", "Sky Blue", "Gold", "Gray", "SILVER", "MAGENTA", "EMERALD", "Magenta", "Maroon", "PLUM", "Navy", "AUBERGINE", "Burgundy", "Peach", "Indigo", "Forest Green", "GOLD", "YELLOW", "SKY_BLUE", "LEMON", "TURQUOISE", "Blue", "Beige", "Black", "RED", "Olive", "Ivory", "Emerald", "ORANGE", "MINT", "Silver"};







































        n = new NamedColorPreset[]{RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, PINK, BURGUNDY, CARROT, GOLD, FOREST_GREEN, NAVY, AUBERGINE, PLUM, SALMON, PEACH, LEMON, MINT, SKY_BLUE, LAVENDER, CYAN, MAGENTA, SILVER, BRONZE, WHITE, BLACK, GRAY, IVORY, BEIGE, OLIVE, TEAL, MAROON, CHARCOAL, FUCHSIA, TURQUOISE, RASPBERRY, TANGERINE, AMETHYST, EMERALD};
        VALUES = Arrays.asList(NamedColorPreset.values());
    }

    public static String[] V() {
        return r;
    }

    private NamedColorPreset(String string2, Color color) {
        this.v = string2;
        this.E = color;
        this.H = NamedColorPreset.o(color);
    }

    public String o() {
        return this.v;
    }

    public Color m() {
        return this.E;
    }


    private static double b(double[] dArray, double[] dArray2) {
        return Math.sqrt(Math.pow(dArray[0] - dArray2[0], 2.0) + Math.pow(dArray[1] - dArray2[1], 2.0) + Math.pow(dArray[2] - dArray2[2], 2.0));
    }

    private static double[] o(Color color) {
        double d = (double)color.getRed() / 255.0;
        double d2 = (double)color.getGreen() / 255.0;
        double d3 = (double)color.getBlue() / 255.0;
        d = d > 0.04045 ? Math.pow((d + 0.055) / 1.055, 2.4) : d / 12.92;
        d2 = d2 > 0.04045 ? Math.pow((d2 + 0.055) / 1.055, 2.4) : d2 / 12.92;
        d3 = d3 > 0.04045 ? Math.pow((d3 + 0.055) / 1.055, 2.4) : d3 / 12.92;
        double d4 = d * 0.4124 + d2 * 0.3576 + d3 * 0.1805;
        double d5 = d * 0.2126 + d2 * 0.7152 + d3 * 0.0722;
        double d6 = d * 0.0193 + d2 * 0.1192 + d3 * 0.9505;
        d4 = d4 > 0.008856 ? Math.pow(d4, 0.3333333333333333) : 7.787 * (d4 /= 0.95047) + 0.13793103448275862;
        d5 = d5 > 0.008856 ? Math.pow(d5, 0.3333333333333333) : 7.787 * (d5 /= 1.0) + 0.13793103448275862;
        d6 = d6 > 0.008856 ? Math.pow(d6, 0.3333333333333333) : 7.787 * (d6 /= 1.08883) + 0.13793103448275862;
        double d7 = 116.0 * d5 - 16.0;
        double d8 = 500.0 * (d4 - d5);
        double d9 = 200.0 * (d5 - d6);
        return new double[]{d7, d8, d9};
    }

    public static NamedColorPreset R(Color color) {
        double[] dArray = NamedColorPreset.o(color);
        NamedColorPreset namedColorPreset = null;
        double d = Double.MAX_VALUE;
        for (NamedColorPreset namedColorPreset2 : VALUES) {
            double d2 = NamedColorPreset.b(dArray, namedColorPreset2.H);
            if (!(d2 < d)) continue;
            d = d2;
            namedColorPreset = namedColorPreset2;
        }
        return namedColorPreset;
    }
}

