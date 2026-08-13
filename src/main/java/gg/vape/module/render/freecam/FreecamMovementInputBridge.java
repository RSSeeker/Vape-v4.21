package gg.vape.module.render.freecam;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;

public class FreecamMovementInputBridge
extends Wrapper {
    public void setSprinting(boolean sprinting) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.applyMovementInput(this.isMovingForward(), this.isMovingBackward(),
                    this.isMovingLeft(), this.isMovingRight(), this.isJumping(),
                    this.isSneaking(), sprinting);
            return;
        }
        FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.K(this.I, sprinting);
    }

    public boolean isSneaking() {
        return FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.r(this.I);
    }

    public boolean isMovingForward() {
        return FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.H(this.I);
    }

    public boolean isMovingBackward() {
        return FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.W(this.I);
    }

    public boolean isMovingLeft() {
        return FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.K(this.I);
    }

    public FreecamMovementInputBridge(Object object) {
        super(object);
    }


    private void applyMovementInput(boolean forward, boolean backward, boolean left,
            boolean right, boolean jump, boolean sneak, boolean sprint) {
        FreecamMovementInputBridge input = new FreecamMovementInputBridge(
                FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.z(
                        forward, backward, left, right, jump, sneak, sprint));
        this.I = input.getObject();
        Minecraft.a_xH_J().a_jw_2_I().setFreecamInput(input);
    }

    public boolean isSprinting() {
        return FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.Y(this.I);
    }

    public void setSneaking(boolean sneaking) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.applyMovementInput(this.isMovingForward(), this.isMovingBackward(),
                    this.isMovingLeft(), this.isMovingRight(), this.isJumping(),
                    sneaking, this.isSprinting());
            return;
        }
        FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.V(this.I, sneaking);
    }

    public boolean isMovingRight() {
        return FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.A(this.I);
    }

    public void setJumping(boolean jumping) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.applyMovementInput(this.isMovingForward(), this.isMovingBackward(),
                    this.isMovingLeft(), this.isMovingRight(), jumping,
                    this.isSneaking(), this.isSprinting());
            return;
        }
        FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.A(this.I, jumping);
    }

    public boolean isJumping() {
        return FreecamMovementInputBridge.vapeInstance.getMappingsMapperCompat().Cc.p(this.I);
    }
}

