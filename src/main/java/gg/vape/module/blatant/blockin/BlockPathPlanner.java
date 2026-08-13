package gg.vape.module.blatant.blockin;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.blatant.blockin.AbstractBlockInMovementController;
import gg.vape.module.blatant.blockin.BlockInCollisionMovementController;
import gg.vape.module.blatant.blockin.BlockInEntityMovementController;
import gg.vape.module.blatant.blockin.BlockInLegacyMovementController1122;
import gg.vape.module.blatant.blockin.BlockInLegacyMovementController189;
import gg.vape.module.blatant.blockin.BlockInTargetRotationState;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.module.render.hud.FreeLookHudModule;
import gg.vape.movement.PlayerMovementTaskManager;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.MouseRotationController;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.MathUtil;
import gg.vape.utils.PlayerSimulationUtil;
import gg.vape.utils.RayTraceUtil;
import gg.vape.utils.Vec3d;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.World;

public class BlockPathPlanner {
    private final BlockPlacementGraph initialSnapshot;
    private boolean movementKeysAdjusted;
    private final GuiScreen currentScreen;
    private MouseRotationController rotationController;
    private boolean restoreYawPending;
    private float simulatedYaw;
    private final RotationManager rotationManager = RotationManager.INSTANCE;
    private double rotationUpdateAccumulator;
    private final EntityPlayer sourcePlayer;
    private float savedYaw;
    private final AbstractBlockInMovementController movementController;
    private float simulatedPitch;
    private final EntityPlayer simulatedPlayer;
    private final EntityPlayerSP localPlayer;

    public int getSprintToggleTimer() {
        return this.movementController.getSprintToggleTimer();
    }

    public boolean isJumpInput() {
        return this.movementController.isJumpInput();
    }

    public void setRotationController(MouseRotationController mouseRotationController) {
        AdaptiveRotationController adaptiveRotationController;
        if (this.rotationController == mouseRotationController) {
            return;
        }
        if (this.rotationController instanceof AdaptiveRotationController && mouseRotationController instanceof AdaptiveRotationController) {
            adaptiveRotationController = (AdaptiveRotationController)this.rotationController;
            adaptiveRotationController.setRelativeMode(true);
            adaptiveRotationController.setComplete(true);
            AdaptiveRotationController adaptiveRotationController2 = (AdaptiveRotationController)mouseRotationController;
            adaptiveRotationController2.setRelativeMode(false);
            adaptiveRotationController2.setCurrentYaw(adaptiveRotationController.getRenderedYaw());
            adaptiveRotationController2.setCurrentPitch(adaptiveRotationController.getRenderedPitch());
        }
        if (this.rotationController == null && mouseRotationController instanceof AdaptiveRotationController) {
            adaptiveRotationController = (AdaptiveRotationController)mouseRotationController;
            this.simulatedYaw = adaptiveRotationController.getRenderedYaw();
            this.simulatedPitch = adaptiveRotationController.getRenderedPitch();
        }
        this.rotationController = mouseRotationController;
    }

    public boolean hasAdaptiveRotationController() {
        return this.rotationController instanceof AdaptiveRotationController;
    }

    public void setForwardOnly() {
        if (this.initialSnapshot != null) {
            this.initialSnapshot.forwardKeyDown = true;
            this.initialSnapshot.backwardKeyDown = false;
            this.initialSnapshot.leftKeyDown = false;
            this.initialSnapshot.rightKeyDown = false;
        }
        this.setDirectionalKeys(true, false, false, false);
    }

    private static AbstractBlockInMovementController createMovementController(EntityPlayer simulatedPlayer,
                                                                              EntityPlayerSP localPlayer,
                                                                              EntityPlayer sourcePlayer,
                                                                              World world) {
        if (ForgeVersion.MC_1_21_4.d()) {
            return new BlockInEntityMovementController(simulatedPlayer, localPlayer, sourcePlayer, world);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return new BlockInCollisionMovementController(simulatedPlayer, localPlayer, sourcePlayer, world);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return new BlockInLegacyMovementController1122(simulatedPlayer, localPlayer, sourcePlayer, world);
        }
        return new BlockInLegacyMovementController189(simulatedPlayer, localPlayer, sourcePlayer, world);
    }

