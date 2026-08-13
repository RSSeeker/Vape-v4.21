package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ResourceLocation;

public class ResourceLocationConstantPair {
    private static GuiComponent[] controlFlowState;

    public static void setControlFlowState(GuiComponent[] state) {
        controlFlowState = state;
    }

    public static ResourceLocation getItems() {
        return new ResourceLocation(Vape.INSTANCE.getMappingsMapperCompat().resourceLocationConstants.getItems());
    }

    public static GuiComponent[] getControlFlowState() {
        return controlFlowState;
    }

    public static ResourceLocation getGui() {
        return new ResourceLocation(Vape.INSTANCE.getMappingsMapperCompat().resourceLocationConstants.getGui());
    }

    static {
        if (ResourceLocationConstantPair.getControlFlowState() != null) {
            ResourceLocationConstantPair.setControlFlowState(new GuiComponent[3]);
        }
    }
}
