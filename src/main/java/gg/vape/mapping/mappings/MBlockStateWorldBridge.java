package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MBlockStateWorldBridge
extends Mapping {
    private final MappingMethod getTypeMethod;
    private final MappingMethod isTagMethod;
    private final MappingMethod isEmptyMethod;
    private static boolean fluidStateControlFlowState;
    private final MappingMethod getHeightMethod;

    public MBlockStateWorldBridge() {
        super(MappedClasses.Dw);
        this.getTypeMethod = this.Y("getType", true, MappedClasses.VR, new Class[]{});
        this.isEmptyMethod = this.Y("isEmpty", true, Boolean.TYPE, new Class[]{});
        this.isTagMethod = this.Y("is", true, Boolean.TYPE, new Class[]{MappedClasses.qC});
        this.getHeightMethod = this.Y("getHeight", true, Float.TYPE, new Class[]{MappedClasses.zJ, MappedClasses.lf});
    }

    public static void setFluidStateControlFlowState(boolean state) {
        fluidStateControlFlowState = state;
    }

    public Object getType(Object fluidState) {
        return this.getTypeMethod.invokeObject(fluidState, new Object[0]);
    }

    public boolean isEmpty(Object fluidState) {
        return this.isEmptyMethod.invokeBoolean(fluidState, new Object[0]);
    }

    public static boolean getControlFlowSentinel() {
        return false;
    }

    public static boolean getFluidStateControlFlowState() {
        return fluidStateControlFlowState;
    }

    public float getHeight(Object fluidState, Object world, Object blockPosition) {
        return this.getHeightMethod.invokeFloat(fluidState, world, blockPosition);
    }

    public boolean isTag(Object fluidState, Object tag) {
        return this.isTagMethod.invokeBoolean(fluidState, tag);
    }

    static {
        MBlockStateWorldBridge.setFluidStateControlFlowState(true);
    }
}

