package gg.vape.wrapper.impl;

import java.util.Optional;

public class PacketVelocityBridge
extends Packet {
    private void setPlayerKnockbackX(float motionX) {
        Optional<Object> playerKnockback = this.getPlayerKnockback();
        if (playerKnockback.isPresent()) {
            Vec3 knockback = new Vec3(playerKnockback.get());
            knockback.N(motionX);
        }
    }

    public void setMotionY(float motionY) {
        if (ForgeVersion.MC_1_21_0.d()) {
            this.setPlayerKnockbackY(motionY);
            return;
        }
        PacketVelocityBridge.vapeInstance.getMappingsMapperCompat().qO.setMotionY(this.I, motionY);
    }

    public float getMotionZ() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return this.getPlayerKnockbackZ();
        }
        return PacketVelocityBridge.vapeInstance.getMappingsMapperCompat().qO.getMotionZ(this.I);
    }

    private float getPlayerKnockbackZ() {
        Optional<Object> playerKnockback = this.getPlayerKnockback();
        if (playerKnockback.isPresent()) {
            Vec3 knockback = new Vec3(playerKnockback.get());
            return (float)knockback.getZ();
        }
        return 0.0f;
    }

    public void setMotionX(float motionX) {
        if (ForgeVersion.MC_1_21_0.d()) {
            this.setPlayerKnockbackX(motionX);
            return;
        }
        PacketVelocityBridge.vapeInstance.getMappingsMapperCompat().qO.setMotionX(this.I, motionX);
    }

    public float getMotionY() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return this.getPlayerKnockbackY();
        }
        return PacketVelocityBridge.vapeInstance.getMappingsMapperCompat().qO.getMotionY(this.I);
    }

    public PacketVelocityBridge(Object handle) {
        super(handle);
    }

    private void setPlayerKnockbackZ(float motionZ) {
        Optional<Object> playerKnockback = this.getPlayerKnockback();
        if (playerKnockback.isPresent()) {
            Vec3 knockback = new Vec3(playerKnockback.get());
            knockback.Z(motionZ);
        }
    }

    public void setMotionZ(float motionZ) {
        if (ForgeVersion.MC_1_21_0.d()) {
            this.setPlayerKnockbackZ(motionZ);
            return;
        }
        PacketVelocityBridge.vapeInstance.getMappingsMapperCompat().qO.setMotionZ(this.I, motionZ);
    }

    private float getPlayerKnockbackX() {
        Optional<Object> playerKnockback = this.getPlayerKnockback();
        if (playerKnockback.isPresent()) {
            Vec3 knockback = new Vec3(playerKnockback.get());
            return (float)knockback.getX();
        }
        return 0.0f;
    }

    private void setPlayerKnockbackY(float motionY) {
        Optional<Object> playerKnockback = this.getPlayerKnockback();
        if (playerKnockback.isPresent()) {
            Vec3 knockback = new Vec3(playerKnockback.get());
            knockback.m(motionY);
        }
    }

    private float getPlayerKnockbackY() {
        Optional<Object> playerKnockback = this.getPlayerKnockback();
        if (playerKnockback.isPresent()) {
            Vec3 knockback = new Vec3(playerKnockback.get());
            return (float)knockback.getY();
        }
        return 0.0f;
    }

    public float getMotionX() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return this.getPlayerKnockbackX();
        }
        return PacketVelocityBridge.vapeInstance.getMappingsMapperCompat().qO.getMotionX(this.I);
    }

    public Optional<Object> getPlayerKnockback() {
        return PacketVelocityBridge.vapeInstance.getMappingsMapperCompat().qO.getPlayerKnockback(this.I);
    }

}

