package gg.vape.module.blatant.blockin;

import gg.vape.Vape;
import gg.vape.inventory.InventoryClick;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.combat.AttackPacketTimingTracker;
import gg.vape.module.utility.MLG;
import gg.vape.module.utility.mlg.MLGImpactState;
import gg.vape.module.utility.inventory.ItemStackActionPredicate;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.Container;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.GuiContainer;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockInHotbarSlotHelper {
    private final MLG MLG;

    @Nullable
    private Slot findEmptyBucketSlot(ItemMappingEntry bucketItem) {
        if (!this.MLG.checkInventory.getEffectiveValue().booleanValue()) {
            return null;
        }
        return ItemStackActionPredicate.findSlot(BlockPlacementUtility.createItemPredicate(bucketItem), MLGImpactState.MAIN_INVENTORY);
    }

    @NotNull
    public HotbarSlotResolution placeMlgItem(@Nullable BlockCoordinate targetCoordinate,
                                            @NotNull TimerUtil timeoutTimer) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return HotbarSlotResolution.pending("Player is unavailable");
        }
        ItemStack heldItem = player.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt();
        ItemMappingEntry mlgItem = BlockPlacementUtility.getEmptyBucketItem();
        boolean alreadyHoldingItem = mlgItem.equals(Vape.INSTANCE.getItemStackResolver().resolve(heldItem));
        Slot slot = BlockPlacementUtility.findHotbarSlot(mlgItem);
        if (!alreadyHoldingItem) {
            Slot candidateSlot = slot == null || slot.isNull() ? this.findEmptyBucketSlot(mlgItem) : slot;
            if (candidateSlot == null || candidateSlot.isNull()) {
                return HotbarSlotResolution.failure("No empty buckets available in the inventory");
            }
            HotbarSlotResolutionWithValue<Slot> equipResult = this.equipMlgItem(candidateSlot);
            if (equipResult.isFailure()) {
                return HotbarSlotResolution.failure(String.format("Failed to move item into hand: %s", equipResult.getMessage())).setForced(equipResult.canContinue());
            }
            if (equipResult.isPending()) {
                return HotbarSlotResolution.pending(String.format("Waiting to complete moving item into hand (%s)", equipResult.getMessage()));
            }
            slot = equipResult.getValue();
        }
        long placementDelay = Math.max(AttackPacketTimingTracker.INSTANCE.getAverageHitDelay(), 150L);
        if (!this.MLG.placementTimer.hasTimeElapsed(placementDelay)) {
            return HotbarSlotResolution.pending("Waiting for latency timer to exceed ping / 50ms");
        }
        if (slot == null || slot.isNull()) {
            return HotbarSlotResolution.pending("Waiting to find a Hotbar Slot w/ Empty Bucket.");
        }
        HotbarSlotResolutionWithValue<BlockPos> placementResult =
                this.MLG.placementController.placeItem(BlockPlacementUtility.getEmptyBucketItem(), targetCoordinate, timeoutTimer);
        if (placementResult.isSuccess()) {
            this.MLG.placementTimer.reset();
        }
        return placementResult;
    }

    @Nullable
    private Slot resolveMlgSlot() {
        Slot slot = this.findPreferredMlgSlot();
        if (slot != null) {
            return slot;
        }
        return this.findMlgSlotInInventory();
    }

    @Nullable
    private Slot findPreferredMlgSlot() {
        Slot slot;
        if (this.MLG.useBuckets.getEffectiveValue().booleanValue() && (slot = BlockPlacementUtility.findHotbarSlot(BlockPlacementUtility.getWaterBucketItem())) != null) {
            return slot;
        }
        if (this.MLG.useCobwebs.getEffectiveValue().booleanValue()) {
            return BlockPlacementUtility.findHotbarSlot(BlockPlacementUtility.getCobwebItem());
        }
        return null;
    }

    public BlockInHotbarSlotHelper(MLG MLG) {
        this.MLG = MLG;
    }

    @Nullable
    private Slot findHotbarReplaceSlot() {
        Slot slot = ItemStackActionPredicate.findSlot(BlockInHotbarSlotHelper::isSlotEmpty, MLGImpactState.HOTBAR);
        if (slot == null || slot.isNull()) {
            slot = ItemStackActionPredicate.findSlot(this::isReplaceableBlock, MLGImpactState.HOTBAR);
        }
        return slot;
    }

    private static boolean isSlotEmpty(Slot slot) {
        return slot.getStack().isNull();
    }

    private boolean isReplaceableBlock(Slot slot) {
        return slot.getStack().isNotNull() && this.MLG.nonRemovableItems.doesNotMatch(slot.getStack());
    }

    @Nullable
    private Slot findMlgSlotInInventory() {
        Slot slot;
        if (!this.MLG.checkInventory.getEffectiveValue().booleanValue()) {
            return null;
        }
        if (this.MLG.useBuckets.getEffectiveValue().booleanValue() && (slot = this.findEmptyBucketSlot(BlockPlacementUtility.getWaterBucketItem())) != null) {
            return slot;
        }
        if (this.MLG.useCobwebs.getEffectiveValue().booleanValue()) {
            return this.findEmptyBucketSlot(BlockPlacementUtility.getCobwebItem());
        }
        return null;
    }

    @NotNull
    private HotbarSlotResolutionWithValue<Slot> equipMlgItem(@NotNull Slot slot) {
        HotbarSlotResolutionWithValue<Slot> result = new HotbarSlotResolutionWithValue<>();
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return result.markPending("Player is unavailable");
        }
        boolean isHotbarSlot = slot.getSlotNumber() >= 36 && slot.getSlotNumber() <= 44;
        if (isHotbarSlot) {
            HotbarSlotResolution hotbarSlotResolution = this.MLG.closeInventory();
            if (hotbarSlotResolution.isFailure()) {
                return result.markFailure(String.format("Failed to close GUI before equipping MLG item. Reason: %s", hotbarSlotResolution.getMessage())).setForced(hotbarSlotResolution.canContinue());
            }
            if (hotbarSlotResolution.isPending()) {
                return result.markPending("Waiting for GUI to close before equipping MLG item");
            }
            int hotbarIndex = slot.getSlotNumber() - 36;
            player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(hotbarIndex);
            return result.markSuccess("Equipped MLG Item").setValue(slot);
        }
        if (!this.MLG.checkInventory.getEffectiveValue().booleanValue()) {
            return result.markFailure("MLG Item is in inventory, but inventory use is disabled.");
        }
        Slot slot2 = this.findHotbarReplaceSlot();
        if (slot2 == null || slot2.isNull()) {
            return result.markFailure("No slot to replace found on Hotbar");
        }
        HotbarSlotResolution hotbarSlotResolution = this.MLG.openInventory();
        if (hotbarSlotResolution.isFailure()) {
            return result.markFailure(String.format("Failed to open Inventory GUI before equipping MLG item due to: %s", hotbarSlotResolution.getMessage())).setForced(hotbarSlotResolution.canContinue());
        }
        if (hotbarSlotResolution.isPending()) {
            return result.markPending(String.format("Waiting to open Inventory GUI (state: %s)", hotbarSlotResolution.getMessage()));
        }
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (guiScreen.isNull()) {
            return result.markPending("Current screen is null, cannot enqueue click.");
        }
        Container container = new GuiContainer(guiScreen.getObject()).getInventorySlots();
        if (container.isNull()) {
            return result.markPending("Inventory slots are null, cannot enqueue click.");
        }
        int windowId = container.getWindowId();
        if (this.MLG.clickQueue.isEmpty()) {
            InventoryClick inventoryClick = InventoryClick.builder().window(windowId).slot(slot.getSlotNumber())
                    .swapWithHotbarSlot(slot2.getSlotNumber() - 36).build();
            this.MLG.clickQueue.add(inventoryClick);
        }
        if (!this.MLG.clickQueue.isEmpty() && this.MLG.isInventoryClickReady()) {
            InventoryClick inventoryClick = this.MLG.clickQueue.poll();
            if (inventoryClick != null) {
                this.executeClick(inventoryClick, windowId);
            }
        }
        return result.markPending("Waiting to execute queued click");
    }

    @NotNull
    public HotbarSlotResolutionWithValue<Slot> equipAvailableMlgItem() {
        HotbarSlotResolutionWithValue<Slot> result = new HotbarSlotResolutionWithValue<>();
        Slot slot = this.resolveMlgSlot();
        if (slot == null) {
            return result.markFailure("No MLG Item in Inventory");
        }
        return this.equipMlgItem(slot);
    }


    private void executeClick(@NotNull InventoryClick inventoryClick, int currentWindowId) {
        this.MLG.inventoryClickTimer.reset();
        if (currentWindowId == inventoryClick.getWindowId()) {
            inventoryClick.execute();
        }
    }
}
