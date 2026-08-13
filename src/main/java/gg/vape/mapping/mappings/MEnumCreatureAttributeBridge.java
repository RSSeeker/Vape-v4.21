package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEnumCreatureAttributeBridge
extends Mapping {
    private static final String UNDEFINED_FIELD_NAME = "UNDEFINED";
    private final MappingField undefinedField;

    public MEnumCreatureAttributeBridge() {
        super(MappedClasses.O);
        this.undefinedField = this.registerStaticField(UNDEFINED_FIELD_NAME, true, MappedClasses.O);
    }

    public Object getUndefined() {
        return this.undefinedField.getObject(null);
    }
}

