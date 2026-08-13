package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MMappedFieldSingletonWrapper
extends Mapping {
    private static final String HUMANOID_ARMOR_FIELD_NAME = "HUMANOID_ARMOR";
    private final MappingField humanoidArmorField;

    public MMappedFieldSingletonWrapper() {
        super(MappedClasses.Vf);
        this.humanoidArmorField = this.registerStaticField(HUMANOID_ARMOR_FIELD_NAME, true, MappedClasses.Vf);
    }

    public static MappingField getHumanoidArmorField(MMappedFieldSingletonWrapper mapping) {
        return mapping.humanoidArmorField;
    }
}

