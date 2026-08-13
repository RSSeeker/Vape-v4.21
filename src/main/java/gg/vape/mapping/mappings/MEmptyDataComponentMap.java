package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MEmptyDataComponentMap
extends Mapping {
    private static final String CONSTRUCTOR;
    private final MappingMethod constructor;
    private static String[] controlFlowState;

    static {
        MEmptyDataComponentMap.setControlFlowState(null);
        CONSTRUCTOR = "<init>";
    }

    public MEmptyDataComponentMap() {
        this(MEmptyDataComponentMap.getControlFlowState());
    }

    private MEmptyDataComponentMap(String[] componentMapControlFlowState) {
        super(MappedClasses.DP);
        this.constructor = this.Y(CONSTRUCTOR, false, Void.TYPE, MappedClasses.zD);
        if (GuiComponent.getLegacyComponentState() == null) {
            MEmptyDataComponentMap.setControlFlowState(new String[5]);
        }
    }

    public Object create(Object defaultComponentsHandle) {
        return this.constructor.newInstance(defaultComponentsHandle);
    }


    public static void setControlFlowState(String[] state) {
        controlFlowState = state;
    }

    public static String[] getControlFlowState() {
        return controlFlowState;
    }
}

