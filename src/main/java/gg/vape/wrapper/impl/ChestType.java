package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ChestType
extends Wrapper {
    public ChestType(Object object) {
        super(object);
    }

    public static ChestType e() {
        return new ChestType(ChestType.vapeInstance.getMappingsMapperCompat().qa.getFallDamageResetting());
    }
}
