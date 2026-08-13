package gg.vape.ui.click.frame.impl.main;

public enum ClickGuiSection {
    MODULES("Modules"),
    FRIENDS("Friends"),
    PROFILES("Profiles");

    private static final /* synthetic */ ClickGuiSection[] R;
    private final String a;

    static {
        String[] stringArray = new String[]{"FRIENDS", "PROFILES", "Profiles", "Modules", "Friends", "MODULES"};



        R = new ClickGuiSection[]{MODULES, FRIENDS, PROFILES};
    }

    public String A() {
        return this.a;
    }

    private ClickGuiSection(String string2) {
        this.a = string2;
    }
}

