package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;

public class MBiomeProvider
extends Mapping {
    private static String[] controlFlowState;

    public static void setControlFlowState(String[] state) {
        controlFlowState = state;
    }

    public MBiomeProvider() {
        super(MappedClasses.lq);
    }

    public static String[] getControlFlowState() {
        return controlFlowState;
    }

    static {
        if (MBiomeProvider.getControlFlowState() != null) {
            MBiomeProvider.setControlFlowState(new String[4]);
        }
    }
}
