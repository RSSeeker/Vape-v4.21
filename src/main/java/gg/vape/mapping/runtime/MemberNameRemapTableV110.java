package gg.vape.mapping.runtime;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.runtime.MemberNameRemapTableV100;

public class MemberNameRemapTableV110
extends MemberNameRemapTableV100 {
    protected void renderPipeline262() {
        this.f(MappedClasses.FW, "update", "update", Void.TYPE, MappedClasses.uy);
        this.f(MappedClasses.FW, "getFramebuffer", "mainRenderTarget", MappedClasses.DA);
        this.f(MappedClasses.zg, "drawFromBuffers", "drawFromBuffers", Void.TYPE,
                MappedClasses.uq, Integer.TYPE, Integer.TYPE, Integer.TYPE,
                MappedClasses.Yz, MappedClasses.FA, Integer.TYPE, Integer.TYPE);
        this.f(MappedClasses.zs, "renderLevel", "render", Void.TYPE,
                MappedClasses.Ds, MappedClasses.uy, Boolean.TYPE, MappedClasses.zf,
                MappedClasses.ZA, MappedClasses.qk, MappedClasses.FC, Boolean.TYPE);
        this.t(MappedClasses.LEVEL_EXTRACTOR, "loadRenderers", "allChanged");
    }

    protected void sU() {
        this.f(MappedClasses.Zk, "updateEntityMovementAfterFallOn", "fallOn", Void.TYPE, MappedClasses.YU, MappedClasses.Zl, MappedClasses.lf, MappedClasses.zc, Double.TYPE);
    }
}
