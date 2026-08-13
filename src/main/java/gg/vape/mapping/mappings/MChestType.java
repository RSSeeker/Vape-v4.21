package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MChestType
extends Mapping {
    private static final String FALL_DAMAGE_RESETTING_FIELD_NAME = "FALLDAMAGE_RESETTING";
    private final MappingField fallDamageResettingField;

    public MChestType() {
        super(MappedClasses.lN);
        this.fallDamageResettingField = this.registerStaticField(FALL_DAMAGE_RESETTING_FIELD_NAME, true, MappedClasses.lN);
    }

    public Object getFallDamageResetting() {
        return this.fallDamageResettingField.getObject(null);
    }
}

