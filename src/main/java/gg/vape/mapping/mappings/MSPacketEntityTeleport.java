package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSPacketEntityTeleport
extends Mapping {
    private MappingField entityIdField;
    private MappingField zField;
    private MappingField xField;
    private MappingField yField;
    private MappingField yawField;
    private MappingField pitchField;

    public int getY(Object packet) {
        if (ForgeVersion.MC_1_21_4.d()) {
            return (int)this.yField.getDouble(packet);
        }
        return this.yField.getInt(packet);
    }

    public byte getYaw(Object packet) {
        return (byte)this.yawField.getInt(packet);
    }

    public int getZ(Object packet) {
        if (ForgeVersion.MC_1_21_4.d()) {
            return (int)this.zField.getDouble(packet);
        }
        return this.zField.getInt(packet);
    }

    public byte getPitch(Object packet) {
        return (byte)this.pitchField.getInt(packet);
    }

    public int getEntityId(Object packet) {
        return this.entityIdField.getInt(packet);
    }

    public int getX(Object packet) {
        if (ForgeVersion.MC_1_21_4.d()) {
            return (int)this.xField.getDouble(packet);
        }
        return this.xField.getInt(packet);
    }

    public MSPacketEntityTeleport() {
        this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
    }

    private MSPacketEntityTeleport(int[] controlFlowState) {
        super(MappedClasses.uW);
        if (ForgeVersion.MC_1_7_10.L()) {
            this.entityIdField = this.J("field_148957_a", Wrapper.isNativeAvailable, Integer.TYPE);
            this.xField = this.J("field_148956_c", Wrapper.isNativeAvailable, Integer.TYPE);
            this.yField = this.J("field_148953_d", Wrapper.isNativeAvailable, Integer.TYPE);
            this.zField = this.J("field_148954_e", Wrapper.isNativeAvailable, Integer.TYPE);
            this.yawField = this.J("field_148951_f", Wrapper.isNativeAvailable, Byte.TYPE);
            this.pitchField = this.J("field_148952_g", Wrapper.isNativeAvailable, Byte.TYPE);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            this.entityIdField = this.J("entityId", true, Integer.TYPE);
            if (ForgeVersion.MC_1_17.d()) {
                this.xField = this.J("x", true, Double.TYPE);
                this.yField = this.J("y", true, Double.TYPE);
                this.zField = this.J("z", true, Double.TYPE);
            } else {
                this.xField = this.J("x", true, Double.TYPE);
                this.yField = this.J("y", true, Double.TYPE);
                this.zField = this.J("z", true, Double.TYPE);
            }
            this.yawField = this.J("yRot", true, Byte.TYPE);
            this.pitchField = this.J("xRot", true, Byte.TYPE);
        } else {
            this.entityIdField = this.J("entityId", true, Integer.TYPE);
            this.xField = this.J("x", true, Integer.TYPE);
            this.yField = this.J("y", true, Integer.TYPE);
            this.zField = this.J("z", true, Integer.TYPE);
            this.yawField = this.J("yaw", true, Byte.TYPE);
            this.pitchField = this.J("pitch", true, Byte.TYPE);
        }
    }
}

