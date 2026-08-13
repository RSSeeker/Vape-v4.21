package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGameSettingsValue;
import gg.vape.wrapper.Wrapper;

public class GameSettingsValue
extends Wrapper {
    public GameSettingsValue(Object object) {
        super(object);
    }

    public void a(Object object) {
        MGameSettingsValue.S(GameSettingsValue.vapeInstance.getMappingsMapperCompat().DM, this.I, object);
    }

    public void f(Object object) {
        GameSettingsValue.vapeInstance.getMappingsMapperCompat().DM.b(this.I, object);
    }

    public Object i() {
        return MGameSettingsValue.U(GameSettingsValue.vapeInstance.getMappingsMapperCompat().DM, this.I);
    }
}

