package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MItemCameraTransformLeafBridge
extends Mapping {
    private static final String KEY_FIELD_NAME = "key";
    private final MappingField keyField;

    public Object getKey(Object transform) {
        return this.keyField.getObject(transform);
    }

    public MItemCameraTransformLeafBridge() {
        super(MappedClasses.zo);
        this.keyField = this.J(KEY_FIELD_NAME, true, MappedClasses.qC);
    }
}

