package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MInputMappingsBridge
extends Mapping {
    private static final String GET_INPUT_BY_CODE = "getInputByCode";
    private final MappingMethod getInputByCodeMethod;

    public MInputMappingsBridge() {
        super(MappedClasses.Q);
        this.getInputByCodeMethod = this.registerStaticMethod(
                GET_INPUT_BY_CODE, true, MappedClasses.zp, new Class[]{Integer.TYPE, Integer.TYPE});
    }

    public Object getInputByCode(int keyCode, int scanCode) {
        return this.getInputByCodeMethod.invokeObject(null, keyCode, scanCode);
    }

    public Object getInputByCode(Object keyEvent) {
        return this.getInputByCodeMethod.invokeObject(null, keyEvent);
    }
}
