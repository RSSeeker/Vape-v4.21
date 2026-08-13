package gg.vape.ui.click.frame.impl.main;

public enum ClickGuiLayer {
    MAIN,
    OVERLAYS;

    private static final ClickGuiLayer[] r;

    static {
        String[] stringArray = new String[]{"MAIN", "OVERLAYS"};


        r = new ClickGuiLayer[]{MAIN, OVERLAYS};
    }

}

