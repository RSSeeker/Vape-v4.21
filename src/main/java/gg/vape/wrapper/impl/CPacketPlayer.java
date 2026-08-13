package gg.vape.wrapper.impl;

public class CPacketPlayer
extends C03PacketPlayer {
    public static CPacketPlayer create(boolean onGround, boolean horizontalCollision) {
        return new CPacketPlayer(CPacketPlayer.vapeInstance.getMappingsMapperCompat().C_.newInstance(onGround, horizontalCollision));
    }

    private CPacketPlayer(Object wrappedObject) {
        super(wrappedObject);
    }
}
