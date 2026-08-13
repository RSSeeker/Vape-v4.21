package gg.vape.module.render.proj;

import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityEnderPearl;

public class EnderPearlProjectileBridge
extends EntityEnderPearl {
    public EnderPearlProjectileBridge(Object handle) {
        super(handle);
    }

    public Entity getOwnerEntity() {
        Object ownerHandle = EnderPearlProjectileBridge.vapeInstance.getMappings()
                .enderPearlProjectileBridge.getOwner(this.getObject());
        return new Entity(ownerHandle);
    }
}
