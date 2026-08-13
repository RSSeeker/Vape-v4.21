package gg.vape.module.blatant.scaffold;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreEntityUpdate;
import gg.vape.event.impl.EventPreTick;
import gg.vape.input.KeyboardCodeUtil;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.blatant.Scaffold;
import gg.vape.module.blatant.scaffold.ScaffoldEdgeSneakHelper;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.module.render.hud.FreeLookHudModule;
import gg.vape.movement.MovementInputHelper;
import gg.vape.movement.PlayerMovementTaskManager;
import gg.vape.movement.TargetPositionMovementTask;
import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class BlatantScaffoldMode
extends SubModule<Scaffold> {
    private final KeyBinding leftKey;
    private TimerUtil moveTimer;
    private double[] targetPos;
    private ArrayList<Integer> slotHistory;
    private boolean rotationPending = false;
    private boolean atEdge = false;
    private boolean taskBlocksUpdate = false;
    private int taskTickLimit;
    private final KeyBinding rightKey;
    private boolean prevLeftDown = false;
    private final KeyBinding backwardKey;
    private int blocksPlaced;
    private int pendingDirection;
    private int direction = 0;
    private final NumberValue activationBlocks = NumberValue.createWithDescription(this, "Activation blocks", "#", "", 1.0, 2.0, 4.0, "Manual blocks placed before bridging");
    private boolean activationPending;
    private int taskTicks;
    private boolean reversed;
    private TargetPositionMovementTask movementTask = null;
    private boolean keyIdle = true;
    private ArrayList<Integer> directionCycle;
    private ItemStack heldBlock;
    private TimerUtil switchTimer;
    private boolean pendingReversed;
    private double[] pendingPos;
    private boolean switching = false;
    private float[] targetRotation;
    private boolean prevRightDown = false;
    private ScaffoldEdgeSneakHelper edgeSneakHelper;
    private final Scaffold scaffold = (Scaffold)this.getParent();
    private double[] placePos;

    private boolean isNextBlockEmpty(EntityPlayerSP player) {
        double sampleX = player.z();
        double sampleZ = player.h();
        if (this.direction == 1) {
            sampleX += 0.2;
            sampleZ += 0.2;
        } else if (this.direction == 2) {
            sampleX -= 0.2;
            sampleZ += 0.2;
        } else if (this.direction == 3) {
            sampleX -= 0.2;
            sampleZ -= 0.2;
        } else if (this.direction == 4) {
            sampleX += 0.2;
            sampleZ -= 0.2;
        } else if (this.direction == 6) {
            sampleX += 0.25;
            sampleZ = this.targetPos[1];
        } else if (this.direction == 8) {
            sampleX -= 0.25;
            sampleZ = this.targetPos[1];
        } else if (this.direction == 7) {
            sampleX = this.targetPos[0];
            sampleZ += 0.25;
        } else if (this.direction == 5) {
            sampleX = this.targetPos[0];
            sampleZ -= 0.25;
        } else {
            return true;
        }
        sampleX = MathUtil.floor(sampleX);
        double placementY = this.scaffold.getPlacementY(player);
        sampleZ = MathUtil.floor(sampleZ);
        return !this.scaffold.isAirBlockAt(sampleX, placementY, sampleZ);
    }

    @EventHandler
    public void onPostTick(EventPostTick eventPostTick) {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        if (this.activationPending) {
            this.edgeSneakHelper.onPostTick(eventPostTick);
        }
    }

    private boolean advanceTask() {
        if (this.movementTask != null) {
            if (!this.movementTask.isCompleted() && this.taskTicks < this.taskTickLimit) {
                ++this.taskTicks;
                if (this.taskBlocksUpdate) {
                    return true;
                }
            } else if (this.movementTask.isCompleted()) {
                this.taskTicks = 0;
                this.taskBlocksUpdate = false;
            } else {
                this.movementTask.setCompleted(true);
                this.scaffold.cancelMovementTask();
                this.movementTask = null;
                this.taskTicks = 0;
                this.taskBlocksUpdate = false;
                return true;
            }
        }
        return false;
    }

    private boolean shouldReverse(int direction) {
        if (direction > 4) {
            double[] yawBounds = this.getDirectionYawBounds(direction);
            double currentYaw = RotationUtil.c();
            return this.scaffold.angularDistance(currentYaw, yawBounds[0]) <= this.scaffold.angularDistance(currentYaw, yawBounds[1]);
        }
        return !this.reversed;
    }

    @Override
    public void onEnable() {
        this.activationPending = true;
    }

    private void applyRotation(float[] rotation, float speed) {
        if (this.scaffold.rotationController == null) {
            this.scaffold.rotationController = new FixedRotationController(rotation[0], rotation[1]);
            this.scaffold.rotationController.setSpeed(Math.min(Math.max(2.0f, speed), 12.0f));
            this.scaffold.rotationController.setClampStepToRemaining(true);
            this.scaffold.rotationController.setTolerance(0.0f);
            this.scaffold.rotationController.setLinearAcceleration(true);
            this.scaffold.rotationController.setScaleAxesProportionally(true);
            this.scaffold.rotationController.setRetainAfterCompletion(true);
            RotationManager.INSTANCE.setController(this.scaffold.rotationController);
        } else {
            this.scaffold.rotationController.setSpeed(Math.min(Math.max(2.0f, speed), 12.0f));
            this.scaffold.rotationController.setTargetRotation(rotation[0], rotation[1]);
        }
    }

    private boolean isPastCorner(EntityPlayerSP player, int direction) {
        double playerX = player.z();
        double playerZ = player.h();
        double blockX = MathUtil.floor(playerX);
        double blockZ = MathUtil.floor(playerZ);
        if (direction == 1) {
            return playerX - blockX + (playerZ - blockZ) > 1.0;
        }
        if (direction == 2) {
            return blockX - playerX + (playerZ - blockZ) > 1.0;
        }
        if (direction == 3) {
            return blockX - playerX + (blockZ - playerZ) > 1.0;
        }
        if (direction == 4) {
            return playerX - blockX + (blockZ - playerZ) > 1.0;
        }
        if (direction == 6) {
            return playerX - blockX > 0.5;
        }
        if (direction == 8) {
            return blockX - playerX > 0.5;
        }
        if (direction == 7) {
            return playerZ - blockZ > 0.5;
        }
        if (direction == 5) {
            return blockZ - playerZ > 0.5;
        }
        return false;
    }

    private boolean ensureBlockSelected(EntityPlayerSP player) {
        int blockSlot = this.scaffold.findBlockHotbarSlot();
        if (blockSlot == -1) {
            return false;
        }
        if (this.heldBlock != null && !player.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt().equals(this.heldBlock)) {
            int heldBlockSlot = this.scaffold.findMatchingHotbarSlot(player, this.heldBlock);
            if (heldBlockSlot != -1) {
                this.scaffold.selectHotbarSlot(heldBlockSlot);
            } else if (player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v() != blockSlot) {
                this.scaffold.selectHotbarSlot(blockSlot);
            }
        }
        return true;
    }

    private boolean isFrontClear(EntityPlayerSP player) {
        AxisAlignedBB boundingBox;
        double shrinkAmount = -0.2;
        if (ForgeVersion.MC_1_8_9.d()) {
            boundingBox = player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
        } else {
            AxisAlignedBB currentBox = player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
            boundingBox = currentBox.copy();
        }
        double motionX = player.t();
        double verticalOffset = ForgeVersion.MC_1_20_6.d() ? 1.0 : -1.0;
        double motionZ = player.T();
        AxisAlignedBB checkBox = boundingBox.expand(shrinkAmount, 0.0, shrinkAmount).k(motionX, verticalOffset, motionZ);
        int collisionCount = Minecraft.theWorld().i(player, checkBox).size();
        return collisionCount == 0;
    }

    private void updateBridgeInput(EntityPlayerSP player) {
        if (Minecraft.currentScreen().getObject() == null) {
            KeyboardCodeUtil.disableLegacyRepeatEvents();
        }
        boolean atEdge = this.isAtEdge(player, this.direction);
        boolean diagonalDirection = this.direction < 5;
        if (!atEdge) {
            this.moveTimer.reset();
        }
        this.atEdge = atEdge;
        if (atEdge) {
            MovementInputHelper.setKeyPressed(this.backwardKey, false);
            MovementInputHelper.setKeyPressed(this.leftKey, false);
            MovementInputHelper.setKeyPressed(this.rightKey, false);
            if (!diagonalDirection) {
                if (this.reversed) {
                    MovementInputHelper.setKeyPressed(this.rightKey, false);
                } else {
                    MovementInputHelper.setKeyPressed(this.leftKey, false);
                }
            }
        } else {
            MovementInputHelper.setKeyPressed(this.backwardKey, true);
            if (!diagonalDirection) {
                if (this.reversed) {
                    MovementInputHelper.setKeyPressed(this.rightKey, true);
                } else {
                    MovementInputHelper.setKeyPressed(this.leftKey, true);
                }
            } else {
                MovementInputHelper.setKeyPressed(this.leftKey, false);
                MovementInputHelper.setKeyPressed(this.rightKey, false);
            }
        }
    }

    private double[] getDirectionYawBounds(int direction) {
        if (direction == 6) {
            return new double[]{135.0, 45.0};
        }
        if (direction == 8) {
            return new double[]{315.0, 225.0};
        }
        if (direction == 7) {
            return new double[]{225.0, 135.0};
        }
        if (direction == 5) {
            return new double[]{45.0, 315.0};
        }
        return new double[0];
    }

    private double[] computeAnchorTarget(EntityPlayerSP player, int direction) {
        double placementY = player.q() > 0.0 ? this.scaffold.getPlacementY(player) + 1.0 : this.scaffold.getPlacementY(player);
        double[] anchorTarget = new double[]{MathUtil.floor(player.z()), placementY, MathUtil.floor(player.h())};
        if (this.scaffold.isAirBlockAt(anchorTarget)
                && this.scaffold.isAirBlockAt(anchorTarget = this.scaffold.offsetPosition(anchorTarget, -1, direction))
                && this.scaffold.isAirBlockAt(anchorTarget = this.scaffold.offsetPosition(anchorTarget, 1, this.rotateDirection(direction, 2)))) {
            anchorTarget = this.scaffold.offsetPosition(anchorTarget, -2, this.rotateDirection(direction, 2));
        }
        return anchorTarget;
    }

    private boolean shouldReleaseControl(EntityPlayerSP player) {
        if (!SharedModuleControlClaims.rotation.isOwnedBy(this.scaffold)) {
            this.activationPending = true;
            MovementInputHelper.restorePhysicalInput();
            return true;
        }
        if (!this.ensureBlockSelected(player)) {
            this.activationPending = true;
            MovementInputHelper.releaseMovementKeys();
            return true;
        }
        if (!ClientSettings.isPhysicalKeyDown(this.backwardKey)) {
            if (!player.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying() && !this.isNextBlockEmpty(player)) {
                return false;
            }
            MovementInputHelper.releaseMovementKeys();
            this.activationPending = true;
            return true;
        }
        if (!FreeLookHudModule.isActive()) {
            this.slotHistory = this.scaffold.updatePlacementHistory(this.slotHistory);
            if (this.scaffold.sumPlacementHistory(this.slotHistory) >= 10) {
                MovementInputHelper.releaseMovementKeys();
                this.activationPending = true;
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onPreEntityUpdate(EventPreEntityUpdate eventPreEntityUpdate) {
        if (this.activationPending) {
            this.edgeSneakHelper.onPreEntityUpdate(eventPreEntityUpdate);
        }
    }

    private boolean isRotationOffTarget() {
        if (this.scaffold.rotationController != null) {
            if (this.scaffold.angularDistance(RotationUtil.c(), this.scaffold.rotationController.getTargetYaw()) > 4.0) {
                if (this.rotationPending) {
                    return true;
                }
            } else if (this.rotationPending) {
                this.scaffold.releaseRotation();
                this.rotationPending = false;
            }
        }
        return false;
    }

    private boolean handleRotationAndTask() {
        boolean rotationOffTarget = this.isRotationOffTarget();
        boolean taskBlocking = this.advanceTask();
        return rotationOffTarget || taskBlocking;
    }

    private boolean isAtEdge(EntityPlayerSP player, int direction) {
        AxisAlignedBB boundingBox;
        if (direction > 4) {
            double sampleX = player.z();
            double sampleZ = player.h();
            if (direction == 6) {
                sampleX += -0.15;
                sampleZ = this.targetPos[1];
            } else if (direction == 8) {
                sampleX -= -0.15;
                sampleZ = this.targetPos[1];
            } else if (direction == 7) {
                sampleX = this.targetPos[0];
                sampleZ += -0.15;
            } else if (direction == 5) {
                sampleX = this.targetPos[0];
                sampleZ -= -0.15;
            }
            sampleX = MathUtil.floor(sampleX);
            double placementY = this.scaffold.getPlacementY(player);
            sampleZ = MathUtil.floor(sampleZ);
            return this.scaffold.isAirBlockAt(sampleX, placementY, sampleZ);
        }
        double shrinkAmount = -0.16;
        if (ForgeVersion.MC_1_8_9.d()) {
            boundingBox = player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
        } else {
            AxisAlignedBB currentBox = player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
            boundingBox = currentBox.copy();
        }
        double motionX = player.t();
        double verticalOffset = ForgeVersion.MC_1_20_6.d() ? 1.0 : -1.0;
        double motionZ = player.T();
        AxisAlignedBB checkBox = boundingBox.expand(shrinkAmount, 0.0, shrinkAmount).k(motionX, verticalOffset, motionZ);
        int collisionCount = Minecraft.theWorld().i(player, checkBox).size();
        return collisionCount == 0;
    }

    private float computeRotationSpeed(float[] rotation, int divisor) {
        return (float)Math.min(2.0 + this.scaffold.angularDistance(RotationUtil.c(), rotation[0]) / (double)divisor, 12.0);
    }


    private boolean updatePlacement(EntityPlayerSP player) {
        double blockX = MathUtil.floor(player.z());
        double blockZ = MathUtil.floor(player.h());
        double placementY = this.scaffold.getPlacementY(player);
        if (!this.scaffold.isValidBlockStack(player.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt())) {
            this.placePos = null;
            this.blocksPlaced = 0;
            return true;
        }
        int currentDirection = this.scaffold.getCardinalDirection();
        if (this.direction != 0 && currentDirection != this.direction) {
            this.placePos = null;
            this.blocksPlaced = 0;
        }
        this.direction = currentDirection;
        double[] playerBlock = new double[]{blockX, placementY, blockZ};
        double[] oneBlockAhead = this.scaffold.offsetPosition(playerBlock, 1, this.direction);
        double[] twoBlocksAhead = this.scaffold.offsetPosition(playerBlock, 2, this.direction);
        if (this.placePos == null && player.b$src$Z$fqlxe4()) {
            if (this.scaffold.isAirBlockAt(playerBlock)) {
                this.placePos = playerBlock;
            } else if (this.scaffold.isAirBlockAt(oneBlockAhead)) {
                this.placePos = oneBlockAhead;
            } else if (this.scaffold.isAirBlockAt(twoBlocksAhead)) {
                this.placePos = twoBlocksAhead;
            }
        } else if (this.placePos != null) {
            if ((double)this.blocksPlaced >= (Double)this.activationBlocks.getValue()) {
                this.reversed = this.shouldReverse(this.direction);
                this.targetPos = this.computePlacementPoint(new double[]{this.placePos[0], this.placePos[2]}, this.direction, this.reversed);
                this.heldBlock = player.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt();
                if (!ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().O()) && !player.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
                    this.startMoveTaskNoTurn(this.targetPos, true, false, 40);
                    this.targetRotation = this.computeDiagonalRotation(player, this.reversed);
                    this.scaffold.applyFixedRotation(this.targetRotation, this.computeRotationSpeed(this.targetRotation, 15));
                }
                this.blocksPlaced = 0;
                this.placePos = null;
                this.moveTimer.reset();
                return false;
            }
            if (!this.scaffold.isAirBlockAt(this.placePos)) {
                ++this.blocksPlaced;
                double[] nextPlacement = this.scaffold.offsetPosition(this.placePos, 1, this.direction);
                boolean nextPlacementIsEmpty = this.scaffold.isAirBlockAt(nextPlacement);
                if (nextPlacementIsEmpty && (double)this.blocksPlaced < (Double)this.activationBlocks.getValue()) {
                    this.placePos = nextPlacement;
                } else if (!nextPlacementIsEmpty) {
                    this.placePos = null;
                    this.blocksPlaced = 0;
                }
            } else if (this.scaffold.hasPlacementDrifted(this.placePos, playerBlock, this.direction, (Double)this.activationBlocks.getValue(), this.blocksPlaced)) {
                this.placePos = null;
                this.blocksPlaced = 0;
            }
        }
        return true;
    }

    private boolean handleDirectionSwitch(EntityPlayerSP player) {
        if (Minecraft.currentScreen().getObject() == null) {
            KeyboardCodeUtil.disableLegacyRepeatEvents();
        }
        if (this.movementTask != null && this.movementTask.isCompleted() && this.switching) {
            this.atEdge = true;
            this.movementTask = null;
            this.targetPos = this.pendingPos;
            this.direction = this.pendingDirection;
            this.reversed = this.pendingReversed;
            this.switching = false;
            MovementInputHelper.releaseMovementKeys();
            this.moveTimer.reset();
            this.switchTimer.reset();
            if (this.direction < 5) {
                float[] rotation = this.computePlacementRotation(player, this.computePlacementPoint(this.targetPos, this.direction, this.reversed), this.direction);
                this.applyRotation(rotation, this.computeRotationSpeed(rotation, 15));
            } else {
                float[] rotation = this.computeDiagonalRotation(player, this.reversed);
                this.applyRotation(rotation, this.computeRotationSpeed(rotation, 12));
            }
            this.rotationPending = true;
            return true;
        }

        boolean leftPressed = ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg());
        boolean rightPressed = ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3());
        if (!this.switching) {
            boolean leftTransition;
            boolean rightTransition;
            if (this.keyIdle) {
                leftTransition = leftPressed && !this.prevLeftDown;
                rightTransition = rightPressed && !this.prevRightDown;
            } else {
                leftTransition = !leftPressed && this.prevLeftDown;
                rightTransition = !rightPressed && this.prevRightDown;
            }
            this.prevLeftDown = leftPressed;
            this.prevRightDown = rightPressed;

            if (this.keyIdle && this.switchTimer.hasTimeElapsed(0L)) {
                this.keyIdle = !leftTransition && !rightTransition;
                if (leftTransition) {
                    this.pendingDirection = this.rotateDirection(this.direction, 1);
                } else if (rightTransition) {
                    this.pendingDirection = this.rotateDirection(this.direction, -1);
                }
            } else if (!this.keyIdle) {
                this.keyIdle = leftTransition || rightTransition;
                if (leftTransition) {
                    this.pendingDirection = this.rotateDirection(this.direction, -1);
                } else if (rightTransition) {
                    this.pendingDirection = this.rotateDirection(this.direction, 1);
                }
            } else {
                if (leftPressed) {
                    MovementInputHelper.setKeyPressed(this.leftKey, false);
                } else if (rightPressed) {
                    MovementInputHelper.setKeyPressed(this.rightKey, false);
                }
                return false;
            }
            this.switching = leftTransition || rightTransition;
        }

        if (this.switching) {
            this.movementTask = null;
            double[] playerBlock = new double[]{MathUtil.floor(player.z()), this.scaffold.getPlacementY(player), MathUtil.floor(player.h())};
            if (!(this.scaffold.isAirBlockAt(playerBlock) || this.isFrontClear(player) || this.isPastCorner(player, this.direction))) {
                this.pendingReversed = this.shouldReverse(this.pendingDirection);
                this.pendingPos = this.computePlacementPoint(new double[]{playerBlock[0], playerBlock[2]}, this.pendingDirection, this.pendingReversed);
                this.scaffold.releaseControls();
                if (this.direction > 4 && !this.reversed && this.pendingDirection == this.rotateDirection(this.direction, -1) || this.direction > 4 && this.reversed && this.pendingDirection == this.rotateDirection(this.direction, 1)) {
                    this.movementTask = new TargetPositionMovementTask(0.0, 0.0);
                    this.movementTask.setCompleted(true);
                } else if (Math.abs(player.z() - this.pendingPos[0]) > 0.15 || Math.abs(player.h() - this.pendingPos[1]) > 0.15) {
                    this.startMoveTaskNoTurn(this.pendingPos, true, false, 40);
                } else {
                    this.movementTask = new TargetPositionMovementTask(0.0, 0.0);
                    this.movementTask.setCompleted(true);
                }
            } else if (!this.reversed) {
                if (leftPressed && this.isAtEdge(player, this.direction)) {
                    MovementInputHelper.setKeyPressed(this.leftKey, false);
                } else if (rightPressed) {
                    MovementInputHelper.setKeyPressed(this.rightKey, false);
                }
            } else if (rightPressed && this.isAtEdge(player, this.direction)) {
                MovementInputHelper.setKeyPressed(this.rightKey, false);
            } else if (leftPressed) {
                MovementInputHelper.setKeyPressed(this.leftKey, false);
            }
        }
        return false;
    }

    private boolean recoverIfStuck(EntityPlayerSP player) {
        if (this.moveTimer.hasTimeElapsed(800L)) {
            double[] anchorTarget = this.computeAnchorTarget(player, this.direction);
            this.targetPos = this.computePlacementPoint(new double[]{anchorTarget[0], anchorTarget[2]}, this.direction, this.reversed);
            this.targetRotation = this.computeDiagonalRotation(player, this.reversed);
            this.scaffold.applyFixedRotation(this.targetRotation, this.computeRotationSpeed(this.targetRotation, 15));
            this.startMoveTaskNoTurn(this.targetPos, true, false, 40);
            this.atEdge = true;
            this.moveTimer.reset();
            return true;
        }
        return false;
    }

    private float[] computeDiagonalRotation(EntityPlayerSP player, boolean reversed) {
        double yaw = player.J();
        double playerX = player.f();
        double playerZ = player.R();
        double reversedYaw = yaw;
        double forwardYaw = yaw;
        if (this.direction == 6) {
            reversedYaw = 135.0 + 20.0 * (this.targetPos[1] - playerZ);
            forwardYaw = 45.0 + 20.0 * (this.targetPos[1] - playerZ);
        } else if (this.direction == 8) {
            reversedYaw = -45.0 - 20.0 * (this.targetPos[1] - playerZ);
            forwardYaw = -135.0 - 20.0 * (this.targetPos[1] - playerZ);
        } else if (this.direction == 7) {
            reversedYaw = -135.0 - 20.0 * (this.targetPos[0] - playerX);
            forwardYaw = 135.0 + 20.0 * (playerX - this.targetPos[0]);
        } else if (this.direction == 5) {
            reversedYaw = 45.0 + 20.0 * (this.targetPos[0] - playerX);
            forwardYaw = -45.0 - 20.0 * (playerX - this.targetPos[0]);
        }
        yaw = reversed ? reversedYaw : forwardYaw;
        return new float[]{(float)yaw, this.moveTimer.hasTimeElapsed(300L) ? 80 : 78};
    }

    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        if (Minecraft.thePlayer().isNull() || Minecraft.theWorld().isNull()) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (!this.activationPending && this.isLookingAtPlacement(player)) {
            Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362().onTick(1);
        }
        if (!(this.activationPending || this.rotationPending || this.taskBlocksUpdate)) {
            this.updateBridgeInput(player);
        }
        if (this.activationPending) {
            if (this.movementTask != null || this.targetRotation != null) {
                this.scaffold.releaseControls();
                this.edgeSneakHelper.onEnable();
            }
            this.resetState();
            this.activationPending = this.updatePlacement(player);
            SharedModuleControlClaims.movementInput.unlock();
            SharedModuleControlClaims.rightClickUse.clearClaimed();
            return;
        }
        SharedModuleControlClaims.movementInput.lock();
        SharedModuleControlClaims.rightClickUse.blockUse();
        if (this.shouldReleaseControl(player)) {
            this.scaffold.releaseRotation();
            this.edgeSneakHelper.onEnable();
            SharedModuleControlClaims.movementInput.unlock();
            SharedModuleControlClaims.rightClickUse.clearClaimed();
            return;
        }
        if (this.handleRotationAndTask()) {
            return;
        }
        if (this.handleDirectionSwitch(player)) {
            return;
        }
        if (this.recoverIfStuck(player)) {
            return;
        }
        this.updateRotation(player);
        this.updateBridgeInput(player);
    }

    private int rotateDirection(int direction, int offset) {
        int rawIndex = this.directionCycle.indexOf(direction) + offset;
        int wrappedIndex = rawIndex < 0 ? rawIndex % this.directionCycle.size() + this.directionCycle.size() : rawIndex % this.directionCycle.size();
        return this.directionCycle.get(wrappedIndex);
    }

    public BlatantScaffoldMode(Mod parent, String name) {
        super(parent, name);
        this.edgeSneakHelper = new ScaffoldEdgeSneakHelper((Mod)this.getParent(), "legit");
        this.moveTimer = new TimerUtil();
        this.switchTimer = new TimerUtil();
        this.directionCycle = new ArrayList<Integer>(Arrays.asList(5, 4, 6, 1, 7, 2, 8, 3));
        this.slotHistory = new ArrayList<>();
        this.backwardKey = Minecraft.gameSettings().s();
        this.rightKey = Minecraft.gameSettings().g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3();
        this.leftKey = Minecraft.gameSettings().x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg();
        this.addValue(this.activationBlocks);
    }

    private void resetState() {
        this.activationPending = true;
        this.targetPos = null;
        this.movementTask = null;
        this.taskTicks = 0;
        this.targetRotation = null;
        this.prevLeftDown = false;
        this.prevRightDown = false;
        this.switching = false;
        this.atEdge = true;
        this.direction = 0;
        this.pendingDirection = 0;
        this.slotHistory = new ArrayList<>();
        this.heldBlock = null;
        this.pendingReversed = false;
        this.keyIdle = true;
        SharedModuleControlClaims.movementInput.unlock();
        SharedModuleControlClaims.rightClickUse.clearClaimed();
    }

    private void startMoveTaskNoTurn(double[] targetPosition, boolean blockUpdates,
                                     boolean restoreInput, int tickLimit) {
        this.movementTask = new TargetPositionMovementTask(targetPosition[0], targetPosition[1]);
        this.taskBlocksUpdate = blockUpdates;
        this.movementTask.setRestoreInputOnCompletion(restoreInput);
        PlayerMovementTaskManager.INSTANCE.submit(this.movementTask);
        this.taskTicks = 0;
        this.taskTickLimit = tickLimit;
    }

    private void updateRotation(EntityPlayerSP player) {
        if (this.direction == 0) {
            this.targetRotation = new float[]{player.J(), 90.0f};
        } else if (this.direction < 5) {
            float[] previousRotation = this.targetRotation;
            this.targetRotation = this.computePlacementRotation(player, this.computePlacementPoint(this.targetPos, this.direction, this.reversed), this.direction);
            if (this.scaffold.rotationController == null || previousRotation == null || previousRotation[0] != this.targetRotation[0] || previousRotation[1] != this.targetRotation[1]) {
                this.scaffold.applyFixedRotation(this.targetRotation, this.computeRotationSpeed(this.targetRotation, 15));
            }
        } else {
            float[] previousRotation = this.targetRotation;
            this.targetRotation = this.computeDiagonalRotation(player, this.reversed);
            if (this.scaffold.rotationController == null || previousRotation == null || previousRotation[0] != this.targetRotation[0] || previousRotation[1] != this.targetRotation[1]) {
                this.scaffold.applyFixedRotation(this.targetRotation, this.computeRotationSpeed(this.targetRotation, 15));
            }
        }
    }

    private double[] computePlacementPoint(double[] position, int direction, boolean reversed) {
        double x = position[0];
        double z = position[1];
        if (direction == 1) {
            if (reversed) {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.65)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.35)).doubleValue();
            } else {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.35)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.65)).doubleValue();
            }
        } else if (direction == 2) {
            if (reversed) {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.65)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.65)).doubleValue();
            } else {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.35)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.35)).doubleValue();
            }
        } else if (direction == 3) {
            if (reversed) {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.35)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.65)).doubleValue();
            } else {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.65)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.35)).doubleValue();
            }
        } else if (direction == 4) {
            if (reversed) {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.35)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.35)).doubleValue();
            } else {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.65)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.65)).doubleValue();
            }
        } else if (direction == 6) {
            if (reversed) {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.8)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.8)).doubleValue();
            } else {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.8)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.2)).doubleValue();
            }
        } else if (direction == 8) {
            if (reversed) {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.2)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.2)).doubleValue();
            } else {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.2)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.8)).doubleValue();
            }
        } else if (direction == 7) {
            if (reversed) {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.2)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.8)).doubleValue();
            } else {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.8)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.8)).doubleValue();
            }
        } else if (direction == 5) {
            if (reversed) {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.8)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.2)).doubleValue();
            } else {
                x = new BigDecimal(String.valueOf((double)MathUtil.floor(x) + 0.2)).doubleValue();
                z = new BigDecimal(String.valueOf((double)MathUtil.floor(z) + 0.2)).doubleValue();
            }
        }
        return new double[]{x, z};
    }

    private float[] computePlacementRotation(EntityPlayerSP player, double[] placementPoint,
                                             int direction) {
        float pathOffset = 0.1f * this.computeSideOfPath(placementPoint, new double[]{player.z(), player.h()}, direction);
        float yaw = direction == 1 ? 135.0f - pathOffset : (direction == 2 ? -135.0f - pathOffset : (direction == 3 ? -45.0f - pathOffset : 45.0f - pathOffset));
        return new float[]{yaw, this.moveTimer.getLastMS() > 500L ? 83.0f : 81.0f};
    }

    private boolean isLookingAtPlacement(EntityPlayerSP player) {
        RayTraceResult rayTraceResult = RotationManager.INSTANCE.getNormalReachRayTrace();
        if (rayTraceResult.isNull() || !rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.block())) {
            return false;
        }
        int placementAttempts = rayTraceResult.Z();
        double verticalMotion = player.q();
        if (verticalMotion > 0.1 || verticalMotion < -0.1 || this.switching) {
            return placementAttempts != 0;
        }
        return placementAttempts > 1;
    }

    private float computeSideOfPath(double[] pathPoint, double[] playerPosition,
                                    int direction) {
        int relativeDirection = (int)(RotationUtil.y(pathPoint[0], 0.0, pathPoint[1], playerPosition[0], 0.0, playerPosition[1]) + (double)this.scaffold.countBlocks(1));
        double[] adjacentPoint = this.scaffold.offsetPosition(new double[]{pathPoint[0], 0.0, pathPoint[1]}, relativeDirection, direction);
        return (float)((adjacentPoint[0] - pathPoint[0]) * (playerPosition[1] - pathPoint[1]) - (adjacentPoint[2] - pathPoint[1]) * (playerPosition[0] - pathPoint[0]));
    }

    @Override
    public void onDisable() {
        this.scaffold.releaseControls();
        this.resetState();
    }
}
