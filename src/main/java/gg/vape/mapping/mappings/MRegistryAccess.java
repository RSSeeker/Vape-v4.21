package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MRegistryAccess
extends Mapping {
    private final MappingMethod lookupOrThrowMethod;
    private static String controlFlowMarker;
    private static final String LOOKUP_OR_THROW_METHOD_NAME;

    static {
        MRegistryAccess.setControlFlowMarker("Wm5ntb");
        LOOKUP_OR_THROW_METHOD_NAME = "lookupOrThrow";
    }

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }

    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    public MRegistryAccess() {
        super(MappedClasses.Fd);
        this.lookupOrThrowMethod = this.Y(LOOKUP_OR_THROW_METHOD_NAME, true, MappedClasses.Fk, new Class[]{MappedClasses.qB});
    }

    public Object lookupOrThrow(Object registryAccess, Object resourceKey) {
        return this.lookupOrThrowMethod.invokeObject(registryAccess, resourceKey);
    }
}

