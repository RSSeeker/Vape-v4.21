package gg.vape.wrapper.impl;

public class CPacketPlayerPosition
extends C03PacketPlayer {
    public static CPacketPlayerPosition newInstance(double x, double y, double z, boolean onGround) {
        if (ForgeVersion.MC_1_21_4.d()) {
            return new CPacketPlayerPosition(CPacketPlayerPosition.vapeInstance.getMappingsMapperCompat().R5.createPositionPacket(x, y, z, onGround, false));
        }
        return new CPacketPlayerPosition(CPacketPlayerPosition.vapeInstance.getMappingsMapperCompat().R5.createPositionPacket(x, y, z, onGround));
    }

    private CPacketPlayerPosition(Object handle) {
        super(handle);
    }

    public static CPacketPlayerPosition newInstance(double x, double feetY, double stanceY, double z, boolean onGround) {
        return new CPacketPlayerPosition(CPacketPlayerPosition.vapeInstance.getMappingsMapperCompat().R5.createLegacyPositionPacket(x, feetY, stanceY, z, onGround));
    }

}

