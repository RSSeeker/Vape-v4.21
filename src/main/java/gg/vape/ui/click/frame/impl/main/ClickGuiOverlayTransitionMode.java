package gg.vape.ui.click.frame.impl.main;

public enum ClickGuiOverlayTransitionMode {
    REPLACE,
    PUSH;

    private static final ClickGuiOverlayTransitionMode[] M;

    static {
        String[] stringArray = new String[]{"REPLACE", "PUSH"};


        M = new ClickGuiOverlayTransitionMode[]{REPLACE, PUSH};
    }

}

