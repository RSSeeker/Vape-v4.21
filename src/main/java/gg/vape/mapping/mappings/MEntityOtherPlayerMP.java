package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityPlayerSP;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityOtherPlayerMP
extends Mapping {
    private static final String CONSTRUCTOR = "<init>";
    private final MappingMethod constructor;

    public Object create(Object worldHandle, Object gameProfileHandle) {
        return this.constructor.newInstance(worldHandle, gameProfileHandle);
    }

    public MEntityOtherPlayerMP() {
        this(MEntityPlayerSP.r());
    }

    private MEntityOtherPlayerMP(GuiComponent[] legacyComponentState) {
        super(MappedClasses.lG);
        if (legacyComponentState != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.constructor = this.Y(CONSTRUCTOR, false, Void.TYPE, MappedClasses.Z, MappedClasses.VD);
            } else {
                this.constructor = this.Y(CONSTRUCTOR, false, Void.TYPE, MappedClasses.YU, MappedClasses.VD);
            }
            return;
        }
        this.constructor = this.Y(CONSTRUCTOR, false, Void.TYPE, MappedClasses.YU, MappedClasses.VD);
    }
}
