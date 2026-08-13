package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSoundEventRegistryName
extends Mapping {
    private static final String NAME_FIELD_NAME = "name";
    private final MappingField nameField;

    public static Object getName(MSoundEventRegistryName mapping, Object soundEvent) {
        return mapping.readName(soundEvent);
    }

    private Object readName(Object soundEvent) {
        return this.nameField.getObject(soundEvent);
    }

    public MSoundEventRegistryName() {
        super(MappedClasses.V4);
        this.nameField = this.J(NAME_FIELD_NAME, true, MappedClasses.zC);
    }
}

