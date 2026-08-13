package gg.vape.module.blatant.backtrack;

import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.PlayerLocationSnapshot;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.NetworkPlayerInfo;
import gg.vape.wrapper.impl.SEntityPacket;
import gg.vape.wrapper.impl.SPacketEntity;
import gg.vape.wrapper.impl.Vec3;

public class BacktrackPacketState {
    private long encodedZ;
    private long encodedX;
    private long encodedY;

    public void applyAbsolutePacket(SPacketEntity entityPacket) {
        this.encodedX = entityPacket.getX();
        this.encodedY = entityPacket.getY();
        this.encodedZ = entityPacket.getZ();
    }

    public void applyNetworkPlayerInfo(NetworkPlayerInfo playerInfo) {
        Vec3 position = playerInfo.getValues().getPosition();
        this.encodedX = MathUtil.floor(position.getX() * BacktrackPacketState.positionScale());
        this.encodedY = MathUtil.floor(position.getY() * BacktrackPacketState.positionScale());
        this.encodedZ = MathUtil.floor(position.getZ() * BacktrackPacketState.positionScale());
    }

    public static BacktrackPacketState fromEntity(Entity entity) {
        return new BacktrackPacketState(MathUtil.floor(entity.double_z() * BacktrackPacketState.positionScale()), MathUtil.floor(entity.double_N() * BacktrackPacketState.positionScale()), MathUtil.floor(entity.double_h() * BacktrackPacketState.positionScale()));
    }

    private static double positionScale() {
        return ForgeVersion.MC_1_21_4.d() ? 4096.0 : 32.0;
    }

    public PlayerLocationSnapshot toSnapshot() {
        return new PlayerLocationSnapshot((double)this.encodedX / BacktrackPacketState.positionScale(), (double)this.encodedY / BacktrackPacketState.positionScale(), (double)this.encodedZ / BacktrackPacketState.positionScale());
    }


    public void applyRelativeMove(SEntityPacket entityPacket) {
        this.encodedX += (long)entityPacket.getDeltaX();
        this.encodedY += (long)entityPacket.getDeltaY();
        this.encodedZ += (long)entityPacket.getDeltaZ();
    }

    public BacktrackPacketState(int x, int y, int z) {
        this.encodedX = x;
        this.encodedY = y;
        this.encodedZ = z;
    }
}

