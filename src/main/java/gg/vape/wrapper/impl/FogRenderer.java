package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class FogRenderer
extends Wrapper {
    public FogRenderer(Object wrappedObject) {
        super(wrappedObject);
    }

    public Object getBuffer(FogType fogType) {
        return FogRenderer.vapeInstance.getMappingsMapperCompat().fogRenderer.getBuffer(this.I, fogType.getObject());
    }
}
