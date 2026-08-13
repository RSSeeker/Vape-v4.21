package gg.vape.ui.click.component;

public enum IconShape {
    CIRCLE,
    ROUNDED_RECT;

    private static final IconShape[] LEGACY_VALUES;

    static {
        String[] legacyNames = new String[]{"ROUNDED_RECT", "CIRCLE"};


        LEGACY_VALUES = new IconShape[]{CIRCLE, ROUNDED_RECT};
    }

}
