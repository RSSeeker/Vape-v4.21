package gg.vape.module.debug.rotation;

import gg.vape.mapping.MappedClasses;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.RotationAngles;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.MathUtil;
import gg.vape.utils.Vec3d;
import gg.vape.wrapper.impl.C03PacketPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;

public class RotationDebugSample {
    private final int id;
    private boolean targetAttackSent;
    private RotationAngles rotationDelta;
    private RotationAngles rotation;
    private boolean auxiliaryPacketSent;
    private Vec3d target;
    private RotationAngles controllerRotation;
    private Vec3d position;

    public Vec3d getPosition() {
        return this.position;
    }

    public void markTargetAttackSent() {
        this.targetAttackSent = true;
    }

    public void markAuxiliaryPacketSent() {
        this.auxiliaryPacketSent = true;
    }

    public RotationAngles getRotation() {
        return this.rotation;
    }

    public RotationDebugSample(int id) {
        this.id = id;
    }

    public void capturePacket(C03PacketPlayer c03PacketPlayer, EntityPlayerSP entityPlayerSP) {
        this.position = c03PacketPlayer.isInstance(MappedClasses.ul) || c03PacketPlayer.isInstance(MappedClasses.FK) ? new Vec3d(c03PacketPlayer.getX(), c03PacketPlayer.getY(), c03PacketPlayer.getZ()) : new Vec3d(entityPlayerSP.o$src$D$1u5n7bh(), entityPlayerSP.Q$src$D$1tp5din(), entityPlayerSP.X$src$D$1tszxo6());
        this.rotation = c03PacketPlayer.isInstance(MappedClasses.qw) || c03PacketPlayer.isInstance(MappedClasses.FK) ? new RotationAngles(c03PacketPlayer.getYaw(), c03PacketPlayer.getPitch()) : new RotationAngles(entityPlayerSP.g(), entityPlayerSP.a$src$F$1txy325());
        if (RotationManager.INSTANCE.getActiveController() instanceof AdaptiveRotationController) {
            AdaptiveRotationController adaptiveRotationController = (AdaptiveRotationController)RotationManager.INSTANCE.getActiveController();
            this.controllerRotation = new RotationAngles(adaptiveRotationController.getRenderedYaw(), adaptiveRotationController.getRenderedPitch());
            if (this.target != null) {
                RotationAngles rotationAngles = adaptiveRotationController.calculateRotation(this.target.toVec3().addVector(0.0, entityPlayerSP.X(), 0.0));
                double yawDelta = MathUtil.wrapAngleTo180((double)((rotationAngles.getYaw() - this.rotation.getYaw()) % 360.0f));
                double pitchDelta = MathUtil.wrapAngleTo180((double)((rotationAngles.getPitch() - this.rotation.getPitch()) % 360.0f));
                this.rotationDelta = this.rotation.add(new RotationAngles(yawDelta, pitchDelta));
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.format("%d,%f,%f,%f,%f,%f,%b,%b,", this.id, this.position.getX(), this.position.getY(), this.position.getZ(), Float.valueOf(this.rotation.getYaw() % 360.0f), Float.valueOf(this.rotation.getPitch() % 360.0f), this.auxiliaryPacketSent, this.targetAttackSent));
        if (this.target != null) {
            stringBuilder.append(this.target.getX());
            stringBuilder.append(",");
            stringBuilder.append(this.target.getY());
            stringBuilder.append(",");
            stringBuilder.append(this.target.getZ());
            stringBuilder.append(",");
        } else {
            stringBuilder.append(",,,");
        }
        if (this.controllerRotation != null) {
            stringBuilder.append(this.controllerRotation.getYaw() % 360.0f);
            stringBuilder.append(", ");
            stringBuilder.append(this.controllerRotation.getPitch() % 360.0f);
            stringBuilder.append(",");
        } else {
            stringBuilder.append(",,");
        }
        if (this.rotationDelta != null) {
            stringBuilder.append(this.rotationDelta.getYaw() % 360.0f);
            stringBuilder.append(", ");
            stringBuilder.append(this.rotationDelta.getPitch() % 360.0f);
        } else {
            stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }


    public void setTarget(Vec3d target) {
        this.target = target;
    }
}
