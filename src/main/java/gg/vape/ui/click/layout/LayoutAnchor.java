package gg.vape.ui.click.layout;

public enum LayoutAnchor {
    NONE,
    BOTTOM_RIGHT,
    TOP_LEFT,
    OFFSET;

    private static final LayoutAnchor[] t;

    static {
        String[] stringArray = new String[]{"NONE", "OFFSET", "TOP_LEFT", "BOTTOM_RIGHT"};




        t = new LayoutAnchor[]{NONE, BOTTOM_RIGHT, TOP_LEFT, OFFSET};
    }
}

