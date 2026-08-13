package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class TextComponentTranslation
extends Wrapper {
    public Object getModelIdentity() {
        return TextComponentTranslation.vapeInstance.getMappings().trackingItemStackRenderState.getModelIdentity(this.I);
    }

    public TextComponentTranslation(Object handle) {
        super(handle);
    }
}
