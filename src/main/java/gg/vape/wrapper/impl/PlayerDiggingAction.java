package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class PlayerDiggingAction
extends Wrapper {
    public static PlayerDiggingAction releaseUseItem() {
        return new PlayerDiggingAction(PlayerDiggingAction.vapeInstance.getMappingsMapperCompat().Ci.getReleaseUseItem());
    }

    public PlayerDiggingAction(Object wrappedObject) {
        super(wrappedObject);
    }
}
