package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSoundEvent
extends Mapping {
    private static final String NAME_FIELD_NAME = "name";
    private final MappingField nameField;

    public MSoundEvent() {
        super(MappedClasses.Y6);
        this.nameField = this.J(NAME_FIELD_NAME, true, MappedClasses.zC);
    }

    public Object getName(Object soundEvent) {
        return this.nameField.getObject(soundEvent);
    }
}

