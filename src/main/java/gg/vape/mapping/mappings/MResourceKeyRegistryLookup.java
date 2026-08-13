package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MRegistryAccess;
import gg.vape.ui.click.component.GuiComponent;
import java.util.Optional;

public class MResourceKeyRegistryLookup
extends Mapping {
    private final MappingMethod getMethod;
    private final MappingMethod getOrThrowMethod;


    public Object getOrThrow(Object lookupHandle, Object resourceKeyHandle) {
        return this.getOrThrowMethod.invokeObject(lookupHandle, resourceKeyHandle);
    }

    public Optional<Object> get(Object lookupHandle, Object resourceKeyHandle) {
        return (Optional)this.getMethod.invokeObject(lookupHandle, resourceKeyHandle);
    }

    public MResourceKeyRegistryLookup() {
        this(MRegistryAccess.getControlFlowMarker());
    }

    private MResourceKeyRegistryLookup(String controlFlowMarker) {
        super(MappedClasses.u2);
        this.getMethod = this.Y("get", true, Optional.class, MappedClasses.qB);
        this.getOrThrowMethod = this.Y("getOrThrow", true, MappedClasses.qx, MappedClasses.qB);
        if (GuiComponent.getLegacyComponentState() == null) {
            MRegistryAccess.setControlFlowMarker("Th1Gp");
        }
    }
}

