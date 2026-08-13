package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityPlayerMacroBridge;
import gg.vape.module.render.proj.EnderPearlProjectileBridge;

public class EntityPlayerMacroBridge
extends Entity {
    public Entity A$src$Lgg_vape_wrapper_impl_Entity_$12ijiu4() {
        if (ForgeVersion.MC_1_16_5.v()) {
            return new EntityPlayer(MEntityPlayerMacroBridge.F(EntityPlayerMacroBridge.vapeInstance.getMappings().Da, this.I));
        }
        return new EnderPearlProjectileBridge(this.getObject()).getOwnerEntity();
    }

    public Entity r$src$Lgg_vape_wrapper_impl_Entity_$18p7x3h() {
        return new Entity(MEntityPlayerMacroBridge.W(EntityPlayerMacroBridge.vapeInstance.getMappings().Da, this.I));
    }


    public EntityPlayerMacroBridge(Object object) {
        super(object);
    }

    public boolean o() {
        return MEntityPlayerMacroBridge.B(EntityPlayerMacroBridge.vapeInstance.getMappings().Da, this.I);
    }
}

