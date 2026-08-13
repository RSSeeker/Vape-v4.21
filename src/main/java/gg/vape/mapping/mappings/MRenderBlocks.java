package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.ui.click.component.GuiComponent;

public class MRenderBlocks
extends Mapping {
    public final MappingMethod renderBlockByRenderTypeMethod;
    private final MappingField renderAllFacesField;
    public final MappingMethod renderStandardBlockMethod;
    public final MappingMethod renderFaceZNegMethod;
    public final MappingMethod renderStandardBlockWithColorMultiplierMethod;
    public final MappingMethod renderFaceYPosMethod;
    public final MappingMethod renderFaceYNegMethod;
    public final MappingMethod renderFaceXPosMethod;
    public final MappingMethod renderFaceZPosMethod;
    public final MappingMethod renderFaceXNegMethod;

    public MRenderBlocks() {
        this(MEntityRenderer.X());
    }

    private MRenderBlocks(int rendererControlFlowState) {
        super(MappedClasses.q5);
        this.renderAllFacesField = this.J("renderAllFaces", true, Boolean.TYPE);
        this.renderBlockByRenderTypeMethod = this.Y("renderBlockByRenderType", true, Boolean.TYPE,
                MappedClasses.Zk, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        this.renderStandardBlockMethod = this.Y("renderStandardBlock", true, Boolean.TYPE,
                MappedClasses.Zk, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        this.renderStandardBlockWithColorMultiplierMethod = this.Y(
                "renderStandardBlockWithColorMultiplier", true, Boolean.TYPE,
                MappedClasses.Zk, Integer.TYPE, Integer.TYPE, Integer.TYPE,
                Float.TYPE, Float.TYPE, Float.TYPE);
        this.renderFaceXNegMethod = this.Y("renderFaceXNeg", true, Void.TYPE,
                MappedClasses.Zk, Double.TYPE, Double.TYPE, Double.TYPE, MappedClasses.Z9);
        this.renderFaceXPosMethod = this.Y("renderFaceXPos", true, Void.TYPE,
                MappedClasses.Zk, Double.TYPE, Double.TYPE, Double.TYPE, MappedClasses.Z9);
        this.renderFaceYNegMethod = this.Y("renderFaceYNeg", true, Void.TYPE,
                MappedClasses.Zk, Double.TYPE, Double.TYPE, Double.TYPE, MappedClasses.Z9);
        this.renderFaceYPosMethod = this.Y("renderFaceYPos", true, Void.TYPE,
                MappedClasses.Zk, Double.TYPE, Double.TYPE, Double.TYPE, MappedClasses.Z9);
        this.renderFaceZNegMethod = this.Y("renderFaceZNeg", true, Void.TYPE,
                MappedClasses.Zk, Double.TYPE, Double.TYPE, Double.TYPE, MappedClasses.Z9);
        this.renderFaceZPosMethod = this.Y("renderFaceZPos", true, Void.TYPE,
                MappedClasses.Zk, Double.TYPE, Double.TYPE, Double.TYPE, MappedClasses.Z9);
        if (rendererControlFlowState != 0) {
            GuiComponent.setLegacyComponentState(new GuiComponent[3]);
        }
    }

    public boolean renderStandardBlockWithColorMultiplier(
            Object renderBlocksHandle, Object blockHandle, int x, int y, int z, float red, float green, float blue) {
        return this.renderStandardBlockWithColorMultiplierMethod.invokeBoolean(
                renderBlocksHandle, blockHandle, x, y, z, red, green, blue);
    }

    public void setRenderAllFaces(Object renderBlocksHandle, boolean renderAllFaces) {
        this.renderAllFacesField.setBoolean(renderBlocksHandle, renderAllFaces);
    }

}

