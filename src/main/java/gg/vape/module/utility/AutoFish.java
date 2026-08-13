package gg.vape.module.utility;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPreTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.inventory.InventoryClick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.utility.inventory.ItemStackActionPredicate;
import gg.vape.module.utility.mlg.MLGBlockWrapper;
import gg.vape.module.utility.mlg.MLGImpactState;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockStateWorldBridge;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayerMacroBridge;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GuiContainer;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Material;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.SPacketEntityVelocity;
import gg.vape.wrapper.impl.Slot;
import gg.vape.wrapper.impl.World;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jetbrains.annotations.Nullable;

public class AutoFish
extends Mod {
    private final BooleanValue refillRods;
    private final BooleanValue recastGround;
    private final TimerUtil stationaryTimer;
    private static final int RECAST_GROUND_DELAY_MS = 3000;
    private boolean biteDetected = false;
    private static final double VELOCITY_THRESHOLD = 0.05;
    private final Queue<InventoryClick> clickQueue;
    private boolean velocityBite = false;
    private boolean hasCast = false;
    private final ArrayDeque<KeyBinding> pressedKeys;
    private final TimerUtil castTimer;
    private double accumulatedMotionY = 0.0;
    private final TimerUtil clickTimer;
    private static final int STATIONARY_DELAY_MS = 1000;
    private final RandomValue clickDelay = RandomValue.createWithDescription(this, "Click delay", "#", "ms", 50.0, 75.0, 125.0, 200.0, 5.0, "How long to wait between clicks in the inventory");
    private boolean refillPending = false;
    private final ArrayDeque<KeyBinding> queuedKeys;
    private final BooleanValue recastCaught;
    private final TimerUtil groundTimer;

    private boolean cancelRefill() {
        if (this.refillPending) {
            this.clickQueue.clear();
            this.refillPending = false;
            return ItemStackActionPredicate.closeCurrentScreen();
        }
        return false;
    }


    private boolean pumpKeyPresses() {
        boolean processedKey = false;
        KeyBinding pressedKey = this.pressedKeys.poll();
        if (pressedKey != null && pressedKey.isNotNull()) {
            KeyBindingHelper.updateKeyBinding(pressedKey, false, false);
            processedKey = true;
        }
        KeyBinding queuedKey = this.queuedKeys.poll();
        if (queuedKey != null && queuedKey.isNotNull()) {
            KeyBindingHelper.updateKeyBinding(queuedKey, true, true);
            processedKey = true;
            this.pressedKeys.add(queuedKey);
        }
        return processedKey;
    }

    private boolean hasWaterBelow(EntityPlayerMacroBridge fishHook, WorldClient world) {
        double hookX = fishHook.z();
        double hookY = fishHook.N();
        double hookZ = fishHook.h();
        for (double scanY = hookY; scanY >= 0.0 && scanY >= hookY - 3.0; scanY -= 1.0) {
            Block block = world.getBlock(hookX, scanY, hookZ);
            if (block == null || !block.isNotNull()) continue;
            if (BlockUtil.C(block)) {
                return true;
            }
            if (BlockUtil.p(block)) continue;
            return false;
        }
        return false;
    }

    private boolean beginRefill() {
        this.refillPending = true;
        return ItemStackActionPredicate.openInventory();
    }

    @EventHandler
    public void onTick(EventPreTick event) {
        EntityPlayerSP localPlayer = event.getThePlayer();
        WorldClient world = event.getWorld();
        if (!this.isHoldingRod()) {
            InventoryClick pendingClick;
            Slot hotbarRodSlot = ItemStackActionPredicate.findSlotByItemClass(MappedClasses.Yi, MLGImpactState.HOTBAR);
            if (hotbarRodSlot != null && hotbarRodSlot.isNotNull()) {
                if (ItemStackActionPredicate.isAnyScreenOpen()) {
                    if (this.refillPending) {
                        this.cancelRefill();
                    }
                    return;
                }
                InventoryPlayer inventoryPlayer = localPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
                if (inventoryPlayer.isNull()) {
                    return;
                }
                int rodHotbarIndex = hotbarRodSlot.getSlotNumber() - 36;
                int selectedHotbarSlot = inventoryPlayer.v();
                if (selectedHotbarSlot == rodHotbarIndex) {
                    return;
                }
                inventoryPlayer.g(rodHotbarIndex);
                return;
            }
            if (!this.refillRods.getEffectiveValue().booleanValue()) {
                this.setEnabled(false);
                return;
            }
            Slot inventoryRodSlot = ItemStackActionPredicate.findSlotByItemClass(MappedClasses.Yi, MLGImpactState.MAIN_INVENTORY);
            if (inventoryRodSlot == null || inventoryRodSlot.isNull()) {
                this.setEnabled(false);
                return;
            }
            int inventorySlot = inventoryRodSlot.getSlotNumber();
            if (!ItemStackActionPredicate.isInventoryScreenOpen()) {
                if (ItemStackActionPredicate.isAnyScreenOpen()) {
                    if (this.refillPending) {
                        this.cancelRefill();
                    }
                    return;
                }
                this.beginRefill();
                return;
            }
            GuiContainer guiContainer = new GuiContainer(Minecraft.currentScreen().getObject());
            int windowId = guiContainer.getInventorySlots().getWindowId();
            if (this.clickQueue.isEmpty()) {
                this.clickQueue.add(new InventoryClick(windowId, inventorySlot, 0, 2));
                return;
            }
            if (this.clickTimer.hasTimeElapsed((long)this.clickDelay.getRandomValue()) && (pendingClick = this.clickQueue.poll()) != null) {
                this.performClick(pendingClick, windowId);
            }
            return;
        }
        if (ItemStackActionPredicate.isAnyScreenOpen()) {
            if (this.refillPending) {
                this.cancelRefill();
            }
            return;
        }
        if (this.pumpKeyPresses()) {
            return;
        }
        EntityPlayerMacroBridge fishHook = this.getFishHook();
        if (fishHook == null || fishHook.isNull()) {
            this.recast();
            return;
        }
        Entity caughtEntity = fishHook.r$src$Lgg_vape_wrapper_impl_Entity_$18p7x3h();
        if (caughtEntity != null && caughtEntity.isNotNull()) {
            if (this.recastCaught.getEffectiveValue().booleanValue()) {
                this.recast();
            } else {
                this.setEnabled(false);
            }
            return;
        }
        if (!this.isHookInLiquid(fishHook, world)) {
            if (this.recastGround.getEffectiveValue().booleanValue() && this.groundTimer.hasTimeElapsed(RECAST_GROUND_DELAY_MS)) {
                this.recast();
            }
            return;
        }
        this.groundTimer.reset();
        double motionY = fishHook.q();
        if (!this.biteDetected) {
            double motionX = fishHook.t();
            double motionZ = fishHook.T();
            double totalMotion = Math.abs(motionX) + Math.abs(motionY) + Math.abs(motionZ);
            if (totalMotion <= VELOCITY_THRESHOLD) {
                if (this.stationaryTimer.hasTimeElapsed(STATIONARY_DELAY_MS)) {
                    this.biteDetected = true;
                }
            } else {
                this.stationaryTimer.reset();
            }
            return;
        }
        this.accumulatedMotionY = motionY <= -0.1 ? (this.accumulatedMotionY += motionY) : 0.0;
        if (this.accumulatedMotionY <= -VELOCITY_THRESHOLD || this.velocityBite) {
            this.recast();
            this.castTimer.reset();
            this.stationaryTimer.reset();
            this.groundTimer.reset();
            this.biteDetected = false;
            this.velocityBite = false;
        }
    }

    @Nullable
    private EntityPlayerMacroBridge getFishHook() {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNull()) {
            return null;
        }
        EntityPlayerMacroBridge attachedHook = localPlayer.K$src$Lgg_vape_wrapper_impl_EntityPlayerMacroBridge_$1agjn9();
        if (attachedHook.isNotNull()) {
            return attachedHook;
        }
        World world = localPlayer.getWorld();
        if (world.isNull()) {
            return null;
        }
        ArrayList<EntityPlayerMacroBridge> ownedHooks = new ArrayList<>();
        for (Object handle : world.z()) {
            Entity entity = new Entity(handle);
            if (!entity.isInstance(MappedClasses.lM)) continue;
            EntityPlayerMacroBridge hook = new EntityPlayerMacroBridge(entity.getObject());
            if (!localPlayer.equals(hook.A$src$Lgg_vape_wrapper_impl_Entity_$12ijiu4())) continue;
            ownedHooks.add(hook);
        }
        switch (ownedHooks.size()) {
            case 0: {
                return null;
            }
            case 1: {
                return ownedHooks.get(0);
            }
        }
        return null;
    }

    private boolean isHookInBlock(EntityPlayerMacroBridge fishHook, WorldClient world) {
        BlockPos blockPos = fishHook.J$src$Lgg_vape_wrapper_impl_BlockPos_$kv8a0x();
        BlockStateWorldBridge blockState = world.o(blockPos);
        float fluidHeight = 0.0f;
        if (blockState.isTag(MLGBlockWrapper.getWaterBlock())) {
            fluidHeight = blockState.getHeight(world, blockPos);
        }
        return fluidHeight > 0.0f;
    }

    private boolean isHookInWater(EntityPlayerMacroBridge fishHook, WorldClient world) {
        int sampleCount = 5;
        double submergedFraction = 0.0;
        for (int sample = 0; sample < sampleCount; ++sample) {
            AxisAlignedBB hookBounds = fishHook.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
            double height = hookBounds.getMaxY() - hookBounds.getMinY();
            double sampleMinY = hookBounds.getMinY() + height * (double)sample / (double)sampleCount;
            double sampleMaxY = hookBounds.getMinY() + height * (double)(sample + 1) / (double)sampleCount;
            AxisAlignedBB sampleBounds = AxisAlignedBB.create(hookBounds.getMinX(), sampleMinY, hookBounds.getMinZ(), hookBounds.getMaxX(), sampleMaxY, hookBounds.getMaxZ());
            if (!fishHook.getWorld().h(sampleBounds, Material.water())) continue;
            submergedFraction += 1.0 / (double)sampleCount;
        }
        return submergedFraction > 0.0;
    }

    private void recast() {
        EntityPlayerMacroBridge fishHook = this.getFishHook();
        boolean hasActiveHook = fishHook != null && fishHook.isNotNull();
        if (hasActiveHook || !this.hasCast || this.castTimer.hasTimeElapsed(1000L)) {
            this.pressUseKey(Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362());
            this.castTimer.reset();
            this.groundTimer.reset();
            this.stationaryTimer.reset();
            this.hasCast = true;
            this.accumulatedMotionY = 0.0;
        }
    }

    private void pressUseKey(KeyBinding keyBinding) {
        KeyBindingHelper.updateKeyBinding(keyBinding, true, true);
        this.pressedKeys.add(keyBinding);
    }

    public AutoFish() {
        super("AutoFish", 12452021, Category.WORLD, "Automatically fishes for you.");
        this.clickQueue = new ConcurrentLinkedQueue<InventoryClick>();
        this.queuedKeys = new ArrayDeque();
        this.pressedKeys = new ArrayDeque();
        this.recastCaught = BooleanValue.create(this, "Recast caught", false, "Automatically recasts if the hook catches onto an entity");
        this.recastGround = BooleanValue.create(this, "Recast ground", false, "Automatically recasts if the hook hits the ground");
        this.clickTimer = new TimerUtil();
        this.castTimer = new TimerUtil();
        this.stationaryTimer = new TimerUtil();
        this.groundTimer = new TimerUtil();
        this.refillRods = BooleanValue.create(this, "Refill rods", true, "Automatically replaces broken rods with rods from your inventory.");
        this.refillRods.addDependentValues(this.clickDelay);
        this.addValue(this.recastGround, this.recastCaught, this.refillRods, this.clickDelay);
    }

    private boolean isHookInLiquid(EntityPlayerMacroBridge fishHook, WorldClient world) {
        if (fishHook.h$src$Z$ftwoya()) {
            return true;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.isHookInBlock(fishHook, world);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return this.hasWaterBelow1122(fishHook, world);
        }
        return this.isHookInWater(fishHook, world) || this.hasWaterBelow(fishHook, world);
    }

    private void performClick(InventoryClick inventoryClick, int currentWindowId) {
        this.clickTimer.reset();
        int clickWindowId = inventoryClick.getWindowId();
        if (currentWindowId == clickWindowId) {
            inventoryClick.execute();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.resetState();
    }

    private void resetState() {
        this.clickTimer.reset();
        this.castTimer.reset();
        this.stationaryTimer.reset();
        this.groundTimer.reset();
        this.clickQueue.clear();
        this.queuedKeys.clear();
        this.pressedKeys.clear();
        this.hasCast = false;
        this.biteDetected = false;
        this.velocityBite = false;
        this.refillPending = false;
        this.accumulatedMotionY = 0.0;
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        if (!this.biteDetected) {
            return;
        }
        EntityPlayerMacroBridge fishHook = this.getFishHook();
        if (fishHook == null || fishHook.isNull()) {
            return;
        }
        Packet packet = eventPacketReceive.getPacket();
        if (packet.isInstance(MappedClasses.YX)) {
            SPacketEntityVelocity velocityPacket = new SPacketEntityVelocity(packet);
            if (velocityPacket.getEntityId() != fishHook.S()) {
                return;
            }
            int motionX = velocityPacket.getMotionX();
            int motionZ = velocityPacket.getMotionZ();
            double motionY = (double)velocityPacket.getMotionY() / 8000.0;
            if (motionX == 0 && motionZ == 0 && motionY <= -VELOCITY_THRESHOLD) {
                this.velocityBite = true;
            }
            return;
        }
        if (packet.isInstance(MappedClasses.qz) && !packet.isInstance(MappedClasses.uJ)) {
            return;
        }
        if (packet.isInstance(MappedClasses.Dk)) {
            return;
        }
    }

    private boolean hasWaterBelow1122(EntityPlayerMacroBridge fishHook, WorldClient world) {
        return this.hasWaterBelow(fishHook, world);
    }

    private boolean isHoldingRod() {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNull()) {
            return false;
        }
        ItemStack itemStack = localPlayer.getHeldItemHand();
        return !itemStack.isNull() && itemStack.getItem().isInstance(MappedClasses.Yi);
    }
}

