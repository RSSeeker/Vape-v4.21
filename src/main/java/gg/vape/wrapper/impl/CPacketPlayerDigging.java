package gg.vape.wrapper.impl;

public class CPacketPlayerDigging
extends Packet {
    public PlayerDiggingAction getAction() {
        return new PlayerDiggingAction(CPacketPlayerDigging.vapeInstance.getMappingsMapperCompat().qA.getAction(this.I));
    }

    public int getLegacyActionId() {
        return CPacketPlayerDigging.vapeInstance.getMappingsMapperCompat().qA.getLegacyActionId(this.I);
    }

    public boolean isReleaseUseItem() {
        return ForgeVersion.MC_1_8_9.d() ? this.getAction().equals(PlayerDiggingAction.releaseUseItem()) : this.getLegacyActionId() == 5;
    }

    public BlockPos getPosition() {
        return new BlockPos(CPacketPlayerDigging.vapeInstance.getMappingsMapperCompat().qA.getPosition(this.I));
    }

    public EnumFacing getFacing() {
        return new EnumFacing(CPacketPlayerDigging.vapeInstance.getMappingsMapperCompat().qA.getFacing(this.I));
    }

    public CPacketPlayerDigging(Object handle) {
        super(handle);
    }
}

