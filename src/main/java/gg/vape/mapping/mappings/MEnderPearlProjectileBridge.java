package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntity;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEnderPearlProjectileBridge
extends Mapping {
    private MappingMethod getOwnerMethod;

    public MEnderPearlProjectileBridge() {
        this(MEntity.P());
    }

    private MEnderPearlProjectileBridge(int entityControlFlowState) {
        super(MappedClasses.lv);
        if (entityControlFlowState != 0) {
            if (ForgeVersion.MC_1_21_4.d()) {
                this.getOwnerMethod = this.Y("getOwner", true, MappedClasses.zc);
            }
            return;
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            this.getOwnerMethod = this.registerInstanceMethodForOwner(
                    MappedClasses.YV, "getOwner", true, MappedClasses.zc);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            this.getOwnerMethod = this.Y("getOwner", true, MappedClasses.zc);
        }
    }

    public Object getOwner(Object projectileHandle) {
        return this.getOwnerMethod.invokeObject(projectileHandle);
    }

}
