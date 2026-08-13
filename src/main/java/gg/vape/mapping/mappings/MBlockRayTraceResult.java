package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;

public class MBlockRayTraceResult
extends Mapping {
    private MappingMethod createMissMethod;
    private static int controlFlowState;
    private MappingField insideField;

    public boolean isInside(Object blockRayTraceResult) {
        return this.insideField.getBoolean(blockRayTraceResult);
    }

    public static void setBlockRayTraceControlFlowState(int state) {
        controlFlowState = state;
    }

    public static int getInitializationBranch() {
        int unusedControlFlowState = MBlockRayTraceResult.getBlockRayTraceControlFlowState();
        return 0;
    }

    public Object createMiss(Object hitLocation, Object direction, Object blockPos) {
        return this.createMissMethod.invokeObject(null, hitLocation, direction, blockPos);
    }

    public static int getBlockRayTraceControlFlowState() {
        return controlFlowState;
    }

    static {
        MBlockRayTraceResult.setBlockRayTraceControlFlowState(15);
    }

    public MBlockRayTraceResult() {
        super(MappedClasses.qF);
        Class[] parameterTypes = new Class[]{MappedClasses.qP, MappedClasses.us, MappedClasses.lf};
        Class returnType = MappedClasses.qF;
        boolean remap = Wrapper.isNativeAvailable;
        String methodName = "func_216352_a";
        MBlockRayTraceResult mappings = this;
        this.createMissMethod = mappings.registerStaticMethod(methodName, remap, returnType, parameterTypes);
        if (MBlockRayTraceResult.getInitializationBranch() != 0) {
            Class<Boolean> fieldType = Boolean.TYPE;
            String fieldName = "inside";
            MBlockRayTraceResult insideMappings = this;
            this.insideField = insideMappings.fieldBuilder(fieldName, fieldType).buildField();
            GuiComponent.setLegacyComponentState(new GuiComponent[4]);
            return;
        }
        Class<Boolean> fieldType = Boolean.TYPE;
        String fieldName = "inside";
        MBlockRayTraceResult insideMappings = this;
        this.insideField = insideMappings.fieldBuilder(fieldName, fieldType).buildField();
    }

}

