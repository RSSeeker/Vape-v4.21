package gg.vape.module.blatant.blockin;

import gg.vape.mapping.MappedClasses;
import gg.vape.module.blatant.blockin.AbstractBlockInMovementController;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.utils.MathUtil;
import gg.vape.utils.PlayerSimulationUtil;
import gg.vape.wrapper.impl.AttributeInstance;
import gg.vape.wrapper.impl.AttributeModifier;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EntitySelectors;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.MoverType;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.World;
import java.util.List;

public class BlockInLegacyMovementController1122
extends AbstractBlockInMovementController {
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

    private void pushNearbyEntities() {
        List nearbyEntities = this.world.i(this.simulatedPlayer,
                this.simulatedPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu(),
                EntitySelectors.getTeamCollisionPredicate(this.localPlayer));
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

    private void applyMovementInputToPlayer() {
        this.simulatedPlayer.k$src$V$5315b7(this.moveStrafe);
        this.simulatedPlayer.M(this.moveForward);
        this.simulatedPlayer.b(this.jumpInput);
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

    private void travel(float strafe, float vertical, float forward) {
        this.simulatedPlayer.z(false);
        MoverType moverType = MoverType.X();
        if (!this.simulatedPlayer.h$src$Z$ftwoya() || this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
            if (!this.simulatedPlayer.Q$src$Z$fh9faz() || this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
                float groundFriction = 0.91f;
                if (this.simulatedPlayer.b$src$Z$fqlxe4()) {
                    BlockPos groundPos = BlockPos.create(MathUtil.floor(this.simulatedPlayer.z()),
                            MathUtil.floor(this.simulatedPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY()) - 1,
                            MathUtil.floor(this.simulatedPlayer.h()));
                    groundFriction = this.world.getBlockState(groundPos).getBlock().c() * 0.91f;
                }
                float frictionAcceleration = 0.16277136f
                        / (groundFriction * groundFriction * groundFriction);
                float movementFactor = this.simulatedPlayer.b$src$Z$fqlxe4()
                        ? this.simulatedPlayer.C$src$F$1i1kt1e() * frictionAcceleration
                        : this.simulatedPlayer.y$src$F$15mczw1();
                this.simulatedPlayer.x(strafe, vertical, forward, movementFactor);
                groundFriction = 0.91f;
                if (this.simulatedPlayer.b$src$Z$fqlxe4()) {
                    BlockPos groundPos = BlockPos.create(MathUtil.floor(this.simulatedPlayer.z()),
                            MathUtil.floor(this.simulatedPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY()) - 1,
                            MathUtil.floor(this.simulatedPlayer.h()));
                    groundFriction = this.world.getBlockState(groundPos).getBlock().c() * 0.91f;
                }
                if (this.simulatedPlayer.S$src$Z$151gttj()) {
                    float ladderSpeedLimit = 0.15f;
                    this.simulatedPlayer.r(MathUtil.clamp(this.simulatedPlayer.t(),
                            -ladderSpeedLimit, ladderSpeedLimit));
                    this.simulatedPlayer.i(MathUtil.clamp(this.simulatedPlayer.T(),
                            -ladderSpeedLimit, ladderSpeedLimit));
                    this.simulatedPlayer.U(0.0f);
                    if (this.simulatedPlayer.q() < -0.15) {
                        this.simulatedPlayer.k(-0.15);
                    }
                    if (this.simulatedPlayer.P() && this.simulatedPlayer.q() < 0.0) {
                        this.simulatedPlayer.k(0.0);
                    }
                }
                this.simulatedPlayer.r(moverType, this.simulatedPlayer.t(), this.simulatedPlayer.q(), this.simulatedPlayer.T());
                if (this.simulatedPlayer.r() && this.simulatedPlayer.S$src$Z$151gttj()) {
                    this.simulatedPlayer.k(0.2);
                }
                BlockPos columnPos = BlockPos.create((int)this.simulatedPlayer.z(), 0,
                        (int)this.simulatedPlayer.h());
                boolean isLoadedColumn = this.world.j$src$Z$11aji0a(columnPos)
                        && this.world.j(columnPos).F();
                if (!this.world.I() || isLoadedColumn) {
                    if (!this.simulatedPlayer.v$src$Z$g1lt9c()) {
                        this.simulatedPlayer.k(this.simulatedPlayer.q() - 0.08);
                    }
                } else if (this.simulatedPlayer.N() > 0.0) {
                    this.simulatedPlayer.k(-0.1);
                } else {
                    this.simulatedPlayer.k(0.0);
                }
                this.simulatedPlayer.k(this.simulatedPlayer.q() * (double)0.98f);
                this.simulatedPlayer.r(this.simulatedPlayer.t() * groundFriction);
                this.simulatedPlayer.i(this.simulatedPlayer.T() * groundFriction);
            } else {
                double previousY = this.simulatedPlayer.N();
                this.simulatedPlayer.x(strafe, vertical, forward, 0.02f);
                this.simulatedPlayer.b(this.simulatedPlayer.t(), this.simulatedPlayer.q(), this.simulatedPlayer.T());
                this.simulatedPlayer.r(this.simulatedPlayer.t() * 0.5);
                this.simulatedPlayer.k(this.simulatedPlayer.q() * 0.5);
                this.simulatedPlayer.i(this.simulatedPlayer.T() * 0.5);
                if (!this.simulatedPlayer.v$src$Z$g1lt9c()) {
                    this.simulatedPlayer.k(this.simulatedPlayer.q() - 0.02);
                }
                if (this.simulatedPlayer.r() && this.simulatedPlayer.i$src$Z$avhpwd(
                        this.simulatedPlayer.t(),
                        this.simulatedPlayer.q() + 0.6f - this.simulatedPlayer.N() + previousY,
                        this.simulatedPlayer.T())) {
                    this.simulatedPlayer.k((double)0.3f);
                }
            }
        } else {
            double previousY = this.simulatedPlayer.N();
            float waterDrag = 0.8f;
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
            this.simulatedPlayer.x(strafe, vertical, forward, waterAcceleration);
            this.simulatedPlayer.r(moverType, this.simulatedPlayer.t(), this.simulatedPlayer.q(), this.simulatedPlayer.T());
            this.simulatedPlayer.r(this.simulatedPlayer.t() * waterDrag);
            this.simulatedPlayer.k(this.simulatedPlayer.q() * (double)0.8f);
            this.simulatedPlayer.i(this.simulatedPlayer.T() * waterDrag);
            if (!this.simulatedPlayer.v$src$Z$g1lt9c()) {
                this.simulatedPlayer.k(this.simulatedPlayer.q() - 0.02);
            }
            if (this.simulatedPlayer.r() && this.simulatedPlayer.i$src$Z$avhpwd(
                    this.simulatedPlayer.t(),
                    this.simulatedPlayer.q() + 0.6f - this.simulatedPlayer.N() + previousY,
                    this.simulatedPlayer.T())) {
                this.simulatedPlayer.k((double)0.3f);
            }
        }
    }

    @Override
    public void tick() {
        this.simulatedPlayer.l$src$V$fw3v8a();
        ++this.sprintingTicksLeft;
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }

        boolean wasSneaking = this.sneakInput;
        float sprintThreshold = 0.8f;
        boolean wasMovingForward = this.moveForward >= sprintThreshold;
        this.updateMovementInput();

        float playerWidth = this.simulatedPlayer.f$src$F$fst3ac();
        double collisionOffset = playerWidth * 0.35;
        double positionX = this.simulatedPlayer.z();
        double positionZ = this.simulatedPlayer.h();
        double collisionY = this.simulatedPlayer
                .u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY() + 0.5;
        PlayerSimulationUtil.p(this.simulatedPlayer, positionX - collisionOffset, collisionY,
                positionZ + collisionOffset);
        PlayerSimulationUtil.p(this.simulatedPlayer, positionX - collisionOffset, collisionY,
                positionZ - collisionOffset);
        PlayerSimulationUtil.p(this.simulatedPlayer, positionX + collisionOffset, collisionY,
                positionZ - collisionOffset);
        PlayerSimulationUtil.p(this.simulatedPlayer, positionX + collisionOffset, collisionY,
                positionZ + collisionOffset);

        boolean canSprint = this.simulatedPlayer
                .Y$src$Lgg_vape_wrapper_impl_FoodStats_$fakh1z().getFoodLevel() > 6
                || this.simulatedPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().H();
        if (canSprint) {
            if (this.simulatedPlayer.b$src$Z$fqlxe4() && !wasSneaking && !wasMovingForward
                    && this.moveForward >= sprintThreshold
                    && !this.simulatedPlayer.B$src$Z$f90iek()
                    && !this.simulatedPlayer.i(PotionRegistry.K)) {
                if (this.sprintToggleTimer <= 0 && !this.sprintKeyDown) {
                    this.sprintToggleTimer = 7;
                } else {
                    this.simulatedPlayer.R(true);
                    this.sprintingTicksLeft = 0;
                }
            }
            if (!this.simulatedPlayer.B$src$Z$f90iek()
                    && this.moveForward >= sprintThreshold
                    && !this.simulatedPlayer.i(PotionRegistry.K)
                    && this.sprintKeyDown) {
                this.simulatedPlayer.R(true);
                this.sprintingTicksLeft = 0;
            }
            if (this.simulatedPlayer.B$src$Z$f90iek()
                    && (this.moveForward < sprintThreshold || this.simulatedPlayer.r())) {
                this.simulatedPlayer.R(false);
                this.sprintingTicksLeft = 0;
            }
        } else if (this.simulatedPlayer.B$src$Z$f90iek()) {
            this.simulatedPlayer.R(false);
            this.sprintingTicksLeft = 0;
        }

        if (this.simulatedPlayer.B$src$I$14s4bbr() > 0) {
            this.simulatedPlayer.L(this.simulatedPlayer.B$src$I$14s4bbr() - 1);
        }
        if (Math.abs(this.simulatedPlayer.t()) < 0.003) {
            this.simulatedPlayer.r(0.0);
        }
        if (Math.abs(this.simulatedPlayer.q()) < 0.003) {
            this.simulatedPlayer.k(0.0);
        }
        if (Math.abs(this.simulatedPlayer.T()) < 0.003) {
            this.simulatedPlayer.i(0.0);
        }

        this.applyMovementInputToPlayer();
        if (this.simulatedPlayer.e$src$Z$15bd4i1()) {
            if (this.simulatedPlayer.h$src$Z$ftwoya()
                    || this.simulatedPlayer.Q$src$Z$fh9faz()) {
                this.simulatedPlayer.k(this.simulatedPlayer.q() + 0.04f);
            } else if (this.simulatedPlayer.b$src$Z$fqlxe4()
                    && this.simulatedPlayer.B$src$I$14s4bbr() == 0) {
                this.simulatedPlayer.t$src$V$15jm1b0();
                if (this.localPlayer.i(PotionRegistry.Z)) {
                    double jumpBoost = (this.localPlayer.b(PotionRegistry.Z).L() + 1) * 0.1f;
                    this.simulatedPlayer.k(this.simulatedPlayer.q() + jumpBoost);
                }
                this.simulatedPlayer.L(10);
            }
        } else {
            this.simulatedPlayer.L(0);
        }

        this.simulatedPlayer.k$src$V$5315b7(
                this.simulatedPlayer.N$src$F$14ypudi() * 0.98f);
        this.simulatedPlayer.M(this.simulatedPlayer.F() * 0.98f);
        this.travel(this.simulatedPlayer.N$src$F$14ypudi(), 0.0f, this.simulatedPlayer.F());
        this.pushNearbyEntities();

        AttributeInstance movementSpeed = this.simulatedPlayer.h(MonsterAttributesBridge.B());
        if (!this.world.I()) {
            movementSpeed.I(this.simulatedPlayer
                    .C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().l());
        }
        this.simulatedPlayer.t(0.02f);
        if (this.simulatedPlayer.B$src$Z$f90iek()) {
            this.simulatedPlayer.t(
                    this.simulatedPlayer.y$src$F$15mczw1() + 0.005999999865889549f);
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

    public BlockInLegacyMovementController1122(EntityPlayer simulatedPlayer, EntityPlayerSP localPlayer,
                                               EntityPlayer sourcePlayer, World world) {
        super(simulatedPlayer, localPlayer, sourcePlayer, world);
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
