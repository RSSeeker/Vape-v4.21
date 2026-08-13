package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MInputMappings;
import gg.vape.wrapper.Wrapper;

public class InputMappings
extends Wrapper {
    public static InputMappings S(int n, int n2) {
        return new InputMappings(MInputMappings.k(InputMappings.vapeInstance.getMappingsMapperCompat().CW, n, n2));
    }

    public InputMappings(Object object) {
        super(object);
    }
}

