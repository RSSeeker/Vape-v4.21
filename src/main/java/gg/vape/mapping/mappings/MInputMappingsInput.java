package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MInputMappingsInput
extends Mapping {
    private static final String KEY_CODE_FIELD_NAME = "keyCode";
    private final MappingField keyCodeField;

    public MInputMappingsInput() {
        super(MappedClasses.zp);
        this.keyCodeField = this.J(KEY_CODE_FIELD_NAME, true, Integer.TYPE);
    }

    private int readKeyCode(Object input) {
        return this.keyCodeField.getInt(input);
    }

    public static int getKeyCode(MInputMappingsInput mapping, Object input) {
        return mapping.readKeyCode(input);
    }
}

