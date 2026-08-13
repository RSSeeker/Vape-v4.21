package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MFontGlyph
extends Mapping {
    private static final String INFO_METHOD_NAME = "info";
    private final MappingMethod infoMethod;

    public MFontGlyph() {
        super(MappedClasses.qd);
        this.infoMethod = this.Y(INFO_METHOD_NAME, true, MappedClasses.g, new Class[]{});
    }

    public Object getInfo(Object fontGlyph) {
        return this.infoMethod.invokeObject(fontGlyph, new Object[0]);
    }
}

