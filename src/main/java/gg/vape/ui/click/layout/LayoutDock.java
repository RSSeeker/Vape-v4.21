package gg.vape.ui.click.layout;

public enum LayoutDock {
    NONE,
    TOP,
    BOTTOM,
    LEFT,
    RIGHT;

    private static final LayoutDock[] D;

    static {
        String[] stringArray = new String[]{"BOTTOM", "LEFT", "TOP", "RIGHT", "NONE"};





        D = new LayoutDock[]{NONE, TOP, BOTTOM, LEFT, RIGHT};
    }

}