    public void applySnapshot(BlockPlacementGraph snapshot) {
        this.movementController.applySnapshot(snapshot);
    }

    public void setSprintKeyDown(boolean sprintKeyDown) {
        this.movementController.setSprintKeyDown(sprintKeyDown);
    }

    private boolean canRayTraceEntity(Entity entity) {
        return !entity.O$src$Z$fg5u49() && entity.n$src$Z$fx7gig()
                && !entity.equals(this.sourcePlayer) && !entity.equals(this.localPlayer);
    }

    public int getSprintingTicksLeft() {
        return this.movementController.getSprintingTicksLeft();
    }

    public float getMoveStrafe() {
        return this.movementController.getMoveStrafe();
    }

    public void restoreSnapshotInput() {
        this.movementController.setInput(this.initialSnapshot.forwardKeyDown, this.initialSnapshot.backwardKeyDown,
                this.initialSnapshot.leftKeyDown, this.initialSnapshot.rightKeyDown,
                this.initialSnapshot.jumpInput, this.initialSnapshot.sneakInput);
    }

    public BlockPathPlanner(EntityPlayer entityPlayer, EntityPlayerSP entityPlayerSP, World world, BlockPlacementGraph blockPlacementGraph) {
        this.sourcePlayer = entityPlayer;
        this.localPlayer = entityPlayerSP;
        this.simulatedPlayer = PlayerSimulationUtil.I(entityPlayer);
        this.initialSnapshot = blockPlacementGraph;
        this.rotationUpdateAccumulator = this.rotationManager.getControllerUpdateAccumulator();
        this.currentScreen = Minecraft.currentScreen();
        this.movementController = BlockPathPlanner.createMovementController(
                this.simulatedPlayer, entityPlayerSP, entityPlayer, world);
        this.initializeMovementController();
        if (entityPlayer.isInstance(MappedClasses.z5) && RotationManager.INSTANCE.hasAdaptiveController()) {
            AdaptiveRotationController adaptiveRotationController = (AdaptiveRotationController)RotationManager.INSTANCE.getActiveController();
            this.simulatedYaw = adaptiveRotationController.getRenderedYaw();
            this.simulatedPitch = adaptiveRotationController.getRenderedPitch();
        }
    }

    public BlockInTargetRotationState createRotationState() {
        BlockInTargetRotationState blockInTargetRotationState = new BlockInTargetRotationState(this.simulatedPlayer);
        blockInTargetRotationState.setPlacementGraph(new BlockPlacementGraph(this));
        return blockInTargetRotationState;
    }

    public void finishSimulation() {
        this.movementController.setJumpKeyDown(false);
    }

    public void initializeMovementController() {
        this.movementController.initialize();
    }

    public void clearInput() {
        this.movementController.setInput(false, false, false, false, false, false);
    }

    public float getMoveForward() {
        return this.movementController.getMoveForward();
    }

    public BlockPathPlanner(EntityPlayer entityPlayer, EntityPlayerSP entityPlayerSP, World world, BlockPlacementGraph blockPlacementGraph, BlockPathPlanner blockPathPlanner) {
        this.sourcePlayer = entityPlayer;
        this.localPlayer = entityPlayerSP;
        this.simulatedPlayer = blockPathPlanner.getSimulatedPlayer();
        PlayerSimulationUtil.s(this.simulatedPlayer, entityPlayer);
        this.initialSnapshot = blockPlacementGraph;
        this.rotationUpdateAccumulator = this.rotationManager.getControllerUpdateAccumulator();
        this.currentScreen = Minecraft.currentScreen();
        this.movementController = BlockPathPlanner.createMovementController(
                this.simulatedPlayer, entityPlayerSP, entityPlayer, world);
        this.initializeMovementController();
        if (entityPlayer.isInstance(MappedClasses.z5) && RotationManager.INSTANCE.hasAdaptiveController()) {
            AdaptiveRotationController adaptiveRotationController = (AdaptiveRotationController)RotationManager.INSTANCE.getActiveController();
            this.simulatedYaw = adaptiveRotationController.getRenderedYaw();
            this.simulatedPitch = adaptiveRotationController.getRenderedPitch();
        }
    }

