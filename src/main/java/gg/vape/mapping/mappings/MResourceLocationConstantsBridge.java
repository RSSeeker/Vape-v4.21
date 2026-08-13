package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ResourceLocationConstantPair;

public class MResourceLocationConstantsBridge
extends Mapping {
    private final MappingField guiField;
    private final MappingField itemsField;

    public Object getItems() {
        return this.itemsField.getObject(null);
    }

    public Object getGui() {
        return this.guiField.getObject(null);
    }


    public MResourceLocationConstantsBridge() {
        this(ResourceLocationConstantPair.getControlFlowState());
    }

    private MResourceLocationConstantsBridge(GuiComponent[] controlFlowState) {
        super(MappedClasses.qq);
        this.guiField = this.registerStaticField("GUI", true, MappedClasses.zC);
        this.itemsField = this.registerStaticField("ITEMS", true, MappedClasses.zC);
        if (controlFlowState != null) {
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
        }
    }
}

