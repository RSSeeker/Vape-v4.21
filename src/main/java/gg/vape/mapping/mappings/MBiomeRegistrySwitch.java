package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;

public class MBiomeRegistrySwitch
extends Mapping {
    private static int[] controlFlowState;

    public MBiomeRegistrySwitch() {
        super(MappedClasses.FU);
    }

    public static void setBiomeRegistryControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public static int[] getBiomeRegistryControlFlowState() {
        return controlFlowState;
    }

    static {
        if (MBiomeRegistrySwitch.getBiomeRegistryControlFlowState() == null) {
            MBiomeRegistrySwitch.setBiomeRegistryControlFlowState(new int[5]);
        }
    }
}
