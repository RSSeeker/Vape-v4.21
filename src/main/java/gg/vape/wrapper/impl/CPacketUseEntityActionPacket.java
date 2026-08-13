package gg.vape.wrapper.impl;

public class CPacketUseEntityActionPacket
extends CPacketUseEntityAction {
    public CPacketUseEntityActionPacket(Object wrappedObject) {
        super(wrappedObject, null);
    }

    public Vec3 getLocation() {
        return new Vec3(CPacketUseEntityActionPacket.vapeInstance.getMappingsMapperCompat().h6.getLocation(this.I));
    }
}
