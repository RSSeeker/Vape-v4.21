package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MRenderWorldLastEvent
extends Mapping {
    private final MappingField renderResolutionMultiplierField;
    private static boolean shadersControlFlowState;
    private static final String RENDER_RESOLUTION_MULTIPLIER_FIELD_NAME;

    public MRenderWorldLastEvent() {
        super(MappedClasses.lO);
        Class<Float> fieldType = Float.TYPE;
        boolean fieldPublic = false;
        String fieldName = RENDER_RESOLUTION_MULTIPLIER_FIELD_NAME;
        MRenderWorldLastEvent mapping = this;
        this.renderResolutionMultiplierField = mapping.registerStaticField(fieldName, fieldPublic, fieldType);
        if (MRenderWorldLastEvent.shouldSkipLegacyStateUpdate()) {
            return;
        }
        GuiComponent.setLegacyComponentState(new GuiComponent[5]);
    }

    private float readRenderResolutionMultiplier() {
        return this.renderResolutionMultiplierField.getFloat(null);
    }


    public static boolean getShadersControlFlowState() {
        return shadersControlFlowState;
    }

    static {
        MRenderWorldLastEvent.setShadersControlFlowState(false);
        RENDER_RESOLUTION_MULTIPLIER_FIELD_NAME = "configRenderResMul";
    }

    public static void setShadersControlFlowState(boolean state) {
        shadersControlFlowState = state;
    }

    public static float getRenderResolutionMultiplier(MRenderWorldLastEvent mapping) {
        return mapping.readRenderResolutionMultiplier();
    }

    public static boolean shouldSkipLegacyStateUpdate() {
        boolean controlFlowState = MRenderWorldLastEvent.getShadersControlFlowState();
        return true;
    }
}

