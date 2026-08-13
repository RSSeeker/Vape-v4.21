package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MPacketIdFactory;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketPlayer_PositionRotation
extends Mapping {
    private MappingMethod positionRotationPacketConstructor;

    public MCPacketPlayer_PositionRotation() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketPlayer_PositionRotation(GuiComponent[] controlFlowState) {
        super(MappedClasses.FK);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_21_4.d()) {
                this.positionRotationPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE, Boolean.TYPE});
            } else if (ForgeVersion.MC_1_7_10.Y()) {
                this.positionRotationPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE});
            } else {
                this.positionRotationPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE});
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MPacketIdFactory.setPacketMappingControlFlowState(new GuiComponent[2]);
            }
            return;
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            this.positionRotationPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE});
        }
        this.positionRotationPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE});
        if (GuiComponent.getLegacyComponentState() == null) {
            MPacketIdFactory.setPacketMappingControlFlowState(new GuiComponent[2]);
        }
    }

    public Object createLegacyPositionRotationPacket(double x, double feetY, double stanceY, double z, float yaw, float pitch, boolean onGround) {
        return this.positionRotationPacketConstructor.newInstance(x, feetY, stanceY, z, Float.valueOf(yaw), Float.valueOf(pitch), onGround);
    }

    public Object createPositionRotationPacket(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        return this.positionRotationPacketConstructor.newInstance(x, y, z, Float.valueOf(yaw), Float.valueOf(pitch), onGround);
    }

    public Object createPositionRotationPacket(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean horizontalCollision) {
        return this.positionRotationPacketConstructor.newInstance(x, y, z, Float.valueOf(yaw), Float.valueOf(pitch), onGround, horizontalCollision);
    }
}
