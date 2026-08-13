package gg.vape.value;

public enum ValueDisplayNameMode {
    FULL,
    SIMPLE,
    CUSTOM;

    private static final ValueDisplayNameMode[] legacyValues;

    static {
        String[] stringArray = new String[]{"FULL", "SIMPLE", "CUSTOM"};



        legacyValues = new ValueDisplayNameMode[]{FULL, SIMPLE, CUSTOM};
    }
}
