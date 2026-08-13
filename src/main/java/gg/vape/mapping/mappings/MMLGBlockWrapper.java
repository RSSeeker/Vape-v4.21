package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.mappings.MChestTypeHolder;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMLGBlockWrapper
extends Mapping {
    private final MappingField lavaField;
    private final MappingField waterField;

    public static Object getLava(MMLGBlockWrapper mapping) {
        return mapping.readLava();
    }

    private Object readLava() {
        return this.lavaField.getObject(null);
    }


    public MMLGBlockWrapper() {
        this(MChestTypeHolder.getChestTypeHolderControlFlowState());
    }

    private MMLGBlockWrapper(boolean controlFlowState) {
        super(MappedClasses.Za);
        this.waterField = ((MappingFieldBuilder)((MappingFieldBuilder)this.fieldBuilder("WATER", MappedClasses.W).setTypeForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.qC)).setStaticMember(true)).buildField();
        this.lavaField = ((MappingFieldBuilder)((MappingFieldBuilder)this.fieldBuilder("LAVA", MappedClasses.W).setTypeForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.qC)).setStaticMember(true)).buildField();
        if (!controlFlowState) {
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
        }
    }

    private Object readWater() {
        return this.waterField.getObject(null);
    }

    public static Object getWater(MMLGBlockWrapper mapping) {
        return mapping.readWater();
    }
}

