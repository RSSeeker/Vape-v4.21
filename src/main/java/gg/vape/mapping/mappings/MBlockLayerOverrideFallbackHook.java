package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MBlockLayerOverrideFallbackHook
extends Mapping {
    private static int controlFlowState;
    private static final String GC_METHOD_NAME;
    public final MappingMethod gcMethod;

    public static void setBlockLayerFallbackControlFlowState(int state) {
        controlFlowState = state;
    }


    public static int getControlFlowConstant() {
        MBlockLayerOverrideFallbackHook.getBlockLayerFallbackControlFlowState();
        return 41;
    }

    public MBlockLayerOverrideFallbackHook() {
        this(MBlockLayerOverrideFallbackHook.getBlockLayerFallbackControlFlowState());
    }

    private MBlockLayerOverrideFallbackHook(int controlFlowState) {
        super(MappedClasses.lA);
        this.gcMethod = this.registerStaticMethod(GC_METHOD_NAME, false, Void.TYPE, new Class[]{});
    }

    static {
        MBlockLayerOverrideFallbackHook.setBlockLayerFallbackControlFlowState(0);
        GC_METHOD_NAME = "gc";
    }

    public static int getBlockLayerFallbackControlFlowState() {
        return controlFlowState;
    }
}

