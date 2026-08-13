package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEntitySize
extends Mapping {
    private final MappingField widthField;
    private final MappingField heightField;

    public MEntitySize() {
        super(MappedClasses.Ve);
        this.widthField = this.J("width", true, Float.TYPE);
        this.heightField = this.J("height", true, Float.TYPE);
    }

    public float getWidth(Object sizeHandle) {
        return this.widthField.getFloat(sizeHandle);
    }

    public float getHeight(Object sizeHandle) {
        return this.heightField.getFloat(sizeHandle);
    }
}
