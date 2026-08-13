package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class FontGlyphInfo
extends Wrapper {
    public float getShadowOffset() {
        return FontGlyphInfo.vapeInstance.getMappingsMapperCompat().hZ.getShadowOffset(this.I);
    }

    public FontGlyphInfo(Object wrappedObject) {
        super(wrappedObject);
    }

    public float getBoldOffset() {
        return FontGlyphInfo.vapeInstance.getMappingsMapperCompat().hZ.getBoldOffset(this.I);
    }

    public float getAdvance(boolean bold) {
        return FontGlyphInfo.vapeInstance.getMappingsMapperCompat().hZ.getAdvance(this.I, bold);
    }

    public float getAdvance() {
        return FontGlyphInfo.vapeInstance.getMappingsMapperCompat().hZ.getAdvance(this.I);
    }
}
