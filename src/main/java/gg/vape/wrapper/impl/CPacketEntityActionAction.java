package gg.vape.wrapper.impl;

public class CPacketEntityActionAction
extends CPacketEntityAction {
    public static CPacketEntityActionAction startSneaking() {
        return new CPacketEntityActionAction(CPacketEntityActionAction.vapeInstance.getMappingsMapperCompat().DQ.getStartSneakingAction());
    }

    public static CPacketEntityActionAction stopSneaking() {
        return new CPacketEntityActionAction(CPacketEntityActionAction.vapeInstance.getMappingsMapperCompat().DQ.getStopSneakingAction());
    }

    public CPacketEntityActionAction(Object handle) {
        super(handle);
    }
}
