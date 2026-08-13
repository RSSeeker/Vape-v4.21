package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnumHandBridge;
import gg.vape.wrapper.Wrapper;

public class EnumHandValueBridge
extends Wrapper {
    public static EnumHandValueBridge v() {
        return new EnumHandValueBridge(MEnumHandBridge.c(EnumHandValueBridge.vapeInstance.getMappingsMapperCompat().hR));
    }

    public EnumHandValueBridge(Object object) {
        super(object);
    }
}

