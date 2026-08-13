package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class NonNullList
extends Wrapper {
    public NonNullList(Object listHandle) {
        super(listHandle);
    }

    public static NonNullList create() {
        return new NonNullList(NonNullList.vapeInstance.getMappingsMapperCompat().nonNullList.create());
    }
}
