package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class CPacketUseEntityAction
extends Wrapper {
    private CPacketUseEntityAction(Object handle) {
        super(handle);
    }

    public CPacketUseEntityAction(Object handle, CPacketUseEntityActionConstructorMarker constructorMarker) {
        this(handle);
    }

    public CPacketUseEntity getType() {
        return new CPacketUseEntity(CPacketUseEntityAction.vapeInstance.getMappingsMapperCompat().qz.getType(this.I), null);
    }
}