    public Vec3d getSimulatedPosition() {
        return new Vec3d(this.simulatedPlayer.z(), this.simulatedPlayer.N(), this.simulatedPlayer.h());
    }

    public boolean isSprintKeyDown() {
        return this.movementController.isSprintKeyDown();
    }

    public boolean isSneaking() {
        return this.movementController.isSneaking();
    }

    public void adjustMovementForRotation() {
        ModeSelection modeSelection = (ModeSelection)Vape.INSTANCE.getClientSettings().movementCorrection.getValue();
        if (modeSelection.equals(ClientSettings.NO_MOVEMENT_CORRECTION)) {
            return;
        }
        boolean movementCorrectionEnabled = modeSelection.equals(ClientSettings.PROPER_MOVEMENT_CORRECTION)
                || modeSelection.equals(ClientSettings.SLOW_MOVEMENT_CORRECTION);
        if (!movementCorrectionEnabled) {
            return;
        }
        if (!this.hasAdaptiveRotationController()) {
            if (this.movementKeysAdjusted) {
                this.restoreSnapshotInput();
                this.movementKeysAdjusted = false;
            }
            return;
        }

        boolean hasDirectionalInput = this.hasDirectionalInput();
        AdaptiveRotationController adaptiveController = (AdaptiveRotationController)this.rotationController;
        this.savedYaw = FreeLookHudModule.isActive()
                ? FreeLookHudModule.getSavedPitch() : this.simulatedPlayer.J();
        float referenceYaw = adaptiveController.getReferenceYaw();
        float movementYaw = this.rotationManager.adjustMovementYaw(referenceYaw,
                this.movementController.isForwardKeyDown(), this.movementController.isRightKeyDown(),
                this.movementController.isLeftKeyDown(), this.movementController.isBackwardKeyDown());
        float correctedYaw = modeSelection.equals(ClientSettings.SLOW_MOVEMENT_CORRECTION) ? movementYaw + 180.0f : this.simulatedYaw;
        this.simulatedPlayer.H(correctedYaw);
        this.simulatedPlayer.z(correctedYaw);
        this.restoreYawPending = true;
        if (hasDirectionalInput) {
            float yawDifference = MathUtil.wrapAngleTo180(MathUtil.wrapAngleTo180(correctedYaw) - movementYaw);
            float yawRadians = yawDifference * ((float)Math.PI / 180);
            float forwardProjection = (float)Math.cos(yawRadians);
            float rightProjection = (float)(-Math.sin(yawRadians));
            double activationThreshold = PlayerMovementTaskManager.INSTANCE.getActiveTask() != null ? 0.075 : 0.4f;
            this.setDirectionalKeys(forwardProjection >= activationThreshold,
                    forwardProjection <= -activationThreshold,
                    rightProjection <= -activationThreshold,
                    rightProjection >= activationThreshold);
            this.movementKeysAdjusted = true;
        } else if (this.movementKeysAdjusted) {
            this.restoreSnapshotInput();
            this.movementKeysAdjusted = false;
        }
    }

    public void setDirectionalKeys(boolean forward, boolean backward, boolean left, boolean right) {
        this.movementController.setForwardKeyDown(forward);
        this.movementController.setBackwardKeyDown(backward);
        this.movementController.setLeftKeyDown(left);
        this.movementController.setRightKeyDown(right);
    }

    public void setInput(boolean forward, boolean backward, boolean left, boolean right,
                         boolean jump, boolean sneak) {
        this.movementController.setInput(forward, backward, left, right, jump, sneak);
    }

    public boolean isRightKeyDown() {
        return this.movementController.isRightKeyDown();
    }

    public MouseRotationController getRotationController() {
        return this.rotationController;
    }

    public boolean isJumpKeyDown() {
        return this.movementController.isJumpKeyDown();
    }

    public EntityPlayer getSimulatedPlayer() {
        return this.simulatedPlayer;
    }

