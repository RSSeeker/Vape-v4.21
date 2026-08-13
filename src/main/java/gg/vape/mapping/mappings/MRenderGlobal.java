package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MRenderGlobal
extends Mapping {
    public MappingMethod extractVisibleEntitiesMethod;
    private final MappingMethod loadRenderersMethod;
    public MappingMethod updateChunksMethod;
    public MappingMethod renderLevelMethod;
    public MappingMethod updateRenderersMethod;
    public MappingMethod renderEntitiesMethod;

    public MRenderGlobal() {
        super(MappedClasses.zs);
        this.loadRenderersMethod = ForgeVersion.MC_26_2.d()
                ? this.registerInstanceMethodForOwner(
                        MappedClasses.LEVEL_EXTRACTOR, "loadRenderers", true, Void.TYPE)
                : this.Y("loadRenderers", true, Void.TYPE);
        int rendererControlFlowState = MEntityRenderer.X();
        if (ForgeVersion.MC_1_7_10.L()) {
            this.updateRenderersMethod = this.Y(
                    "updateRenderers", true, Boolean.TYPE, MappedClasses.zm, Boolean.TYPE);
        } else if (ForgeVersion.MC_26_2.d()) {
            this.updateChunksMethod = this.Y(
                    "compileSections", true, Void.TYPE, MappedClasses.zf);
        } else if (ForgeVersion.MC_1_20_6.d()) {
            this.updateChunksMethod = this.Y("compileSections", true, Void.TYPE, MappedClasses.lt);
        } else {
            this.updateChunksMethod = this.Y("updateChunks", true, Void.TYPE, Long.TYPE);
        }
        if (ForgeVersion.MC_26_2.d()) {
            this.extractVisibleEntitiesMethod = this.registerInstanceMethodForOwner(
                    MappedClasses.LEVEL_EXTRACTOR, "extractVisibleEntities", true, Void.TYPE,
                    MappedClasses.lt, MappedClasses.qh, MappedClasses.uy, MappedClasses.z6);
        } else if (ForgeVersion.MC_1_21_10.d()) {
            this.extractVisibleEntitiesMethod = this.Y("extractVisibleEntities", true, Void.TYPE,
                    MappedClasses.lt, MappedClasses.qh, MappedClasses.uy, MappedClasses.z6);
        } else if (ForgeVersion.MC_1_21_4.d()) {
            this.renderEntitiesMethod = this.Y("renderEntities", true, Void.TYPE,
                    MappedClasses.DQ, MappedClasses.lp, MappedClasses.lt, MappedClasses.uy, List.class);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            if (ForgeVersion.MC_26_2.d()) {
                this.renderLevelMethod = this.Y("renderLevel", true, Void.TYPE,
                        MappedClasses.Ds, MappedClasses.uy, Boolean.TYPE, MappedClasses.zf,
                        MappedClasses.ZA, MappedClasses.qk, MappedClasses.FC, Boolean.TYPE);
            } else if (ForgeVersion.MC_26_1.d()) {
                this.renderLevelMethod = this.Y("renderLevel", true, Void.TYPE,
                        MappedClasses.Ds, MappedClasses.uy, Boolean.TYPE, MappedClasses.zf,
                        MappedClasses.ZA, MappedClasses.qk, MappedClasses.FC, Boolean.TYPE, MappedClasses.VY);
            } else {
                this.renderLevelMethod = this.Y("renderLevel", true, Void.TYPE,
                        Float.TYPE, Long.TYPE, Boolean.TYPE, MappedClasses.lt, MappedClasses.FW,
                        MappedClasses.zH, MappedClasses.qr, MappedClasses.qr);
            }
        } else if (ForgeVersion.MC_1_16_5.d()) {
            this.renderLevelMethod = this.Y("func_228426_a_", Wrapper.isNativeAvailable, Void.TYPE,
                    MappedClasses.DQ, Float.TYPE, Long.TYPE, Boolean.TYPE, MappedClasses.lt,
                    MappedClasses.FW, MappedClasses.zH, MappedClasses.qr);
        }
    }

    public void loadRenderers(Object renderGlobalHandle) {
        this.loadRenderersMethod.invokeVoidNoArgs(renderGlobalHandle);
    }
}

