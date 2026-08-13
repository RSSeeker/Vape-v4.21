package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class SPacketEntityStatus
extends Wrapper {
    public byte getLogicOpcode() {
        return SPacketEntityStatus.vapeInstance.getMappingsMapperCompat().RO.getLogicOpcode(this.I);
    }

    public int getEntityId() {
        return SPacketEntityStatus.vapeInstance.getMappingsMapperCompat().RO.getEntityId(this.I);
    }

    public SPacketEntityStatus(Object handle) {
        super(handle);
    }
}
