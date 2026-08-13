package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class GameSettingsGuiScaleValue
extends Wrapper {
    public static int getAntialiasingLevel() {
        if (GameSettingsGuiScaleValue.vapeInstance.getMappingsMapperCompat().gameSettingsGuiScaleValue == null) {
            return 0;
        }
        return GameSettingsGuiScaleValue.vapeInstance.getMappingsMapperCompat().gameSettingsGuiScaleValue
                .getAntialiasingLevel();
    }


    public GameSettingsGuiScaleValue(Object valueHandle) {
        super(valueHandle);
    }
}

