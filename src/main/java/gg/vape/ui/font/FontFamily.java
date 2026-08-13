package gg.vape.ui.font;

public enum FontFamily {
    ARIAL,
    PROXIMA,
    NOTO,
    POPPINS,
    MINECRAFT;

    private static final FontFamily[] p;

    static {
        String[] stringArray = new String[]{"MINECRAFT", "PROXIMA", "POPPINS", "NOTO", "ARIAL"};





        p = new FontFamily[]{ARIAL, PROXIMA, NOTO, POPPINS, MINECRAFT};
    }
}

