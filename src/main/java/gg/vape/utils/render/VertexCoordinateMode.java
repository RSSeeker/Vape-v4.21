package gg.vape.utils.render;

public enum VertexCoordinateMode {
    DEFAULT,
    MINECRAFT;

    private static final VertexCoordinateMode[] DECLARED_VALUES;

    static {
        String[] legacyNames = new String[]{"DEFAULT", "MINECRAFT"};


        DECLARED_VALUES = new VertexCoordinateMode[]{DEFAULT, MINECRAFT};
    }
}
