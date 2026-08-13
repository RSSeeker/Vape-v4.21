package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityPlayerSP;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MAbstractClientPlayerBridge
extends Mapping {
    private MappingField clientAvatarStateField;
    private MappingMethod locationSkinMethod;

    public Object getClientAvatarState(Object playerHandle) {
        return this.clientAvatarStateField.getObject(playerHandle);
    }

    public MAbstractClientPlayerBridge() {
        this(MEntityPlayerSP.r());
    }

    private MAbstractClientPlayerBridge(GuiComponent[] guiComponentArray) {
        super(MappedClasses.zt);
        if (guiComponentArray != null) {
            if (ForgeVersion.MC_1_21_10.d() && Vape.INSTANCE.isFabricMinecraftPresent()) {
                this.locationSkinMethod = this.registerInstanceMethodForOwner(
                        MappedClasses.lB, "getSkin", true, MappedClasses.uZ, new Class[]{});
            } else {
                this.locationSkinMethod = this.Y("getLocationSkin", true, MappedClasses.zC, new Class[]{});
            }
            if (ForgeVersion.MC_1_21_10.d()) {
                this.clientAvatarStateField = this.J("clientAvatarState", true, MappedClasses.zT);
            }
            return;
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            this.locationSkinMethod = this.registerInstanceMethodForOwner(
                    MappedClasses.lB, "getSkin", true, MappedClasses.uZ, new Class[]{});
        }
        this.locationSkinMethod = this.Y("getLocationSkin", true, MappedClasses.zC, new Class[]{});
        if (ForgeVersion.MC_1_21_10.d()) {
            this.clientAvatarStateField = this.J("clientAvatarState", true, MappedClasses.zT);
        }
    }

    public Object getLocationSkin(Object playerHandle) {
        return this.locationSkinMethod.invokeObject(playerHandle, new Object[0]);
    }
}
