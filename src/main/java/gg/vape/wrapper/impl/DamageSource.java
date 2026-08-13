package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnumCreatureAttribute;
import gg.vape.wrapper.Wrapper;

public class DamageSource
extends Wrapper {
    public static DamageSource C(EntityPlayer entityPlayer) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return new DamageSource(MEnumCreatureAttribute.X(DamageSource.vapeInstance.getMappings().hW, Minecraft.theWorld().q(), entityPlayer.getObject()));
        }
        return new DamageSource(MEnumCreatureAttribute.X(DamageSource.vapeInstance.getMappings().hW, null, entityPlayer.getObject()));
    }


    public DamageSource(Object object) {
        super(object);
    }

    public static DamageSource m$src$Lgg_vape_wrapper_impl_DamageSource_$z0ibym() {
        return new DamageSource(MEnumCreatureAttribute.E(DamageSource.vapeInstance.getMappings().hW));
    }
}

