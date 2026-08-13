package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;

public class MBlockStateContainerBridge
extends Mapping {
    private final MappingMethod getOrUpdateMethod;
    private final MappingField textureViewField;

    public Object getTextureView(Object atlas) {
        return this.textureViewField.getObject(atlas);
    }

    public Object getOrUpdate(Object atlas, Object renderState) {
        return this.getOrUpdateMethod.invokeObject(atlas, renderState);
    }

    public MBlockStateContainerBridge() {
        super(MappedClasses.uF);
        this.getOrUpdateMethod = this.Y("getOrUpdate", true, MappedClasses.uC, new Class[]{MappedClasses.zE});
        this.textureViewField = this.J("textureView", true, MappedClasses.GPU_TEXTURE_VIEW);
    }
}

