package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderItemContext;
import gg.vape.wrapper.Wrapper;

public class RenderItemContext
extends Wrapper {
    public static RenderItemContext gui() {
        return new RenderItemContext(MRenderItemContext.getGui(RenderItemContext.vapeInstance.getMappingsMapperCompat().A));
    }

    public RenderItemContext(Object wrappedObject) {
        super(wrappedObject);
    }
}
