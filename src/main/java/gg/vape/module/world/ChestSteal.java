package gg.vape.module.world;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventWindowClick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.utility.inventory.InventoryActionModule;
import gg.vape.module.world.cheststeal.ChestStealInventoryState;
import gg.vape.module.world.cheststeal.ChestStealBestSlotTracker;
import gg.vape.module.world.cheststeal.ChestStealSlotDistanceComparator;
import gg.vape.utils.RandomUtil;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.Container;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GuiChest;
import gg.vape.wrapper.impl.Inventory;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.Slot;
import java.util.ArrayList;
import java.util.Collections;

public class ChestSteal
extends Mod
implements InventoryActionModule {
    private final int[] leggingsItemIds;
    private final TimerUtil clickTimer;
    private final int[] bootsItemIds;
    private boolean performingWindowClick;
    private final BooleanValue shuffle;
    private final TimerUtil screenReadyTimer;
    private final LimitValue blacklist;
    private final BooleanValue keepOpen;
    private final TimerUtil actionDelayTimer;
    private final int[] helmetItemIds;
    private int previousSlot = -1;
    private final RandomValue clickDelay;
    private final ArrayList<Integer> pendingSlots;
    private boolean queueInitialized;
    private boolean waitingToClose;
    private final BooleanValue checkMenu = BooleanValue.create(this, "Check in menu", false, "This will attempt to ignore Menus such as\nServer selectors, and settings inventory menus\nThis may not work 100% on all servers");
    private final BooleanValue bestOnly = BooleanValue.create(this, "Best only", false, "Only takes an item if it is better than what you have equipped.");
    private boolean stealing;
    private final int[] chestplateItemIds;
    private final TimerUtil windowClickGuardTimer;
    private final ChestStealBestSlotTracker bestSlotTracker;

    public int getArmorSlot(ItemStack itemStack) {
        if (ForgeVersion.MC_1_21_4.d()) {
            int armorType = ItemStackScoreUtil.t(itemStack);
            return armorType >= 0 && armorType <= 3 ? armorType + 5 : -1;
        }
        int itemId = itemStack.getItem().P();
        for (int armorItemId : this.bootsItemIds) {
            if (itemId != armorItemId) continue;
            return 8;
        }
        for (int armorItemId : this.leggingsItemIds) {
            if (itemId != armorItemId) continue;
            return 7;
        }
        for (int armorItemId : this.chestplateItemIds) {
            if (itemId != armorItemId) continue;
            return 6;
        }
        for (int armorItemId : this.helmetItemIds) {
            if (itemId != armorItemId) continue;
            return 5;
        }
        return -1;
    }

    private void scanPlayerInventory() {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        if (entityPlayerSP.isNull()) {
            return;
        }
        for (int i = 0; i < 45; ++i) {
            ItemStack itemStack = entityPlayerSP.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getSlot(i).getStack();
            if (itemStack.isNull() || itemStack.getItem().isNull()) continue;
            this.bestSlotTracker.trackCandidate(itemStack, -1);
        }
    }

    private int calculateSlotTravelDelay(int previousSlot, int targetSlot) {
        int targetColumn = targetSlot % 9;
        int targetRow = (targetSlot - targetColumn) / 9;
        int previousColumn = previousSlot % 9;
        int previousRow = (previousSlot - previousColumn) / 9;
        double distance = RotationUtil.r(targetColumn, targetRow, previousColumn, previousRow);
        if (distance < 3.0) {
            distance = 0.0;
        }
        return (int)(distance * 30.0);
    }

    private void collectAllEligibleSlots(GuiChest guiChest) {
        Inventory inventory = guiChest.getLowerChestInventory();
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if (itemStack.isNull() || ForgeVersion.MC_26_1.v() && itemStack.toString().contains("tile.air") || this.blacklist.isValid(itemStack, true) || itemStack.getItem().isInstance(MappedClasses.Di) && this.isHarmfulSplashPotion(itemStack)) continue;
            this.pendingSlots.add(i);
        }
    }

    @Override
    public boolean isPerformingInventoryAction() {
        return this.isEnabled() && this.stealing;
    }

    private void initializeStealQueue() {
        this.actionDelayTimer.reset();
        this.clickTimer.reset();
        this.pendingSlots.clear();
        this.bestSlotTracker.reset();
        GuiChest guiChest = new GuiChest(Minecraft.currentScreen());
        if (this.bestOnly.getEffectiveValue().booleanValue()) {
            Inventory inventory = guiChest.getLowerChestInventory();
            for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = inventory.getStackInSlot(i);
                if (itemStack.isNull() || this.blacklist.isValid(itemStack, true) || this.bestSlotTracker.trackCandidate(itemStack, i) || !itemStack.getItem().isNotNull() || itemStack.getItem().isInstance(MappedClasses.Di) && this.isHarmfulSplashPotion(itemStack)) continue;
                this.pendingSlots.add(i);
            }
        } else {
            this.collectAllEligibleSlots(guiChest);
        }
        if (this.bestOnly.getEffectiveValue().booleanValue()) {
            this.scanPlayerInventory();
            this.pendingSlots.addAll(this.bestSlotTracker.getBestSlotIndexes());
        }
        if (this.shuffle.getEffectiveValue().booleanValue()) {
            Collections.shuffle(this.pendingSlots);
            try {
                this.pendingSlots.sort(new ChestStealSlotDistanceComparator(this.previousSlot));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.queueInitialized = true;
    }

    private boolean isHarmfulSplashPotion(ItemStack itemStack) {
        if (itemStack.getItem().isNotNull() && itemStack.getItem().isInstance(MappedClasses.Di)) {
            ItemSplashPotion itemSplashPotion = new ItemSplashPotion(itemStack.getItem().getObject());
            for (Object e : itemSplashPotion.getRawPotionEffects(itemStack)) {
                PotionEffect potionEffect = new PotionEffect(e);
                if (potionEffect.C() != 19 && potionEffect.C() != 7 && potionEffect.C() != 2 && potionEffect.C() != 18 && potionEffect.C() != 15) continue;
                return true;
            }
        }
        return false;
    }

    public ChestSteal() {
        super("ChestSteal", -208, Category.WORLD, "Take items upon opening a chest");
        this.keepOpen = BooleanValue.create(this, "Keep open", false, "Keep chest open after clearing");
        this.shuffle = BooleanValue.create(this, "Shuffle", false, "Take items in a random order");
        this.clickDelay = RandomValue.createWithIncrement(this, "Click delay", "#", "", 50.0, 150.0, 200.0, 300.0, 5.0);
        this.blacklist = LimitValue.create(this, "cheatsteal-blacklisted", "Blacklisted", LimitValue.BLOCK_LIST_COLOR, Collections.emptyList());
        this.clickTimer = new TimerUtil();
        this.pendingSlots = new ArrayList();
        this.bestSlotTracker = new ChestStealBestSlotTracker(this);
        this.actionDelayTimer = new TimerUtil();
        this.screenReadyTimer = new TimerUtil();
        this.windowClickGuardTimer = new TimerUtil();
        this.addValue(this.checkMenu, this.bestOnly, this.keepOpen, this.shuffle, this.clickDelay, this.blacklist);
        if (ForgeVersion.MC_1_16_5.d()) {
            this.bootsItemIds = new int[]{633, 629, 625, 641, 645, 637};
            this.chestplateItemIds = new int[]{623, 627, 631, 635, 639, 643};
            this.helmetItemIds = new int[]{622, 630, 626, 634, 638, 642};
            this.leggingsItemIds = new int[]{624, 632, 628, 636, 640, 644};
        } else {
            this.bootsItemIds = new int[]{313, 309, 317, 305, 301};
            this.chestplateItemIds = new int[]{311, 307, 315, 303, 299};
            this.helmetItemIds = new int[]{310, 306, 314, 302, 298};
            this.leggingsItemIds = new int[]{312, 308, 316, 304, 300};
        }
    }

    @EventHandler
    public void onWindowClick(EventWindowClick eventWindowClick) {
        if (!this.performingWindowClick) {
            if (!this.windowClickGuardTimer.hasTimeElapsed(100L)) {
                eventWindowClick.setCancelled(true);
            }
            this.screenReadyTimer.reset();
        }
    }

    @EventHandler
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
        if (Vape.INSTANCE.getModManager().isOtherInventoryActionActive(ChestSteal.class) || Vape.INSTANCE.getClientSettings().isLobbyCheckActive()) {
            this.stealing = false;
            return;
        }
        if (Minecraft.currentScreen().isNull() || !Minecraft.currentScreen().isInstance(MappedClasses.qs)) {
            this.resetState();
            return;
        }
        if (Minecraft.thePlayer().isNull() || Minecraft.theWorld().isNull()) {
            return;
        }
        if (RotationUtil.Z().isNotNull()) {
            return;
        }
        if (!this.screenReadyTimer.hasTimeElapsed(150L)) {
            return;
        }
        if (this.waitingToClose) {
            if (this.clickTimer.hasTimeElapsed(RandomUtil.i(this.clickDelay) + 200) && this.actionDelayTimer.hasTimeElapsed(RandomUtil.i(this.clickDelay) + 200)) {
                if (Minecraft.currentScreen().isNotNull()) {
                    Minecraft.thePlayer().Z$src$V$1ie832h();
                }
                this.resetState();
            }
            return;
        }
        GuiChest guiChest = new GuiChest(Minecraft.currentScreen());
        if (this.checkMenu.getEffectiveValue().booleanValue() && !this.isStandardChest(guiChest)) {
            this.resetState();
            return;
        }
        if (!this.keepOpen.getEffectiveValue().booleanValue() && this.isInventoryFull()) {
            this.waitingToClose = true;
            this.clickTimer.reset();
            this.actionDelayTimer.reset();
            this.stealing = false;
            return;
        }
        if (!this.queueInitialized) {
            this.initializeStealQueue();
            return;
        }
        if (!this.pendingSlots.isEmpty()) {
            int slotIndex = this.pendingSlots.get(0);
            int travelDelay = this.calculateSlotTravelDelay(this.previousSlot, slotIndex);
            if (this.clickTimer.hasTimeElapsed(RandomUtil.i(this.clickDelay) + travelDelay) && this.actionDelayTimer.hasTimeElapsed(RandomUtil.i(this.clickDelay) + 100)) {
                this.stealing = true;
                this.previousSlot = slotIndex;
                Slot slot = guiChest.getInventorySlots().getSlot(slotIndex);
                if (slot.hasStack()) {
                    this.performingWindowClick = true;
                    this.windowClickGuardTimer.reset();
                    Minecraft.playerController().O(guiChest.getInventorySlots().getWindowId(), slotIndex, 0, 1, Minecraft.thePlayer());
                    this.performingWindowClick = false;
                }
                this.clickTimer.reset();
                this.pendingSlots.remove(0);
            }
        } else {
            this.stealing = false;
            if (!this.keepOpen.getEffectiveValue().booleanValue()) {
                this.waitingToClose = true;
                this.clickTimer.reset();
            }
            this.previousSlot = -1;
        }
    }

    private boolean isInventoryFull() {
        Container container = Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        for (int i = 9; i <= 44; ++i) {
            ItemStack itemStack = container.getSlot(i).getStack();
            if (!itemStack.isNull() && (!ForgeVersion.MC_26_1.v() || !itemStack.toString().contains("tile.air"))) continue;
            return false;
        }
        return true;
    }

    private boolean isStandardChest(GuiChest guiChest) {
        Inventory inventory = guiChest.getLowerChestInventory();
        String chestTitle = guiChest.getTitle();
        if (ForgeVersion.MC_1_16_5.d()) {
            String standardChestTitle = ChestStealInventoryState.createTranslation("container.chest", new Object[0]).getFormattedText().toLowerCase();
            String doubleChestTitle = ChestStealInventoryState.createTranslation("container.chestDouble", new Object[0]).getFormattedText().toLowerCase();
            return chestTitle.equalsIgnoreCase(standardChestTitle) || chestTitle.equalsIgnoreCase(doubleChestTitle);
        }
        String standardChestTitle = ChestStealInventoryState.createTranslation("container.chest", new Object[0]).a().toLowerCase();
        String doubleChestTitle = ChestStealInventoryState.createTranslation("container.chestDouble", new Object[0]).a().toLowerCase();
        return !inventory.hasCustomInventoryName() || chestTitle.equalsIgnoreCase(standardChestTitle) || chestTitle.equalsIgnoreCase(doubleChestTitle);
    }

    private void resetState() {
        this.actionDelayTimer.reset();
        this.clickTimer.reset();
        this.pendingSlots.clear();
        this.waitingToClose = false;
        this.previousSlot = -1;
        this.stealing = false;
        this.queueInitialized = false;
    }
}
