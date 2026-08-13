package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEnumHandBridge;
import gg.vape.ui.click.component.GuiComponent;

public class MRenderStateBuilder
extends Mapping {
    private static final String DRAW_WITH_SHADER = "drawWithShader";
    private final MappingMethod drawWithShaderMethod;

    public MRenderStateBuilder() {
        this(MEnumHandBridge.s());
    }

    private MRenderStateBuilder(int controlFlowState) {
        super(MappedClasses.ug);
        this.drawWithShaderMethod = this.registerStaticMethod(
                DRAW_WITH_SHADER, true, Void.TYPE, MappedClasses.qE);
        if (controlFlowState != 0) {
            GuiComponent.setLegacyComponentState(new GuiComponent[1]);
        }
    }

    public void drawWithShader(Object renderStateHandle) {
        this.drawWithShaderMethod.invokeVoid(null, renderStateHandle);
    }
}