    public boolean isLeftKeyDown() {
        return this.movementController.isLeftKeyDown();
    }

    public void updateRotation() {
        if (this.rotationController != null) {
            double updatesPerTick = Math.round(50.0f * this.rotationManager.getSensitivityTimeScale());
            this.rotationUpdateAccumulator += updatesPerTick;
            int updateCount = (int)Math.round(this.rotationUpdateAccumulator);
            for (int update = 0; update < updateCount; ++update) {
                try {
                    this.rotationController.update(this.localPlayer, this.currentScreen);
                    this.rotationController.applyPendingMovement(this.currentScreen);
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.rotationUpdateAccumulator -= updateCount;
            if (this.hasAdaptiveRotationController()) {
                AdaptiveRotationController adaptiveRotationController =
                        (AdaptiveRotationController)this.rotationController;
                this.simulatedYaw = adaptiveRotationController.getRenderedYaw();
                this.simulatedPitch = adaptiveRotationController.getRenderedPitch();
            }
        }
    }

    public boolean isForwardKeyDown() {
        return this.movementController.isForwardKeyDown();
    }


    public void simulateTick() {
        this.simulateTick(true);
    }

    public RayTraceResult rayTrace(double reach, float partialTicks, boolean includeFluids) {
        RayTraceResult result;
        if (this.hasAdaptiveRotationController()) {
            float savedRenderYaw = this.simulatedPlayer.J();
            float savedYaw = this.simulatedPlayer.s();
            float savedPitch = this.simulatedPlayer.V();
            this.simulatedPlayer.H(this.simulatedYaw);
            this.simulatedPlayer.z(this.simulatedYaw);
            this.simulatedPlayer.C(this.simulatedPitch);
            result = RayTraceUtil.U(this.simulatedPlayer, reach, partialTicks, includeFluids,
                    ForgeVersion.MC_1_16_5.v() ? null : this::canRayTraceEntity);
            this.simulatedPlayer.H(savedRenderYaw);
            this.simulatedPlayer.z(savedYaw);
            this.simulatedPlayer.C(savedPitch);
        } else {
            this.simulatedPlayer.z(this.simulatedPlayer.J());
            result = RayTraceUtil.U(this.simulatedPlayer, reach, partialTicks, includeFluids,
                    ForgeVersion.MC_1_16_5.v() ? null : this::canRayTraceEntity);
        }
        return result;
    }

    public void simulateTick(boolean updateRotation) {
        if (updateRotation) {
            this.updateRotation();
        }
        this.simulatedPlayer.D(this.simulatedPlayer.J());
        this.simulatedPlayer.l(this.simulatedPlayer.V());
        this.simulatedPlayer.n(this.simulatedPlayer.z());
        this.simulatedPlayer.w(this.simulatedPlayer.N());
        this.simulatedPlayer.A(this.simulatedPlayer.h());
        if (this.hasAdaptiveRotationController()) {
            AdaptiveRotationController adaptiveRotationController =
                    (AdaptiveRotationController)this.rotationController;
            this.simulatedYaw = adaptiveRotationController.getRenderedYaw();
            this.simulatedPitch = MathUtil.clamp(adaptiveRotationController.getRenderedPitch(), -90.0f, 90.0f);
            this.adjustMovementForRotation();
        }
        this.movementController.tick();
        if (this.restoreYawPending) {
            this.simulatedPlayer.H(this.savedYaw);
            this.simulatedPlayer.z(this.savedYaw);
            this.restoreYawPending = false;
        }
        if (this.movementKeysAdjusted) {
            this.restoreSnapshotInput();
            this.movementKeysAdjusted = false;
        }
    }

    public boolean hasDirectionalInput() {
        return this.movementController.isForwardKeyDown() || this.movementController.isBackwardKeyDown()
                || this.movementController.isLeftKeyDown() || this.movementController.isRightKeyDown();
    }

    public boolean isBackwardKeyDown() {
        return this.movementController.isBackwardKeyDown();
    }

    public boolean isSneakKeyDown() {
        return this.movementController.isSneakKeyDown();
    }
}
