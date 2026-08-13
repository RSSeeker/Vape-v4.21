package gg.vape.module.blatant.blockin;

import gg.vape.Vape;
import gg.vape.module.combat.Sprint;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.MouseRotationController;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.AttributeInstance;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.MovementInput;
import java.util.ArrayList;
import java.util.List;

public class BlockPlacementGraph {
    public final double positionZ;
    public final double previousPositionY;
    public final int jumpTicks;
    public final ArrayList movementSpeedModifiers;
    public final float moveForward;
    public final double motionZ;
    public boolean jumpKeyDown;
    public boolean sprintKeyDown;
    public final int sprintToggleTimer;
    public final double positionX;
    public final boolean sprinting;
    public boolean leftKeyDown;
    public final double previousPositionX;
    public final boolean inWater;
    public final boolean onGround;
    public final float aiMoveSpeed;
    public final boolean sneaking;
    public final float jumpMovementFactor;
    private static Sprint sprintMod;
    public final float previousYaw;
    public boolean backwardKeyDown;
    public final float moveStrafe;
    public final double positionY;
    public final boolean sneakInput;
    public final float pitch;
    public final double motionY;
    public final float previousPitch;
    public final BlockPlacementGraphNode aimNode;
    public final float yaw;
    public boolean forwardKeyDown;
    public final int sprintingTicksLeft;
    public boolean rightKeyDown;
    public final double motionX;
    public boolean sneakKeyDown;
    public final double previousPositionZ;
    public final boolean jumpInput;

    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.positionX).append(",");
        stringBuilder.append(this.positionY).append(",");
        stringBuilder.append(this.positionZ).append(",");
        stringBuilder.append(this.previousPositionX).append(",");
        stringBuilder.append(this.previousPositionY).append(",");
        stringBuilder.append(this.previousPositionZ).append(",");
        stringBuilder.append(this.motionX).append(",");
        stringBuilder.append(this.motionY).append(",");
        stringBuilder.append(this.motionZ).append(",");
        stringBuilder.append(this.yaw).append(",");
        stringBuilder.append(this.pitch).append(",");
        stringBuilder.append(this.previousYaw).append(",");
        stringBuilder.append(this.previousPitch).append(",");
        stringBuilder.append(this.sprintingTicksLeft).append(",");
        stringBuilder.append(this.sprintToggleTimer).append(",");
        stringBuilder.append(this.onGround).append(",");
        stringBuilder.append(this.sneaking).append(",");
        stringBuilder.append(this.sprinting).append(",");
        stringBuilder.append(this.jumpTicks).append(",");
        stringBuilder.append(this.jumpMovementFactor).append(",");
        stringBuilder.append(this.aiMoveSpeed).append(",");
        stringBuilder.append(this.inWater).append(",");
        stringBuilder.append(this.moveStrafe).append(",");
        stringBuilder.append(this.moveForward).append(",");
        stringBuilder.append(this.jumpInput).append(",");
        stringBuilder.append(this.sneakInput).append(",");
        stringBuilder.append(this.forwardKeyDown).append(",");
        stringBuilder.append(this.backwardKeyDown).append(",");
        stringBuilder.append(this.leftKeyDown).append(",");
        stringBuilder.append(this.rightKeyDown).append(",");
        stringBuilder.append(this.sneakKeyDown).append(",");
        stringBuilder.append(this.jumpKeyDown).append(",");
        stringBuilder.append(this.sprintKeyDown);
        return stringBuilder.toString();
    }

    public static BlockPlacementGraph deserialize(String serialized) {
        String[] values = serialized.split(",");
        if (values.length < 33) {
            throw new IllegalArgumentException("Invalid data for MovementSnapshot");
        }
        return new BlockPlacementGraph(
                Double.parseDouble(values[0]), Double.parseDouble(values[1]),
                Double.parseDouble(values[2]), Double.parseDouble(values[3]),
                Double.parseDouble(values[4]), Double.parseDouble(values[5]),
                Double.parseDouble(values[6]), Double.parseDouble(values[7]),
                Double.parseDouble(values[8]), Float.parseFloat(values[9]),
                Float.parseFloat(values[10]), Float.parseFloat(values[11]),
                Float.parseFloat(values[12]), Integer.parseInt(values[13]),
                Integer.parseInt(values[14]), Boolean.parseBoolean(values[15]),
                Boolean.parseBoolean(values[16]), Boolean.parseBoolean(values[17]),
                Integer.parseInt(values[18]), Float.parseFloat(values[19]),
                Float.parseFloat(values[20]), Boolean.parseBoolean(values[21]),
                Float.parseFloat(values[22]), Float.parseFloat(values[23]),
                Boolean.parseBoolean(values[24]), Boolean.parseBoolean(values[25]),
                Boolean.parseBoolean(values[26]), Boolean.parseBoolean(values[27]),
                Boolean.parseBoolean(values[28]), Boolean.parseBoolean(values[29]),
                Boolean.parseBoolean(values[30]), Boolean.parseBoolean(values[31]),
                Boolean.parseBoolean(values[32]));
    }

    public boolean hasSameDirectionalKeys(BlockPlacementGraph other) {
        return this.forwardKeyDown == other.forwardKeyDown
                && this.backwardKeyDown == other.backwardKeyDown
                && this.leftKeyDown == other.leftKeyDown
                && this.rightKeyDown == other.rightKeyDown;
    }

    public BlockPlacementGraph(BlockPathPlanner blockPathPlanner) {
        EntityPlayer entityPlayer = blockPathPlanner.getSimulatedPlayer();
        this.positionX = entityPlayer.z();
        this.positionY = entityPlayer.N();
        this.positionZ = entityPlayer.h();
        this.previousPositionX = entityPlayer.f();
        this.previousPositionY = entityPlayer.H();
        this.previousPositionZ = entityPlayer.R();
        this.motionX = entityPlayer.t();
        this.motionY = entityPlayer.q();
        this.motionZ = entityPlayer.T();
        this.yaw = entityPlayer.J();
        this.pitch = entityPlayer.V();
        this.previousYaw = entityPlayer.j();
        this.previousPitch = entityPlayer.D();
        this.aimNode = this.buildAimNode(blockPathPlanner.getRotationController());
        this.sprintingTicksLeft = blockPathPlanner.getSprintingTicksLeft();
        this.sprintToggleTimer = blockPathPlanner.getSprintToggleTimer();
        this.onGround = entityPlayer.b$src$Z$fqlxe4();
        this.sneaking = blockPathPlanner.isSneaking();
        this.sprinting = entityPlayer.B$src$Z$f90iek();
        this.jumpTicks = entityPlayer.B$src$I$14s4bbr();
        this.jumpMovementFactor = entityPlayer.y$src$F$15mczw1();
        this.aiMoveSpeed = entityPlayer.C$src$F$1i1kt1e();
        this.inWater = entityPlayer.D$src$Z$fa43la();
        AttributeInstance attributeInstance = ForgeVersion.MC_1_20_6.d() ? entityPlayer.t(MonsterAttributesBridge.U()) : entityPlayer.h(MonsterAttributesBridge.B());
        this.movementSpeedModifiers = new ArrayList(attributeInstance.I());
        this.moveForward = blockPathPlanner.getMoveForward();
        this.moveStrafe = blockPathPlanner.getMoveStrafe();
        this.jumpInput = blockPathPlanner.isJumpInput();
        this.sneakInput = blockPathPlanner.isSneaking();
        this.forwardKeyDown = blockPathPlanner.isForwardKeyDown();
        this.backwardKeyDown = blockPathPlanner.isBackwardKeyDown();
        this.leftKeyDown = blockPathPlanner.isLeftKeyDown();
        this.rightKeyDown = blockPathPlanner.isRightKeyDown();
        this.sneakKeyDown = blockPathPlanner.isSneakKeyDown();
        this.jumpKeyDown = blockPathPlanner.isJumpKeyDown();
        if (sprintMod == null) {
            sprintMod = Vape.INSTANCE.getModManager().getMod(Sprint.class);
        }
        this.sprintKeyDown = blockPathPlanner.isSprintKeyDown() || sprintMod.isEnabled();
    }

    private BlockPlacementGraph(double positionX, double positionY, double positionZ,
                                double previousPositionX, double previousPositionY, double previousPositionZ,
                                double motionX, double motionY, double motionZ,
                                float yaw, float pitch, float previousYaw, float previousPitch,
                                int sprintingTicksLeft, int sprintToggleTimer, boolean onGround,
                                boolean sneaking, boolean sprinting, int jumpTicks,
                                float jumpMovementFactor, float aiMoveSpeed, boolean inWater,
                                float moveStrafe, float moveForward, boolean jumpInput, boolean sneakInput,
                                boolean forwardKeyDown, boolean backwardKeyDown, boolean leftKeyDown,
                                boolean rightKeyDown, boolean sneakKeyDown, boolean jumpKeyDown,
                                boolean sprintKeyDown) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.previousPositionX = previousPositionX;
        this.previousPositionY = previousPositionY;
        this.previousPositionZ = previousPositionZ;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.previousYaw = previousYaw;
        this.previousPitch = previousPitch;
        this.sprintingTicksLeft = sprintingTicksLeft;
        this.sprintToggleTimer = sprintToggleTimer;
        this.onGround = onGround;
        this.sneaking = sneaking;
        this.sprinting = sprinting;
        this.jumpTicks = jumpTicks;
        this.jumpMovementFactor = jumpMovementFactor;
        this.aiMoveSpeed = aiMoveSpeed;
        this.inWater = inWater;
        this.movementSpeedModifiers = new ArrayList();
        this.moveStrafe = moveStrafe;
        this.moveForward = moveForward;
        this.jumpInput = jumpInput;
        this.sneakInput = sneakInput;
        this.forwardKeyDown = forwardKeyDown;
        this.backwardKeyDown = backwardKeyDown;
        this.leftKeyDown = leftKeyDown;
        this.rightKeyDown = rightKeyDown;
        this.sneakKeyDown = sneakKeyDown;
        this.jumpKeyDown = jumpKeyDown;
        this.sprintKeyDown = sprintKeyDown;
        this.aimNode = null;
    }

    public boolean matchesSimulationState(BlockPlacementGraph snapshot) {
        if (this.positionX != snapshot.positionX || this.positionY != snapshot.positionY
                || this.positionZ != snapshot.positionZ || this.motionX != snapshot.motionX
                || this.motionY != snapshot.motionY || this.motionZ != snapshot.motionZ) {
            return false;
        }
        if (ForgeVersion.MC_1_16_5.B()
                && (this.previousPositionX != snapshot.previousPositionX
                || this.previousPositionY != snapshot.previousPositionY
                || this.previousPositionZ != snapshot.previousPositionZ)) {
            return false;
        }
        if (this.sprintingTicksLeft != snapshot.sprintingTicksLeft
                || this.sprintToggleTimer != snapshot.sprintToggleTimer
                || this.onGround != snapshot.onGround || this.sneaking != snapshot.sneaking
                || this.sprinting != snapshot.sprinting || this.jumpTicks != snapshot.jumpTicks
                || this.jumpMovementFactor != snapshot.jumpMovementFactor
                || this.aiMoveSpeed != snapshot.aiMoveSpeed || this.inWater != snapshot.inWater
                || this.moveStrafe != snapshot.moveStrafe || this.moveForward != snapshot.moveForward
                || this.jumpInput != snapshot.jumpInput || this.sneakInput != snapshot.sneakInput) {
            return false;
        }
        if (this.aimNode != null && snapshot.aimNode != null) {
            return this.aimNode.describeDifferences(snapshot.aimNode).isEmpty();
        }
        float sensitivity = RotationManager.INSTANCE.getMouseSensitivity();
        float sensitivityStep = sensitivity * 0.6f + 0.2f;
        float angleThreshold = sensitivityStep * sensitivityStep * sensitivityStep * 8.0f * 0.15f;
        float yawDifference = MathUtil.wrapAngleTo180(snapshot.yaw - this.yaw);
        if (this.yaw != snapshot.yaw && yawDifference >= angleThreshold) {
            return false;
        }
        float pitchDifference = MathUtil.wrapAngleTo180(snapshot.pitch - this.pitch);
        return this.pitch == snapshot.pitch || pitchDifference < angleThreshold;
    }

    private BlockPlacementGraphNode buildAimNode(MouseRotationController mouseRotationController) {
        BlockPlacementGraphNode blockPlacementGraphNode = null;
        if (mouseRotationController != null) {
            blockPlacementGraphNode = mouseRotationController instanceof AdaptiveRotationController
                    ? new BlockPlacementGraphNode((AdaptiveRotationController)mouseRotationController)
                    : new BlockPlacementGraphNode(mouseRotationController, this.yaw, this.pitch);
        }
        return blockPlacementGraphNode;
    }

    public static String toCsv(List<BlockPlacementGraph> snapshots) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("posX,posY,posZ,prevPosX,prevPosY,prevPosZ,motionX,motionY,motionZ,yaw,pitch,prevYaw,prevPitch,sprintingTicksLeft,sprintToggleTimer,onGround,sneaking,sprinting,jumpTicks,jumpMovementFactor,aiMoveSpeed,moveStrafe,moveForward,jump,sneak,inputForward,inputBackwards,inputLeft,inputRight,inputSneak,inputJump,inputSprint,targetYaw,targetPitch,deltaX,deltaY,yawChanged,pitchChanged,isCompleted,currentYaw,currentPitch\n");
        for (BlockPlacementGraph snapshot : snapshots) {
            stringBuilder.append(snapshot.serialize()).append(",");
            if (snapshot.aimNode != null) {
                BlockPlacementGraphNode node = snapshot.aimNode;
                stringBuilder.append(node.targetYaw).append(",").append(node.targetPitch).append(",")
                        .append(node.pendingYawDelta).append(",").append(node.pendingPitchDelta).append(",")
                        .append(node.yawDistance).append(",").append(node.pitchDistance).append(",")
                        .append(node.complete).append(",").append(node.currentYaw).append(",")
                        .append(node.currentPitch).append("\n");
                continue;
            }
            stringBuilder.append(",,,,,,,,\n");
        }
        return stringBuilder.toString();
    }

    public BlockPlacementGraph(EntityPlayerSP player) {
        this.positionX = player.z();
        this.positionY = player.N();
        this.positionZ = player.h();
        this.previousPositionX = player.f();
        this.previousPositionY = player.H();
        this.previousPositionZ = player.R();
        this.motionX = player.t();
        this.motionY = player.q();
        this.motionZ = player.T();
        this.yaw = player.J();
        this.pitch = player.V();
        this.previousYaw = player.j();
        this.previousPitch = player.D();
        this.aimNode = this.buildAimNode(RotationManager.INSTANCE.getActiveController());
        this.sprintingTicksLeft = player.z$src$I$1uboxyr();
        this.sprintToggleTimer = player.L$src$I$1tmeeo5();
        this.onGround = player.b$src$Z$fqlxe4();
        this.sneaking = player.P();
        this.sprinting = player.B$src$Z$f90iek();
        this.jumpTicks = player.B$src$I$14s4bbr();
        this.jumpMovementFactor = player.y$src$F$15mczw1();
        this.aiMoveSpeed = player.C$src$F$1i1kt1e();
        this.inWater = player.D$src$Z$fa43la();
        AttributeInstance attributeInstance = ForgeVersion.MC_1_20_6.d()
                ? player.t(MonsterAttributesBridge.U()) : player.h(MonsterAttributesBridge.B());
        this.movementSpeedModifiers = new ArrayList(attributeInstance.I());
        MovementInput movementInput = player.movementInput();
        this.moveStrafe = movementInput.T();
        this.moveForward = movementInput.D();
        this.jumpInput = movementInput.G();
        this.sneakInput = movementInput.D$src$Z$v5d6e8();
        GameSettings gameSettings = Minecraft.gameSettings();
        this.forwardKeyDown = gameSettings.Y().isKeyDown();
        this.backwardKeyDown = gameSettings.s().isKeyDown();
        this.leftKeyDown = gameSettings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg().isKeyDown();
        this.rightKeyDown = gameSettings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3().isKeyDown();
        this.sneakKeyDown = gameSettings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0().isKeyDown();
        this.jumpKeyDown = gameSettings.O().isKeyDown();
        if (sprintMod == null) {
            sprintMod = Vape.INSTANCE.getModManager().getMod(Sprint.class);
        }
        this.sprintKeyDown = gameSettings.r().isKeyDown() || sprintMod.isEnabled();
    }

    public boolean hasSameInputKeys(BlockPlacementGraph other) {
        return this.hasSameDirectionalKeys(other)
                && this.sneakKeyDown == other.sneakKeyDown
                && this.jumpKeyDown == other.jumpKeyDown
                && this.sprintKeyDown == other.sprintKeyDown;
    }
}
