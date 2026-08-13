package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MItemRendererBridge
extends Mapping {
    private static final String FIRST_PERSON_FIELD_NAME;
    private static String controlFlowState;
    private MappingField firstPersonField;

    public static void setItemCameraTransformControlFlowState(String state) {
        controlFlowState = state;
    }

    public Object getFirstPerson() {
        return this.firstPersonField.getObject(null);
    }

    public MItemRendererBridge() {
        this(MItemRendererBridge.getItemCameraTransformControlFlowState());
    }

    private MItemRendererBridge(String state) {
        super(MappedClasses.z0);
        String currentControlFlowState = state;
        Class firstPersonFieldType = MappedClasses.z0;
        boolean firstPersonFieldPublic = true;
        String firstPersonFieldName = FIRST_PERSON_FIELD_NAME;
        MItemRendererBridge mapping = this;
        this.firstPersonField = mapping.registerStaticField(firstPersonFieldName, firstPersonFieldPublic, firstPersonFieldType);
    }

    static {
        MItemRendererBridge.setItemCameraTransformControlFlowState(null);
        FIRST_PERSON_FIELD_NAME = "FIRST_PERSON";
    }


    public static String getItemCameraTransformControlFlowState() {
        return controlFlowState;
    }
}

