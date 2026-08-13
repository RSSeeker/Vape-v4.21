package gg.vape.ui.click.component.value;

public enum RangeEndpoint {
    MINIMUM,
    MAXIMUM;

    private static final RangeEndpoint[] LEGACY_VALUES;

    static {
        String[] legacyNames = new String[]{"MINIMUM", "MAXIMUM"};


        LEGACY_VALUES = new RangeEndpoint[]{MINIMUM, MAXIMUM};
    }

}
