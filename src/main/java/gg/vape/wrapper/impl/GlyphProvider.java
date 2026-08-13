package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGlyphProvider;
import gg.vape.wrapper.Wrapper;

public class GlyphProvider
extends Wrapper {
    public GlyphProvider(Object handle) {
        super(handle);
    }

    public FontGlyph getGlyph(int codePoint) {
        return new FontGlyph(MGlyphProvider.getGlyph(GlyphProvider.vapeInstance.getMappingsMapperCompat().glyphSource, this.I, codePoint));
    }
}
