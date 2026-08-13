package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ForgeEvent
extends Wrapper {
    public void setCancelled(boolean bl) {
        ForgeEvent.vapeInstance.getMappingsMapperCompat().forgeEvent.setCanceled(this.getObject(), bl);
    }

    public ForgeEvent(Object object) {
        super(object);
    }
}
