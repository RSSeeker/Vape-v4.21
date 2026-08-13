package gg.vape.wrapper.impl;

public class C06PacketPlayerPositionLook
extends C03PacketPlayer {
    public static C06PacketPlayerPositionLook create(double x, double feetY, double stanceY, double z, float yaw, float pitch, boolean onGround) {
        return new C06PacketPlayerPositionLook(C06PacketPlayerPositionLook.vapeInstance.getMappings().qq.createLegacyPositionRotationPacket(x, feetY, stanceY, z, yaw, pitch, onGround));
    }

    private C06PacketPlayerPositionLook(Object handle) {
        super(handle);
    }

    public static C06PacketPlayerPositionLook create(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        if (ForgeVersion.MC_1_21_4.d()) {
            EntityPlayerSP player = Minecraft.thePlayer();
            return new C06PacketPlayerPositionLook(C06PacketPlayerPositionLook.vapeInstance.getMappings().qq.createPositionRotationPacket(x, y, z, yaw, pitch, onGround, player.r()));
        }
        return new C06PacketPlayerPositionLook(C06PacketPlayerPositionLook.vapeInstance.getMappings().qq.createPositionRotationPacket(x, y, z, yaw, pitch, onGround));
    }
}
