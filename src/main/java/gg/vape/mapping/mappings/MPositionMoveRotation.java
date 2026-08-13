package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MPositionMoveRotation
extends Mapping {
    private static String[] mappingControlFlowState;
    private final MappingField yawField;
    private final MappingField pitchField;
    private final MappingField positionField;
    private final MappingField deltaMovementField;

    public float getPitch(Object values) {
        return this.pitchField.getFloat(values);
    }

    public void setYaw(Object values, float yaw) {
        this.yawField.setFloat(values, yaw);
    }

    public float getYaw(Object values) {
        return this.yawField.getFloat(values);
    }

    public MPositionMoveRotation() {
        this(MPositionMoveRotation.getMappingControlFlowState());
    }

    private MPositionMoveRotation(String[] controlFlowState) {
        super(MappedClasses.Dd);
        this.positionField = this.J("position", true, MappedClasses.qP);
        this.deltaMovementField = this.J("deltaMovement", true, MappedClasses.qP);
        this.yawField = this.J("yRot", true, Float.TYPE);
        this.pitchField = this.J("xRot", true, Float.TYPE);
    }

    public Object getDeltaMovement(Object values) {
        return this.deltaMovementField.getObject(values);
    }

    public static void setMappingControlFlowState(String[] state) {
        mappingControlFlowState = state;
    }

    public static String[] getMappingControlFlowState() {
        return mappingControlFlowState;
    }

    public Object getPosition(Object values) {
        return this.positionField.getObject(values);
    }

    static {
        MPositionMoveRotation.setMappingControlFlowState(null);
    }

    public void setPitch(Object values, float pitch) {
        this.pitchField.setFloat(values, pitch);
    }
}

