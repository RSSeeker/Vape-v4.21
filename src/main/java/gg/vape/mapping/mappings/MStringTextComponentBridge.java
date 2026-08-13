package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MTextComponentTranslationBridge;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MStringTextComponentBridge
extends Mapping {
    private MappingField nameField;
    private MappingField itemStackRenderStateField;

    public MStringTextComponentBridge() {
        this(MTextComponentTranslationBridge.isControlFlowStateDisabled());
    }

    private MStringTextComponentBridge(boolean controlFlowState) {
        super(MappedClasses.DE);
        if (controlFlowState) {
            if (GuiComponent.getLegacyComponentState() == null) {
                MTextComponentTranslationBridge.setControlFlowStateEnabled(false);
            }
            return;
        }
        if (ForgeVersion.MC_26_1.v()) {
            this.nameField = this.J("name", true, String.class);
        }
        this.itemStackRenderStateField = this.J("itemStackRenderState", true, MappedClasses.zE);
        if (GuiComponent.getLegacyComponentState() == null) {
            MTextComponentTranslationBridge.setControlFlowStateEnabled(true);
        }
    }

    public String getName(Object componentHandle) {
        if (this.nameField == null) {
            return "";
        }
        return (String)this.nameField.getObject(componentHandle);
    }


    public Object getItemStackRenderState(Object componentHandle) {
        return this.itemStackRenderStateField.getObject(componentHandle);
    }
}
