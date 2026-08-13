package gg.vape.module.blatant.blockin;

import gg.vape.module.blatant.blockin.AbstractBlockInMovementController;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.utils.MathUtil;
import gg.vape.utils.PlayerSimulationUtil;
import gg.vape.wrapper.impl.AttributeInstance;
import gg.vape.wrapper.impl.AttributeModifier;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.World;

public class BlockInLegacyMovementController189
extends AbstractBlockInMovementController {


    @Override
    public void initialize() {
        this.sprintToggleTimer = this.localPlayer.L$src$I$1tmeeo5();
        this.sprintingTicksLeft = this.localPlayer.z$src$I$1uboxyr();
        this.moveForward = this.localPlayer.movementInput().D();
        this.moveStrafe = this.localPlayer.movementInput().T();
        AttributeInstance sourceMovementSpeed = this.sourcePlayer.h(MonsterAttributesBridge.B());
        AttributeInstance simulatedMovementSpeed = this.simulatedPlayer.h(MonsterAttributesBridge.B());
        simulatedMovementSpeed.J();
        for (Object modifier : sourceMovementSpeed.I()) {
            simulatedMovementSpeed.applyModifier(new AttributeModifier(modifier));
        }
    }

    @Override
    public void tick() {
        this.simulatedPlayer.l$src$V$fw3v8a();
        if (this.sprintingTicksLeft > 0) {
            --this.sprintingTicksLeft;
            if (this.sprintingTicksLeft == 0) {
                this.simulatedPlayer.R(false);
            }
        }
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        boolean wasSneaking = this.sneakInput;
        float sprintThreshold = 0.8f;
        boolean wasMovingForward = this.moveForward >= sprintThreshold;
        this.updateMovementInput();
        boolean isUsingItem = ForgeVersion.MC_1_8_9.v() && this.simulatedPlayer.l$src$Z$1io4duf();
        if (isUsingItem && !this.simulatedPlayer.f$src$Z$fst3rk()) {
            this.moveStrafe *= 0.2f;
            this.moveForward *= 0.2f;
            this.sprintToggleTimer = 0;
        }
        AxisAlignedBB boundingBox = this.simulatedPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
        double collisionOffset = this.simulatedPlayer.f$src$F$fst3ac() * 0.35;
        double collisionY = boundingBox.getMinY() + 0.5;
        PlayerSimulationUtil.p(this.simulatedPlayer, this.simulatedPlayer.z() - collisionOffset, collisionY, this.simulatedPlayer.h() + collisionOffset);
        PlayerSimulationUtil.p(this.simulatedPlayer, this.simulatedPlayer.z() - collisionOffset, collisionY, this.simulatedPlayer.h() - collisionOffset);
        PlayerSimulationUtil.p(this.simulatedPlayer, this.simulatedPlayer.z() + collisionOffset, collisionY, this.simulatedPlayer.h() - collisionOffset);
        PlayerSimulationUtil.p(this.simulatedPlayer, this.simulatedPlayer.z() + collisionOffset, collisionY, this.simulatedPlayer.h() + collisionOffset);
        boolean canSprint = this.simulatedPlayer.Y$src$Lgg_vape_wrapper_impl_FoodStats_$fakh1z().getFoodLevel() > 6
                || this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().H();
        if (this.simulatedPlayer.b$src$Z$fqlxe4() && !wasSneaking && !wasMovingForward
                && this.moveForward >= sprintThreshold && !this.simulatedPlayer.B$src$Z$f90iek()
                && canSprint && !isUsingItem && !this.simulatedPlayer.i(PotionRegistry.K)) {
            if (this.sprintToggleTimer <= 0 && !this.sprintKeyDown) {
                this.sprintToggleTimer = 7;
            } else {
                this.simulatedPlayer.R(true);
                this.sprintingTicksLeft = 600;
            }
        }
        if (!this.simulatedPlayer.B$src$Z$f90iek() && this.moveForward >= sprintThreshold
                && canSprint && !isUsingItem && !this.simulatedPlayer.i(PotionRegistry.K)
                && this.sprintKeyDown) {
            this.simulatedPlayer.R(true);
            this.sprintingTicksLeft = 600;
        }
        if (this.simulatedPlayer.B$src$Z$f90iek()
                && (this.moveForward < sprintThreshold || this.simulatedPlayer.r() || !canSprint)) {
            this.simulatedPlayer.R(false);
            this.sprintingTicksLeft = 0;
        }
        if (this.simulatedPlayer.B$src$I$14s4bbr() > 0) {
            this.simulatedPlayer.L(this.simulatedPlayer.B$src$I$14s4bbr() - 1);
        }
        if (Math.abs(this.simulatedPlayer.t()) < 0.005) {
            this.simulatedPlayer.r(0.0);
        }
        if (Math.abs(this.simulatedPlayer.q()) < 0.005) {
            this.simulatedPlayer.k(0.0);
        }
        if (Math.abs(this.simulatedPlayer.T()) < 0.005) {
            this.simulatedPlayer.i(0.0);
        }
        this.simulatedPlayer.k$src$V$5315b7(this.moveStrafe);
        this.simulatedPlayer.M(this.moveForward);
        this.simulatedPlayer.b(this.jumpInput);
        if (this.simulatedPlayer.e$src$Z$15bd4i1()) {
            if (this.simulatedPlayer.h$src$Z$ftwoya()) {
                this.simulatedPlayer.k(this.simulatedPlayer.q() + (double)0.04f);
            } else if (this.simulatedPlayer.Q$src$Z$fh9faz()) {
                this.simulatedPlayer.k(this.simulatedPlayer.q() + (double)0.04f);
            } else if (this.simulatedPlayer.b$src$Z$fqlxe4() && this.simulatedPlayer.B$src$I$14s4bbr() == 0) {
                this.simulatedPlayer.k((double)0.42f);
                if (this.localPlayer.i(PotionRegistry.Z)) {
                    double jumpBoost = (this.localPlayer.b(PotionRegistry.Z).L() + 1) * 0.1f;
                    this.simulatedPlayer.k(this.simulatedPlayer.q() + jumpBoost);
                }
                if (this.simulatedPlayer.B$src$Z$f90iek()) {
                    float yawRadians = this.simulatedPlayer.J() * ((float)Math.PI / 180);
                    this.simulatedPlayer.r(this.simulatedPlayer.t() - MathUtil.sin(yawRadians) * 0.2f);
                    this.simulatedPlayer.i(this.simulatedPlayer.T() + MathUtil.cos(yawRadians) * 0.2f);
                }
                this.simulatedPlayer.L(10);
            }
        } else {
            this.simulatedPlayer.L(0);
        }
        this.simulatedPlayer.k$src$V$5315b7(this.simulatedPlayer.N$src$F$14ypudi() * 0.98f);
        this.simulatedPlayer.M(this.simulatedPlayer.F() * 0.98f);
        PlayerSimulationUtil.g(this.simulatedPlayer, this.simulatedPlayer.N$src$F$14ypudi(), this.simulatedPlayer.F());
        AttributeInstance movementSpeed = this.simulatedPlayer.h(MonsterAttributesBridge.B());
        if (!this.world.I()) {
            movementSpeed.I(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().l());
        }
        this.simulatedPlayer.t(0.02f);
        if (this.simulatedPlayer.B$src$Z$f90iek()) {
            this.simulatedPlayer.t((float)((double)this.simulatedPlayer.y$src$F$15mczw1() + 0.005999999865889549));
        }
        this.simulatedPlayer.I((float)movementSpeed.W());
    }

    @Override
    public void applySnapshot(BlockPlacementGraph blockPlacementGraph) {
        this.sprintingTicksLeft = blockPlacementGraph.sprintingTicksLeft;
        this.sprintToggleTimer = blockPlacementGraph.sprintToggleTimer;
        this.moveForward = blockPlacementGraph.moveForward;
        this.moveStrafe = blockPlacementGraph.moveStrafe;
        this.jumpInput = blockPlacementGraph.jumpInput;
        this.sneakInput = blockPlacementGraph.sneakInput;
        this.simulatedPlayer.H(blockPlacementGraph.positionX);
        this.simulatedPlayer.u(blockPlacementGraph.positionY);
        this.simulatedPlayer.l(blockPlacementGraph.positionZ);
        this.simulatedPlayer.n(blockPlacementGraph.previousPositionX);
        this.simulatedPlayer.w(blockPlacementGraph.previousPositionY);
        this.simulatedPlayer.A(blockPlacementGraph.previousPositionZ);
        this.simulatedPlayer.r(blockPlacementGraph.motionX);
        this.simulatedPlayer.k(blockPlacementGraph.motionY);
        this.simulatedPlayer.i(blockPlacementGraph.motionZ);
        this.simulatedPlayer.H(blockPlacementGraph.yaw);
        this.simulatedPlayer.C(blockPlacementGraph.pitch);
        this.simulatedPlayer.D(blockPlacementGraph.previousYaw);
        this.simulatedPlayer.l(blockPlacementGraph.previousPitch);
        this.simulatedPlayer.U(blockPlacementGraph.onGround);
        this.simulatedPlayer.F(blockPlacementGraph.sneaking);
        this.simulatedPlayer.R(blockPlacementGraph.sprinting);
        this.simulatedPlayer.L(blockPlacementGraph.jumpTicks);
        this.simulatedPlayer.t(blockPlacementGraph.jumpMovementFactor);
        this.simulatedPlayer.I(blockPlacementGraph.aiMoveSpeed);
        this.simulatedPlayer.J(blockPlacementGraph.inWater);
        AttributeInstance movementSpeed = this.simulatedPlayer.h(MonsterAttributesBridge.B());
        movementSpeed.J();
        for (Object modifier : blockPlacementGraph.movementSpeedModifiers) {
            movementSpeed.applyModifier(new AttributeModifier(modifier));
        }
        this.forwardKeyDown = blockPlacementGraph.forwardKeyDown;
        this.backwardKeyDown = blockPlacementGraph.backwardKeyDown;
        this.leftKeyDown = blockPlacementGraph.leftKeyDown;
        this.rightKeyDown = blockPlacementGraph.rightKeyDown;
        this.sneakKeyDown = blockPlacementGraph.sneakKeyDown;
        this.jumpKeyDown = blockPlacementGraph.jumpKeyDown;
        this.sprintKeyDown = blockPlacementGraph.sprintKeyDown;
    }

    public BlockInLegacyMovementController189(EntityPlayer simulatedPlayer, EntityPlayerSP localPlayer,
                                              EntityPlayer sourcePlayer, World world) {
        super(simulatedPlayer, localPlayer, sourcePlayer, world);
    }

    @Override
    public void setInput(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean sneak) {
        this.forwardKeyDown = forward;
        this.backwardKeyDown = backward;
        this.leftKeyDown = left;
        this.rightKeyDown = right;
        this.jumpKeyDown = jump;
        this.sneakKeyDown = sneak;
    }


    private void updateMovementInput() {
        this.moveForward = 0.0f;
        this.moveStrafe = 0.0f;
        if (this.forwardKeyDown) {
            this.moveForward += 1.0f;
        }
        if (this.backwardKeyDown) {
            this.moveForward -= 1.0f;
        }
        if (this.leftKeyDown) {
            this.moveStrafe += 1.0f;
        }
        if (this.rightKeyDown) {
            this.moveStrafe -= 1.0f;
        }
        this.jumpInput = this.jumpKeyDown;
        this.sneakInput = this.sneakKeyDown;
        if (this.sneakInput) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
            this.moveForward = (float)((double)this.moveForward * 0.3);
        }
    }
}
