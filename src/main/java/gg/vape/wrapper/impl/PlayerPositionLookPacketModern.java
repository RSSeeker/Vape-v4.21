package gg.vape.wrapper.impl;

import java.util.Set;

public class PlayerPositionLookPacketModern
extends Packet {
    public PositionMoveRotation getChange() {
        return new PositionMoveRotation(PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.getChange(this.I));
    }

    public Set getRelatives() {
        return (Set)PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.getRelatives(this.I);
    }

    public void setPitch(float pitch) {
        if (ForgeVersion.MC_1_21_4.d()) {
            PositionMoveRotation change = this.getChange();
            change.setPitch(pitch);
            return;
        }
        PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.setPitch(this.I, pitch);
    }

    public float getPitch() {
        if (ForgeVersion.MC_1_21_4.d()) {
            PositionMoveRotation change = this.getChange();
            return change.getPitch();
        }
        return PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.getPitch(this.I);
    }

    public Set getRelativeFlags() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return this.getRelatives();
        }
        return (Set)PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.getRelativeFlags(this.I);
    }

    public double getY() {
        if (ForgeVersion.MC_1_21_4.d()) {
            PositionMoveRotation change = this.getChange();
            return change.getPosition().getY();
        }
        return PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.getY(this.I);
    }

    public double getX() {
        if (ForgeVersion.MC_1_21_4.d()) {
            PositionMoveRotation change = this.getChange();
            return change.getPosition().getX();
        }
        return PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.getX(this.I);
    }

    public PlayerPositionLookPacketModern(Object handle) {
        super(handle);
    }

    public float getYaw() {
        if (ForgeVersion.MC_1_21_4.d()) {
            PositionMoveRotation change = this.getChange();
            return change.getYaw();
        }
        return PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.getYaw(this.I);
    }

    public void setYaw(float yaw) {
        if (ForgeVersion.MC_1_21_4.d()) {
            PositionMoveRotation change = this.getChange();
            change.setYaw(yaw);
            return;
        }
        PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.setYaw(this.I, yaw);
    }

    public int getTeleportId() {
        return PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.getTeleportId(this.I);
    }

    public double getZ() {
        if (ForgeVersion.MC_1_21_4.d()) {
            PositionMoveRotation change = this.getChange();
            return change.getPosition().getZ();
        }
        return PlayerPositionLookPacketModern.vapeInstance.getMappings().Ry.getZ(this.I);
    }
}

