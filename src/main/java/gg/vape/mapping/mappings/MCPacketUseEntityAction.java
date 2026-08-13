package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MCPacketUseEntityAction
extends Mapping {
    private final MappingMethod getTypeMethod;

    public MCPacketUseEntityAction() {
        super(MappedClasses.lw);
        this.getTypeMethod = this.methodBuilder("getType", MappedClasses.D5, new Class[]{}).setSkipAccessorGeneration(true).buildMethod();
    }

    public Object getType(Object action) {
        return this.getTypeMethod.invokeObject(action, new Object[0]);
    }
}

