package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class InputMappingsInputFactory
extends Wrapper {
    public static InputMappingsInputFactory create(int keyCode, int scanCode, int modifiers) {
        return new InputMappingsInputFactory(InputMappingsInputFactory.vapeInstance.getMappingsMapperCompat().Du.createKeyEvent(keyCode, scanCode, modifiers));
    }

    public InputMappingsInputFactory(Object handle) {
        super(handle);
    }
}
