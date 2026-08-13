package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MSoundAwareEntityFX;

public class SoundAwareEntityFX
extends EntityFX {
    public EnumParticleTypes Z$src$Lgg_vape_wrapper_impl_EnumParticleTypes_$1aa3947() {
        return new EnumParticleTypes(MSoundAwareEntityFX.b(SoundAwareEntityFX.vapeInstance.getMappings().R4, this.I));
    }

    public void O(Entity entity) {
        MSoundAwareEntityFX.u(SoundAwareEntityFX.vapeInstance.getMappings().R4, this.I, entity.getObject());
    }

    public Entity M$src$Lgg_vape_wrapper_impl_Entity_$1791qxt() {
        return new Entity(MSoundAwareEntityFX.q(SoundAwareEntityFX.vapeInstance.getMappings().R4, this.I));
    }

    public SoundAwareEntityFX(Object object) {
        super(object);
    }
}

