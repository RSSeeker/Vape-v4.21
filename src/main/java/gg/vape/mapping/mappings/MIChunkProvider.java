package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MIChunkProvider
extends Mapping {
    private MappingMethod chunkExistsMethod;

    public MIChunkProvider() {
        this(MTickingBlockEntity.getTickingBlockEntityControlFlowState());
    }

    private MIChunkProvider(int[] chunkProviderControlFlowState) {
        super(MappedClasses.lg);
        if (chunkProviderControlFlowState != null) {
            if (ForgeVersion.MC_1_12_2.d()) {
                if (ForgeVersion.MC_1_16_5.d()) {
                    this.chunkExistsMethod = this.Y(
                            "chunkExists", true, Boolean.TYPE, Integer.TYPE, Integer.TYPE);
                } else {
                    this.chunkExistsMethod = this.Y(
                            "func_191062_e", Wrapper.isNativeAvailable, Boolean.TYPE, Integer.TYPE, Integer.TYPE);
                }
            } else {
                this.chunkExistsMethod = this.Y(
                        "chunkExists", true, Boolean.TYPE, Integer.TYPE, Integer.TYPE);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MTickingBlockEntity.setTickingBlockEntityControlFlowState(new int[2]);
            }
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            this.chunkExistsMethod = this.Y("chunkExists", true, Boolean.TYPE, Integer.TYPE, Integer.TYPE);
        }
        this.chunkExistsMethod = this.Y(
                "func_191062_e", Wrapper.isNativeAvailable, Boolean.TYPE, Integer.TYPE, Integer.TYPE);
        this.chunkExistsMethod = this.Y("chunkExists", true, Boolean.TYPE, Integer.TYPE, Integer.TYPE);
        if (GuiComponent.getLegacyComponentState() == null) {
            MTickingBlockEntity.setTickingBlockEntityControlFlowState(new int[2]);
        }
    }


    public boolean chunkExists(Object chunkProviderHandle, int chunkX, int chunkZ) {
        return this.chunkExistsMethod.invokeBoolean(chunkProviderHandle, chunkX, chunkZ);
    }
}

