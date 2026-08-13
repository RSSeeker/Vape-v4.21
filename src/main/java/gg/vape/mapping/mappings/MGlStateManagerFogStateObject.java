package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MTextComponentTranslationBridge;
import gg.vape.ui.click.component.GuiComponent;

public class MGlStateManagerFogStateObject
extends Mapping {
    private final MappingMethod resetMethod;
    private final MappingField currentField;

    public Object getCurrent(Object fogStateHandle) {
        return this.currentField.getObject(fogStateHandle);
    }

    public MGlStateManagerFogStateObject() {
        this(MTextComponentTranslationBridge.isControlFlowStateEnabled());
    }

    private MGlStateManagerFogStateObject(boolean controlFlowState) {
        super(MappedClasses.i);
        this.currentField = this.J("current", true, MappedClasses.zM);
        this.resetMethod = this.Y("reset", true, Void.TYPE);
        if (controlFlowState) {
            return;
        }
        GuiComponent.setLegacyComponentState(new GuiComponent[4]);
    }


    public void reset(Object fogStateHandle) {
        this.resetMethod.invokeVoidNoArgs(fogStateHandle);
    }
}

