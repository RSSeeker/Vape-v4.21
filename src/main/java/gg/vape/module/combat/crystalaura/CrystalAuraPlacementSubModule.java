package gg.vape.module.combat.crystalaura;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventRender3D;
import gg.vape.event.impl.EventWorldChange;
import gg.vape.friend.FriendEntry;
import gg.vape.manager.client.FriendManager;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.module.ModuleDisplayInfo;
import gg.vape.module.SubModule;
import gg.vape.module.blatant.blockin.BlockPathPlanner;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.module.combat.CrystalAura;
import gg.vape.module.control.MouseOverUpdateControlClaim;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.module.utility.clutch.ClutchPlacementPathUtils;
import gg.vape.module.utility.clutch.PlacementTarget;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.RotationAngles;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.EntityAngleComparator;
import gg.vape.utils.EntityDistanceComparator;
import gg.vape.utils.EntityEquipmentValueComparator;
import gg.vape.utils.EntityHealthComparator;
import gg.vape.utils.MathUtil;
import gg.vape.utils.MutableColor;
import gg.vape.utils.RayTraceUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.RotationVectorMath;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.utils.datas.BlockData;
import gg.vape.utils.datas.DirectionalPosition;
import gg.vape.utils.math.NumericMathUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.EntityTargetFilterValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.CPacketPlayerBlockPlacement;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.ItemStack;
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
import java.util.List;
import org.lwjgl.opengl.GL11;

