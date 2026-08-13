package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MPlayerInput;
import gg.vape.wrapper.Wrapper;

public class PlayerInput
extends Wrapper {
    public PlayerInput(Object handle) {
        super(handle);
    }

    public void setForwardImpulse(float forwardImpulse) {
        MPlayerInput.setY(PlayerInput.vapeInstance.getMappingsMapperCompat().playerInputVector, this.I, forwardImpulse);
    }

    public float getForwardImpulse() {
        return MPlayerInput.getY(PlayerInput.vapeInstance.getMappingsMapperCompat().playerInputVector, this.I);
    }

    public float getLeftImpulse() {
        return MPlayerInput.getX(PlayerInput.vapeInstance.getMappingsMapperCompat().playerInputVector, this.I);
    }

    public void setLeftImpulse(float leftImpulse) {
        MPlayerInput.setX(PlayerInput.vapeInstance.getMappingsMapperCompat().playerInputVector, this.I, leftImpulse);
    }
}
