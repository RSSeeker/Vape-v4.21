package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MVoxelShapeBridge
extends Mapping {
    private final MappingMethod bufferSourceMethod;

    public MVoxelShapeBridge() {
        super(MappedClasses.ZL);
        this.bufferSourceMethod = this.Y("bufferSource", true, MappedClasses.lp, new Class[]{});
    }

    public Object getBufferSource(Object renderer) {
        return this.bufferSourceMethod.invokeObject(renderer, new Object[0]);
    }
}

