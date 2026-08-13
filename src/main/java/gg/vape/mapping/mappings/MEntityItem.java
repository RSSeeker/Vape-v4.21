package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityEnderPearl;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityItem
extends Mapping {
    private final MappingMethod getItemMethod;

    public Object getItem(Object entityItemHandle) {
        return this.getItemMethod.invokeObject(entityItemHandle);
    }

    public MEntityItem() {
        this(MEntityEnderPearl.getEntityControlFlowState());
    }

    private MEntityItem(GuiComponent[] entityControlFlowState) {
        super(MappedClasses.zW);
        if (entityControlFlowState != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.getItemMethod = this.Y("getItem", true, MappedClasses.VK);
            } else {
                this.getItemMethod = this.Y(
                        ForgeVersion.c() >= 23 ? "getItem" : "getEntityItem", true, MappedClasses.VK);
            }
            return;
        }
        GuiComponent.setLegacyComponentState(new GuiComponent[4]);
        this.getItemMethod = this.Y(
                ForgeVersion.c() >= 23 ? "getItem" : "getEntityItem", true, MappedClasses.VK);
    }
}
