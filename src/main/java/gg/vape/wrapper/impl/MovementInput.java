package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MMovementInput;
import gg.vape.module.render.freecam.FreecamMovementInputBridge;
import gg.vape.wrapper.Wrapper;

public class MovementInput
extends Wrapper {
    public void B(float f) {
        if (ForgeVersion.MC_1_21_6.d()) {
            PlayerInput playerInput = new PlayerInput(MMovementInput.W(MovementInput.vapeInstance.getMappings().h7, this.I));
            playerInput.setForwardImpulse(f);
            return;
        }
        MMovementInput.p(MovementInput.vapeInstance.getMappings().h7, this.I, f);
    }

    public MovementInput(Object object) {
        super(object);
    }

    public void setFreecamInput(FreecamMovementInputBridge freecamMovementInputBridge) {
        MMovementInput.x(MovementInput.vapeInstance.getMappings().h7, this.I, freecamMovementInputBridge.getObject());
    }

    public void M(float f) {
        if (ForgeVersion.MC_1_21_6.d()) {
            PlayerInput playerInput = new PlayerInput(MMovementInput.W(MovementInput.vapeInstance.getMappings().h7, this.I));
            playerInput.setLeftImpulse(f);
            return;
        }
        MMovementInput.j(MovementInput.vapeInstance.getMappings().h7, this.I, f);
    }

    public FreecamMovementInputBridge getFreecamInput() {
        return new FreecamMovementInputBridge(MMovementInput.x(MovementInput.vapeInstance.getMappings().h7, this.I));
    }

    public float T() {
        if (ForgeVersion.MC_1_21_6.d()) {
            PlayerInput playerInput = new PlayerInput(MMovementInput.W(MovementInput.vapeInstance.getMappings().h7, this.I));
            return playerInput.getLeftImpulse();
        }
        return MMovementInput.b(MovementInput.vapeInstance.getMappings().h7, this.I);
    }

    public void V(boolean bl) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.getFreecamInput().setJumping(bl);
            return;
        }
        MMovementInput.H(MovementInput.vapeInstance.getMappings().h7, this.I, bl);
    }

    public float D() {
        if (ForgeVersion.MC_1_21_6.d()) {
            PlayerInput playerInput = new PlayerInput(MMovementInput.W(MovementInput.vapeInstance.getMappings().h7, this.I));
            return playerInput.getForwardImpulse();
        }
        return MMovementInput.f(MovementInput.vapeInstance.getMappings().h7, this.I);
    }


    public boolean G() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return this.getFreecamInput().isJumping();
        }
        return MMovementInput.j(MovementInput.vapeInstance.getMappings().h7, this.I);
    }

    public void setCancelled(boolean bl) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.getFreecamInput().setSneaking(bl);
            return;
        }
        MMovementInput.V(MovementInput.vapeInstance.getMappings().h7, this.I, bl);
    }

    public boolean D$src$Z$v5d6e8() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return this.getFreecamInput().isSneaking();
        }
        return MMovementInput.R(MovementInput.vapeInstance.getMappings().h7, this.I);
    }
}

