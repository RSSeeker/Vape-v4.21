package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnumWorldBlockLayer;
import gg.vape.wrapper.Wrapper;

public class EnumWorldBlockLayer
extends Wrapper {
    private static EnumWorldBlockLayer translucent;
    private static EnumWorldBlockLayer solid;

    public static EnumWorldBlockLayer translucent() {
        if (translucent == null) {
            translucent = new EnumWorldBlockLayer(MEnumWorldBlockLayer.getTranslucent(EnumWorldBlockLayer.vapeInstance.getMappingsMapperCompat().worldBlockLayer));
        }
        return translucent;
    }


    public EnumWorldBlockLayer(Object handle) {
        super(handle);
    }

    public static EnumWorldBlockLayer solid() {
        if (solid == null) {
            solid = new EnumWorldBlockLayer(MEnumWorldBlockLayer.getSolid(EnumWorldBlockLayer.vapeInstance.getMappingsMapperCompat().worldBlockLayer));
        }
        return solid;
    }
}

