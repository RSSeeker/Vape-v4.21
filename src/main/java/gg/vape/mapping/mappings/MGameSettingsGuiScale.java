package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MGameSettingsGuiScale
extends Mapping {
    private static int[] controlFlowState;
    private static final String BASE_FIELD_NAME;
    private MappingField baseField;

    static {
        MGameSettingsGuiScale.setVecDeltaCodecControlFlowState(new int[1]);
        BASE_FIELD_NAME = "base";
    }

    public static void setVecDeltaCodecControlFlowState(int[] state) {
        controlFlowState = state;
    }


    public MGameSettingsGuiScale() {
        super(MappedClasses.Fg);
        Class baseFieldType = MappedClasses.qP;
        boolean baseFieldPublic = true;
        String baseFieldName = BASE_FIELD_NAME;
        MGameSettingsGuiScale mapping = this;
        this.baseField = mapping.J(baseFieldName, baseFieldPublic, baseFieldType);
        int[] currentControlFlowState = MGameSettingsGuiScale.getVecDeltaCodecControlFlowState();
    }

    private Object readBase(Object codecHandle) {
        return this.baseField.getObject(codecHandle);
    }

    public static int[] getVecDeltaCodecControlFlowState() {
        return controlFlowState;
    }

    public static Object getBase(MGameSettingsGuiScale mapping, Object codecHandle) {
        return mapping.readBase(codecHandle);
    }
}

