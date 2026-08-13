package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class EnumHand
extends Wrapper {
    public static EnumHand mainHand() {
        return new EnumHand(EnumHand.vapeInstance.getMappingsMapperCompat().DX.getMainHand());
    }

    public static EnumHand offHand() {
        return new EnumHand(EnumHand.vapeInstance.getMappingsMapperCompat().DX.getOffHand());
    }

    public EnumHand(Object wrappedObject) {
        super(wrappedObject);
    }
}
