package gg.vape.module.blatant.blockin;

import gg.vape.module.blatant.blockin.BlockInMovementController;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.World;

public abstract class AbstractBlockInMovementController
implements BlockInMovementController {
    protected boolean backwardKeyDown;
    protected final EntityPlayer simulatedPlayer;
    protected final World world;
    protected boolean jumpKeyDown;
    protected int sprintToggleTimer;
    protected boolean sprintKeyDown;
    protected float moveForward;
    protected boolean rightKeyDown;
    protected final EntityPlayer sourcePlayer;
    protected boolean jumpInput;
    protected int sprintingTicksLeft;
    protected final EntityPlayerSP localPlayer;
    protected boolean sneakKeyDown;
    protected boolean sneakInput;
    protected float moveStrafe;
    protected boolean leftKeyDown;
    protected boolean forwardKeyDown;

    protected AbstractBlockInMovementController(EntityPlayer simulatedPlayer, EntityPlayerSP localPlayer,
                                                EntityPlayer sourcePlayer, World world) {
        this.simulatedPlayer = simulatedPlayer;
        this.localPlayer = localPlayer;
        this.sourcePlayer = sourcePlayer;
        this.world = world;
    }

    public boolean isJumpInput() {
        return this.jumpInput;
    }

    public void setForwardKeyDown(boolean forwardKeyDown) {
        this.forwardKeyDown = forwardKeyDown;
    }

    public void setJumpKeyDown(boolean jumpKeyDown) {
        this.jumpKeyDown = jumpKeyDown;
    }

    public boolean isJumpKeyDown() {
        return this.jumpKeyDown;
    }

    public boolean isRightKeyDown() {
        return this.rightKeyDown;
    }

    public boolean isBackwardKeyDown() {
        return this.backwardKeyDown;
    }

    public float getMoveStrafe() {
        return this.moveStrafe;
    }

    public void setRightKeyDown(boolean rightKeyDown) {
        this.rightKeyDown = rightKeyDown;
    }

    public int getSprintingTicksLeft() {
        return this.sprintingTicksLeft;
    }

    public void setSneakKeyDown(boolean sneakKeyDown) {
        this.sneakKeyDown = sneakKeyDown;
    }

    public int getSprintToggleTimer() {
        return this.sprintToggleTimer;
    }

    public void setLeftKeyDown(boolean leftKeyDown) {
        this.leftKeyDown = leftKeyDown;
    }

    public void setSprintKeyDown(boolean sprintKeyDown) {
        this.sprintKeyDown = sprintKeyDown;
    }

    public boolean isForwardKeyDown() {
        return this.forwardKeyDown;
    }

    public void setBackwardKeyDown(boolean backwardKeyDown) {
        this.backwardKeyDown = backwardKeyDown;
    }

    public boolean isSprintKeyDown() {
        return this.sprintKeyDown;
    }

    public float getMoveForward() {
        return this.moveForward;
    }

    public boolean isSneakKeyDown() {
        return this.sneakKeyDown;
    }

    public boolean isLeftKeyDown() {
        return this.leftKeyDown;
    }

    public boolean isSneaking() {
        return this.sneakInput;
    }
}

