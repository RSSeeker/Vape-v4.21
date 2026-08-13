package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSPacketEntity
extends Mapping {
    private MappingField changeField;
    private MappingField entityIdField;
    private MappingField yawField;
    private MappingField zField;
    private MappingField pitchField;
    private MappingField xField;
    private MappingField yField;

    public Object getChange(Object packet) {
        return this.changeField.getObject(packet);
    }

    public int getX(Object packet) {
        return this.xField.getInt(packet);
    }

    public byte getYaw(Object packet) {
        return (byte)this.yawField.getInt(packet);
    }

    public int getY(Object packet) {
        return this.yField.getInt(packet);
    }

    public int getZ(Object packet) {
        return this.zField.getInt(packet);
    }

    public int getEntityId(Object packet) {
        return this.entityIdField.getInt(packet);
    }

    public MSPacketEntity() {
        this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
    }

    private MSPacketEntity(int[] controlFlowState) {
        super(MappedClasses.s);
        if (ForgeVersion.MC_1_21_4.d()) {
            this.changeField = this.J("change", true, MappedClasses.Dd);
            this.entityIdField = this.J("id", true, Integer.TYPE);
        } else if (ForgeVersion.MC_1_7_10.L()) {
            this.entityIdField = this.J("field_149458_a", Wrapper.isNativeAvailable, Integer.TYPE);
            this.xField = this.J("field_149456_b", Wrapper.isNativeAvailable, Integer.TYPE);
            this.yField = this.J("field_149457_c", Wrapper.isNativeAvailable, Integer.TYPE);
            this.zField = this.J("field_149454_d", Wrapper.isNativeAvailable, Integer.TYPE);
            this.yawField = this.J("field_149455_e", Wrapper.isNativeAvailable, Byte.TYPE);
            this.pitchField = this.J("field_149453_f", Wrapper.isNativeAvailable, Byte.TYPE);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            this.entityIdField = this.J("id", true, Integer.TYPE);
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
            this.xField = this.J("posX", true, Integer.TYPE);
            this.yField = this.J("posY", true, Integer.TYPE);
            this.zField = this.J("posZ", true, Integer.TYPE);
            this.yawField = this.J("yaw", true, Byte.TYPE);
            this.pitchField = this.J("pitch", true, Byte.TYPE);
        }
    }

    public byte getPitch(Object packet) {
        return (byte)this.pitchField.getInt(packet);
    }
}

