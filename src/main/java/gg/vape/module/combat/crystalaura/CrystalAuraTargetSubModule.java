package gg.vape.module.combat.crystalaura;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventRender3D;
import gg.vape.event.impl.EventWorldChange;
import gg.vape.input.KeyBindingInputState;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.combat.CrystalAura;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.module.utility.clutch.ClutchPlacementPathUtils;
import gg.vape.module.utility.clutch.PlacementTarget;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.RotationAngles;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.RotationVectorMath;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.datas.BlockData;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.CPacketUseEntity;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.UseEntityPacketBridge;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class CrystalAuraTargetSubModule
extends SubModule<CrystalAura> {
    private final NumberValue aimSpeed = NumberValue.createWithDescription(this, "Aim speed", "#.#", "", 1.0, 4.5, 10.0, "Aim rotation speed");
    private final TimerUtil stateTimer;
    private FixedRotationController rotationController;
    private final TimerUtil actionTimer;
    private CrystalAuraActionState state;
    private final ModeOption rapidFireMode;
    private static final Color SAFE_COLOR;
    private final NumberValue maxSelfDamage;
    private final BooleanValue showTargetBlock;
    private CrystalAura crystalAura;
    private final ModeOption noneMode;
    private final ModeOption predictMode;
    private int crystalSlot = -1;
    private EnumFacing obsidianFacing;
    private Entity trackedCrystal;
    private String debugMessage = "";
    private boolean obsidianPlaced;
    private BlockData obsidianSupport;
    private final RotationControlClaim rotationClaim;
    private final BooleanValue placeObsidian;
    private int savedSlot = -1;
    private final ModeValue optimizationMode;
    private static final Color DEFAULT_COLOR;
    private final RandomValue delay;
    private static final Color PLACING_COLOR;
    private int pendingRemoveEntityId = -1;
    private int obsidianSlot = -1;
    private BlockData targetObsidian;
    private final RotationManager rotationManager;
    private final BooleanValue antiSuicide = BooleanValue.create(this, "Anti suicide", true, "Prevents breaking if it will result in fatal damage");

    private void resetState() {
        this.state = CrystalAuraActionState.IDLE;
        this.targetObsidian = null;
        this.trackedCrystal = null;
        this.crystalSlot = -1;
        this.savedSlot = -1;
        this.pendingRemoveEntityId = -1;
        this.resetObsidianState();
        this.actionTimer.reset();
        this.stateTimer.reset();
        this.debugMessage = "";
    }

    private Entity findCrystalAbove(World world, BlockData baseBlock) {
        int blockX = baseBlock.D();
        int blockY = baseBlock.B();
        int blockZ = baseBlock.G();
        AxisAlignedBB searchBox = AxisAlignedBB.create(blockX, blockY + 1, blockZ, (double)blockX + 1.0, (double)blockY + 3.0, (double)blockZ + 1.0);
        ArrayList<Entity> crystals = new ArrayList<Entity>();
        world.getEntityGetter().forEachEntityInBounds(searchBox, entityObject -> CrystalAuraTargetSubModule.collectCrystals(crystals, entityObject));
        if (!crystals.isEmpty()) {
            return crystals.get(0);
        }
        return null;
    }

    private Color computeRenderColor(EntityPlayerSP player, World world) {
        if (this.targetObsidian == null) {
            return DEFAULT_COLOR;
        }
        if (this.state == CrystalAuraActionState.PLACING_OBSIDIAN) {
            return PLACING_COLOR;
        }
        if (!this.isHoldingCrystal(player)) {
            return DEFAULT_COLOR;
        }
        BlockPos basePosition = BlockPos.create(this.targetObsidian.D(), this.targetObsidian.B(), this.targetObsidian.G());
        BlockState blockState = world.getBlockState(basePosition);
        if (!this.crystalAura.isCrystalBaseBlock(blockState)) {
            return DEFAULT_COLOR;
        }
        if (this.state == CrystalAuraActionState.PLACING_CRYSTAL && !this.canPlaceCrystalAbove(world, this.targetObsidian)) {
            return DEFAULT_COLOR;
        }
        Vec3 explosionPosition = this.state == CrystalAuraActionState.BREAKING_CRYSTAL && this.trackedCrystal != null && this.trackedCrystal.isNotNull() && !this.trackedCrystal.M$src$Z$ff28xj()
                ? this.trackedCrystal.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk()
                : Vec3.create((double)this.targetObsidian.D() + 0.5, this.targetObsidian.B() + 1, (double)this.targetObsidian.G() + 0.5);
        if (!this.crystalAura.isSelfDamageSafe(explosionPosition, ExplosionType.CRYSTAL, player, world, this.antiSuicide.getEffectiveValue(), ((Double)this.maxSelfDamage.getValue()).floatValue())) {
            return DEFAULT_COLOR;
        }
        return SAFE_COLOR;
    }

    private void clearTracking(String reason) {
        boolean hadTrackingState = this.state != CrystalAuraActionState.IDLE || this.targetObsidian != null || this.trackedCrystal != null || this.rotationController != null;
        this.releaseRotation();
        this.resetState();
        if (hadTrackingState) {
            this.debug("cleared tracking: " + reason);
        }
    }

    private void setState(CrystalAuraActionState newState, String reason) {
        if (this.state == newState) {
            return;
        }
        this.state = newState;
        this.stateTimer.reset();
        this.debug("state -> " + (Object)((Object)newState) + " (" + reason + ")");
        if (newState == CrystalAuraActionState.IDLE) {
            this.targetObsidian = null;
            this.trackedCrystal = null;
            this.crystalSlot = -1;
            this.releaseRotation();
        }
    }

    @Override
    public String getDetailedSuffix() {
        return "Manual";
    }

    private void debug(String message) {
    }

    private void handlePlacingCrystal(EntityPlayerSP player, World world) {
        if (!this.canPlaceCrystalAbove(world, this.targetObsidian)) {
            this.clearTracking("target obsidian no longer has valid crystal placement space");
            return;
        }
        if (!this.actionTimer.hasTimeElapsed((long)this.delay.getRandomValue())) {
            KeyBinding useItemKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
            if (useItemKey.u() || useItemKey.isPressed()) {
                KeyBinding.setKeyBindState(useItemKey, false);
            }
            return;
        }
        Entity existingCrystal = this.findCrystalAbove(world, this.targetObsidian);
        if (existingCrystal != null) {
            this.trackedCrystal = existingCrystal;
            this.setState(CrystalAuraActionState.BREAKING_CRYSTAL, "crystal already exists while placing");
            return;
        }
        if (!this.rotationClaim.isOwnedBy(this.crystalAura) && !this.rotationClaim.acquire(this.crystalAura, true)) {
            return;
        }
        if (player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v() != this.crystalSlot) {
            if (this.savedSlot == -1) {
                this.savedSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
            }
            player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.crystalSlot);
        }
        if (this.rotationController != null && this.rotationManager.getActiveController() == this.rotationController) {
            RayTraceResult crosshairHit = this.rotationManager.getNormalReachRayTrace();
            if (crosshairHit.getTypeOfHit().equals(RayTraceResult_type.entity())) {
                Entity hitEntity = crosshairHit.getEntity();
                if (hitEntity.isNotNull() && hitEntity.isInstance(MappedClasses.Ze)) {
                    this.trackedCrystal = hitEntity;
                    this.setState(CrystalAuraActionState.BREAKING_CRYSTAL, "spoofed mouse-over hit crystal while placing");
                    return;
                }
            }
            if (crosshairHit.getTypeOfHit().equals(RayTraceResult_type.block())) {
                BlockPos hitBlock = crosshairHit.getBlockPos();
                BlockData targetBlock = this.targetObsidian;
                if (hitBlock.getX() == targetBlock.D() && hitBlock.getY() == targetBlock.B() && hitBlock.getZ() == targetBlock.G()) {
                    KeyBinding attackKey = Minecraft.gameSettings().F();
                    if (attackKey.u() || attackKey.isPressed()) {
                        KeyBinding.setKeyBindState(attackKey, false);
                    }
                    KeyBinding useItemKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
                    KeyBinding.setKeyBindState(useItemKey, true);
                    KeyBinding.onTick(useItemKey);
                    KeyBinding.setKeyBindState(useItemKey, false);
                    this.setState(CrystalAuraActionState.BREAKING_CRYSTAL, "placement click sent, waiting for crystal");
                }
            }
        }
    }

    private String formatPos(BlockPos position) {
        if (position == null) {
            return "null";
        }
        return position.getX() + "," + position.getY() + "," + position.getZ();
    }

    private static void markCollidingEntity(boolean[] collisionFound, Object entityObject) {
        Entity entity = new Entity(entityObject);
        if (entity.n$src$Z$fx7gig() || entity.isInstance(MappedClasses.Ze)) {
            collisionFound[0] = true;
        }
    }

    private void aimAt(Vec3 eyePosition, Vec3 targetPosition) {
        if (this.rotationController == null || this.rotationController.isComplete() || this.rotationController instanceof AdaptiveRotationController && ((AdaptiveRotationController)this.rotationController).isRelativeMode()) {
            this.rotationController = new AdaptiveRotationController();
        }
        this.rotationController.setSpeed(((Double)this.aimSpeed.getValue()).floatValue());
        this.rotationController.setComplete(false);
        this.rotationController.setRetainAfterCompletion(true);
        this.rotationController.setClampStepToRemaining(true);
        this.rotationController.setTolerance(0.0f);
        this.rotationController.setScaleAxesProportionally(true);
        this.rotationController.setLinearAcceleration(true);
        if (this.rotationController instanceof AdaptiveRotationController) {
            ((AdaptiveRotationController)this.rotationController).setRelativeMode(false);
        }
        float managedYaw = this.rotationManager.getManagedYaw();
        float managedPitch = this.rotationManager.getManagedPitch();
        RotationAngles targetRotation = RotationVectorMath.d(eyePosition, targetPosition, managedYaw, managedPitch);
        this.rotationController.setTargetRotation(targetRotation);
        if (this.rotationManager.getActiveController() != this.rotationController) {
            this.rotationManager.setController(this.rotationController);
        }
    }

    private void handlePlacingObsidian(EntityPlayerSP player, World world) {
        if (this.obsidianSupport == null || this.obsidianFacing == null) {
            this.clearTracking("obsidian placement data lost");
            return;
        }
        if (this.obsidianSlot == -1 || !this.crystalAura.isObsidian(player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(this.obsidianSlot))) {
            this.obsidianSlot = this.crystalAura.findObsidianSlot(player);
            if (this.obsidianSlot == -1) {
                this.clearTracking("lost obsidian from hotbar");
                return;
            }
        }
        BlockData targetBlock = this.targetObsidian;
        Block currentBlock = world.getBlockByPos(targetBlock.D(), targetBlock.B(), targetBlock.G());
        if (!BlockUtil.u(currentBlock)) {
            BlockState blockState = world.getBlockState(BlockPos.create(targetBlock.D(), targetBlock.B(), targetBlock.G()));
            if (this.crystalAura.isCrystalBaseBlock(blockState)) {
                this.obsidianSupport = null;
                this.obsidianFacing = null;
                this.obsidianPlaced = false;
                if (this.crystalSlot != -1) {
                    player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.crystalSlot);
                }
                this.setState(CrystalAuraActionState.PLACING_CRYSTAL, "obsidian placed successfully");
            } else {
                this.clearTracking("unexpected block at obsidian placement position");
            }
            return;
        }
        if (!this.rotationClaim.isOwnedBy(this.crystalAura) && !this.rotationClaim.acquire(this.crystalAura, true)) {
            return;
        }
        KeyBinding useItemKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
        if (useItemKey.u() || useItemKey.isPressed()) {
            KeyBinding.setKeyBindState(useItemKey, false);
        }
        if (player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v() != this.obsidianSlot) {
            if (this.savedSlot == -1) {
                this.savedSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
            }
            player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.obsidianSlot);
        }
        if (this.rotationController != null && this.rotationManager.getActiveController() == this.rotationController) {
            RayTraceResult crosshairHit = this.rotationManager.getNormalReachRayTrace();
            if (crosshairHit.getTypeOfHit().equals(RayTraceResult_type.block())) {
                BlockPos hitBlock = crosshairHit.getBlockPos();
                int hitSide = crosshairHit.Z();
                if (hitBlock.getX() == this.obsidianSupport.D() && hitBlock.getY() == this.obsidianSupport.B()
                        && hitBlock.getZ() == this.obsidianSupport.G() && hitSide == this.obsidianFacing.Y()) {
                    KeyBinding attackKey = Minecraft.gameSettings().F();
                    if (attackKey.u() || attackKey.isPressed()) {
                        KeyBinding.setKeyBindState(attackKey, false);
                    }
                    KeyBinding.setKeyBindState(useItemKey, true);
                    KeyBinding.onTick(useItemKey);
                    KeyBinding.setKeyBindState(useItemKey, false);
                    this.obsidianPlaced = true;
                }
            }
        }
    }

    private boolean acquireHoveredTarget(EntityPlayerSP player, WorldClient world, RayTraceResult crosshairHit) {
        if (crosshairHit.isNull() || !crosshairHit.getTypeOfHit().equals(RayTraceResult_type.block())) {
            this.debug("idle: screen crosshair not hitting a block");
            return false;
        }
        BlockPos hoveredPosition = crosshairHit.getBlockPos();
        if (this.isValidBaseBlock(world, hoveredPosition)) {
            this.crystalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
            BlockData baseBlock = new BlockData(hoveredPosition.getX(), hoveredPosition.getY(), hoveredPosition.getZ());
            Entity crystal = this.findCrystalAbove(world, baseBlock);
            this.targetObsidian = baseBlock;
            if (crystal != null) {
                this.trackedCrystal = crystal;
                this.setState(CrystalAuraActionState.BREAKING_CRYSTAL, "found crystal above hovered obsidian");
            } else if (this.canPlaceCrystalAbove(world, baseBlock)) {
                this.setState(CrystalAuraActionState.PLACING_CRYSTAL, "hovered obsidian valid for crystal placement");
            } else {
                this.targetObsidian = null;
                this.debug("idle: rejected blocked crystal space at " + this.formatPos(hoveredPosition));
                return false;
            }
            return true;
        }
        if (!this.placeObsidian.getEffectiveValue().booleanValue()) {
            this.debug("idle: rejected invalid base block at " + this.formatPos(hoveredPosition));
            return false;
        }
        int availableObsidianSlot = this.crystalAura.findObsidianSlot(player);
        if (availableObsidianSlot == -1) {
            this.debug("idle: place obsidian enabled but no obsidian in hotbar");
            return false;
        }
        if (BlockUtil.u(world.getBlockState(hoveredPosition).getBlock())) {
            this.debug("idle: hovered block is replaceable, cannot place obsidian on it");
            return false;
        }
        EnumFacing facing = EnumFacing.T(crosshairHit.Z());
        BlockData supportBlock = new BlockData(hoveredPosition.getX(), hoveredPosition.getY(), hoveredPosition.getZ());
        BlockData placementTarget = supportBlock.R(facing);
        if (!this.hasCrystalPlacementSpace(world, placementTarget)) {
            this.debug("idle: no room for crystal above obsidian placement");
            return false;
        }
        this.crystalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
        this.obsidianSlot = availableObsidianSlot;
        this.obsidianSupport = supportBlock;
        this.obsidianFacing = facing;
        this.obsidianPlaced = false;
        this.targetObsidian = placementTarget;
        this.setState(CrystalAuraActionState.PLACING_OBSIDIAN, "placing obsidian on hovered surface");
        return true;
    }

    private boolean hasCrystalPlacementSpace(World world, BlockData placementTarget) {
        Block targetBlock = world.getBlockByPos(placementTarget.D(), placementTarget.B(), placementTarget.G());
        Block blockAbove = world.getBlockByPos(placementTarget.D(), placementTarget.B() + 1, placementTarget.G());
        Block secondBlockAbove = world.getBlockByPos(placementTarget.D(), placementTarget.B() + 2, placementTarget.G());
        return BlockUtil.u(targetBlock) && BlockUtil.u(blockAbove) && BlockUtil.u(secondBlockAbove);
    }

    private void switchHoveredTarget(EntityPlayerSP player, WorldClient world, RayTraceResult crosshairHit) {
        if (this.state == CrystalAuraActionState.IDLE || !this.placeObsidian.getEffectiveValue().booleanValue()
                || crosshairHit.isNull() || !crosshairHit.getTypeOfHit().equals(RayTraceResult_type.block())) {
            return;
        }
        BlockPos hoveredPosition = crosshairHit.getBlockPos();
        boolean hoveringTarget = this.targetObsidian != null && hoveredPosition.getX() == this.targetObsidian.D()
                && hoveredPosition.getY() == this.targetObsidian.B() && hoveredPosition.getZ() == this.targetObsidian.G();
        boolean hoveringSupport = this.state == CrystalAuraActionState.PLACING_OBSIDIAN && this.obsidianSupport != null
                && hoveredPosition.getX() == this.obsidianSupport.D() && hoveredPosition.getY() == this.obsidianSupport.B()
                && hoveredPosition.getZ() == this.obsidianSupport.G();
        if (hoveringTarget || hoveringSupport) {
            return;
        }
        int currentCrystalSlot = this.crystalSlot;
        if (this.isValidBaseBlock(world, hoveredPosition)) {
            BlockData baseBlock = new BlockData(hoveredPosition.getX(), hoveredPosition.getY(), hoveredPosition.getZ());
            Entity crystal = this.findCrystalAbove(world, baseBlock);
            if (crystal == null && !this.canPlaceCrystalAbove(world, baseBlock)) {
                return;
            }
            this.releaseRotation();
            this.resetObsidianState();
            this.trackedCrystal = crystal;
            this.crystalSlot = currentCrystalSlot;
            this.targetObsidian = baseBlock;
            this.setState(crystal != null ? CrystalAuraActionState.BREAKING_CRYSTAL : CrystalAuraActionState.PLACING_CRYSTAL,
                    crystal != null ? "switched to new obsidian target (crystal found)" : "switched to new obsidian target");
            return;
        }
        int availableObsidianSlot = this.crystalAura.findObsidianSlot(player);
        if (BlockUtil.u(world.getBlockState(hoveredPosition).getBlock()) || availableObsidianSlot == -1) {
            return;
        }
        EnumFacing facing = EnumFacing.T(crosshairHit.Z());
        BlockData supportBlock = new BlockData(hoveredPosition.getX(), hoveredPosition.getY(), hoveredPosition.getZ());
        BlockData placementTarget = supportBlock.R(facing);
        if (!this.hasCrystalPlacementSpace(world, placementTarget)) {
            return;
        }
        this.releaseRotation();
        this.resetObsidianState();
        this.trackedCrystal = null;
        this.crystalSlot = currentCrystalSlot;
        this.obsidianSlot = availableObsidianSlot;
        this.obsidianSupport = supportBlock;
        this.obsidianFacing = facing;
        this.obsidianPlaced = false;
        this.targetObsidian = placementTarget;
        this.setState(CrystalAuraActionState.PLACING_OBSIDIAN, "switched to new obsidian placement target");
    }

    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        WorldClient world = eventPreTick.getWorld();
        EntityPlayerSP player = eventPreTick.getThePlayer();
        int controlFlowState = ExplosionType.getControlFlowState();
        if (world.isNull() || player.isNull() || eventPreTick.getCurrentScreen().isNotNull()) {
            this.clearTracking("world/player/screen invalid");
            return;
        }
        if (!KeyBindingInputState.isRightButtonDown()) {
            if (this.state != CrystalAuraActionState.IDLE || this.targetObsidian != null || this.trackedCrystal != null) {
                this.clearTracking("right click released");
            }
            return;
        }
        if (!this.isHoldingCrystal(player) && this.state != CrystalAuraActionState.PLACING_OBSIDIAN) {
            if (this.state != CrystalAuraActionState.IDLE || this.targetObsidian != null || this.trackedCrystal != null) {
                this.clearTracking("not holding crystal");
            }
            return;
        }
        RayTraceResult crosshairHit = this.rayTraceScreenCenter(player, world);
        if (this.state == CrystalAuraActionState.IDLE && !this.acquireHoveredTarget(player, world, crosshairHit)) {
            return;
        }
        if (this.targetObsidian != null && this.state != CrystalAuraActionState.PLACING_OBSIDIAN) {
            BlockState targetState = world.getBlockState(BlockPos.create(this.targetObsidian.D(), this.targetObsidian.B(), this.targetObsidian.G()));
            if (!this.crystalAura.isCrystalBaseBlock(targetState)) {
                this.clearTracking("target obsidian no longer valid base block");
                return;
            }
        }
        this.switchHoveredTarget(player, world, crosshairHit);
        if (this.state != CrystalAuraActionState.PLACING_OBSIDIAN && !this.isHoldingCrystal(player)) {
            this.clearTracking("lost held crystal");
            return;
        }
        if (this.state != CrystalAuraActionState.PLACING_OBSIDIAN) {
            this.crystalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
        }
        switch (this.state) {
            case BREAKING_CRYSTAL:
                this.handleBreakingCrystal(player, world);
                break;
            case PLACING_CRYSTAL:
                this.handlePlacingCrystal(player, world);
                break;
            case PLACING_OBSIDIAN:
                this.handlePlacingObsidian(player, world);
                break;
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            ExplosionType.setControlFlowState(++controlFlowState);
        }
    }

    private static void collectCrystals(ArrayList<Entity> crystals, Object entityObject) {
        if (MappedClasses.Ze.isAssignableFrom(entityObject.getClass())) {
            crystals.add(new Entity(entityObject));
        }
    }

    private boolean isValidBaseBlock(World world, BlockPos position) {
        return position != null && this.crystalAura.isCrystalBaseBlock(world.getBlockState(position));
    }

    private Vec3 computeCrystalHitVec(EntityPlayerSP player, World world, Vec3 eyePosition, BlockData baseBlock, float currentYaw, float currentPitch) {
        EnumFacing[] facings = new EnumFacing[]{EnumFacing.F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5(), EnumFacing.w(), EnumFacing.M(), EnumFacing.X(), EnumFacing.g$src$Lgg_vape_wrapper_impl_EnumFacing_$1ii8mzu(), EnumFacing.B()};
        for (EnumFacing facing : facings) {
            PlacementTarget placementTarget = new PlacementTarget(baseBlock, facing);
            Vec3 hitVector = ClutchPlacementPathUtils.findBestPlacementHitPoint(player, world, eyePosition, placementTarget, currentYaw, currentPitch);
            if (hitVector == null) continue;
            return hitVector;
        }
        return null;
    }

    @EventHandler
    public void onPacketSend(EventPacketSend eventPacketSend) {
        EntityPlayerSP player = eventPacketSend.getThePlayer();
        int controlFlowState = ExplosionType.getDerivedControlFlowState();
        if (controlFlowState != 0) {
            WorldClient world = eventPacketSend.getWorld();
            if (player.isNull() || world.isNull()) {
                return;
            }
            Packet packet = eventPacketSend.getPacket();
            if (packet.isInstance(MappedClasses.Fa)) {
                UseEntityPacketBridge useEntityPacket = new UseEntityPacketBridge(packet.getObject());
                Entity attackedEntity = useEntityPacket.getEntity(world);
                boolean attackedCrystal = attackedEntity.isNotNull() && attackedEntity.isInstance(MappedClasses.Ze);
                if (useEntityPacket.getAction().equals(CPacketUseEntity.attack()) && this.state == CrystalAuraActionState.BREAKING_CRYSTAL && attackedCrystal) {
                    if (((ModeSelection)this.optimizationMode.getValue()).equals(this.predictMode) && !Minecraft.V()) {
                        this.pendingRemoveEntityId = attackedEntity.S();
                    }
                    this.trackedCrystal = null;
                    this.setState(CrystalAuraActionState.PLACING_CRYSTAL, "attack packet sent");
                    this.actionTimer.reset();
                }
            }
            return;
        }
        if (player.isNull()) {
            return;
        }
        Packet packet = eventPacketSend.getPacket();
        if (packet.isInstance(MappedClasses.Fa)) {
            UseEntityPacketBridge useEntityPacket = new UseEntityPacketBridge(packet.getObject());
            Entity attackedEntity = useEntityPacket.getEntity(eventPacketSend.getWorld());
            if (this.state == CrystalAuraActionState.BREAKING_CRYSTAL) {
                if (attackedEntity.isNotNull() && !Minecraft.V()) {
                    this.pendingRemoveEntityId = attackedEntity.S();
                }
                this.trackedCrystal = null;
                this.setState(CrystalAuraActionState.PLACING_CRYSTAL, "attack packet sent");
                this.actionTimer.reset();
            }
        }
    }

    @EventHandler
    public void onWorldChange(EventWorldChange eventWorldChange) {
        this.releaseRotation();
        this.resetState();
    }

    private void releaseRotation() {
        if (this.rotationController != null) {
            RotationManager.INSTANCE.releaseController(this.rotationController);
            if (this.rotationManager.getActiveController() == this.rotationController) {
                this.rotationController.setRetainAfterCompletion(false);
                this.rotationController.setComplete(true);
                if (this.rotationController instanceof AdaptiveRotationController) {
                    ((AdaptiveRotationController)this.rotationController).setRelativeMode(true);
                }
            }
            this.rotationController = null;
        }
        this.rotationClaim.release(this.crystalAura);
        if (this.savedSlot != -1) {
            EntityPlayerSP player = Minecraft.thePlayer();
            if (!player.isNull()) {
                player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.savedSlot);
            }
            this.savedSlot = -1;
        }
    }

    @EventHandler
    public void onRender3D(EventRender3D eventRender3D) {
        if (!this.showTargetBlock.getEffectiveValue().booleanValue()) {
            return;
        }
        EntityPlayerSP player = eventRender3D.getThePlayer();
        WorldClient world = eventRender3D.getWorld();
        if (player.isNull() || world.isNull() || this.state == CrystalAuraActionState.IDLE || this.targetObsidian == null || !KeyBindingInputState.isRightButtonDown()) {
            return;
        }
        boolean blendEnabled = GL11.glIsEnabled((int)3042);
        boolean lineSmoothingEnabled = GL11.glIsEnabled((int)2848);
        try {
            RenderUtil.d();
            RenderUtils.g();
            GlStateManager.enableBlend();
            OpenGlBackendHolder.backend.enableCapability(3042);
            GL11.glBlendFunc((int)770, (int)771);
            GlStateManager.disableTexture2D();
            GlStateManager.r();
            RenderUtil.w(RenderManager.getInterpolatedRenderPosX(), RenderManager.getInterpolatedRenderPosY(), RenderManager.getInterpolatedRenderPosZ(), this.targetObsidian.D(), this.targetObsidian.B(), this.targetObsidian.G(), this.computeRenderColor(player, world));
        }
        finally {
            if (blendEnabled) {
                GlStateManager.enableBlend();
            } else {
                GlStateManager.disableBlend();
            }
            if (lineSmoothingEnabled) {
                GlStateManager.r();
            } else {
                GlStateManager.P();
            }
            RenderUtils.f();
            RenderUtil.Y();
        }
    }

    @EventHandler
    public void onPostTick(EventPostTick eventPostTick) {
        WorldClient world = eventPostTick.getWorld();
        EntityPlayerSP player = eventPostTick.getThePlayer();
        if (this.pendingRemoveEntityId != -1 && world.isNotNull()) {
            Entity pendingEntity = ((World)world).V(this.pendingRemoveEntityId);
            if (pendingEntity.isNotNull()) {
                world.M(pendingEntity);
            }
            this.pendingRemoveEntityId = -1;
        }
        if (world.isNull() || player.isNull() || this.state == CrystalAuraActionState.IDLE || this.targetObsidian == null) {
            return;
        }
        if (!this.rotationClaim.isOwnedBy(this.crystalAura) && !this.rotationClaim.acquire(this.crystalAura, true)) {
            return;
        }
        Vec3 eyePosition = player.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk().addVector(0.0, player.X(), 0.0);
        if (this.state == CrystalAuraActionState.BREAKING_CRYSTAL && this.trackedCrystal != null && this.trackedCrystal.isNotNull() && !this.trackedCrystal.M$src$Z$ff28xj()) {
            Vec3 crystalHitPoint = RotationUtil.M(eyePosition, this.trackedCrystal.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl(), 0.0, 0.0, 0.0).toVec3();
            this.aimAt(eyePosition, crystalHitPoint);
        } else if (this.state == CrystalAuraActionState.BREAKING_CRYSTAL) {
            BlockData baseBlock = this.targetObsidian;
            Vec3 expectedCrystalPosition = Vec3.create((double)baseBlock.D() + 0.5, baseBlock.B() + 1, (double)baseBlock.G() + 0.5);
            this.aimAt(eyePosition, expectedCrystalPosition);
        } else if (this.state == CrystalAuraActionState.PLACING_CRYSTAL) {
            BlockData baseBlock = this.targetObsidian;
            float managedYaw = this.rotationManager.getManagedYaw();
            float managedPitch = this.rotationManager.getManagedPitch();
            Vec3 hitPoint = this.computeCrystalHitVec(player, world, eyePosition, baseBlock, managedYaw, managedPitch);
            if (hitPoint == null) {
                hitPoint = Vec3.create((double)baseBlock.D() + 0.5, baseBlock.B() + 1, (double)baseBlock.G() + 0.5);
            }
            this.aimAt(eyePosition, hitPoint);
        } else if (this.state == CrystalAuraActionState.PLACING_OBSIDIAN && this.obsidianSupport != null && this.obsidianFacing != null) {
            PlacementTarget placementTarget = new PlacementTarget(this.obsidianSupport, this.obsidianFacing);
            float managedYaw = this.rotationManager.getManagedYaw();
            float managedPitch = this.rotationManager.getManagedPitch();
            Vec3 hitPoint = ClutchPlacementPathUtils.findBestPlacementHitPoint(player, world, eyePosition, placementTarget, managedYaw, managedPitch);
            if (hitPoint == null) {
                hitPoint = Vec3.create((double)this.obsidianSupport.D() + 0.5 + (double)this.obsidianFacing.getDirectionVector().getX() * 0.5, (double)this.obsidianSupport.B() + 0.5 + (double)this.obsidianFacing.getDirectionVector().getY() * 0.5, (double)this.obsidianSupport.G() + 0.5 + (double)this.obsidianFacing.getDirectionVector().getZ() * 0.5);
            }
            this.aimAt(eyePosition, hitPoint);
        }
    }

    private void handleBreakingCrystal(EntityPlayerSP player, World world) {
        if (this.trackedCrystal == null || this.trackedCrystal.isNull() || this.trackedCrystal.M$src$Z$ff28xj()) {
            this.trackedCrystal = null;
            Entity crystal = this.findCrystalAbove(world, this.targetObsidian);
            if (crystal != null) {
                this.trackedCrystal = crystal;
            } else {
                this.setState(CrystalAuraActionState.PLACING_CRYSTAL, "crystal missing above obsidian");
                this.actionTimer.reset();
                return;
            }
        }
        Vec3 crystalPosition = this.trackedCrystal.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk();
        if (!this.crystalAura.isSelfDamageSafe(crystalPosition, ExplosionType.CRYSTAL, player, world, this.antiSuicide.getEffectiveValue(), ((Double)this.maxSelfDamage.getValue()).floatValue())) {
            this.clearTracking("unsafe self damage while breaking crystal");
            return;
        }
        if (!this.rotationClaim.isOwnedBy(this.crystalAura) && !this.rotationClaim.acquire(this.crystalAura, true)) {
            return;
        }
        KeyBinding useItemKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
        if (useItemKey.u() || useItemKey.isPressed()) {
            KeyBinding.setKeyBindState(useItemKey, false);
        }
        if (this.rotationController != null && this.rotationManager.getActiveController() == this.rotationController) {
            RayTraceResult crosshairHit = this.rotationManager.getNormalReachRayTrace();
            Entity hitEntity = crosshairHit.getEntity();
            if (crosshairHit.getTypeOfHit().equals(RayTraceResult_type.entity()) && hitEntity.isInstance(MappedClasses.Ze)) {
                KeyBinding attackKey = Minecraft.gameSettings().F();
                if (attackKey.u() || attackKey.isPressed()) {
                    KeyBinding.setKeyBindState(attackKey, false);
                }
                if (((ModeSelection)this.optimizationMode.getValue()).equals(this.rapidFireMode)) {
                    KeyBinding.setKeyBindState(useItemKey, true);
                    KeyBinding.onTick(useItemKey);
                    KeyBinding.setKeyBindState(useItemKey, false);
                }
                KeyBinding.setKeyBindState(attackKey, true);
                KeyBinding.onTick(attackKey);
                KeyBinding.setKeyBindState(attackKey, false);
            }
        }
    }

    private boolean canPlaceCrystalAbove(World world, BlockData baseBlock) {
        if (baseBlock == null) {
            return false;
        }
        Block blockAbove = world.getBlockByPos(baseBlock.D(), baseBlock.B() + 1, baseBlock.G());
        Block secondBlockAbove = world.getBlockByPos(baseBlock.D(), baseBlock.B() + 2, baseBlock.G());
        if (!BlockUtil.u(blockAbove) || !BlockUtil.u(secondBlockAbove)) {
            return false;
        }
        AxisAlignedBB placementBox = AxisAlignedBB.create(baseBlock.D(), baseBlock.B() + 1, baseBlock.G(), (double)baseBlock.D() + 1.0, (double)baseBlock.B() + 3.0, (double)baseBlock.G() + 1.0);
        boolean[] collisionFound = new boolean[]{false};
        world.getEntityGetter().forEachEntityInBounds(placementBox, entityObject -> CrystalAuraTargetSubModule.markCollidingEntity(collisionFound, entityObject));
        return !collisionFound[0];
    }


    private boolean isLookingAtObsidian(RayTraceResult crosshairHit, World world, BlockData baseBlock) {
        if (crosshairHit == null || crosshairHit.isNull() || baseBlock == null) {
            return false;
        }
        if (!crosshairHit.getTypeOfHit().equals(RayTraceResult_type.block())) {
            return false;
        }
        BlockPos hitBlock = crosshairHit.getBlockPos();
        if (hitBlock == null) {
            return false;
        }
        if (hitBlock.getX() != baseBlock.D() || hitBlock.getY() != baseBlock.B() || hitBlock.getZ() != baseBlock.G()) {
            return false;
        }
        return this.crystalAura.isCrystalBaseBlock(world.getBlockState(hitBlock));
    }

    static {
        DEFAULT_COLOR = new Color(255, 0, 0, 25);
        SAFE_COLOR = new Color(0, 255, 255, 25);
        PLACING_COLOR = new Color(0, 0, 255, 25);
    }

    public CrystalAuraTargetSubModule(Mod parent, String name) {
        super(parent, name);
        this.maxSelfDamage = NumberValue.create(this, "Max self damage", "#", "HP", 0.0, 19.0, 20.0, 1.0, "Maximum self damage allowed");
        this.delay = RandomValue.createWithDescription(this, "Delay", "#", "ms", 0.0, 0.0, 100.0, 500.0, 1.0, "Delay between break/place cycles");
        this.rapidFireMode = new ModeOption("Rapid fire");
        this.predictMode = new ModeOption("Predict");
        this.noneMode = new ModeOption("None");
        this.optimizationMode = ModeValue.create((Object)this, "Optimization", "Controls crystal optimization behavior\nNone - No crystal optimization\nRapid fire - Crystals are broken and replaced in same tick when possible\nPredict - Predicts explosion timing and pre-removes crystal for faster placement(potentially unsafe)", (ModeSelection)this.noneMode, this.rapidFireMode, this.predictMode, this.noneMode);
        this.placeObsidian = BooleanValue.create(this, "Place obsidian", false, "Automatically places obsidian from hotbar when hovering a valid placement surface");
        this.showTargetBlock = BooleanValue.create(this, "Show target block", true, "Renders a highlight on the target obsidian block");
        this.rotationManager = RotationManager.INSTANCE;
        this.rotationClaim = SharedModuleControlClaims.rotation;
        this.state = CrystalAuraActionState.IDLE;
        this.actionTimer = new TimerUtil();
        this.stateTimer = new TimerUtil();
        this.crystalAura = (CrystalAura)parent;
        this.addValue(this.aimSpeed, this.antiSuicide, this.maxSelfDamage, this.delay, this.optimizationMode, this.placeObsidian, this.showTargetBlock);
    }

    @Override
    public void onDisable() {
        this.releaseRotation();
        this.resetState();
    }

    private void resetObsidianState() {
        this.obsidianSlot = -1;
        this.obsidianSupport = null;
        this.obsidianFacing = null;
        this.obsidianPlaced = false;
    }

    private boolean isHoldingCrystal(EntityPlayerSP player) {
        return player.isNotNull() && this.crystalAura.isEndCrystal(player.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt());
    }

    @Override
    public void onEnable() {
        this.resetState();
    }

    private RayTraceResult rayTraceScreenCenter(EntityPlayerSP player, World world) {
        EntityLivingBase viewEntity = Minecraft.F();
        if (viewEntity.isNull()) {
            return new RayTraceResult(null);
        }
        float yaw = viewEntity.J();
        float pitch = viewEntity.V();
        Vec3 eyePosition = Vec3.create(player.z(), player.N() + (double)player.X(), player.h());
        float yawCosine = MathUtil.cos(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
        float yawSine = MathUtil.sin(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
        float pitchCosine = -MathUtil.cos(-pitch * ((float)Math.PI / 180));
        float pitchSine = MathUtil.sin(-pitch * ((float)Math.PI / 180));
        float directionX = yawSine * pitchCosine;
        float directionY = pitchSine;
        float directionZ = yawCosine * pitchCosine;
        double reach = 5.0;
        Vec3 endPosition = eyePosition.addVector((double)directionX * reach, (double)directionY * reach, (double)directionZ * reach);
        return world.K(eyePosition, endPosition, false, true, false, player);
    }
}
