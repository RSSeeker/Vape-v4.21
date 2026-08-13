package gg.vape.ui.click.frame.impl.profile;

public enum ProfileModuleSnapshotListStyle {
    LEGACY,
    MODERN;

    private static final ProfileModuleSnapshotListStyle[] LEGACY_ORDER;

    static {
        String[] stringArray = new String[]{"MODERN", "LEGACY"};


        LEGACY_ORDER = new ProfileModuleSnapshotListStyle[]{LEGACY, MODERN};
    }

}
