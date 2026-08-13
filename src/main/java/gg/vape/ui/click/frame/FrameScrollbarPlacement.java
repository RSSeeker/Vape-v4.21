package gg.vape.ui.click.frame;

public enum FrameScrollbarPlacement {
    INSIDE,
    OUTSIDE;

    private static final FrameScrollbarPlacement[] M;

    static {
        String[] stringArray = new String[]{"OUTSIDE", "INSIDE"};


        M = new FrameScrollbarPlacement[]{INSIDE, OUTSIDE};
    }

}

