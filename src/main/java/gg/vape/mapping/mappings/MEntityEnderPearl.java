package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityEnderPearl
extends Mapping {
    private MappingMethod getFuseMethod;
    private MappingField fuseField;
    private static GuiComponent[] controlFlowState;

    public int getFuse(Object entityHandle) {
        if (ForgeVersion.MC_1_17.d()) {
            return this.getFuseMethod.invokeInt(entityHandle);
        }
        return this.fuseField.getInt(entityHandle);
    }

    public MEntityEnderPearl() {
        this(MEntityEnderPearl.getEntityControlFlowState());
    }

    private MEntityEnderPearl(GuiComponent[] entityControlFlowState) {
        super(MappedClasses.qM);
        if (entityControlFlowState != null) {
            if (ForgeVersion.MC_1_17.d()) {
                this.getFuseMethod = this.Y("getFuse", true, Integer.TYPE);
            } else {
                this.fuseField = this.J("fuse", true, Integer.TYPE);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MEntityEnderPearl.setEntityControlFlowState(new GuiComponent[1]);
            }
            return;
        }
        this.fuseField = this.J("fuse", true, Integer.TYPE);
        if (GuiComponent.getLegacyComponentState() == null) {
            MEntityEnderPearl.setEntityControlFlowState(new GuiComponent[1]);
        }
    }

    static {
        MEntityEnderPearl.setEntityControlFlowState(new GuiComponent[3]);
    }

    public static GuiComponent[] getEntityControlFlowState() {
        return controlFlowState;
    }


    public static void setEntityControlFlowState(GuiComponent[] state) {
        controlFlowState = state;
    }
}