public class CrystalAuraPlacementSubModule
extends SubModule<CrystalAura> {
    private final NumberValue range;
    private FixedRotationController rotationController;
    private final ModeOption distanceMode;
    private final RotationControlClaim rotationClaim;
    public final ModeValue targetMode;
    private final BooleanValue predictAttackVelocity;
    private final ModeValue optimizationMode;
    private final RotationManager rotationManager;
    private final BooleanValue antiSuicide;
    private int crystalSlot = -1;
    private EntityLivingBase lastTarget;
    private CrystalAuraActionCandidate currentAction;
    public final ModeOption yawMode;
    private final MouseOverUpdateControlClaim mouseOverClaim;
    private final ColorValue attackColor;
    private static final Color OBSIDIAN_COLOR;
    private int pendingRemoveEntityId = -1;
    private final NumberValue rapidMinEfficiency;
    private CrystalAura crystalAura;
    public final EntityTargetFilterValue targetFilter;
    private final TimerUtil delayTimer;
    private final NumberValue aimSpeed = NumberValue.createWithDescription(this, "Aim speed", "#.#", "", 1.0, 4.5, 10.0, "Aim rotation speed");
    private int previousSlot = -1;
    private final ColorValue targetColor;
    private final ModeOption predictMode;
    private long lastTargetTime;
    private final BooleanValue showTarget;
    private final ModeOption noneMode;
    private final FriendManager friendManager;
    public final ModeOption healthMode;
    private final RandomValue activationDelay;
    private static final Color ATTACK_COLOR;
    private final BooleanValue centerScreen;
    private static final Color PLACE_CRYSTAL_COLOR;
    private boolean placementSent;
    private final ModeOption rapidFireMode;
    private final BooleanValue autoObsidian;
    private boolean toggledOff;
    private EntityLivingBase target;
    public final ModeOption armorMode;
    private boolean attackedLastTick;
    private final NumberValue minEfficiency;
    public final NumberValue maxAngle;
    private final NumberValue maxSelfDamage;
    private static final long TARGET_MEMORY_MS = 1200L;
    private String statusLabel;
    private boolean lowEfficiency;
    private boolean attacked;

    private void invalidateAction() {
        this.currentAction = null;
        this.placementSent = false;
    }

    private void clickUseItem() {
        KeyBinding attackKey = Minecraft.gameSettings().F();
        if (attackKey.u() || attackKey.isPressed()) {
            KeyBinding.setKeyBindState(attackKey, false);
        }
        KeyBinding useItemKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
        if (useItemKey.u() || useItemKey.isPressed()) {
            KeyBinding.setKeyBindState(useItemKey, false);
        }
        KeyBinding.setKeyBindState(useItemKey, true);
        KeyBinding.onTick(useItemKey);
        KeyBinding.setKeyBindState(useItemKey, false);
    }

    private boolean hasPlacementPath(EntityPlayerSP player, World world, Vec3 eyePosition, DirectionalPosition actionPosition) {
        BlockData baseBlock = new BlockData(actionPosition.B(), actionPosition.E(), actionPosition.A());
        EnumFacing facing = EnumFacing.T(actionPosition.getFacingIndex());
        PlacementTarget placementTarget = new PlacementTarget(baseBlock, facing);
        return ClutchPlacementPathUtils.isBlockFaceVisible(eyePosition, world, baseBlock, facing)
                && ClutchPlacementPathUtils.findBestPlacementHitPoint(player, world, eyePosition, placementTarget,
                this.rotationManager.getManagedYaw(), this.rotationManager.getManagedPitch()) != null;
    }

    private boolean hasComputedPlacementHit(EntityPlayerSP player, World world, Vec3 eyePosition, DirectionalPosition actionPosition) {
        BlockData baseBlock = new BlockData(actionPosition.B(), actionPosition.E(), actionPosition.A());
        PlacementTarget placementTarget = new PlacementTarget(baseBlock, EnumFacing.T(actionPosition.getFacingIndex()));
        return ClutchPlacementPathUtils.findBestPlacementHitPoint(player, world, eyePosition, placementTarget,
                this.rotationManager.getManagedYaw(), this.rotationManager.getManagedPitch()) != null;
    }

    private void validateAction(EntityPlayerSP player, World world) {
        if (this.currentAction == null || this.rotationController == null || this.rotationManager.getActiveController() != this.rotationController) {
            return;
        }
        RayTraceResult crosshairHit = this.rotationManager.getNormalReachRayTrace();
        CrystalAuraAction action = this.currentAction.getAction();
        DirectionalPosition actionPosition = this.currentAction.directionalPosition;
        Vec3 eyePosition = player.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk().addVector(0.0, player.X(), 0.0);
        Vec3 interactionPoint = this.crystalAura.getInteractionPoint(actionPosition);
        if (eyePosition.distanceTo(interactionPoint) > 4.0) {
            Vec3 currentPlayerPosition = Vec3.create(player.z(), interactionPoint.getY(), player.h());
            Vec3 previousPlayerPosition = Vec3.create(player.M(), interactionPoint.getY(), player.m$src$D$fwnne5());
            double currentDistance = currentPlayerPosition.distanceTo(interactionPoint);
            if (currentDistance >= previousPlayerPosition.distanceTo(interactionPoint) || currentDistance > 4.5) {
                this.invalidateAction();
            }
            return;
        }

        if (action == CrystalAuraAction.PLACING_OBSIDIAN) {
            AxisAlignedBB placementBox = AxisAlignedBB.create(actionPosition.B(), actionPosition.E() + 1, actionPosition.A(),
                    (double)actionPosition.B() + 1.0, (double)actionPosition.E() + 2.0, (double)actionPosition.A() + 1.0);
            ArrayList<Entity> entities = new ArrayList<Entity>();
            world.getEntityGetter().forEachEntityInBounds(placementBox, entityObject -> CrystalAuraPlacementSubModule.collectEntities(entities, entityObject));
            for (Entity entity : entities) {
                if (entity.n$src$Z$fx7gig() || entity.isInstance(MappedClasses.Ze)) {
                    this.invalidateAction();
                    return;
                }
            }
            if (crosshairHit.getTypeOfHit().equals(RayTraceResult_type.block())) {
                BlockPos hitPosition = crosshairHit.getBlockPos();
                DirectionalPosition hitSide = new DirectionalPosition(hitPosition.getX(), hitPosition.getY(), hitPosition.getZ(), crosshairHit.Z());
                Block hitBlock = crosshairHit.Z$src$Lgg_vape_wrapper_impl_Block_$6x2c9a();
                boolean replaceableHit = BlockUtil.u(hitBlock) && !BlockUtil.J(hitBlock);
                EnumFacing facing = EnumFacing.T(crosshairHit.Z());
                BlockData adjacentBlock = new BlockData(hitPosition.getX(), hitPosition.getY(), hitPosition.getZ()).R(facing.getOpposite());
                DirectionalPosition adjacentSide = new DirectionalPosition(adjacentBlock.D(), adjacentBlock.B(), adjacentBlock.G(), facing.Y());
                if (hitSide.equals(actionPosition) || replaceableHit && adjacentSide.equals(actionPosition)) {
                    this.clickUseItem();
                } else if (!ClutchPlacementPathUtils.isBlockFaceVisible(eyePosition, world,
                        new BlockData(actionPosition.B(), actionPosition.E(), actionPosition.A()), EnumFacing.T(actionPosition.getFacingIndex()))
                        && !this.hasComputedPlacementHit(player, world, eyePosition, actionPosition)) {
                    this.invalidateAction();
                }
            }
            return;
        }

        if (action == CrystalAuraAction.PLACING_CRYSTAL) {
            AxisAlignedBB crystalBox = AxisAlignedBB.create(actionPosition.B(), actionPosition.E(), actionPosition.A(),
                    (double)actionPosition.B() + 1.0, (double)actionPosition.E() + 2.0, (double)actionPosition.A() + 1.0);
            ArrayList<Entity> entities = new ArrayList<Entity>();
            world.getEntityGetter().forEachEntityInBounds(crystalBox, entityObject -> CrystalAuraPlacementSubModule.collectEntities(entities, entityObject));
            if (!entities.isEmpty() && this.wouldIntersect(this.target, actionPosition, player, world)) {
                Entity crystal = null;
                for (Entity entity : entities) {
                    if (entity.isInstance(MappedClasses.Ze)) {
                        crystal = entity;
                    }
                }
                if (crystal != null && entities.size() == 1) {
                    this.currentAction.setAction(CrystalAuraAction.ATTACKING_CRYSTAL);
                } else {
                    this.invalidateAction();
                }
                return;
            }
            if (crosshairHit.getTypeOfHit().equals(RayTraceResult_type.block())) {
                BlockPos hitPosition = crosshairHit.getBlockPos();
                DirectionalPosition hitSide = new DirectionalPosition(hitPosition.getX(), hitPosition.getY(), hitPosition.getZ(), crosshairHit.Z());
                if (actionPosition.equals(hitSide)) {
                    this.clickUseItem();
                } else if (!this.hasPlacementPath(player, world, eyePosition, actionPosition)) {
                    this.invalidateAction();
                }
            } else if (crosshairHit.isEntityHit() && crosshairHit.getEntity().isInstance(MappedClasses.Ze)) {
                this.currentAction.setAction(CrystalAuraAction.ATTACKING_CRYSTAL);
                action = CrystalAuraAction.ATTACKING_CRYSTAL;
            } else if (!this.hasPlacementPath(player, world, eyePosition, actionPosition)) {
                this.invalidateAction();
            }
        }

        if (action == CrystalAuraAction.ATTACKING_CRYSTAL) {
            if (!this.isAlive(this.target)) {
                this.invalidateAction();
                return;
            }
            if (crosshairHit.getTypeOfHit().equals(RayTraceResult_type.entity()) && crosshairHit.getEntity().isInstance(MappedClasses.Ze)) {
                KeyBinding attackKey = Minecraft.gameSettings().F();
                if (attackKey.u() || attackKey.isPressed()) {
                    KeyBinding.setKeyBindState(attackKey, false);
                }
                KeyBinding useItemKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
                if (((ModeSelection)this.optimizationMode.getValue()).equals(this.rapidFireMode)
                        && this.currentAction.getDamage() >= (Double)this.rapidMinEfficiency.getValue() / 100.0) {
                    KeyBinding.setKeyBindState(useItemKey, true);
                    KeyBinding.onTick(useItemKey);
                }
                if (useItemKey.u() || useItemKey.isPressed()) {
                    KeyBinding.setKeyBindState(useItemKey, false);
                }
                KeyBinding.setKeyBindState(attackKey, true);
                KeyBinding.onTick(attackKey);
                KeyBinding.setKeyBindState(attackKey, false);
            }
        }
    }

    private CrystalAuraActionCandidate findAction(EntityPlayerSP player, World world, double searchRange, boolean includeObsidian) {
        int playerY = MathUtil.floor(player.N());
        CrystalAuraActionCandidate currentLevelAction = this.findActionAtYLevel(player, world, searchRange, includeObsidian, playerY);
        if (currentLevelAction != null) {
            return currentLevelAction;
        }
        String currentLevelStatus = this.statusLabel;
        CrystalAuraActionCandidate upperLevelAction = this.findActionAtYLevel(player, world, searchRange, includeObsidian, playerY + 1);
        if (upperLevelAction != null) {
            return upperLevelAction;
        }
        if (this.statusPriority(currentLevelStatus) > this.statusPriority(this.statusLabel)) {
            this.statusLabel = currentLevelStatus;
        }
        return null;
    }

    static {
        ATTACK_COLOR = new Color(255, 0, 0, 25);
        PLACE_CRYSTAL_COLOR = new Color(0, 255, 255, 25);
        OBSIDIAN_COLOR = new Color(0, 0, 255, 25);
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
            if (UseEntityPacketBridge.isUseEntityPacket(packet)) {
                UseEntityPacketBridge useEntityPacket = new UseEntityPacketBridge(packet.getObject());
                if (useEntityPacket.isAttack()) {
                    Entity attackedEntity = useEntityPacket.getEntity(world);
                    if (this.predictAttackVelocity.getEffectiveValue().booleanValue() && attackedEntity.isNotNull() && attackedEntity.isInstance(MappedClasses.zm) && attackedEntity.V$src$I$fk0dv5() <= 13) {
                        this.attacked = true;
                    }
                    if (this.currentAction != null && this.currentAction.getAction() == CrystalAuraAction.ATTACKING_CRYSTAL && attackedEntity.isNotNull() && attackedEntity.isInstance(MappedClasses.Ze)) {
                        if (((ModeSelection)this.optimizationMode.getValue()).equals(this.predictMode) && !Minecraft.V()) {
                            this.pendingRemoveEntityId = attackedEntity.S();
                        }
                        this.currentAction = null;
                        this.placementSent = false;
                    }
                }
            }
            if (packet.isInstance(MappedClasses.YB)) {
                if (this.currentAction != null) {
                    CrystalAuraAction action = this.currentAction.getAction();
                    if (action == CrystalAuraAction.PLACING_OBSIDIAN || action == CrystalAuraAction.PLACING_CRYSTAL) {
                        this.placementSent = true;
                    }
                }
            }
            return;
        }
        if (player.isNull()) {
            return;
        }
        Packet packet = eventPacketSend.getPacket();
        if (UseEntityPacketBridge.isUseEntityPacket(packet) && this.currentAction != null) {
            CPacketPlayerBlockPlacement placementPacket = new CPacketPlayerBlockPlacement(packet.getObject());
            if (this.currentAction.getAction() == CrystalAuraAction.PLACING_OBSIDIAN) {
                this.placementSent = true;
            }
        }
    }

    private float computeDamageEfficiency(EntityLivingBase target, ExplosionType explosionType, Vec3 explosionPosition, EntityPlayerSP player, World world) {
        EntityLivingBase predictedTarget;
        if (target.isInstance(MappedClasses.Yl)) {
            EntityPlayer targetPlayer = new EntityPlayer(target);
            BlockPathPlanner pathPlanner = new BlockPathPlanner(targetPlayer, player, world, new BlockPlacementGraph(player));
            predictedTarget = pathPlanner.getSimulatedPlayer();
            pathPlanner.clearInput();
            pathPlanner.finishSimulation();
            double motionX = predictedTarget.z() - predictedTarget.M();
            double motionY = predictedTarget.N() - predictedTarget.W();
            double motionZ = predictedTarget.h() - predictedTarget.m$src$D$fwnne5();
            if (this.predictAttackVelocity.getEffectiveValue().booleanValue() && this.attacked && target.equals(this.target)) {
                float attackVerticalVelocity = 0.4f;
                motionY = target.b$src$Z$fqlxe4() ? Math.min(0.4, motionY / 2.0 + (double)attackVerticalVelocity) : (double)attackVerticalVelocity;
            }
            predictedTarget.F(motionX, motionY, motionZ);
            for (int predictionTick = 0; predictionTick < 3; ++predictionTick) {
                pathPlanner.simulateTick();
            }
        } else {
            predictedTarget = target;
        }

        float explosionDiameter = explosionType.getExplosionPower() * 2.0f;
        double explosionX = explosionPosition.getX();
        double explosionY = explosionPosition.getY();
        double explosionZ = explosionPosition.getZ();
        int minimumX = NumericMathUtil.floorDouble(explosionX - (double)explosionDiameter - 1.0);
        int maximumX = NumericMathUtil.floorDouble(explosionX + (double)explosionDiameter + 1.0);
        int minimumY = NumericMathUtil.floorDouble(explosionY - (double)explosionDiameter - 1.0);
        int maximumY = NumericMathUtil.floorDouble(explosionY + (double)explosionDiameter + 1.0);
        int minimumZ = NumericMathUtil.floorDouble(explosionZ - (double)explosionDiameter - 1.0);
        int maximumZ = NumericMathUtil.floorDouble(explosionZ + (double)explosionDiameter + 1.0);
        boolean insideExplosionBounds = MathUtil.e(predictedTarget.z(), (double)minimumX, (double)maximumX)
                && MathUtil.e(predictedTarget.N(), (double)minimumY, (double)maximumY)
                && MathUtil.e(predictedTarget.h(), (double)minimumZ, (double)maximumZ);
        if (insideExplosionBounds) {
            double normalizedDistance = ForgeVersion.MC_1_16_5.d()
                    ? Math.sqrt(predictedTarget.g(explosionPosition)) / (double)explosionDiameter
                    : predictedTarget.i(explosionX, explosionY, explosionZ) / (double)explosionDiameter;
            if (normalizedDistance <= 1.0) {
                float blockDensity = CrystalAura.calculateBlockDensity(explosionPosition, predictedTarget, world);
                double damageScale = ForgeVersion.MC_1_12_2.d() ? 7.0 : 8.0;
                float maximumDamage = (float)(damageScale * (double)explosionDiameter + 1.0);
                double impact = (1.0 - normalizedDistance) * (double)blockDensity;
                float damage = (float)((impact * impact + impact) / 2.0 * damageScale * (double)explosionDiameter + 1.0);
                return damage / maximumDamage;
            }
        }
        return -1.0f;
    }

    private void updateTarget(EntityPlayerSP player, World world) {
        ArrayList<EntityLivingBase> candidates = new ArrayList<EntityLivingBase>();
        if (this.crystalAura.shouldPause()) {
            return;
        }
        List<?> loadedEntities = world.z();
        for (Object underlyingEntity : loadedEntities) {
            Entity entity = new Entity(underlyingEntity);
            EntityLivingBase candidate;
            if (ClientSettings.IS_LEGACY_1_7 && entity.isInstance(MappedClasses.FT)
                    || !entity.isInstance(MappedClasses.zm)
                    || !this.isValidTarget(candidate = new EntityLivingBase(underlyingEntity), player)) {
                continue;
            }
            candidates.add(candidate);
        }
        if (this.targetMode.getValue() == this.yawMode) {
            candidates.sort(new EntityAngleComparator());
        } else if (this.targetMode.getValue() == this.distanceMode) {
            candidates.sort(new EntityDistanceComparator());
        } else if (this.targetMode.getValue() == this.armorMode) {
            candidates.sort(new EntityEquipmentValueComparator());
        } else if (this.targetMode.getValue() == this.healthMode) {
            candidates.sort(new EntityHealthComparator());
        }
        if (!candidates.isEmpty()) {
            EntityLivingBase selectedTarget = candidates.get(0);
            this.target = selectedTarget;
            this.rememberTarget(selectedTarget);
        } else {
            this.reset();
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public ModuleDisplayInfo getModuleDisplayInfo() {
        if (!this.centerScreen.getEffectiveValue().booleanValue()) {
            return null;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return null;
        }
        int crystalCount = this.crystalAura.countCrystalsInHotbar(player);
        Color countColor = new Color(255, 20, 20);
        if (crystalCount >= 32) {
            countColor = new Color(2, 190, 58);
        } else if (crystalCount >= 16) {
            countColor = new Color(255, 249, 18);
        }
        String moduleColor = this.currentAction != null ? "\u00a7f\u00a7l" : "\u00a77";
        String countText = (this.currentAction == null ? "\u00a7r" : "\u00a75\u00a7l") + crystalCount;
        if (this.currentAction == null && this.statusLabel != null && ("no target".equals(this.statusLabel) || "no crystals".equals(this.statusLabel) || this.getActiveTarget() != null)) {
            countText = countText + " \u00a7c[" + this.statusLabel + "]";
        }
        String suffix = " " + moduleColor + "(CrystalAura)";
        return new ModuleDisplayInfo(countText, countColor, suffix);
    }

    private void reset() {
        this.rememberTarget(this.target);
        this.target = null;
        this.currentAction = null;
        this.placementSent = false;
        this.attackedLastTick = false;
        this.attacked = false;
        this.delayTimer.reset();
        if (this.rotationController != null) {
            this.rotationController.setClampStepToRemaining(true);
            this.rotationController.setCubicAcceleration(true);
            this.rotationController.setScaleAxesProportionally(true);
            this.rotationController.setTolerance(0.0f);
            this.rotationController.setSpeed(5.0f);
            RotationManager.INSTANCE.releaseController(this.rotationController);
        }
        if (this.previousSlot != -1) {
            EntityPlayerSP player = Minecraft.thePlayer();
            if (!player.isNull()) {
                player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.previousSlot);
            }
            this.previousSlot = -1;
        }
        if (this.rotationManager.getActiveController() == null || this.rotationManager.getActiveController() != this.rotationController || this.rotationController != null && !this.rotationController.shouldRetainAfterCompletion() && this.rotationController.isComplete()) {
            this.rotationController = null;
            this.rotationClaim.release(this.crystalAura);
            if (this.toggledOff) {
                this.toggledOff = false;
                this.crystalAura.setEnabled(false, true);
            }
        }
    }

    @Override
    public String getDetailedSuffix() {
        if (this.currentAction != null) {
            return this.currentAction.getAction().toString();
        }
        return "";
    }

    private static void collectCrystalsForAim(ArrayList<Entity> crystals, Object entityObject) {
        if (MappedClasses.Ze.isAssignableFrom(entityObject.getClass())) {
            crystals.add(new Entity(entityObject));
        }
    }

    private static void collectCrystalsAtPosition(ArrayList<Entity> crystals, Object entityObject) {
        if (MappedClasses.Ze.isAssignableFrom(entityObject.getClass())) {
            crystals.add(new Entity(entityObject));
        }
    }

    private int statusPriority(String status) {
        if (status == null) {
            return 0;
        }
        if ("low eff".equals(status)) {
            return 6;
        }
        if ("blocked".equals(status) || "out range".equals(status) || "no place".equals(status)) {
            return 5;
        }
        if ("no candidate".equals(status)) {
            return 4;
        }
        if ("no placement block".equals(status)) {
            return 3;
        }
        if ("no target".equals(status)) {
            return 2;
        }
        return 1;
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        WorldClient world = eventPacketReceive.getWorld();
        EntityPlayerSP player = eventPacketReceive.getThePlayer();
        if (world.isNull() || player.isNull()) {
            return;
        }
    }

    private boolean isInRange(EntityLivingBase target, EntityPlayerSP player) {
        return (double)target.getDistanceToEntity(player) <= (Double)this.range.getValue();
    }

    private boolean isAlive(EntityLivingBase entity) {
        return entity != null && entity.isNotNull() && !entity.M$src$Z$ff28xj() && entity.w$src$F$15l9epb() > 0.0f;
    }

    private static void collectEntities(ArrayList<Entity> entities, Object entityObject) {
        entities.add(new Entity(entityObject));
    }

    public boolean isValidTarget(EntityLivingBase target, EntityPlayerSP player) {
        if (target.isNull()) {
            return false;
        }
        if (target.equals(player)) {
            return false;
        }
        if (target.w$src$F$15l9epb() <= 0.0f || target.M$src$Z$ff28xj()) {
            return false;
        }
        if (!this.isInRange(target, player)) {
            return false;
        }
        if (RotationUtil.a(player, target) > ((Double)this.maxAngle.getValue()).intValue() / 2) {
            return false;
        }
        FriendEntry friendEntry = this.friendManager.findTargetedFriend(target.getName());
        if (friendEntry != null && !friendEntry.isTargeted()) {
            return false;
        }
        if (target.equals(player.S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12())) {
            return false;
        }
        return this.targetFilter.isValidTarget(target);
    }

    private static void collectCrystals(ArrayList<Entity> crystals, Object entityObject) {
        if (MappedClasses.Ze.isAssignableFrom(entityObject.getClass())) {
            crystals.add(new Entity(entityObject));
        }
    }

    @EventHandler
    public void onWorldChange(EventWorldChange eventWorldChange) {
        this.releaseControl();
        this.target = null;
        this.lastTarget = null;
        this.lastTargetTime = 0L;
        this.lowEfficiency = false;
        this.statusLabel = null;
        this.currentAction = null;
        this.placementSent = false;
    }

    private AxisAlignedBB crystalBoundingBox(BlockCoordinate position) {
        double minimumX = position.B();
        double minimumY = position.E() + 1;
        double minimumZ = position.A();
        return AxisAlignedBB.create(minimumX, minimumY, minimumZ, minimumX + 1.0, minimumY + 2.0, minimumZ + 1.0);
    }

    private EntityLivingBase getActiveTarget() {
        EntityLivingBase activeTarget;
        if (this.isAlive(this.target)) {
            this.rememberTarget(this.target);
            activeTarget = this.target;
        } else {
            activeTarget = this.isAlive(this.lastTarget) && System.currentTimeMillis() - this.lastTargetTime <= TARGET_MEMORY_MS ? this.lastTarget : null;
        }
        if (activeTarget == null) {
            this.lastTarget = null;
        }
        return activeTarget;
    }

    private static void collectCollidingEntities(ArrayList<Entity> entities, Object entityObject) {
        Entity entity = new Entity(entityObject);
        if (entity.n$src$Z$fx7gig()) {
            entities.add(entity);
        }
    }

    @Override
    public void onDisable() {
        this.releaseControl();
    }

    @EventHandler
    public void onRender3D(EventRender3D eventRender3D) {
        EntityLivingBase activeTarget = this.getActiveTarget();
        boolean blendEnabled = GL11.glIsEnabled((int)3042);
        boolean lineSmoothingEnabled = GL11.glIsEnabled((int)2848);
        boolean depthTestEnabled = GL11.glIsEnabled((int)2929);
        boolean depthMaskEnabled = GL11.glGetBoolean((int)2930);
        try {
            RenderUtil.d();
            RenderUtils.g();
            GlStateManager.enableBlend();
            OpenGlBackendHolder.backend.enableCapability(3042);
            GL11.glBlendFunc((int)770, (int)771);
            GlStateManager.disableTexture2D();
            GlStateManager.r();
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            double renderX = RenderManager.getInterpolatedRenderPosX();
            double renderY = RenderManager.getInterpolatedRenderPosY();
            double renderZ = RenderManager.getInterpolatedRenderPosZ();
            if (this.currentAction != null && this.currentAction.directionalPosition != null) {
                Color color = this.currentAction.getAction() == CrystalAuraAction.ATTACKING_CRYSTAL ? ATTACK_COLOR : (this.currentAction.getAction() == CrystalAuraAction.PLACING_CRYSTAL ? PLACE_CRYSTAL_COLOR : OBSIDIAN_COLOR);
                try {
                    RenderUtil.w(renderX, renderY, renderZ, this.currentAction.directionalPosition.B(), this.currentAction.directionalPosition.E(), this.currentAction.directionalPosition.A(), color);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        finally {
            if (blendEnabled) {
                GlStateManager.enableBlend();
            } else {
                GlStateManager.disableBlend();
            }
            if (depthTestEnabled) {
                GlStateManager.enableDepth();
            } else {
                GlStateManager.disableDepth();
            }
            if (lineSmoothingEnabled) {
                GlStateManager.r();
            } else {
                GlStateManager.P();
            }
            GlStateManager.depthMask(depthMaskEnabled);
            RenderUtils.f();
            RenderUtil.Y();
        }
        if (this.showTarget.getEffectiveValue().booleanValue() && activeTarget != null && Minecraft.currentScreen().isNull()) {
            float ringRadius = activeTarget.isInstance(MappedClasses.Yl) || activeTarget.isInstance(MappedClasses.lG) ? 0.7f : activeTarget.f$src$F$fst3ac();
            MutableColor mutableColor = this.currentAction != null && this.currentAction.getAction() == CrystalAuraAction.ATTACKING_CRYSTAL ? this.attackColor.getMutableColor() : this.targetColor.getMutableColor();
            GuiRenderPrimitives.R(activeTarget.c(), activeTarget.A(), activeTarget.Z(), 50.0f, ringRadius, activeTarget.Y(), mutableColor);
        }
    }

    @EventHandler
    public void onPostTick(EventPostTick eventPostTick) {
        WorldClient world = eventPostTick.getWorld();
        EntityPlayerSP player = eventPostTick.getThePlayer();
        if (this.pendingRemoveEntityId != -1) {
            Entity entity = ((World)world).V(this.pendingRemoveEntityId);
            if (entity.isNotNull()) {
                world.M(entity);
            }
            this.pendingRemoveEntityId = -1;
        }
        if (world.isNull() || player.isNull() || this.currentAction == null) {
            return;
        }
        this.aimAtAction(player);
        if (!this.placementSent) {
            return;
        }
        CrystalAuraAction action = this.currentAction.getAction();
        if (action == CrystalAuraAction.PLACING_OBSIDIAN) {
            DirectionalPosition actionPosition = this.currentAction.directionalPosition;
            EnumFacing facing = EnumFacing.T(actionPosition.getFacingIndex());
            BlockData placedBlock = new BlockData(actionPosition.B(), actionPosition.E(), actionPosition.A()).R(facing);
            BlockPos placedPosition = BlockPos.create(placedBlock.D(), placedBlock.B(), placedBlock.G());
            BlockState blockState = world.getBlockState(placedPosition);
            if (blockState.isNotNull() && blockState.getBlock().U().toLowerCase().contains("obsidian")) {
                this.currentAction.setAction(CrystalAuraAction.PLACING_CRYSTAL);
                BlockCoordinate placedCoordinate = new BlockCoordinate(placedPosition.getX(), placedPosition.getY(), placedPosition.getZ());
                this.currentAction.directionalPosition = new DirectionalPosition(placedCoordinate, 1);
                this.aimAtAction(player);
                this.placementSent = false;
            } else {
                this.currentAction = null;
                this.placementSent = false;
            }
        } else if (action == CrystalAuraAction.PLACING_CRYSTAL) {
            DirectionalPosition actionPosition = this.currentAction.directionalPosition;
            BlockCoordinate crystalPosition = new BlockCoordinate(actionPosition.B(), actionPosition.E() + 1, actionPosition.A());
            AxisAlignedBB crystalBox = AxisAlignedBB.create((double)crystalPosition.B() - 0.5, crystalPosition.E(), (double)crystalPosition.A() - 0.5, (double)crystalPosition.B() + 0.5, (double)crystalPosition.E() + 2.0, (double)crystalPosition.A() + 0.5);
            ArrayList<Entity> crystals = new ArrayList<Entity>();
            world.getEntityGetter().forEachEntityInBounds(crystalBox, entityObject -> CrystalAuraPlacementSubModule.collectCrystals(crystals, entityObject));
            if (!crystals.isEmpty()) {
                this.currentAction.setAction(CrystalAuraAction.ATTACKING_CRYSTAL);
                this.currentAction.setTargetEntity(crystals.get(0));
                this.placementSent = false;
            } else {
                Vec3 eyePosition = player.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk().addVector(0.0, player.X(), 0.0);
                Vec3 interactionPoint = this.crystalAura.getInteractionPoint(this.currentAction.directionalPosition);
                if (eyePosition.distanceTo(interactionPoint) > 3.0) {
                    Vec3 currentPlayerPosition = Vec3.create(player.z(), interactionPoint.getY(), player.h());
                    Vec3 previousPlayerPosition = Vec3.create(player.M(), interactionPoint.getY(), player.m$src$D$fwnne5());
                    if (currentPlayerPosition.distanceTo(interactionPoint) > previousPlayerPosition.distanceTo(interactionPoint)) {
                        this.currentAction = null;
                        this.placementSent = false;
                    }
                }
            }
        }
    }

    private boolean wouldIntersect(EntityLivingBase target, BlockCoordinate crystalPosition, EntityPlayerSP player, World world) {
        boolean intersects = false;
        AxisAlignedBB crystalBox = this.crystalBoundingBox(crystalPosition);
        if (target.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().intersects(crystalBox)) {
            intersects = true;
        }
        if (target.isInstance(MappedClasses.Yl)) {
            EntityPlayer targetPlayer = new EntityPlayer(target);
            BlockPathPlanner pathPlanner = new BlockPathPlanner(targetPlayer, player, world, new BlockPlacementGraph(player));
            EntityPlayer predictedTarget = pathPlanner.getSimulatedPlayer();
            pathPlanner.clearInput();
            pathPlanner.finishSimulation();
            double motionX = predictedTarget.z() - predictedTarget.M();
            double motionY = predictedTarget.N() - predictedTarget.W();
            double motionZ = predictedTarget.h() - predictedTarget.m$src$D$fwnne5();
            if (this.predictAttackVelocity.getEffectiveValue().booleanValue() && this.attacked && target.equals(this.target)) {
                float attackVerticalVelocity = 0.4f;
                motionY = target.b$src$Z$fqlxe4() ? Math.min(0.4, motionY / 2.0 + (double)attackVerticalVelocity) : (double)attackVerticalVelocity;
                double playerDeltaX = player.z() - target.z();
                double playerDeltaZ = player.h() - target.h();
                double horizontalDistance = Math.sqrt(playerDeltaX * playerDeltaX + playerDeltaZ * playerDeltaZ);
                motionX = motionX / 2.0 - playerDeltaX / horizontalDistance * 0.3;
                motionZ = motionZ / 2.0 - playerDeltaZ / horizontalDistance * 0.3;
            }
            predictedTarget.F(motionX, motionY, motionZ);
            for (int predictionTick = 0; predictionTick < 3; ++predictionTick) {
                pathPlanner.simulateTick();
            }
            if (predictedTarget.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().intersects(crystalBox)) {
                intersects = true;
            }
        }
        return intersects;
    }

    private void aimAtAction(EntityPlayerSP player) {
        if (!this.rotationClaim.isOwnedBy(this.crystalAura)) {
            return;
        }
        if (this.rotationController == null) {
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
        Vec3 eyePosition = player.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk().addVector(0.0, player.X(), 0.0);
        DirectionalPosition actionPosition = this.currentAction.directionalPosition;
        EnumFacing facing = EnumFacing.T(actionPosition.getFacingIndex());
        Vec3 targetPoint;
        if (this.currentAction.getAction() == CrystalAuraAction.ATTACKING_CRYSTAL) {
            BlockCoordinate crystalPosition = new BlockCoordinate(actionPosition.B(), actionPosition.E() + 1, actionPosition.A());
            AxisAlignedBB crystalBox = AxisAlignedBB.create((double)crystalPosition.B() - 0.5, crystalPosition.E(), (double)crystalPosition.A() - 0.5, (double)crystalPosition.B() + 0.5, (double)crystalPosition.E() + 2.0, (double)crystalPosition.A() + 0.5);
            ArrayList<Entity> crystals = new ArrayList<Entity>();
            player.getWorld().getEntityGetter().forEachEntityInBounds(crystalBox, entityObject -> CrystalAuraPlacementSubModule.collectCrystalsForAim(crystals, entityObject));
            BlockData baseBlock = new BlockData(actionPosition.B(), actionPosition.E(), actionPosition.A());
            boolean hasPlacementPath = ClutchPlacementPathUtils.isBlockFaceVisible(eyePosition, player.getWorld(), baseBlock, facing);
            targetPoint = !crystals.isEmpty() && !hasPlacementPath
                    ? RotationUtil.M(eyePosition, crystals.get(0).R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl(), 0.0, 0.0, 0.0).toVec3()
                    : this.crystalAura.getInteractionPoint(actionPosition);
        } else {
            PlacementTarget placementTarget = new PlacementTarget(new BlockData(actionPosition.B(), actionPosition.E(), actionPosition.A()), facing);
            Vec3 placementHitPoint = ClutchPlacementPathUtils.findBestPlacementHitPoint(player, player.getWorld(), eyePosition, placementTarget, managedYaw, managedPitch);
            targetPoint = placementHitPoint != null ? placementHitPoint : this.crystalAura.getInteractionPoint(actionPosition);
        }
        RotationAngles targetRotation = RotationVectorMath.d(eyePosition, targetPoint, managedYaw, managedPitch);
        this.rotationController.setTargetRotation(targetRotation);
        this.rotationManager.setController(this.rotationController);
    }

    private CrystalAuraActionCandidate findActionAtYLevel(EntityPlayerSP player, World world, double searchRange,
                                                            boolean renderCandidates, int searchY) {
        if (this.target == null) {
            this.lowEfficiency = false;
            this.statusLabel = null;
            return null;
        }
        Vec3 eyePosition = player.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk().addVector(0.0, player.X(), 0.0);
        int playerBlockX = MathUtil.floor(player.z());
        int playerBlockZ = MathUtil.floor(player.h());
        CrystalAuraActionCandidate bestDirectPlacement = null;
        CrystalAuraActionCandidate bestObsidianPlacement = null;
        boolean foundCrystalBase = false;
        float bestDirectEfficiency = 0.0f;
        float bestObsidianEfficiency = 0.0f;
        double minimumEfficiency = (double)((Double)this.minEfficiency.getValue()).intValue() / 100.0;
        double collisionMargin = 0.1;
        CrystalAuraPlacementSearchState searchState = new CrystalAuraPlacementSearchState();

        BlockState obsidianState = null;
        int obsidianSlot = this.crystalAura.findObsidianSlot(player);
        if (obsidianSlot != -1) {
            ItemStack obsidianStack = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(obsidianSlot);
            if (!obsidianStack.isNull()) {
                obsidianState = BlockUtil.E(obsidianStack);
            }
        }
        boolean canPlaceObsidian = this.autoObsidian.getEffectiveValue().booleanValue() && obsidianState != null;
        if (this.autoObsidian.getEffectiveValue().booleanValue() && !canPlaceObsidian) {
            searchState.markNoPlacement();
        } else if (!this.autoObsidian.getEffectiveValue().booleanValue()) {
            int radius = (int)searchRange;
            for (int x = playerBlockX - radius; x <= playerBlockX + radius && !foundCrystalBase; ++x) {
                for (int z = playerBlockZ - radius; z <= playerBlockZ + radius && !foundCrystalBase; ++z) {
                    BlockState state = world.getBlockState(BlockPos.D(x, searchY, z));
                    if (!state.isNull() && this.crystalAura.isCrystalBaseBlock(state)) {
                        foundCrystalBase = true;
                    }
                }
            }
        }
        double renderX = RenderManager.getInterpolatedRenderPosX();
        double renderY = RenderManager.getInterpolatedRenderPosY();
        double renderZ = RenderManager.getInterpolatedRenderPosZ();
        double playerX = player.z();
        double playerZ = player.h();
        double targetOffsetX = this.target.z() - playerX;
        double targetOffsetZ = this.target.h() - playerZ;
        Color directCandidateColor = new Color(0, 0, 255, 25);
        Color obsidianCandidateColor = new Color(9, 255, 0, 25);
        boolean targetIsClose = player.getDistanceToEntity(this.target) <= 1.75;
        int radius = (int)searchRange;

        for (int searchPass = 0; searchPass < 2; ++searchPass) {
            boolean onlySearchTowardTarget = searchPass == 0 && !targetIsClose;
            for (int x = playerBlockX - radius; x <= playerBlockX + radius; ++x) {
                for (int z = playerBlockZ - radius; z <= playerBlockZ + radius; ++z) {
                    if (x == playerBlockX && z == playerBlockZ) {
                        continue;
                    }
                    double candidateOffsetX = x - playerX;
                    double candidateOffsetZ = z - playerZ;
                    if (onlySearchTowardTarget
                            && targetOffsetX * candidateOffsetX + targetOffsetZ * candidateOffsetZ < 0.0) {
                        continue;
                    }

                    AxisAlignedBB crystalSearchBox = AxisAlignedBB.create(
                            x - collisionMargin, searchY + 1.0, z - collisionMargin,
                            x + 1.0 + collisionMargin, searchY + 3.0, z + 1.0 + collisionMargin);
                    ArrayList<Entity> crystals = new ArrayList<>();
                    world.getEntityGetter().forEachEntityInBounds(crystalSearchBox,
                            entity -> CrystalAuraPlacementSubModule.collectCrystalsAtPosition(crystals, entity));
                    for (Entity crystal : crystals) {
                        Vec3 crystalPosition = crystal.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk();
                        if (eyePosition.distanceTo(crystalPosition) > 4.0
                                || !MathUtil.e(crystal.z(), x, x + 1.0)
                                || !MathUtil.e(crystal.h(), z, z + 1.0)
                                || !this.isSafeDamage(crystalPosition, ExplosionType.CRYSTAL, player, world)) {
                            continue;
                        }
                        float efficiency = this.computeDamageEfficiency(
                                this.target, ExplosionType.CRYSTAL, crystalPosition, player, world);
                        Vec3 nearestCrystalPoint = RotationUtil.M(eyePosition,
                                crystal.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().contract(0.01, 0.0, 0.01),
                                0.0, 0.0, 0.0).toVec3();
                        if (eyePosition.distanceTo(nearestCrystalPoint) > 3.5) {
                            Vec3 currentPlayerPosition = Vec3.create(player.z(), nearestCrystalPoint.getY(), player.h());
                            Vec3 previousPlayerPosition = Vec3.create(
                                    player.M(), nearestCrystalPoint.getY(), player.m$src$D$fwnne5());
                            if (currentPlayerPosition.distanceTo(nearestCrystalPoint)
                                    >= previousPlayerPosition.distanceTo(nearestCrystalPoint)) {
                                searchState.markOutOfRange();
                                continue;
                            }
                        }
                        RayTraceResult crystalTrace = RayTraceUtil.b(
                                eyePosition, nearestCrystalPoint, world, player, false, false, false, null);
                        if (!crystalTrace.isEntityHit() || !crystalTrace.getEntity().equals(crystal)) {
                            continue;
                        }
                        CrystalAuraActionCandidate candidate = new CrystalAuraActionCandidate(
                                ExplosionType.CRYSTAL, new DirectionalPosition(x, searchY, z, 1),
                                crystalPosition, efficiency, 0.0);
                        candidate.setAction(CrystalAuraAction.ATTACKING_CRYSTAL);
                        candidate.setTargetEntity(crystal);
                        return candidate;
                    }

                    BlockData candidateBlock = new BlockData(x, searchY, z);
                    BlockPos candidatePos = BlockPos.D(x, searchY, z);
                    BlockState candidateState = world.getBlockState(candidatePos);
                    if (candidateState.isNull()) {
                        continue;
                    }
                    Block candidateBlockType = candidateState.getBlock();
                    boolean hasCrystalBase = this.crystalAura.isCrystalBaseBlock(candidateState);
                    if (hasCrystalBase) {
                        foundCrystalBase = true;
                    }
                    BlockData blockAbove = candidateBlock.R(EnumFacing.F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5());
                    if (!BlockUtil.u(world.getBlockByPos(blockAbove.D(), blockAbove.B(), blockAbove.G()))) {
                        searchState.markBlocked();
                        continue;
                    }
                    boolean needsObsidian = canPlaceObsidian && BlockUtil.u(candidateBlockType);
                    if (!hasCrystalBase && !needsObsidian) {
                        continue;
                    }

                    int xOffset = x - playerBlockX;
                    int zOffset = z - playerBlockZ;
                    int xFacingIndex = xOffset > 0 ? 5 : (xOffset < 0 ? 4 : -1);
                    int zFacingIndex = zOffset > 0 ? 3 : (zOffset < 0 ? 2 : -1);
                    int[] preferredFacingIndices = new int[]{0, xFacingIndex, zFacingIndex};
                    BlockData clickedBlock = null;
                    EnumFacing placementFacing = null;
                    if (needsObsidian) {
                        for (int facingIndex : preferredFacingIndices) {
                            if (facingIndex == -1) {
                                continue;
                            }
                            EnumFacing direction = EnumFacing.t()[facingIndex];
                            BlockData adjacentBlock = candidateBlock.R(direction);
                            Block adjacentBlockType = world.getBlockByPos(
                                    adjacentBlock.D(), adjacentBlock.B(), adjacentBlock.G());
                            if (BlockUtil.u(adjacentBlockType) || !BlockUtil.u(candidateBlockType)) {
                                continue;
                            }
                            clickedBlock = adjacentBlock;
                            placementFacing = direction.getOpposite();
                            break;
                        }
                    } else {
                        clickedBlock = candidateBlock;
                        for (int facingIndex : preferredFacingIndices) {
                            if (facingIndex == -1) {
                                continue;
                            }
                            EnumFacing direction = EnumFacing.t()[facingIndex];
                            BlockData oppositeBlock = clickedBlock.R(direction.getOpposite());
                            Block clickedBlockType = world.getBlockByPos(
                                    clickedBlock.D(), clickedBlock.B(), clickedBlock.G());
                            Block oppositeBlockType = world.getBlockByPos(
                                    oppositeBlock.D(), oppositeBlock.B(), oppositeBlock.G());
                            if (BlockUtil.u(clickedBlockType) || !BlockUtil.u(oppositeBlockType)) {
                                continue;
                            }
                            placementFacing = direction.getOpposite();
                            break;
                        }
                    }
                    if (placementFacing == null) {
                        searchState.markNoPlacement();
                        continue;
                    }

                    DirectionalPosition explosionPosition = new DirectionalPosition(
                            x, searchY, z, placementFacing.Y());
                    DirectionalPosition clickPosition = new DirectionalPosition(
                            clickedBlock.D(), clickedBlock.B(), clickedBlock.G(), placementFacing.Y());
                    Vec3 interactionPoint = this.crystalAura.getInteractionPoint(explosionPosition);
                    if (eyePosition.distanceTo(interactionPoint) > 4.0) {
                        Vec3 currentPlayerPosition = Vec3.create(player.z(), interactionPoint.getY(), player.h());
                        Vec3 previousPlayerPosition = Vec3.create(
                                player.M(), interactionPoint.getY(), player.m$src$D$fwnne5());
                        if (currentPlayerPosition.distanceTo(interactionPoint)
                                < previousPlayerPosition.distanceTo(interactionPoint)) {
                            continue;
                        }
                        searchState.markOutOfRange();
                        continue;
                    }

                    if (hasCrystalBase) {
                        float efficiency = this.computeDamageEfficiency(
                                this.target, ExplosionType.CRYSTAL, interactionPoint, player, world);
                        if (efficiency > 0.0f && efficiency < minimumEfficiency) {
                            searchState.markLowEfficiency();
                        }
                        if (!this.isSafeDamage(interactionPoint, ExplosionType.CRYSTAL, player, world)
                                || efficiency < minimumEfficiency || efficiency <= bestDirectEfficiency) {
                            continue;
                        }
                        AxisAlignedBB placementBox = AxisAlignedBB.create(
                                x - collisionMargin, searchY + 1.0, z - collisionMargin,
                                x + 1.0 + collisionMargin, searchY + 2.0, z + 1.0 + collisionMargin);
                        ArrayList<Entity> collidingEntities = new ArrayList<>();
                        world.getEntityGetter().forEachEntityInBounds(placementBox,
                                entity -> CrystalAuraPlacementSubModule.collectCollidingEntities(collidingEntities, entity));
                        if (!collidingEntities.isEmpty()) {
                            searchState.markBlocked();
                            continue;
                        }
                        PlacementTarget placementTarget = new PlacementTarget(candidateBlock, placementFacing);
                        if (!ClutchPlacementPathUtils.isBlockFaceVisible(
                                eyePosition, world, placementTarget.supportBlock, placementTarget.facing)) {
                            searchState.markOutOfRange();
                            continue;
                        }
                        Vec3 placementHit = ClutchPlacementPathUtils.findBestPlacementHitPoint(
                                player, world, eyePosition, placementTarget,
                                this.rotationManager.getManagedYaw(), this.rotationManager.getManagedPitch());
                        if (placementHit == null || eyePosition.distanceTo(placementHit) > searchRange) {
                            searchState.markOutOfRange();
                            continue;
                        }
                        RayTraceResult placementTrace = RayTraceUtil.b(
                                eyePosition, placementHit, world, player, false, false, false, null);
                        if (!placementTrace.isBlockHit() || !placementTrace.getBlockPos().equals(candidatePos)) {
                            searchState.markOutOfRange();
                            continue;
                        }
                        bestDirectEfficiency = efficiency;
                        bestDirectPlacement = new CrystalAuraActionCandidate(
                                ExplosionType.CRYSTAL, clickPosition, interactionPoint, efficiency, 0.0);
                        if (renderCandidates) {
                            RenderUtil.w(renderX, renderY, renderZ, x, searchY, z, directCandidateColor);
                        }
                        continue;
                    }

                    BlockData supportBlock = new BlockData(x, searchY - 1, z);
                    BlockPos supportPos = BlockPos.D(x, searchY - 1, z);
                    BlockState supportState = world.getBlockState(supportPos);
                    if (supportState.isNull() || BlockUtil.p(supportState.getBlock())) {
                        searchState.markNoPlacement();
                        continue;
                    }
                    Vec3 crystalPlacementPoint = interactionPoint.C(0.0, 1.0, 0.0);
                    if (eyePosition.distanceTo(crystalPlacementPoint) > 4.0) {
                        Vec3 currentPlayerPosition = Vec3.create(
                                player.z(), crystalPlacementPoint.getY() - 1.0, player.h());
                        Vec3 previousPlayerPosition = Vec3.create(
                                player.M(), crystalPlacementPoint.getY() - 1.0, player.m$src$D$fwnne5());
                        if (currentPlayerPosition.distanceTo(crystalPlacementPoint)
                                >= previousPlayerPosition.distanceTo(crystalPlacementPoint)) {
                            searchState.markOutOfRange();
                            continue;
                        }
                    }

                    float efficiency;
                    boolean safeDamage;
                    boolean obsidianReachable;
                    BlockUtil.z(world, candidatePos, obsidianState);
                    try {
                        efficiency = this.computeDamageEfficiency(
                                this.target, ExplosionType.CRYSTAL, interactionPoint, player, world);
                        safeDamage = this.isSafeDamage(
                                interactionPoint, ExplosionType.CRYSTAL, player, world);
                        PlacementTarget obsidianTarget = new PlacementTarget(
                                candidateBlock, EnumFacing.F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5());
                        obsidianReachable = ClutchPlacementPathUtils.isBlockFaceVisible(
                                eyePosition, world, obsidianTarget.supportBlock, obsidianTarget.facing);
                        if (obsidianReachable) {
                            Vec3 obsidianHit = ClutchPlacementPathUtils.findBestPlacementHitPoint(
                                    player, world, eyePosition, obsidianTarget,
                                    this.rotationManager.getManagedYaw(), this.rotationManager.getManagedPitch());
                            obsidianReachable = obsidianHit != null
                                    && eyePosition.distanceTo(obsidianHit) <= searchRange;
                        }
                    } finally {
                        BlockUtil.z(world, candidatePos, candidateState);
                    }
                    if (efficiency > 0.0f && efficiency < minimumEfficiency) {
                        searchState.markLowEfficiency();
                    }
                    if (!safeDamage || efficiency < minimumEfficiency || efficiency <= bestObsidianEfficiency) {
                        continue;
                    }
                    if (!obsidianReachable) {
                        searchState.markOutOfRange();
                        continue;
                    }
                    AxisAlignedBB placementBox = AxisAlignedBB.create(
                            x - collisionMargin, searchY + 1.0, z - collisionMargin,
                            x + 1.0 + collisionMargin, searchY + 2.0, z + 1.0 + collisionMargin);
                    ArrayList<Entity> collidingEntities = new ArrayList<>();
                    world.getEntityGetter().forEachEntityInBounds(placementBox,
                            entity -> CrystalAuraPlacementSubModule.collectCollidingEntities(collidingEntities, entity));
                    if (!collidingEntities.isEmpty()
                            || this.wouldIntersect(this.target, explosionPosition, player, world)) {
                        searchState.markBlocked();
                        continue;
                    }
                    PlacementTarget supportTarget = new PlacementTarget(supportBlock, placementFacing);
                    if (!ClutchPlacementPathUtils.isBlockFaceVisible(eyePosition, world, supportTarget.supportBlock, supportTarget.facing)) {
                        searchState.markOutOfRange();
                        continue;
                    }
                    Vec3 supportHit = ClutchPlacementPathUtils.findBestPlacementHitPoint(
                            player, world, eyePosition, supportTarget,
                            this.rotationManager.getManagedYaw(), this.rotationManager.getManagedPitch());
                    if (supportHit == null || eyePosition.distanceTo(supportHit) > searchRange) {
                        searchState.markOutOfRange();
                        continue;
                    }
                    RayTraceResult supportTrace = RayTraceUtil.b(
                            eyePosition, supportHit, world, player, false, false, false, null);
                    if (!supportTrace.isBlockHit()
                            || supportTrace.getSideHit().Y() != placementFacing.Y()
                            || !supportTrace.getBlockPos().equals(supportPos)) {
                        searchState.markOutOfRange();
                        continue;
                    }
                    bestObsidianEfficiency = efficiency;
                    BlockCoordinate supportCoordinate = new BlockCoordinate(x, searchY - 1, z);
                    bestObsidianPlacement = new CrystalAuraActionCandidate(
                            ExplosionType.CRYSTAL,
                            new DirectionalPosition(supportCoordinate, placementFacing.Y()),
                            interactionPoint, efficiency, 0.0);
                    bestObsidianPlacement.requiresObsidianPlacement = true;
                    if (renderCandidates) {
                        RenderUtil.w(renderX, renderY, renderZ,
                                x, searchY - 1, z, obsidianCandidateColor);
                    }
                }
            }
            if (bestDirectPlacement != null || bestObsidianPlacement != null
                    || searchPass == 0 && targetIsClose) {
                break;
            }
        }

        CrystalAuraActionCandidate result = bestDirectPlacement != null
                ? bestDirectPlacement : bestObsidianPlacement;
        if (renderCandidates && result != null) {
            DirectionalPosition resultPosition = result.directionalPosition;
            RenderUtil.u(resultPosition.B(), resultPosition.E(), resultPosition.A(),
                    1.0, 1.0, 1.0, 0.1, new Color(255, 255, 255, 255), null,
                    renderX, renderY, renderZ);
        }
        this.lowEfficiency = result == null && searchState.lowEfficiency;
        this.statusLabel = result == null
                ? (searchState.lowEfficiency ? "low eff"
                : (searchState.blocked ? "blocked"
                : (searchState.outOfRange ? "out range"
                : (searchState.noPlacement
                ? (this.autoObsidian.getEffectiveValue().booleanValue() && obsidianSlot == -1 ? "no obsidian" : "no place")
                : (!this.autoObsidian.getEffectiveValue().booleanValue() && !foundCrystalBase
                ? "no placement block" : "no candidate")))))
                : null;
        return result;
    }

    private void rememberTarget(EntityLivingBase target) {
        if (target != null && target.isNotNull()) {
            this.lastTarget = target;
            this.lastTargetTime = System.currentTimeMillis();
        }
    }

    public CrystalAuraPlacementSubModule(Mod parent, String name) {
        super(parent, name);
        this.targetFilter = EntityTargetFilterValue.createForModule(this);
        this.centerScreen = BooleanValue.create(this, "Center screen", true, "Renders crystal info on the center of your screen");
        this.showTarget = BooleanValue.create(this, "Show target", false);
        this.targetColor = ColorValue.createWithAlpha(this, "Target color", new Color(255, 40, 255), 50);
        this.attackColor = ColorValue.create(this, "Attack color", new Color(169, 0, 255, 255));
        this.range = NumberValue.createWithDescription(this, "Range", "#.#", "m", 0.0, 4.5, 6.0, "Range to check for targets");
        this.maxAngle = NumberValue.create(this, "Max angle", "#", "", 1.0, 120.0, 360.0, 5.0, "Angle at which targets will be acquired and aimed at\n(From your cursor)");
        this.distanceMode = new ModeOption("Distance");
        this.yawMode = new ModeOption("Yaw");
        this.armorMode = new ModeOption("Armor");
        this.healthMode = new ModeOption("Health");
        this.targetMode = ModeValue.create((Object)this, "Target Mode", "How targets will be prioritized\nArmor will default to Distance for non player targets", (ModeSelection)this.distanceMode, this.distanceMode, this.yawMode, this.armorMode, this.healthMode);
        this.activationDelay = RandomValue.createWithDescription(this, "Delay", "#", "ms", 50.0, 50.0, 150.0, 500.0, 1.0, "Delay before activating");
        this.antiSuicide = BooleanValue.create(this, "Anti suicide", true, "Prevents placing/breaking if it will result in fatal damage");
        this.maxSelfDamage = NumberValue.create(this, "Max self damage", "#", "HP", 0.0, 19.0, 20.0, 1.0, "Maximum self damage allowed");
        this.rapidFireMode = new ModeOption("Rapid fire");
        this.predictMode = new ModeOption("Predict");
        this.noneMode = new ModeOption("None");
        this.optimizationMode = ModeValue.create((Object)this, "Optimization", "Controls crystal optimization behavior\nNone - No crystal optimization\nRapid fire - Crystals are broken and replaced in same tick when possible\nPredict - Predicts explosion timing and pre-removes crystal for faster placement(potentially unsafe)", (ModeSelection)this.noneMode, this.rapidFireMode, this.predictMode, this.noneMode);
        this.rapidMinEfficiency = NumberValue.create(this, "Rapid min efficiency", "#", "%", 0.0, 50.0, 100.0, 1.0, "Minimum damage efficiency (0-100%) to trigger rapid fire when placing/breaking crystals");
        this.predictAttackVelocity = BooleanValue.create(this, "Predict attack velocity", true, "Predicts target movement when calculating damage after successfully attacking");
        this.autoObsidian = BooleanValue.create(this, "Auto obsidian", false, "Automatically places obsidian to place crystals");
        this.minEfficiency = NumberValue.create(this, "Min efficiency", "#", "%", 0.0, 50.0, 100.0, 1.0, "Minimum damage efficiency (0-100%) for placing and breaking crystals");
        this.rotationManager = RotationManager.INSTANCE;
        this.rotationClaim = SharedModuleControlClaims.rotation;
        this.mouseOverClaim = SharedModuleControlClaims.mouseOverUpdate;
        this.friendManager = Vape.INSTANCE.getFriendManager();
        this.delayTimer = new TimerUtil();
        this.crystalAura = (CrystalAura)parent;
        this.showTarget.addDependentValues(this.targetColor, this.attackColor);
        this.optimizationMode.addModeDependentValues(this.rapidFireMode, this.rapidMinEfficiency);
        this.addValue(this.targetFilter, this.targetMode, this.range, this.maxAngle, this.aimSpeed, this.minEfficiency, this.activationDelay, this.maxSelfDamage, this.autoObsidian, this.antiSuicide, this.optimizationMode, this.rapidMinEfficiency, this.predictAttackVelocity, this.centerScreen, this.showTarget, this.targetColor, this.attackColor);
    }

    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        WorldClient world = eventPreTick.getWorld();
        EntityPlayerSP player = eventPreTick.getThePlayer();
        GuiScreen currentScreen = eventPreTick.getCurrentScreen();
        boolean activationDelayElapsed = this.delayTimer.hasTimeElapsed((long)this.activationDelay.getRandomValue());
        if (world.isNull() || player.isNull() || !currentScreen.isNull() || this.toggledOff) {
            this.reset();
            return;
        }
        if (this.attacked && this.attackedLastTick) {
            this.attacked = false;
        }
        this.attackedLastTick = this.attacked;
        this.crystalSlot = this.crystalAura.findCrystalSlot(player);
        if (this.crystalSlot == -1) {
            this.statusLabel = "no crystal";
            this.reset();
            return;
        }
        this.validateCurrentAction(player, world);
        this.updateTarget(player, world);
        if (this.target == null) {
            this.lowEfficiency = false;
            this.statusLabel = "no target";
            this.reset();
            return;
        }
        if (this.currentAction == null && activationDelayElapsed) {
            this.currentAction = this.findAction(player, world, 4.0, false);
            if (this.currentAction == null) {
                this.reset();
                if (this.statusLabel == null) {
                    this.statusLabel = "no candidate";
                }
                return;
            }
            this.lowEfficiency = false;
            this.statusLabel = null;
            if (this.currentAction.requiresObsidianPlacement) {
                this.currentAction.setAction(CrystalAuraAction.PLACING_OBSIDIAN);
            } else {
                this.currentAction.setAction(CrystalAuraAction.PLACING_CRYSTAL);
            }
        }
        if (this.currentAction == null) {
            return;
        }
        if (!this.rotationClaim.isOwnedBy(this.crystalAura) && !this.rotationClaim.acquire(this.crystalAura, true)) {
            this.statusLabel = "aim lock";
            return;
        }
        CrystalAuraAction actionType = this.currentAction.getAction();
        int requiredSlot;
        if (actionType == CrystalAuraAction.PLACING_OBSIDIAN) {
            requiredSlot = this.crystalAura.findObsidianSlot(player);
            if (requiredSlot == -1) {
                this.statusLabel = "no obby";
                this.invalidateAction();
                return;
            }
        } else {
            requiredSlot = this.crystalSlot;
        }
        if (player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v() != requiredSlot) {
            if (this.previousSlot == -1) {
                this.previousSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
            }
            player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(requiredSlot);
        }
        this.validateAction(player, world);
    }

    private void validateCurrentAction(EntityPlayerSP player, World world) {
        if (this.currentAction == null) {
            return;
        }

        CrystalAuraAction actionType = this.currentAction.getAction();
        DirectionalPosition actionPosition = this.currentAction.directionalPosition;
        Vec3 eyePosition = player.I$src$Lgg_vape_wrapper_impl_Vec3_$q14opk().addVector(0.0, player.X(), 0.0);
        if (actionType == CrystalAuraAction.PLACING_OBSIDIAN || actionType == CrystalAuraAction.PLACING_CRYSTAL) {
            if (actionType == CrystalAuraAction.PLACING_CRYSTAL) {
                BlockCoordinate crystalPosition = new BlockCoordinate(actionPosition.B(), actionPosition.E() + 1, actionPosition.A());
                AxisAlignedBB crystalBox = AxisAlignedBB.create(
                        crystalPosition.B() - 0.5, crystalPosition.E() - 0.01, crystalPosition.A() - 0.5,
                        crystalPosition.B() + 0.5, crystalPosition.E() + 2.0, crystalPosition.A() + 0.5);
                ArrayList<Entity> crystals = new ArrayList<>();
                world.getEntityGetter().forEachEntityInBounds(crystalBox, entity -> CrystalAuraPlacementSubModule.collectCrystals(crystals, entity));
                if (!crystals.isEmpty()) {
                    this.currentAction.setAction(CrystalAuraAction.ATTACKING_CRYSTAL);
                    this.currentAction.setTargetEntity(crystals.get(0));
                }
            }
            if (this.currentAction.getAction() != CrystalAuraAction.ATTACKING_CRYSTAL
                    && !this.hasPlacementPath(player, world, eyePosition, actionPosition)) {
                this.statusLabel = "out range";
                this.invalidateAction();
            }
        }

        if (this.currentAction != null && this.currentAction.getAction() == CrystalAuraAction.ATTACKING_CRYSTAL) {
            Entity crystal = this.currentAction.getTargetEntity();
            if (crystal == null || crystal.isNull() || crystal.M$src$Z$ff28xj()) {
                this.invalidateAction();
            } else {
                BlockData baseBlock = new BlockData(actionPosition.B(), actionPosition.E(), actionPosition.A());
                EnumFacing facing = EnumFacing.T(actionPosition.getFacingIndex());
                Vec3 interactionPoint = this.crystalAura.getInteractionPoint(actionPosition);
                RayTraceResult rayTrace = RayTraceUtil.b(eyePosition, interactionPoint, world, player, false, true, false, null);
                if (ClutchPlacementPathUtils.isBlockFaceVisible(eyePosition, world, baseBlock, facing) && rayTrace.isEntityHit()) {
                    Entity obstructingEntity = rayTrace.getEntity();
                    if (!obstructingEntity.isInstance(MappedClasses.Ze) && obstructingEntity.n$src$Z$fx7gig()) {
                        this.statusLabel = "blocked";
                        this.invalidateAction();
                    }
                }
                Vec3 nearestCrystalPoint = RotationUtil.M(eyePosition,
                        crystal.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl(), 0.0, 0.0, 0.0).toVec3();
                if (eyePosition.distanceTo(nearestCrystalPoint) > 3.0) {
                    this.statusLabel = "out range";
                    this.invalidateAction();
                }
            }
        }

        if (this.currentAction != null && this.currentAction.getAction() == CrystalAuraAction.PLACING_CRYSTAL) {
            BlockData baseBlock = new BlockData(actionPosition.B(), actionPosition.E(), actionPosition.A());
            BlockState baseState = world.getBlockState(BlockPos.create(baseBlock.D(), baseBlock.B(), baseBlock.G()));
            if (!this.crystalAura.isCrystalBaseBlock(baseState)) {
                this.statusLabel = "no place";
                this.invalidateAction();
            }
        }
    }

    private boolean isSafeDamage(Vec3 explosionPosition, ExplosionType explosionType, EntityPlayerSP player, World world) {
        return this.crystalAura.isSelfDamageSafe(explosionPosition, explosionType, player, world, this.antiSuicide.getEffectiveValue(), ((Double)this.maxSelfDamage.getValue()).floatValue());
    }

    public void releaseControl() {
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
        if (this.previousSlot != -1) {
            EntityPlayerSP player = Minecraft.thePlayer();
            if (!player.isNull()) {
                player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.previousSlot);
            }
            this.previousSlot = -1;
        }
        this.target = null;
        this.currentAction = null;
        this.placementSent = false;
        this.attackedLastTick = false;
        this.attacked = false;
        this.toggledOff = false;
        this.delayTimer.reset();
    }

    private static Exception rethrow(Exception exception) {
        return exception;
    }
}
