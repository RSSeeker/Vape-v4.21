package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderLivingBase;

import java.nio.FloatBuffer;
import java.util.List;

public class RenderLivingBase<T extends EntityLivingBase>
extends Render<T> {
    public ModelBipedSkeletonBridge getMainModel() {
        return new ModelBipedSkeletonBridge(MRenderLivingBase.x(RenderLivingBase.vapeInstance.getMappingsMapperCompat().CP, this.I));
    }

    public List<Object> getLayerRenderers() {
        return (List)MRenderLivingBase.i(RenderLivingBase.vapeInstance.getMappingsMapperCompat().CP, this.I);
    }

    public RenderLivingBase(Object object) {
        super(object);
    }

    public void setLayerRenderers(List list) {
        MRenderLivingBase.O(RenderLivingBase.vapeInstance.getMappingsMapperCompat().CP, this.I, list);
    }

    public FloatBuffer P() {
        return MRenderLivingBase.y(RenderLivingBase.vapeInstance.getMappingsMapperCompat().CP, this.I);
    }
}

