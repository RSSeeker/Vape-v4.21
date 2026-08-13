package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MDirectionVector
extends Mapping {
    private static final String POSITIVE_FIELD_NAME = "POSITIVE";
    private final MappingField positiveField;

    public static Object getPositive(MDirectionVector mapping) {
        return mapping.readPositive();
    }

    public MDirectionVector() {
        super(MappedClasses.Vy);
        this.positiveField = this.registerStaticField(POSITIVE_FIELD_NAME, true, MappedClasses.Vy);
    }

    private Object readPositive() {
        return this.positiveField.getObject(null);
    }
}

