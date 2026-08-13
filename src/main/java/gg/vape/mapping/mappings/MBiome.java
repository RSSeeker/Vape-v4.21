package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MBiomeRegistrySwitch;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MBiome
extends Mapping {
    private MappingField categoryField;
    private MappingField biomeNameField;

    public MBiome() {
        this(MBiomeRegistrySwitch.getBiomeRegistryControlFlowState());
    }

    private MBiome(int[] biomeRegistryControlFlowState) {
        super(MappedClasses.uK);
        if (ForgeVersion.MC_1_16_5.d() && ForgeVersion.MC_1_20_6.v()) {
            this.categoryField = this.J("category", true, MappedClasses.h);
        } else if (ForgeVersion.MC_1_16_5.v()) {
            this.biomeNameField = this.J("biomeName", true, String.class);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MBiomeRegistrySwitch.setBiomeRegistryControlFlowState(new int[1]);
        }
    }

    public static Object getCategory(MBiome mapping, Object biome) {
        return mapping.readCategory(biome);
    }

    public String getBiomeName(Object biome) {
        return (String)this.biomeNameField.getObject(biome);
    }

    private Object readCategory(Object biome) {
        return this.categoryField.getObject(biome);
    }

}
