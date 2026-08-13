package gg.vape.wrapper.impl;

public class CPacketPlayer_Rotation
extends C03PacketPlayer {
    private CPacketPlayer_Rotation(Object handle) {
        super(handle);
    }

    public static CPacketPlayer_Rotation create(float yaw, float pitch, boolean onGround) {
        if (ForgeVersion.MC_1_21_4.d()) {
            EntityPlayerSP player = Minecraft.a_xH_J();
            return new CPacketPlayer_Rotation(CPacketPlayer_Rotation.vapeInstance.getMappingsMapperCompat().Cv.createRotationPacket(yaw, pitch, onGround, player.boolean_r()));
        }
        return new CPacketPlayer_Rotation(CPacketPlayer_Rotation.vapeInstance.getMappingsMapperCompat().Cv.createRotationPacket(yaw, pitch, onGround));
    }
}
