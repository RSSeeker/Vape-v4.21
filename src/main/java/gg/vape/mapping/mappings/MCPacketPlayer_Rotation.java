package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MPacketIdFactory;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketPlayer_Rotation
extends Mapping {
    private final MappingMethod rotationPacketConstructor;

    public MCPacketPlayer_Rotation() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketPlayer_Rotation(GuiComponent[] controlFlowState) {
        super(MappedClasses.qw);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_21_4.d()) {
                this.rotationPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Float.TYPE, Float.TYPE, Boolean.TYPE, Boolean.TYPE});
            } else {
                this.rotationPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Float.TYPE, Float.TYPE, Boolean.TYPE});
            }
            return;
        }
        this.rotationPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Float.TYPE, Float.TYPE, Boolean.TYPE});
    }

    public Object createRotationPacket(float yaw, float pitch, boolean onGround) {
        return this.rotationPacketConstructor.newInstance(Float.valueOf(yaw), Float.valueOf(pitch), onGround);
    }

    public Object createRotationPacket(float yaw, float pitch, boolean onGround, boolean horizontalCollision) {
        return this.rotationPacketConstructor.newInstance(Float.valueOf(yaw), Float.valueOf(pitch), onGround, horizontalCollision);
    }
}
