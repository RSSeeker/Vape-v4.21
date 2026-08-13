package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Optional;

public class MPacketVelocityBridge
extends Mapping {
    private MappingField motionYField;
    private MappingField motionZField;
    private MappingField playerKnockbackField;
    private MappingField motionXField;

    public MPacketVelocityBridge() {
        this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
    }

    private MPacketVelocityBridge(int[] controlFlowState) {
        super(MappedClasses.qe);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_21_4.d()) {
                this.playerKnockbackField = this.J("playerKnockback", true, Optional.class);
            } else {
                this.motionXField = this.J("field_149152_f", Wrapper.isNativeAvailable, Float.TYPE);
                this.motionYField = this.J("field_149153_g", Wrapper.isNativeAvailable, Float.TYPE);
                this.motionZField = this.J("field_149159_h", Wrapper.isNativeAvailable, Float.TYPE);
            }
            return;
        }
        this.motionZField = this.J("field_149159_h", Wrapper.isNativeAvailable, Float.TYPE);
    }

    public float getMotionX(Object packet) {
        return this.motionXField.getFloat(packet);
    }

    public void setMotionZ(Object packet, float motionZ) {
        this.motionZField.setFloat(packet, motionZ);
    }

    public void setMotionX(Object packet, float motionX) {
        this.motionXField.setFloat(packet, motionX);
    }

    public Optional<Object> getPlayerKnockback(Object packet) {
        return (Optional<Object>)this.playerKnockbackField.getObject(packet);
    }

    public void setMotionY(Object packet, float motionY) {
        this.motionYField.setFloat(packet, motionY);
    }

    public float getMotionY(Object packet) {
        return this.motionYField.getFloat(packet);
    }

    public float getMotionZ(Object packet) {
        return this.motionZField.getFloat(packet);
    }
}

