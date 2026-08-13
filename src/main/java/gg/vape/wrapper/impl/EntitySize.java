package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class EntitySize
extends Wrapper {
    public float c() {
        return EntitySize.vapeInstance.getMappingsMapperCompat().h2.getWidth(this.I);
    }

    public float u() {
        return EntitySize.vapeInstance.getMappingsMapperCompat().h2.getHeight(this.I);
    }

    public EntitySize(Object object) {
        super(object);
    }
}

