package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MOpenedChestTypeSentinelBridge
extends Mapping {
    private static final String BASIC_FIELD_NAME = "BASIC";
    private final MappingField basicField;

    public MOpenedChestTypeSentinelBridge() {
        super(MappedClasses.q1);
        this.basicField = this.registerStaticField(BASIC_FIELD_NAME, true, MappedClasses.q1);
    }

    public Object getBasic() {
        return this.basicField.getObject(null);
    }
}

