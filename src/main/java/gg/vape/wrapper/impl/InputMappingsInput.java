package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MInputMappingsInput;
import gg.vape.wrapper.Wrapper;

public class InputMappingsInput
extends Wrapper {
    public InputMappingsInput(Object wrappedObject) {
        super(wrappedObject);
    }

    public int getKeyCode() {
        return MInputMappingsInput.getKeyCode(InputMappingsInput.vapeInstance.getMappingsMapperCompat().hm, this.I);
    }
}
