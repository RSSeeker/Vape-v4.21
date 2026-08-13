package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MGlStateManager_BlendState
extends Mapping {
    public MappingField blendEnabledStateField;

    public Object getBlendEnabledState(Object blendStateHandle) {
        return this.blendEnabledStateField.getObject(blendStateHandle);
    }


    public MGlStateManager_BlendState() {
        this(MEntityRenderer.n());
    }

    private MGlStateManager_BlendState(int initializationState) {
        super(MappedClasses.Yk);
        if (initializationState != 0) {
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                this.blendEnabledStateField = this.J("blend", true, MappedClasses.U);
            } else if (ForgeVersion.MC_1_20_6.d()) {
                this.blendEnabledStateField = this.fieldBuilder("mode", MappedClasses.U).buildField();
            } else {
                this.blendEnabledStateField = this.J("field_179213_a", Wrapper.isNativeAvailable, MappedClasses.U);
            }
            return;
        }
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.blendEnabledStateField = this.fieldBuilder("mode", MappedClasses.U).buildField();
        }
        this.blendEnabledStateField = this.J("field_179213_a", Wrapper.isNativeAvailable, MappedClasses.U);
    }
}
