package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;

public class MLanguageStateBridge
extends Mapping {
    private static int[] controlFlowState;

    public static void setLanguageControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public MLanguageStateBridge() {
        super(MappedClasses.FN);
    }

    public static int[] getLanguageControlFlowState() {
        return controlFlowState;
    }

    static {
        if (MLanguageStateBridge.getLanguageControlFlowState() == null) {
            MLanguageStateBridge.setLanguageControlFlowState(new int[3]);
        }
    }
}
