package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class SetVisibility
extends Wrapper {
    public SetVisibility(Object wrappedObject) {
        super(wrappedObject);
    }

    public void setAllVisible(boolean visible) {
        SetVisibility.vapeInstance.getMappingsMapperCompat().setVisibility.setAllVisible(this.I, visible);
    }
}
