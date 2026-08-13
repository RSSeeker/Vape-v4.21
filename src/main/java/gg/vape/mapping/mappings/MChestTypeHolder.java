package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MChestTypeHolder
extends Mapping {
    private final MappingField goldToolMaterialsField;
    private final MappingField woodenToolMaterialsField;
    private static boolean controlFlowState;

    public MChestTypeHolder() {
        this(MChestTypeHolder.getChestTypeHolderControlFlowState());
    }

    private MChestTypeHolder(boolean initialControlFlowState) {
        super(MappedClasses.ZY);
        this.woodenToolMaterialsField = this.registerStaticField("WOODEN_TOOL_MATERIALS", true, MappedClasses.qC);
        this.goldToolMaterialsField = this.registerStaticField("GOLD_TOOL_MATERIALS", true, MappedClasses.qC);
        if (GuiComponent.getLegacyComponentState() == null) {
            MChestTypeHolder.setChestTypeHolderControlFlowState(!initialControlFlowState);
        }
    }

    public static boolean isChestTypeHolderControlFlowDisabled() {
        return !MChestTypeHolder.getChestTypeHolderControlFlowState();
    }

    public Object getWoodenToolMaterials() {
        return this.woodenToolMaterialsField.getObject(null);
    }


    public static void setChestTypeHolderControlFlowState(boolean state) {
        controlFlowState = state;
    }

    public static boolean getChestTypeHolderControlFlowState() {
        return controlFlowState;
    }

    public Object getGoldToolMaterials() {
        return this.goldToolMaterialsField.getObject(null);
    }

    static {
        MChestTypeHolder.setChestTypeHolderControlFlowState(true);
    }
}

