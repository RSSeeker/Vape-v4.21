package gg.vape.unmap;

public enum ImageParser$Format {
    ALPHA(1, true),
    LUMINANCE(1, false),
    LUMINANCE_ALPHA(2, true),
    RGB(3, false),
    RGBA(4, true),
    BGRA(4, true),
    ABGR(4, true),
    WHITE(4, true);

    final boolean hasAlphaChannel;
    final int componentCount;
    private static final ImageParser$Format[] DECLARED_VALUES;

    private ImageParser$Format(int componentCount, boolean hasAlphaChannel) {
        this.componentCount = componentCount;
        this.hasAlphaChannel = hasAlphaChannel;
    }

    static {
        String[] legacyNames = new String[]{"LUMINANCE", "WHITE", "ALPHA", "ABGR", "LUMINANCE_ALPHA", "RGB", "BGRA", "RGBA"};








        DECLARED_VALUES = new ImageParser$Format[]{ALPHA, LUMINANCE, LUMINANCE_ALPHA, RGB, RGBA, BGRA, ABGR, WHITE};
    }

    public boolean hasAlphaChannel() {
        return this.hasAlphaChannel;
    }

    public int getComponentCount() {
        return this.componentCount;
    }

}
