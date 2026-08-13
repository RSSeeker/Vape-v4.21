package gg.vape.module.blatant.blockin;

import gg.vape.mapping.MappedClasses;
import gg.vape.module.utility.mlg.MLGBlockWrapper;
import gg.vape.utils.math.NumericMathUtil;
import gg.vape.wrapper.impl.AttributeInstance;
import gg.vape.wrapper.impl.AttributeModifier;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockRayTraceResult;
import gg.vape.wrapper.impl.BlockStateWorldBridge;
import gg.vape.wrapper.impl.Blocks;
import gg.vape.wrapper.impl.ChestType;
import gg.vape.wrapper.impl.Direction;
import gg.vape.wrapper.impl.DirectionAxis;
import gg.vape.wrapper.impl.DirectionVector;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EntitySelectors;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ITooltipFlag;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.MoverType;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.RayTraceContextFactory;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;
import java.util.List;

public class BlockInEntityMovementController
extends AbstractBlockInMovementController {
    private void updateMovementInput() {
        this.jumpInput = this.jumpKeyDown;
        this.sneakInput = this.sneakKeyDown;
        this.moveForward = BlockInEntityMovementController.getAxisInput(this.forwardKeyDown, this.backwardKeyDown);
        this.moveStrafe = BlockInEntityMovementController.getAxisInput(this.leftKeyDown, this.rightKeyDown);
    }


    private void tickIfPlayerActive() {
        if (this.localPlayer.s$src$Z$1iryxzy()) {
            this.simulatePlayerTick();
        }
    }

    private boolean isMovingToward(Vec3 movement) {
        float yawRadians = this.simulatedPlayer.J() * ((float)Math.PI / 180);
        double sinYaw = NumericMathUtil.sin(yawRadians);
        double cosYaw = NumericMathUtil.cos(yawRadians);
        double inputX = this.simulatedPlayer.N$src$F$14ypudi() * cosYaw
                - this.simulatedPlayer.F() * sinYaw;
        double inputZ = this.simulatedPlayer.F() * cosYaw
                + this.simulatedPlayer.N$src$F$14ypudi() * sinYaw;
        double inputLengthSquared = NumericMathUtil.square(inputX) + NumericMathUtil.square(inputZ);
        double movementLengthSquared = NumericMathUtil.square(movement.getX())
                + NumericMathUtil.square(movement.getZ());
        if (inputLengthSquared >= 1.0E-5f && movementLengthSquared >= 1.0E-5f) {
            double dotProduct = inputX * movement.getX() + inputZ * movement.getZ();
            double angle = Math.acos(dotProduct
                    / Math.sqrt(inputLengthSquared * movementLengthSquared));
            return angle < 0.13962633907794952;
        }
        return false;
    }

    private boolean isSwimming() {
        return this.simulatedPlayer.N$src$Z$1i7mk1l();
    }

    private void applyMovementInputToPlayer() {
        this.simulatedPlayer.k$src$V$5315b7(this.moveStrafe);
        this.simulatedPlayer.M(this.moveForward);
        this.simulatedPlayer.b(this.jumpKeyDown);
    }

    private void applySneakDownwardMotion() {
        this.simulatedPlayer.h(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().addVector(0.0, -0.04f, 0.0));
    }

    public BlockInEntityMovementController(EntityPlayer simulatedPlayer, EntityPlayerSP localPlayer,
                                           EntityPlayer sourcePlayer, World world) {
        super(simulatedPlayer, localPlayer, sourcePlayer, world);
    }

    @Override
    public void tick() {
        this.tickIfPlayerActive();
    }

    private void travelGliding() {
        Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
        this.simulatedPlayer.h(this.applyGlidingPhysics(motion));
        this.moveEntity(MoverType.X(), this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
    }

    private void updateMovementAndSprinting() {
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }

        boolean wasSneaking = this.sneakInput;
        boolean hadEnoughForwardInput = this.hasEnoughForwardInput();
        this.updateMovementInput();

        if (this.shouldCancelSprintForMovementState()) {
            this.simulatedPlayer.R(false);
        }
        if (this.shouldSlowMovementInput()) {
            float slowdownFactor = (float)this.simulatedPlayer.o(MonsterAttributesBridge.D());
            this.moveStrafe *= slowdownFactor;
            this.moveForward *= slowdownFactor;
        }
        if (!this.simulatedPlayer.d()) {
            double positionX = this.simulatedPlayer.z();
            double positionZ = this.simulatedPlayer.h();
            double collisionOffset = this.simulatedPlayer.f$src$F$fst3ac() * 0.35;
            this.pushOutOfBlocks(positionX - collisionOffset, positionZ + collisionOffset);
            this.pushOutOfBlocks(positionX - collisionOffset, positionZ - collisionOffset);
            this.pushOutOfBlocks(positionX + collisionOffset, positionZ - collisionOffset);
            this.pushOutOfBlocks(positionX + collisionOffset, positionZ + collisionOffset);
        }

        if (wasSneaking) {
            this.sprintToggleTimer = 0;
        } else {
            boolean onGround = this.simulatedPlayer.f$src$Z$fst3rk()
                    ? this.simulatedPlayer.S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12()
                            .b$src$Z$fqlxe4()
                    : this.simulatedPlayer.b$src$Z$fqlxe4();
            if ((onGround || this.simulatedPlayer.N$src$Z$1i7mk1l())
                    && !hadEnoughForwardInput && this.canStartSprinting()) {
                if (this.sprintToggleTimer <= 0 && !this.sprintKeyDown) {
                    this.sprintToggleTimer = 7;
                } else {
                    this.simulatedPlayer.R(true);
                }
            }
        }

        if ((!this.simulatedPlayer.h$src$Z$ftwoya()
                || this.simulatedPlayer.N$src$Z$1i7mk1l())
                && this.canStartSprinting() && this.sprintKeyDown) {
            this.simulatedPlayer.R(true);
        }

        if (this.simulatedPlayer.B$src$Z$f90iek()) {
            boolean lacksForwardInput = !this.hasForwardInput() || !this.hasEnoughFoodToSprint();
            boolean shouldStopSprinting = lacksForwardInput
                    || this.simulatedPlayer.r() && !this.simulatedPlayer.m$src$Z$fwnnx3()
                    || this.simulatedPlayer.h$src$Z$ftwoya()
                            && !this.simulatedPlayer.N$src$Z$1i7mk1l();
            if (this.simulatedPlayer.X$src$Z$1id4hz7()) {
                if (!this.simulatedPlayer.b$src$Z$fqlxe4()
                        && !this.sneakKeyDown && lacksForwardInput
                        || !this.simulatedPlayer.h$src$Z$ftwoya()) {
                    this.simulatedPlayer.R(false);
                }
            } else if (shouldStopSprinting) {
                this.simulatedPlayer.R(false);
            }
        }

        if (this.simulatedPlayer.h$src$Z$ftwoya() && this.sneakKeyDown
                && this.simulatedPlayer.y$src$Z$1iv9pk4()) {
            this.applySneakDownwardMotion();
        }
        this.updateLivingMotion();
    }
    private void pushOutOfBlocks(double positionX, double positionZ) {
        BlockPos blockPos = BlockPos.D(positionX, this.simulatedPlayer.N(), positionZ);
        if (this.isPositionClear(blockPos)) {
            double localX = positionX - blockPos.getX();
            double localZ = positionZ - blockPos.getZ();
            Direction nearestDirection = null;
            double nearestDistance = Double.MAX_VALUE;
            EnumFacing[] horizontalFacings = new EnumFacing[]{Direction.X(),
                    Direction.g$src$Lgg_vape_wrapper_impl_EnumFacing_$1ii8mzu(),
                    Direction.w(), Direction.M()};
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
                    this.simulatedPlayer.F(0.1 * nearestDirection.S(), motion.getY(), motion.getZ());
                } else {
                    this.simulatedPlayer.F(motion.getX(), motion.getY(), 0.1 * nearestDirection.F());
                }
            }
        }
    }

    private boolean shouldCancelSprintForMovementState() {
        return this.simulatedPlayer.k$src$Z$15enw27() || this.hasBlindness() || this.shouldSlowMovementInput() || this.simulatedPlayer.f$src$Z$fst3rk();
    }

    @Override
    public void applySnapshot(BlockPlacementGraph blockPlacementGraph) {
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
        AttributeInstance movementSpeed = this.simulatedPlayer.t(MonsterAttributesBridge.U());
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

    private void swimUp() {
        this.simulatedPlayer.h(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().addVector(0.0, 0.04f, 0.0));
    }

    private void simulatePlayerTick() {
        this.tickPlayer();
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

    private boolean isCrouchingOutOfWater() {
        return this.simulatedPlayer.I$src$Z$fcv2k3() && !this.simulatedPlayer.h$src$Z$ftwoya();
    }

    private Vec3 applyGroundMovement(Vec3 movementInput, float slipperiness) {
        this.simulatedPlayer.i(this.simulatedPlayer.b(slipperiness), movementInput);
        this.simulatedPlayer.h(this.simulatedPlayer.n(this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi()));
        this.moveEntity(MoverType.X(), this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
        Vec3 resultingMotion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
        if ((this.simulatedPlayer.r() || this.simulatedPlayer.e$src$Z$15bd4i1()) && (this.simulatedPlayer.S$src$Z$151gttj() || this.simulatedPlayer.y().w(Blocks.powderSnow()) && Block.r(this.simulatedPlayer))) {
            resultingMotion = Vec3.create(resultingMotion.getX(), 0.2, resultingMotion.getZ());
        }
        return resultingMotion;
    }


    private void updatePreviousState() {
        this.simulatedPlayer.U(null);
        this.simulatedPlayer.p(this.simulatedPlayer.E$src$Z$fanw6n());
        this.simulatedPlayer.g(false);
        this.simulatedPlayer.x$src$Z$g2peg2();
        this.simulatedPlayer.n();
        this.simulatedPlayer.V$src$V$1ic0wp1();
        if (this.simulatedPlayer.Q$src$Z$fh9faz()) {
            this.simulatedPlayer.U(this.simulatedPlayer.M$src$F$ff28gb() * 0.5f);
        }
        this.simulatedPlayer.A$src$V$f8gppr();
    }

    private float getMovementSpeedAttribute() {
        return (float)this.simulatedPlayer.o(MonsterAttributesBridge.U());
    }

    private void travelInFluid(Vec3 movementInput) {
        boolean descending = this.simulatedPlayer
                .C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().getY() <= 0.0;
        double previousY = this.simulatedPlayer.N();
        double gravity = this.simulatedPlayer.J$src$D$14winyc();
        if (this.simulatedPlayer.h$src$Z$ftwoya()) {
            float waterDrag = this.simulatedPlayer.B$src$Z$f90iek()
                    ? 0.9f : this.simulatedPlayer.m$src$F$15frgrp();
            float waterAcceleration = 0.02f;
            float fluidEfficiency = (float)this.simulatedPlayer.o(MonsterAttributesBridge.g());
            if (!this.simulatedPlayer.b$src$Z$fqlxe4()) {
                fluidEfficiency *= 0.5f;
            }
            if (fluidEfficiency > 0.0f) {
                waterDrag += (0.54600006f - waterDrag) * fluidEfficiency;
                waterAcceleration += (this.getMovementSpeedAttribute() - waterAcceleration)
                        * fluidEfficiency;
            }
            if (this.simulatedPlayer.i(PotionRegistry.H)) {
                waterDrag = 0.96f;
            }
            this.simulatedPlayer.i(waterAcceleration, movementInput);
            this.moveEntity(MoverType.X(),
                    this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
            Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
            if (this.simulatedPlayer.r() && this.simulatedPlayer.S$src$Z$151gttj()) {
                motion = Vec3.create(motion.getX(), 0.2, motion.getZ());
            }
            motion = motion.G(waterDrag, 0.8f, waterDrag);
            this.simulatedPlayer.h(this.simulatedPlayer.G(gravity, descending, motion));
        } else {
            this.simulatedPlayer.i(0.02f, movementInput);
            this.moveEntity(MoverType.X(),
                    this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
            if (this.simulatedPlayer.Z(MLGBlockWrapper.getLavaBlock()) <= this.getFluidJumpThreshold()) {
                this.simulatedPlayer.h(this.simulatedPlayer
                        .C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().G(0.5, 0.8f, 0.5));
                Vec3 adjustedMotion = this.simulatedPlayer.G(gravity, descending,
                        this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi());
                this.simulatedPlayer.h(adjustedMotion);
            } else {
                this.simulatedPlayer.h(this.simulatedPlayer
                        .C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().r(0.5));
            }
            if (gravity != 0.0) {
                this.simulatedPlayer.h(this.simulatedPlayer
                        .C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi()
                        .addVector(0.0, -gravity / 4.0, 0.0));
            }
        }
        Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
        if (this.simulatedPlayer.r() && this.simulatedPlayer.i$src$Z$avhpwd(
                motion.getX(), motion.getY() + 0.6f - this.simulatedPlayer.N() + previousY,
                motion.getZ())) {
            this.simulatedPlayer.F(motion.getX(), 0.3f, motion.getZ());
        }
    }
    private boolean shouldSlowMovementInput() {
        return this.simulatedPlayer.P() || this.isCrouchingOutOfWater();
    }

    private void tickPlayer() {
        this.simulatedPlayer.z(false);
        if (this.simulatedPlayer.O$src$Z$fg5u49() || this.simulatedPlayer.f$src$Z$fst3rk()) {
            this.simulatedPlayer.U(false);
        }
        this.simulatedPlayer.i$src$Z$1imh02c();
        this.updatePlayer();
        double clampedX = NumericMathUtil.clamp(this.simulatedPlayer.z(), -2.9999999E7, 2.9999999E7);
        double clampedZ = NumericMathUtil.clamp(this.simulatedPlayer.h(), -2.9999999E7, 2.9999999E7);
        if (clampedX != this.simulatedPlayer.z() || clampedZ != this.simulatedPlayer.h()) {
            this.simulatedPlayer.B(clampedX, this.simulatedPlayer.N(), clampedZ);
        }
        this.simulatedPlayer.d$src$V$1ijq103();
    }

    private Vec3 applyGlidingPhysics(Vec3 motion) {
        Vec3 lookVector = this.simulatedPlayer.E$src$Lgg_vape_wrapper_impl_Vec3_$2tp8us();
        float pitchRadians = this.simulatedPlayer.V() * ((float)Math.PI / 180);
        double horizontalLookLength = lookVector.Q();
        double horizontalMotionLength = motion.Q();
        double gravity = this.simulatedPlayer.J$src$D$14winyc();
        double pitchFactor = NumericMathUtil.square(Math.cos(pitchRadians));
        motion = motion.addVector(0.0, gravity * (-1.0 + pitchFactor * 0.75), 0.0);

        if (motion.getY() < 0.0 && horizontalLookLength > 0.0) {
            double diveLift = motion.getY() * -0.1 * pitchFactor;
            motion = motion.addVector(
                    lookVector.getX() * diveLift / horizontalLookLength,
                    diveLift,
                    lookVector.getZ() * diveLift / horizontalLookLength);
        }
        if (pitchRadians < 0.0f && horizontalLookLength > 0.0) {
            double climbLift = horizontalMotionLength
                    * -NumericMathUtil.sin(pitchRadians) * 0.04;
            motion = motion.addVector(
                    -lookVector.getX() * climbLift / horizontalLookLength,
                    climbLift * 3.2,
                    -lookVector.getZ() * climbLift / horizontalLookLength);
        }
        if (horizontalLookLength > 0.0) {
            motion = motion.addVector(
                    (lookVector.getX() / horizontalLookLength * horizontalMotionLength
                            - motion.getX()) * 0.1,
                    0.0,
                    (lookVector.getZ() / horizontalLookLength * horizontalMotionLength
                            - motion.getZ()) * 0.1);
        }
        return motion.G(0.99f, 0.98f, 0.99f);
    }
    private boolean hasForwardInput() {
        return this.moveForward > 1.0E-5f;
    }

    private boolean isPositionClear(BlockPos blockPos) {
        AxisAlignedBB playerBounds = this.simulatedPlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
        AxisAlignedBB blockColumn = AxisAlignedBB.create(blockPos.getX(), playerBounds.getMinY(), blockPos.getZ(),
                blockPos.getX() + 1.0, playerBounds.getMaxY(), blockPos.getZ() + 1.0).v(1.0E-7);
        return this.world.M(this.simulatedPlayer, blockColumn);
    }

    private static float getAxisInput(boolean positive, boolean negative) {
        if (positive == negative) {
            return 0.0f;
        }
        return positive ? 1.0f : -1.0f;
    }

    private void updateMovementPhysics() {
        if (this.simulatedPlayer.B$src$I$14s4bbr() > 0) {
            this.simulatedPlayer.L(this.simulatedPlayer.B$src$I$14s4bbr() - 1);
        }

        Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
        double motionX = Math.abs(motion.getX()) < 0.003 ? 0.0 : motion.getX();
        double motionY = Math.abs(motion.getY()) < 0.003 ? 0.0 : motion.getY();
        double motionZ = Math.abs(motion.getZ()) < 0.003 ? 0.0 : motion.getZ();
        this.simulatedPlayer.F(motionX, motionY, motionZ);

        if (this.simulatedPlayer.p$src$Z$15hev10()) {
            this.simulatedPlayer.b(false);
            this.simulatedPlayer.k$src$V$5315b7(0.0f);
            this.simulatedPlayer.M(0.0f);
        } else {
            this.applyMovementInputToPlayer();
        }

        if (this.simulatedPlayer.e$src$Z$15bd4i1()
                && this.simulatedPlayer.y$src$Z$1iv9pk4()) {
            double fluidHeight = this.simulatedPlayer.Q$src$Z$fh9faz()
                    ? this.simulatedPlayer.Z(MLGBlockWrapper.getLavaBlock())
                    : this.simulatedPlayer.Z(MLGBlockWrapper.getWaterBlock());
            boolean submergedInWater = this.simulatedPlayer.h$src$Z$ftwoya()
                    && fluidHeight > 0.0;
            double jumpThreshold = this.getFluidJumpThreshold();
            if (!submergedInWater
                    || this.simulatedPlayer.b$src$Z$fqlxe4()
                            && fluidHeight <= jumpThreshold) {
                if (!this.simulatedPlayer.Q$src$Z$fh9faz()
                        || this.simulatedPlayer.b$src$Z$fqlxe4()
                                && fluidHeight <= jumpThreshold) {
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

        this.simulatedPlayer.k$src$V$5315b7(
                this.simulatedPlayer.N$src$F$14ypudi() * 0.98f);
        this.simulatedPlayer.M(this.simulatedPlayer.F() * 0.98f);
        Vec3 movementInput = Vec3.create(
                this.simulatedPlayer.N$src$F$14ypudi(), 0.0, this.simulatedPlayer.F());
        this.travelWithFlightHandling(movementInput);
        this.simulatedPlayer.B$src$V$14s4bmy();
        this.simulatedPlayer.R$src$V$150x14q();
        this.pushNearbyEntities();
    }
    private boolean hasEnoughForwardInput() {
        return this.simulatedPlayer.N$src$Z$1i7mk1l()
                ? this.hasForwardInput() : this.moveForward >= 0.8f;
    }

    private void updateLivingMotion() {
        this.updateMovementPhysics();
        this.simulatedPlayer.t((float)this.simulatedPlayer.o(MonsterAttributesBridge.U()));
    }

    private void travelOnGround(Vec3 movementInput) {
        BlockPos groundPos = this.simulatedPlayer.x$src$Lgg_vape_wrapper_impl_BlockPos_$1izbb73();
        float slipperiness = this.simulatedPlayer.b$src$Z$fqlxe4()
                ? this.world.getBlockState(groundPos).getBlock().c() : 1.0f;
        float horizontalDrag = slipperiness * 0.91f;
        Vec3 resultingMotion = this.applyGroundMovement(movementInput, slipperiness);
        double verticalMotion = resultingMotion.getY();
        if (this.simulatedPlayer.i(PotionRegistry.h)) {
            PotionEffect levitation = this.simulatedPlayer.b(PotionRegistry.h);
            verticalMotion += (0.05 * (levitation.L() + 1)
                    - resultingMotion.getY()) * 0.2;
        } else {
            verticalMotion -= this.simulatedPlayer.J$src$D$14winyc();
        }
        if (this.simulatedPlayer.V$src$Z$15347lm()) {
            this.simulatedPlayer.F(
                    resultingMotion.getX(), verticalMotion, resultingMotion.getZ());
        } else {
            this.simulatedPlayer.F(
                    resultingMotion.getX() * horizontalDrag,
                    verticalMotion * 0.98f,
                    resultingMotion.getZ() * horizontalDrag);
        }
    }

    private boolean hasEnoughFoodToSprint() {
        return this.simulatedPlayer.f$src$Z$fst3rk() || (float)this.simulatedPlayer.Y$src$Lgg_vape_wrapper_impl_FoodStats_$fakh1z().getFoodLevel() > 6.0f || this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().H();
    }

    @Override
    public void initialize() {
        this.sprintToggleTimer = this.localPlayer.L$src$I$1tmeeo5();
        AttributeInstance sourceMovementSpeed = this.sourcePlayer.t(MonsterAttributesBridge.U());
        AttributeInstance simulatedMovementSpeed = this.simulatedPlayer.t(MonsterAttributesBridge.U());
        simulatedMovementSpeed.J();
        for (Object modifier : sourceMovementSpeed.I()) {
            simulatedMovementSpeed.applyModifier(new AttributeModifier(modifier));
        }
    }

    private void moveEntity(MoverType moverType, Vec3 requestedMovement) {
        if (this.simulatedPlayer.d()) {
            this.simulatedPlayer.B(
                    this.simulatedPlayer.z() + requestedMovement.getX(),
                    this.simulatedPlayer.N() + requestedMovement.getY(),
                    this.simulatedPlayer.h() + requestedMovement.getZ());
            return;
        }

        if (moverType.equals(MoverType.E())) {
            requestedMovement = this.simulatedPlayer.y(requestedMovement);
            if (requestedMovement.equals(Vec3.H())) {
                return;
            }
        }
        if (this.simulatedPlayer.G$src$Lgg_vape_wrapper_impl_Vec3_$efeys6().j() > 1.0E-7) {
            requestedMovement = requestedMovement.N(
                    this.simulatedPlayer.G$src$Lgg_vape_wrapper_impl_Vec3_$efeys6());
            this.simulatedPlayer.Z(Vec3.H());
            this.simulatedPlayer.h(Vec3.H());
        }

        requestedMovement = this.simulatedPlayer.m(requestedMovement, moverType);
        Vec3 adjustedMovement = this.simulatedPlayer.K(requestedMovement);
        double adjustedLengthSquared = adjustedMovement.j();
        if (adjustedLengthSquared > 1.0E-7
                || requestedMovement.j() - adjustedLengthSquared < 1.0E-7) {
            if (this.simulatedPlayer.M$src$F$ff28gb() != 0.0f
                    && adjustedLengthSquared >= 1.0) {
                BlockRayTraceResult rayTrace = this.world.r(RayTraceContextFactory.create(
                        this.simulatedPlayer.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk(),
                        this.simulatedPlayer.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk()
                                .add(adjustedMovement),
                        ChestType.e(), ITooltipFlag.water(), this.simulatedPlayer));
                if (!rayTrace.getTypeOfHit().equals(RayTraceResult_type.miss())) {
                    this.simulatedPlayer.U(0.0f);
                }
            }
            this.simulatedPlayer.B(
                    this.simulatedPlayer.z() + adjustedMovement.getX(),
                    this.simulatedPlayer.N() + adjustedMovement.getY(),
                    this.simulatedPlayer.h() + adjustedMovement.getZ());
        }

        boolean collidedX = !NumericMathUtil.approximatelyEqual(
                requestedMovement.getX(), adjustedMovement.getX());
        boolean collidedZ = !NumericMathUtil.approximatelyEqual(
                requestedMovement.getZ(), adjustedMovement.getZ());
        this.simulatedPlayer.T(collidedX || collidedZ);

        if (Math.abs(requestedMovement.getY()) > 0.0) {
            this.simulatedPlayer.t(requestedMovement.getY() != adjustedMovement.getY());
            this.simulatedPlayer.h(
                    this.simulatedPlayer.u$src$Z$g120nz() && requestedMovement.getY() < 0.0);
            this.simulatedPlayer.X(
                    this.simulatedPlayer.q$src$Z$fyuuaj(),
                    this.simulatedPlayer.r(), adjustedMovement);
        }
        this.simulatedPlayer.K(
                this.simulatedPlayer.r() && this.isMovingToward(adjustedMovement));

        if (this.simulatedPlayer.r()) {
            Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
            this.simulatedPlayer.F(
                    collidedX ? 0.0 : motion.getX(),
                    motion.getY(),
                    collidedZ ? 0.0 : motion.getZ());
        }

        BlockPos landingPos = this.simulatedPlayer
                .C$src$Lgg_vape_wrapper_impl_BlockPos_$y7f4vu();
        Block landingBlock = this.world.getBlockState(landingPos).getBlock();
        if (requestedMovement.getY() != adjustedMovement.getY()) {
            landingBlock.r(this.world, this.simulatedPlayer);
        }
        float velocityMultiplier = this.simulatedPlayer.i$src$F$1imgzl4();
        this.simulatedPlayer.h(this.simulatedPlayer
                .C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi()
                .G(velocityMultiplier, 1.0, velocityMultiplier));
    }
    private double getFluidJumpThreshold() {
        return (double)this.simulatedPlayer.X() < 0.4 ? 0.0 : 0.4;
    }

    private void travel(Vec3 movementInput) {
        BlockStateWorldBridge blockStateWorldBridge = this.world.o(this.simulatedPlayer.J$src$Lgg_vape_wrapper_impl_BlockPos_$kv8a0x());
        if ((this.simulatedPlayer.h$src$Z$ftwoya() || this.simulatedPlayer.Q$src$Z$fh9faz()) && this.simulatedPlayer.y$src$Z$1iv9pk4() && !this.simulatedPlayer.l(blockStateWorldBridge)) {
            this.travelInFluid(movementInput);
        } else if (this.simulatedPlayer.k$src$Z$15enw27()) {
            this.travelGliding();
        } else {
            this.travelOnGround(movementInput);
        }
    }

    private void updatePlayer() {
        float poseHeight;
        this.updatePreviousState();
        if (!this.simulatedPlayer.M$src$Z$ff28xj()) {
            this.updateMovementAndSprinting();
        }
        if (this.simulatedPlayer.k$src$Z$15enw27()) {
            // empty if block
        }
        if (this.simulatedPlayer.w$src$Z$1iu64de()) {
            this.simulatedPlayer.C(0.0f);
        }
        if ((poseHeight = this.simulatedPlayer.e()) != this.simulatedPlayer.K()) {
            this.simulatedPlayer.j(poseHeight);
            this.simulatedPlayer.O$src$V$fg5u0t();
        }
    }

    private boolean canStartSprinting() {
        return !this.simulatedPlayer.B$src$Z$f90iek() && this.hasEnoughForwardInput() && this.hasEnoughFoodToSprint() && !this.hasBlindness() && !this.simulatedPlayer.f$src$Z$fst3rk() && !this.simulatedPlayer.k$src$Z$15enw27() && (!this.shouldSlowMovementInput() || this.simulatedPlayer.N$src$Z$1i7mk1l());
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

    private boolean hasBlindness() {
        return this.simulatedPlayer.i(PotionRegistry.K);
    }

    private void travelWithFlightHandling(Vec3 movementInput) {
        if (this.simulatedPlayer.f$src$Z$fst3rk()) {
            this.travel(movementInput);
        } else {
            if (this.simulatedPlayer.X$src$Z$1id4hz7()) {
                double targetVerticalMotion = this.simulatedPlayer
                        .E$src$Lgg_vape_wrapper_impl_Vec3_$2tp8us().getY();
                double interpolationRate = targetVerticalMotion < -0.2 ? 0.085 : 0.06;
                BlockPos headBlock = BlockPos.D(this.simulatedPlayer.z(),
                        this.simulatedPlayer.N() + 0.9, this.simulatedPlayer.h());
                if (targetVerticalMotion <= 0.0 || this.simulatedPlayer.e$src$Z$15bd4i1()
                        || !this.world.o(headBlock).isEmpty()) {
                    Vec3 motion = this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi();
                    this.simulatedPlayer.h(motion.addVector(0.0,
                            (targetVerticalMotion - motion.getY()) * interpolationRate, 0.0));
                }
            }
            if (this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
                double previousVerticalMotion = this.simulatedPlayer
                        .C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi().getY();
                this.travel(movementInput);
                this.simulatedPlayer.h(this.simulatedPlayer
                        .C$src$Lgg_vape_wrapper_impl_Vec3_$1q93kwi()
                        .i(DirectionAxis.y(), previousVerticalMotion * 0.6));
            } else {
                this.travel(movementInput);
            }
        }
    }
}
