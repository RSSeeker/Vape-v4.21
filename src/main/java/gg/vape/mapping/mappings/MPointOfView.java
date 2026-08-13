package gg.vape.mapping.mappings;

import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MPointOfView
extends Mapping {
    private static final String VALUES_METHOD_NAME = "values";
    private final MappingMethod valuesMethod;

    public Object[] values() {
        return this.valuesMethod.invokeObjectArray(null, new Object[0]);
    }

    public MPointOfView() {
        super(MappedClasses.ZR);
        this.valuesMethod = this.registerStaticMethod(VALUES_METHOD_NAME, true, DescUtils.getArrayType(MappedClasses.ZR), new Class[]{});
    }
}

