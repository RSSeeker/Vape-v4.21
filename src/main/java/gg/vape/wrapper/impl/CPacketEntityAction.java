package gg.vape.wrapper.impl;

public class CPacketEntityAction
extends Packet {
    public static CPacketEntityAction create(Entity entity, CPacketEntityActionAction action) {
        return new CPacketEntityAction(CPacketEntityAction.vapeInstance.getMappingsMapperCompat().Y.createPacket(entity.getObject(), action.getObject()));
    }

    public CPacketEntityAction(Object handle) {
        super(handle);
    }

    public static CPacketEntityAction create(Entity entity, int actionId) {
        return new CPacketEntityAction(CPacketEntityAction.vapeInstance.getMappingsMapperCompat().Y.createPacket(entity.getObject(), actionId));
    }
}
