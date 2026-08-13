package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MISelectionContext
extends Mapping {
    private static final String FOR_ENTITY_METHOD_NAME;
    private static boolean selectionContextControlFlowState;
    private final MappingMethod forEntityMethod;

    public static void setSelectionContextControlFlowState(boolean state) {
        selectionContextControlFlowState = state;
    }

    public static boolean getSelectionContextControlFlowState() {
        return selectionContextControlFlowState;
    }

    public static boolean shouldUpdateLegacyState() {
        boolean controlFlowState = MISelectionContext.getSelectionContextControlFlowState();
        return false;
    }

    public Object forEntity(Object entityHandle) {
        return this.forEntityMethod.newInstance(entityHandle);
    }


    public MISelectionContext() {
        this(MISelectionContext.getSelectionContextControlFlowState());
    }

    private MISelectionContext(boolean controlFlowState) {
        super(MappedClasses.qg);
        Class[] parameterTypes = new Class[]{MappedClasses.zc};
        Class returnType = MappedClasses.qg;
        boolean methodPublic = true;
        String methodName = FOR_ENTITY_METHOD_NAME;
        MISelectionContext mapping = this;
        this.forEntityMethod = mapping.registerStaticMethod(methodName, methodPublic, returnType, parameterTypes);
        boolean currentControlFlowState = controlFlowState;
    }

    static {
        MISelectionContext.setSelectionContextControlFlowState(true);
        FOR_ENTITY_METHOD_NAME = "forEntity";
    }
}

