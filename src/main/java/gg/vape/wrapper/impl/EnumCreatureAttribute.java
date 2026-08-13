package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class EnumCreatureAttribute
extends Wrapper {

    public static EnumCreatureAttribute undefined() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return null;
        }
        Object attributeHandle = EnumCreatureAttribute.vapeInstance.getMappingsMapperCompat()
                .enumCreatureAttributeBridge.getUndefined();
        return new EnumCreatureAttribute(attributeHandle);
    }

    public EnumCreatureAttribute(Object attributeHandle) {
        super(attributeHandle);
    }
}

