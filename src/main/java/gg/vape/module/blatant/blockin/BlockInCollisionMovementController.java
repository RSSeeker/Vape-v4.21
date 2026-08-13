package gg.vape.module.blatant.blockin;

import gg.vape.mapping.MappedClasses;
import gg.vape.module.utility.mlg.MLGBlockWrapper;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.AttributeInstance;
import gg.vape.wrapper.impl.AttributeModifier;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockReaderBridge;
import gg.vape.wrapper.impl.BlockStateWorldBridge;
import gg.vape.wrapper.impl.Direction;
import gg.vape.wrapper.impl.DirectionAxis;
import gg.vape.wrapper.impl.DirectionVector;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EntitySelectors;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.MoverType;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;
import java.util.List;

public class BlockInCollisionMovementController
extends AbstractBlockInMovementController {

    private void applySneakDownwardMotion() {
        this.simulatedPlayer.h(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().addVector(0.0, -0.04f, 0.0));
    }

    @Override
    public void tick() {
        this.simulatePlayerTick();
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

    private boolean isPositionClear(BlockPos blockPos) {
        AxisAlignedBB playerBounds = this.simulatedPlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
        AxisAlignedBB blockColumn = AxisAlignedBB.create(blockPos.getX(), playerBounds.getMinY(), blockPos.getZ(),
                blockPos.getX() + 1.0, playerBounds.getMaxY(), blockPos.getZ() + 1.0).y(1.0E-7);
        return !this.world.z(this.simulatedPlayer, blockColumn, this::shouldBlockPushPlayer);
    }

    private void travelWithFlightHandling(Vec3 movementInput) {
        this.simulatedPlayer.z(false);
        if (this.simulatedPlayer.X$src$Z$1id4hz7() && !this.simulatedPlayer.f$src$Z$fst3rk()) {
            double targetVerticalMotion = this.simulatedPlayer.E$src$Lgg_vape_wrapper_impl_Vec3_$2tp8us().getY();
            double interpolationRate = targetVerticalMotion < -0.2 ? 0.085 : 0.06;
            BlockPos headBlock = BlockPos.D(this.simulatedPlayer.z(),
                    this.simulatedPlayer.N() + 0.9, this.simulatedPlayer.h());
            if (targetVerticalMotion <= 0.0 || this.simulatedPlayer.e$src$Z$15bd4i1()
                    || !this.world.getBlockState(headBlock).j().isEmpty()) {
                Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
                this.simulatedPlayer.h(motion.addVector(
                        0.0, (targetVerticalMotion - motion.getY()) * interpolationRate, 0.0));
            }
        }
        if (this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying() && !this.simulatedPlayer.f$src$Z$fst3rk()) {
            double previousVerticalMotion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().getY();
            float previousJumpMovementFactor = this.simulatedPlayer.y$src$F$15mczw1();
            this.simulatedPlayer.t(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().m$src$F$1kykyr0() * (float)(this.simulatedPlayer.B$src$Z$f90iek() ? 2 : 1));
            this.travel(movementInput);
            Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
            this.simulatedPlayer.F(motion.getX(), previousVerticalMotion * 0.6, motion.getZ());
            this.simulatedPlayer.t(previousJumpMovementFactor);
            this.simulatedPlayer.U(0.0f);
            this.simulatedPlayer.k(7, false);
        } else {
            this.travel(movementInput);
        }
    }

    private boolean shouldSlowMovementInput() {
        return this.simulatedPlayer.P() || this.simulatedPlayer.I$src$Z$fcv2k3();
    }


    private Vec3 applyGroundMovement(Vec3 movementInput, float slipperiness) {
        this.simulatedPlayer.i(this.simulatedPlayer.b(slipperiness), movementInput);
        this.simulatedPlayer.h(this.simulatedPlayer.n(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi()));
        this.simulatedPlayer.B(MoverType.X(), this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
        Vec3 resultingMotion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
        if ((this.simulatedPlayer.r() || this.simulatedPlayer.e$src$Z$15bd4i1()) && this.simulatedPlayer.S$src$Z$151gttj()) {
            resultingMotion = Vec3.create(resultingMotion.getX(), 0.2, resultingMotion.getZ());
        }
        return resultingMotion;
    }

    private boolean shouldApplyDrag() {
        return true;
    }

    private void pushNearbyEntities() {
        List nearbyEntities = this.world.i(this.simulatedPlayer,
                this.simulatedPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu(),
                EntitySelectors.pushableBy(this.localPlayer));
        if (!nearbyEntities.isEmpty()) {
            for (Object entityObject : nearbyEntities) {
                if (MappedClasses.z5.isInstance(entityObject)
                        || entityObject == this.simulatedPlayer.getObject()
                        || entityObject == this.sourcePlayer.getObject()) {
                    continue;
                }
                this.simulatedPlayer.z(new Entity(entityObject));
            }
        }
    }

    private void tickPlayer() {
        this.simulatedPlayer.i$src$Z$1imh02c();
        this.updatePlayer();
        double clampedX = MathUtil.clamp(this.simulatedPlayer.z(), -2.9999999E7, 2.9999999E7);
        double clampedZ = MathUtil.clamp(this.simulatedPlayer.h(), -2.9999999E7, 2.9999999E7);
        if (clampedX != this.simulatedPlayer.z() || clampedZ != this.simulatedPlayer.h()) {
            this.simulatedPlayer.B(clampedX, this.simulatedPlayer.N(), clampedZ);
        }
        this.simulatedPlayer.d$src$V$1ijq103();
    }

    private boolean hasForwardInput() {
        return this.moveForward > 1.0E-5f;
    }

    private void pushOutOfBlocks(double positionX, double positionZ) {
        BlockPos blockPos = BlockPos.D(positionX, this.simulatedPlayer.N(), positionZ);
        if (this.isPositionClear(blockPos)) {
            double localX = positionX - blockPos.getX();
            double localZ = positionZ - blockPos.getZ();
            Direction nearestDirection = null;
            double nearestDistance = Double.MAX_VALUE;
            EnumFacing[] horizontalFacings = new EnumFacing[]{EnumFacing.X(),
                    EnumFacing.g$src$Lgg_vape_wrapper_impl_EnumFacing_$1ii8mzu(),
                    EnumFacing.w(), EnumFacing.M()};
            for (EnumFacing facing : horizontalFacings) {
                Direction direction = new Direction(facing.getObject());
                double axisPosition = direction.n().choose(localX, 0.0, localZ);
                double edgeDistance = direction.Q$src$Lgg_vape_wrapper_impl_DirectionVector_$l2h44r()
                        .equals(DirectionVector.positive())
                        ? 1.0 - axisPosition : axisPosition;
                if (edgeDistance >= nearestDistance
                        || this.isPositionClear(blockPos.offset(direction))) {
                    continue;
                }
                nearestDistance = edgeDistance;
                nearestDirection = direction;
            }
            if (nearestDirection != null) {
                Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
                if (nearestDirection.n().equals(DirectionAxis.x())) {
                    this.simulatedPlayer.F(0.1 * nearestDirection.g(), motion.getY(), motion.getZ());
                } else {
                    this.simulatedPlayer.F(motion.getX(), motion.getY(), 0.1 * nearestDirection.o());
                }
            }
        }
    }

    @Override
    public void initialize() {
        this.sprintToggleTimer = this.localPlayer.L$src$I$1tmeeo5();
        this.sprintingTicksLeft = this.localPlayer.z$src$I$1uboxyr();
        AttributeInstance sourceMovementSpeed = this.sourcePlayer.h(MonsterAttributesBridge.B());
        AttributeInstance simulatedMovementSpeed = this.simulatedPlayer.h(MonsterAttributesBridge.B());
        simulatedMovementSpeed.J();
        for (Object modifier : sourceMovementSpeed.I()) {
            simulatedMovementSpeed.applyModifier(new AttributeModifier(modifier));
        }
        this.simulatedPlayer.M(this.sourcePlayer.F());
        this.simulatedPlayer.k$src$V$5315b7(this.sourcePlayer.N$src$F$14ypudi());
    }

    private void updateLivingMotion() {
        this.updateMovementPhysics();
        this.simulatedPlayer.t(0.02f);
        if (this.simulatedPlayer.B$src$Z$f90iek()) {
            this.simulatedPlayer.t((float)((double)this.simulatedPlayer.y$src$F$15mczw1() + 0.005999999865889549));
        }
        this.simulatedPlayer.I((float)this.simulatedPlayer.z(MonsterAttributesBridge.B()));
    }


    private double getFluidJumpThreshold() {
        return (double)this.simulatedPlayer.X() < 0.4 ? 0.0 : 0.4;
    }

    private void updateMovementAndSprinting() {
        ++this.sprintingTicksLeft;
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        boolean wasSneaking = this.sneakInput;
        boolean hadEnoughForwardInput = this.hasEnoughForwardInput();
        this.updateMovementInput(this.shouldSlowMovementInput());
        double positionX = this.simulatedPlayer.z();
        double positionZ = this.simulatedPlayer.h();
        double collisionOffset = this.simulatedPlayer.f$src$F$fst3ac() * 0.35;
        this.pushOutOfBlocks(positionX - collisionOffset, positionZ + collisionOffset);
        this.pushOutOfBlocks(positionX - collisionOffset, positionZ - collisionOffset);
        this.pushOutOfBlocks(positionX + collisionOffset, positionZ - collisionOffset);
        this.pushOutOfBlocks(positionX + collisionOffset, positionZ + collisionOffset);
        if (wasSneaking) {
            this.sprintToggleTimer = 0;
        }
        boolean canSprint = this.simulatedPlayer
                .Y$src$Lgg_vape_wrapper_impl_FoodStats_$fakh1z().getFoodLevel() > 6
                || this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().H();
        if ((this.simulatedPlayer.b$src$Z$fqlxe4() || this.isSwimming()) && !wasSneaking
                && !hadEnoughForwardInput && this.hasEnoughForwardInput()
                && !this.simulatedPlayer.B$src$Z$f90iek() && canSprint
                && !this.simulatedPlayer.i(PotionRegistry.K)) {
            if (this.sprintToggleTimer <= 0 && !this.sprintKeyDown) {
                this.sprintToggleTimer = 7;
            } else {
                this.simulatedPlayer.R(true);
                this.sprintingTicksLeft = 0;
            }
        }
        if (!this.simulatedPlayer.B$src$Z$f90iek()
                && (!this.simulatedPlayer.h$src$Z$ftwoya() || this.isSwimming())
                && this.hasEnoughForwardInput() && canSprint
                && !this.simulatedPlayer.i(PotionRegistry.K) && this.sprintKeyDown) {
            this.simulatedPlayer.R(true);
            this.sprintingTicksLeft = 0;
        }
        if (this.simulatedPlayer.B$src$Z$f90iek()) {
            boolean lacksForwardInput = !this.hasForwardInput() || !canSprint;
            boolean shouldStopSprinting = lacksForwardInput || this.simulatedPlayer.r()
                    || this.simulatedPlayer.h$src$Z$ftwoya() && !this.isSwimming();
            if (this.simulatedPlayer.X$src$Z$1id4hz7()) {
                if (!this.simulatedPlayer.b$src$Z$fqlxe4() && !this.sneakInput
                        && lacksForwardInput || !this.simulatedPlayer.h$src$Z$ftwoya()) {
                    this.simulatedPlayer.R(false);
                    this.sprintingTicksLeft = 0;
                }
            } else if (shouldStopSprinting) {
                this.simulatedPlayer.R(false);
                this.sprintingTicksLeft = 0;
            }
        }
        if (this.simulatedPlayer.h$src$Z$ftwoya() && this.sneakInput && !this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
            this.applySneakDownwardMotion();
        }
        this.updateLivingMotion();
    }

    private boolean hasEnoughForwardInput() {
        return this.isSwimming() ? this.hasForwardInput() : this.moveForward >= 0.8f;
    }

    private void updateMovementInput(boolean slowDown) {
        if (this.forwardKeyDown != this.backwardKeyDown) {
            this.moveForward = this.forwardKeyDown ? 1.0f : -1.0f;
        }
        this.moveStrafe = this.leftKeyDown == this.rightKeyDown ? 0.0f : (this.leftKeyDown ? 1.0f : -1.0f);
        this.jumpInput = this.jumpKeyDown;
        this.sneakInput = this.sneakKeyDown;
        if (slowDown) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
            this.moveForward = (float)((double)this.moveForward * 0.3);
        }
    }

    private void simulatePlayerTick() {
        this.tickPlayer();
    }

    private void prepareTick() {
        this.updatePreviousState();
    }

    private void applyMovementInputToPlayer() {
        this.simulatedPlayer.k$src$V$5315b7(this.moveStrafe);
        this.simulatedPlayer.M(this.moveForward);
        this.simulatedPlayer.b(this.jumpInput);
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
        Vec3 motion = Vec3.create(
                blockPlacementGraph.motionX, blockPlacementGraph.motionY, blockPlacementGraph.motionZ);
        this.simulatedPlayer.h(motion);
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

    private void updateMovementPhysics() {
        if (this.simulatedPlayer.B$src$I$14s4bbr() > 0) {
            this.simulatedPlayer.L(this.simulatedPlayer.B$src$I$14s4bbr() - 1);
        }
        if (!this.shouldApplyDrag()) {
            this.simulatedPlayer.h(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().r(0.98));
        }
        Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
        double motionX = motion.getX();
        double motionY = motion.getY();
        double motionZ = motion.getZ();
        if (Math.abs(motionX) < 0.003) {
            motionX = 0.0;
        }
        if (Math.abs(motionY) < 0.003) {
            motionY = 0.0;
        }
        if (Math.abs(motionZ) < 0.003) {
            motionZ = 0.0;
        }
        this.simulatedPlayer.F(motionX, motionY, motionZ);
        this.applyMovementInputToPlayer();
        if (this.simulatedPlayer.e$src$Z$15bd4i1() && !this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
            double fluidHeight = this.simulatedPlayer.Q$src$Z$fh9faz()
                    ? this.simulatedPlayer.Z(MLGBlockWrapper.getLavaBlock())
                    : this.simulatedPlayer.Z(MLGBlockWrapper.getWaterBlock());
            boolean submergedInWater = this.simulatedPlayer.h$src$Z$ftwoya() && fluidHeight > 0.0;
            double jumpThreshold = this.getFluidJumpThreshold();
            if (!submergedInWater
                    || this.simulatedPlayer.b$src$Z$fqlxe4() && fluidHeight <= jumpThreshold) {
                if (!this.simulatedPlayer.Q$src$Z$fh9faz()
                        || this.simulatedPlayer.b$src$Z$fqlxe4() && fluidHeight <= jumpThreshold) {
                    if ((this.simulatedPlayer.b$src$Z$fqlxe4()
                            || submergedInWater && fluidHeight <= jumpThreshold)
                            && this.simulatedPlayer.B$src$I$14s4bbr() == 0) {
                        this.simulatedPlayer.t$src$V$15jm1b0();
                        this.simulatedPlayer.L(10);
                    }
                } else {
                    this.swimUp();
                }
            } else {
                this.swimUp();
            }
        } else {
            this.simulatedPlayer.L(0);
        }
        this.simulatedPlayer.k$src$V$5315b7(this.simulatedPlayer.N$src$F$14ypudi() * 0.98f);
        this.simulatedPlayer.M(this.simulatedPlayer.F() * 0.98f);
        Vec3 movementInput = Vec3.create(
                this.simulatedPlayer.N$src$F$14ypudi(), 0.0, this.simulatedPlayer.F());
        this.travelWithFlightHandling(movementInput);
        this.pushNearbyEntities();
    }

    private void updatePreviousState() {
        this.simulatedPlayer.l(this.simulatedPlayer.V());
        this.simulatedPlayer.D(this.simulatedPlayer.J());
        this.simulatedPlayer.x$src$Z$g2peg2();
        this.simulatedPlayer.n();
        this.simulatedPlayer.V$src$V$1ic0wp1();
    }

    private void travel(Vec3 movementInput) {
        if (this.shouldApplyDrag() || this.simulatedPlayer.H$src$Z$fcb9yq()) {
            double gravity = 0.08;
            boolean descending = this.simulatedPlayer
                    .C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().getY() <= 0.0;
            if (descending && this.simulatedPlayer.i(PotionRegistry.k)) {
                gravity = 0.01;
                this.simulatedPlayer.U(0.0f);
            }
            BlockStateWorldBridge currentBlockState = this.world.o(
                    this.simulatedPlayer.J$src$Lgg_vape_wrapper_impl_BlockPos_$kv8a0x());
            if (this.simulatedPlayer.h$src$Z$ftwoya()
                    && this.simulatedPlayer.y$src$Z$1iv9pk4()
                    && !this.simulatedPlayer.l(currentBlockState)) {
                double previousY = this.simulatedPlayer.N();
                float waterDrag = this.simulatedPlayer.B$src$Z$f90iek()
                        ? 0.9f : this.simulatedPlayer.m$src$F$15frgrp();
                float waterAcceleration = 0.02f;
                float depthStriderLevel = EnchantmentHelper.y(this.simulatedPlayer);
                if (depthStriderLevel > 3.0f) {
                    depthStriderLevel = 3.0f;
                }
                if (!this.simulatedPlayer.b$src$Z$fqlxe4()) {
                    depthStriderLevel *= 0.5f;
                }
                if (depthStriderLevel > 0.0f) {
                    waterDrag += (0.54600006f - waterDrag) * depthStriderLevel / 3.0f;
                    waterAcceleration += (this.simulatedPlayer.C$src$F$1i1kt1e()
                            - waterAcceleration) * depthStriderLevel / 3.0f;
                }
                if (this.simulatedPlayer.i(PotionRegistry.H)) {
                    waterDrag = 0.96f;
                }
                this.simulatedPlayer.i(waterAcceleration, movementInput);
                this.simulatedPlayer.B(MoverType.X(), this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
                Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
                if (this.simulatedPlayer.r() && this.simulatedPlayer.S$src$Z$151gttj()) {
                    motion = Vec3.create(motion.getX(), 0.2, motion.getZ());
                }
                this.simulatedPlayer.h(motion.G(waterDrag, 0.8f, waterDrag));
                Vec3 adjustedMotion = this.simulatedPlayer.G(gravity, descending,
                        this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
                this.simulatedPlayer.h(adjustedMotion);
                if (this.simulatedPlayer.r() && this.simulatedPlayer.i$src$Z$avhpwd(
                        adjustedMotion.getX(), adjustedMotion.getY() + 0.6f
                                - this.simulatedPlayer.N() + previousY,
                        adjustedMotion.getZ())) {
                    this.simulatedPlayer.F(adjustedMotion.getX(), 0.3f, adjustedMotion.getZ());
                }
            } else if (this.simulatedPlayer.Q$src$Z$fh9faz()
                    && this.simulatedPlayer.y$src$Z$1iv9pk4()
                    && !this.simulatedPlayer.l(currentBlockState)) {
                Vec3 adjustedMotion;
                double previousY = this.simulatedPlayer.N();
                this.simulatedPlayer.i(0.02f, movementInput);
                this.simulatedPlayer.B(MoverType.X(), this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
                if (this.simulatedPlayer.Z(MLGBlockWrapper.getLavaBlock()) <= this.getFluidJumpThreshold()) {
                    this.simulatedPlayer.h(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().G(0.5, 0.8f, 0.5));
                    adjustedMotion = this.simulatedPlayer.G(gravity, descending,
                            this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
                    this.simulatedPlayer.h(adjustedMotion);
                } else {
                    this.simulatedPlayer.h(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().r(0.5));
                }
                if (!this.simulatedPlayer.v$src$Z$g1lt9c()) {
                    this.simulatedPlayer.h(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi()
                            .addVector(0.0, -gravity / 4.0, 0.0));
                }
                adjustedMotion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
                if (this.simulatedPlayer.r() && this.simulatedPlayer.i$src$Z$avhpwd(
                        adjustedMotion.getX(), adjustedMotion.getY() + 0.6f
                                - this.simulatedPlayer.N() + previousY,
                        adjustedMotion.getZ())) {
                    this.simulatedPlayer.F(adjustedMotion.getX(), 0.3f, adjustedMotion.getZ());
                }
            } else if (!this.simulatedPlayer.k$src$Z$15enw27()) {
                BlockPos groundPos = this.simulatedPlayer.x$src$Lgg_vape_wrapper_impl_BlockPos_$1izbb73();
                float slipperiness = this.world.getBlockState(groundPos).getBlock().c();
                float horizontalDrag = this.simulatedPlayer.b$src$Z$fqlxe4()
                        ? slipperiness * 0.91f : 0.91f;
                Vec3 resultingMotion = this.applyGroundMovement(movementInput, slipperiness);
                double verticalMotion = resultingMotion.getY();
                if (this.simulatedPlayer.i(PotionRegistry.h)) {
                    verticalMotion += (0.05 * (this.simulatedPlayer.b(PotionRegistry.h).L() + 1)
                            - resultingMotion.getY()) * 0.2;
                    this.simulatedPlayer.U(0.0f);
                } else if (!this.world.j$src$Z$11aji0a(groundPos)) {
                    verticalMotion = this.simulatedPlayer.N() > 0.0 ? -0.1 : 0.0;
                } else if (!this.simulatedPlayer.v$src$Z$g1lt9c()) {
                    verticalMotion -= gravity;
                }
                this.simulatedPlayer.F(resultingMotion.getX() * horizontalDrag,
                        verticalMotion * 0.98f, resultingMotion.getZ() * horizontalDrag);
            }
        }
    }

    private boolean shouldBlockPushPlayer(Object blockReader, Object position) {
        BlockReaderBridge blockReaderBridge = new BlockReaderBridge(blockReader);
        return blockReaderBridge.isSuffocating(this.world.getObject(), position);
    }

    private void swimUp() {
        this.simulatedPlayer.h(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().addVector(0.0, 0.04f, 0.0));
    }

    public BlockInCollisionMovementController(EntityPlayer simulatedPlayer, EntityPlayerSP localPlayer,
                                              EntityPlayer sourcePlayer, World world) {
        super(simulatedPlayer, localPlayer, sourcePlayer, world);
    }

    private void updatePlayer() {
        this.prepareTick();
        this.updateMovementAndSprinting();
    }

    private boolean isSwimming() {
        return this.simulatedPlayer.N$src$Z$1i7mk1l();
    }
}
