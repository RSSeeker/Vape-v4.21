package gg.vape.wrapper.impl;

import gg.vape.utils.MutableFloatTriple;
import gg.vape.wrapper.Wrapper;

public class CPacketPlayerBlockPlacement
extends Wrapper {
    public float getFacingX() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
            return this.getFacingVector().getX();
        }
        return CPacketPlayerBlockPlacement.vapeInstance.getMappings().D3.getFacingX(this.getObject());
    }

    public Object getHand() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
            return CPacketPlayerBlockPlacement.vapeInstance.getMappings().D3.getHand(this.I);
        }
        throw new UnsupportedOperationException("Unimplemented");
    }

    public ItemStack getItemStack() {
        if (ForgeVersion.MC_1_12_2.d()) {
            return Minecraft.thePlayer().getHeldItemHand();
        }
        return new ItemStack(CPacketPlayerBlockPlacement.vapeInstance.getMappings().D3.getItemStack(this.I));
    }

    public BlockRayTraceResult getBlockHit() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
            return new BlockRayTraceResult(CPacketPlayerBlockPlacement.vapeInstance.getMappings().D3.getBlockHit(this.I));
        }
        throw new UnsupportedOperationException("Unimplemented");
    }

    public int getSequence() {
        if (ForgeVersion.MC_1_21_11.d()) {
            return CPacketPlayerBlockPlacement.vapeInstance.getMappings().D3.getSequence(this.I);
        }
        return -1;
    }

    public float getFacingZ() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
            return this.getFacingVector().getZ();
        }
        return CPacketPlayerBlockPlacement.vapeInstance.getMappings().D3.getFacingZ(this.getObject());
    }

    public MutableFloatTriple getFacingVector() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
            BlockRayTraceResult blockHit = this.getBlockHit();
            BlockPos blockPosition = blockHit.getBlockPos();
            Vec3 hitLocation = blockHit.getHitVec();
            float facingX = (float)(hitLocation.getX() - (double)blockPosition.getX());
            float facingY = (float)(hitLocation.getY() - (double)blockPosition.getY());
            float facingZ = (float)(hitLocation.getZ() - (double)blockPosition.getZ());
            return new MutableFloatTriple(facingX, facingY, facingZ);
        }
        return new MutableFloatTriple(this.getFacingX(), this.getFacingY(), this.getFacingZ());
    }

    public float getFacingY() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
            return this.getFacingVector().getY();
        }
        return CPacketPlayerBlockPlacement.vapeInstance.getMappings().D3.getFacingY(this.getObject());
    }

    private static UnsupportedOperationException propagateException(UnsupportedOperationException exception) {
        return exception;
    }

    public CPacketPlayerBlockPlacement(Object handle) {
        super(handle);
    }

    public BlockPos getBlockPosition() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
            return this.getBlockHit().getBlockPos();
        }
        return new BlockPos(CPacketPlayerBlockPlacement.vapeInstance.getMappings().D3.getBlockPosition(this.getObject()));
    }

    public int getPlacedBlockDirection() {
        if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
            return this.getBlockHit().Z();
        }
        return CPacketPlayerBlockPlacement.vapeInstance.getMappings().D3.getPlacedBlockDirection(this.getObject());
    }
}
