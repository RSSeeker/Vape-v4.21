package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class FontSet
extends Wrapper {
    public FontGlyph getGlyph(int codePoint, boolean filterFishyGlyphs) {
        GlyphProvider glyphSource = this.getGlyphSource(filterFishyGlyphs);
        if (glyphSource.isNull()) {
            return null;
        }
        FontGlyph resolvedGlyph = glyphSource.getGlyph(codePoint);
        FontGlyph fontGlyph = resolvedGlyph != null ? resolvedGlyph : this.getMissingGlyph();
        return fontGlyph;
    }

    public FontGlyph getMissingGlyph() {
        Object glyphHandle = FontSet.vapeInstance.getMappingsMapperCompat().fontSet.getMissingGlyph(this.I);
        FontGlyph fontGlyph = glyphHandle != null ? new FontGlyph(glyphHandle) : null;
        return fontGlyph;
    }

    public FontGlyph getGlyph(char character) {
        return this.getGlyph(character, false);
    }

    public GlyphProvider getGlyphSource(boolean filterFishyGlyphs) {
        return new GlyphProvider(FontSet.vapeInstance.getMappingsMapperCompat().fontSet.getSource(this.I, filterFishyGlyphs));
    }

    public GlyphInfo getGlyphInfo(char character) {
        FontGlyph fontGlyph = this.getGlyph(character);
        GlyphInfo glyphInfo = fontGlyph != null ? fontGlyph.asGlyphInfo() : null;
        return glyphInfo;
    }

    public FontSet(Object handle) {
        super(handle);
    }

}

