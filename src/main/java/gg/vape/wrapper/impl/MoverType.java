package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class MoverType
extends Wrapper {
    public MoverType(Object object) {
        super(object);
    }

    public static MoverType G() {
        return new MoverType(MoverType.vapeInstance.getMappingsMapperCompat().q6.k());
    }

    public static MoverType W() {
        return new MoverType(MoverType.vapeInstance.getMappingsMapperCompat().q6.h());
    }

    public static MoverType E() {
        return new MoverType(MoverType.vapeInstance.getMappingsMapperCompat().q6.x());
    }

    public static MoverType X() {
        return new MoverType(MoverType.vapeInstance.getMappingsMapperCompat().q6.W());
    }

    public static MoverType U() {
        return new MoverType(MoverType.vapeInstance.getMappingsMapperCompat().q6.q());
    }
}

