package gg.vape.module.utility;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.inventory.InventoryClick;
import gg.vape.inventory.InventoryClickQueue;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.utility.inventory.InventoryActionGuard;
import gg.vape.module.utility.inventory.InventoryActionModule;
import gg.vape.module.utility.inventory.cleaner.ArmorSlotInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.HiddenInventoryItemMatchers;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfileValue;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterAction;
import gg.vape.module.utility.inventory.cleaner.InventoryItemCategoryRegistry;
import gg.vape.module.utility.inventory.cleaner.ItemInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.SlotInventoryFilterRule;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.Container;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GuiContainer;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Slot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryManager
extends Mod
implements InventoryActionModule {
    private boolean pendingClose;
    private final ModeValue activationMode;
    private boolean keyPressed;
    private final TimerUtil openTimer;
    private final TimerUtil clickTimer;
    private String[] helmetKeywords;
    private GuiScreen lastScreen = null;
    private static final long MODULE_ID = 6780151590972480718L;
    private String[] leggingsKeywords;
    private final InventoryActionGuard combatGuard;
    private final TimerUtil closeTimer;
    private final RandomValue clickDelayValue = RandomValue.create(this, "Click delay", "#", "", 50.0, 100.0, 150.0, 300.0);
    private final ModeOption onKeyMode = new ModeOption("On Key");
    private final ModeOption toggleMode = new ModeOption("Toggle");
    private final List<Integer> touchedSlots;
    private String[] chestplateKeywords;
    private boolean needsScan = false;
    private boolean forceClose = false;
    private final BooleanValue combatCheckValue;
    private final InventoryCleanerProfileValue profileValue;
    private boolean idle = false;
    private String[] bootsKeywords;
    private final BooleanValue openInventoryValue;
    private final Queue<InventoryClick> clickQueue;

    private boolean processClickQueue() {
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (guiScreen.isNotNull() && guiScreen.isInstance(MappedClasses.YS) && !this.clickQueue.isEmpty()) {
            if (!this.openTimer.hasTimeElapsed(200 + ThreadLocalRandom.current().nextInt(200))) {
                return true;
            }
            if (this.clickTimer.hasTimeElapsed((long)this.clickDelayValue.getRandomValue())) {
                InventoryClick inventoryClick = this.clickQueue.poll();
                if (inventoryClick != null) {
                    inventoryClick.execute();
                }
                this.clickTimer.reset();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isPerformingInventoryAction() {
        return this.isEnabled() && !this.clickQueue.isEmpty() && (this.openInventoryValue.getEffectiveValue() != false || Minecraft.currentScreen().isNull());
    }

    @Override
    public boolean isRequiresBind() {
        return this.activationMode.getValue() == this.onKeyMode;
    }

    private Collection<SlotInventoryFilterRule> buildSlotRules() {
        ArrayList<SlotInventoryFilterRule> slotRules = new ArrayList<SlotInventoryFilterRule>(((InventoryCleanerProfile)this.profileValue.getValue()).getSlotRules());
        if (((InventoryCleanerProfile)this.profileValue.getValue()).bestArmor.isSelected()) {
            slotRules.add(new ArmorSlotInventoryFilterRule(0));
            slotRules.add(new ArmorSlotInventoryFilterRule(1));
            slotRules.add(new ArmorSlotInventoryFilterRule(2));
            slotRules.add(new ArmorSlotInventoryFilterRule(3));
        }
        return slotRules;
    }

    private boolean isSlotEmpty(Slot slot) {
        ItemStack itemStack = slot.getStack();
        return itemStack.isNull();
    }

    private boolean isTargetSlotEmpty(SlotInventoryFilterRule slotInventoryFilterRule) {
        Container container;
        Wrapper wrapper;
        if (Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
            wrapper = new GuiContainer(Minecraft.currentScreen());
            container = ((GuiContainer)wrapper).getInventorySlots();
        } else {
            container = Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        }
        if (slotInventoryFilterRule instanceof ArmorSlotInventoryFilterRule) {
            wrapper = container.getSlot(slotInventoryFilterRule.getContainerSlot());
            return ((Slot)wrapper).getStack().isNull();
        }
        for (int i = 0; i < 9; ++i) {
            Slot slot = container.getSlot(36 + i);
            if (!slot.getStack().isNull()) continue;
            return i == slotInventoryFilterRule.getSlot();
        }
        return false;
    }

    private boolean shouldSkipProcessing() {
        if (Vape.INSTANCE.getModManager().isOtherInventoryActionActive(InventoryManager.class) || Vape.INSTANCE.getClientSettings().isLobbyCheckActive()) {
            this.clickQueue.clear();
            this.clickTimer.reset();
            this.touchedSlots.clear();
            return true;
        }
        if (Minecraft.thePlayer().C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isCreativeMode()) {
            return true;
        }
        InventoryCleanerProfile inventoryCleanerProfile = (InventoryCleanerProfile)this.profileValue.getValue();
        return inventoryCleanerProfile == null;
    }

    private void enqueueCleanupClicks() {
        if (RotationUtil.Z().isNotNull()) {
            this.clickQueue.clear();
            InventoryClickQueue.enqueueDropMouseStack(0, this.clickQueue);
            return;
        }
        InventoryCleanerProfile inventoryCleanerProfile = (InventoryCleanerProfile)this.profileValue.getValue();
        Container container = Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        if (inventoryCleanerProfile.getItemRules().isEmpty()) {
            return;
        }
        ArrayList<Slot> inventorySlots = new ArrayList<Slot>(container.getInventorySlots());
        LinkedHashMap<ItemInventoryFilterRule, List<Slot>> slotsByRule = new LinkedHashMap<>();
        for (Slot slot : inventorySlots) {
            SlotInventoryFilterRule slotInventoryFilterRule;
            ItemInventoryFilterRule itemInventoryFilterRule;
            ItemStack itemStack = slot.getStack();
            if (itemStack.isNull() || itemStack.getItem().isNull() || (itemInventoryFilterRule = inventoryCleanerProfile.findMatchingItemRule(itemStack)) == null || !itemInventoryFilterRule.matches(itemStack) || slot.getSlotNumber() >= 36 && slot.getSlotNumber() <= 44 && (slotInventoryFilterRule = inventoryCleanerProfile.getOrCreateSlotRule(slot.getSlotNumber() - 36)).getItemSelection().matches(itemStack) && slotInventoryFilterRule.matches(itemStack) || this.touchedSlots.contains(slot.getSlotNumber())) continue;
            if (itemInventoryFilterRule.getAction() == InventoryFilterAction.REMOVE) {
                InventoryClickQueue.enqueueDropSlot(slot.getSlotNumber(), container.getWindowId(), this.clickQueue);
                continue;
            }
            if (itemInventoryFilterRule.getAction() != InventoryFilterAction.CONDENSE) continue;
            slotsByRule.computeIfAbsent(itemInventoryFilterRule, InventoryManager::createSlotList).add(slot);
        }
        this.condenseInventory(slotsByRule, container.getWindowId());
    }

    private boolean hasEmptyInventorySlot() {
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (!guiScreen.isInstance(MappedClasses.YS)) {
            return false;
        }
        GuiContainer guiContainer = new GuiContainer(guiScreen);
        Container container = guiContainer.getInventorySlots();
        for (int slotIndex = 9; slotIndex < 36; ++slotIndex) {
            Slot slot = container.getSlot(slotIndex);
            ItemStack itemStack = slot.getStack();
            if (!itemStack.isNull()) continue;
            return true;
        }
        return false;
    }

    private int getSlotItemValue(int slotIndex) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        ItemStack itemStack = localPlayer.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getSlot(slotIndex).getStack();
        return itemStack.isNotNull() ? itemStack.L() : 999;
    }

    private boolean handlePendingClose() {
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (!this.clickQueue.isEmpty() && this.keyPressed && (guiScreen.isNull() || !guiScreen.isInstance(MappedClasses.YS))) {
            this.forceClose = true;
            this.finishManaging(false);
            return true;
        }
        return false;
    }

    public InventoryManager() {
        super("InventoryManager", (int)MODULE_ID, Category.INVENTORY, "Manage your inventory");
        this.activationMode = ModeValue.create((Object)this, "Activation", this.onKeyMode, this.onKeyMode, this.toggleMode);
        this.openInventoryValue = BooleanValue.create(this, "Open inventory", true, "If on, inventory will automatically be opened when inventory needs to be managed\nIf off, inventory will only be managed after inventory is manually opened");
        this.combatCheckValue = BooleanValue.create(this, "Combat check", false);
        this.profileValue = InventoryCleanerProfileValue.create(this, "Inventory");
        this.clickQueue = new ConcurrentLinkedQueue<InventoryClick>();
        this.touchedSlots = new ArrayList<Integer>();
        this.clickTimer = new TimerUtil();
        this.openTimer = new TimerUtil();
        this.closeTimer = new TimerUtil();
        this.combatGuard = new InventoryActionGuard(20);
        this.activationMode.addActiveMode(this.openInventoryValue, this.toggleMode);
        this.activationMode.addActiveMode(this.combatCheckValue, this.toggleMode);
        this.addValue(this.activationMode, this.openInventoryValue, this.combatCheckValue, this.clickDelayValue, this.profileValue);
        this.helmetKeywords = new String[]{"cap", "helmet"};
        this.chestplateKeywords = new String[]{"tunic", "chestplate"};
        this.leggingsKeywords = new String[]{"pants", "leggings"};
        this.bootsKeywords = new String[]{"boots"};
    }


    private static List<Slot> createSlotList(ItemInventoryFilterRule ignored) {
        return new ArrayList<>();
    }

    @Override
    public void onEnable() {
        this.reset();
        if (this.activationMode.getValue() == this.onKeyMode) {
            this.needsScan = true;
        }
    }

    private void validateRotationItem() {
        if (RotationUtil.Z().isNull()) {
            return;
        }
        if (this.hasEmptyInventorySlot()) {
            return;
        }
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (guiScreen.isNull()) {
            return;
        }
        GuiContainer guiContainer = new GuiContainer(guiScreen);
        Container container = guiContainer.getInventorySlots();
        ItemStack itemStack = RotationUtil.Z();
        boolean foundBetter = false;
        for (SlotInventoryFilterRule slotInventoryFilterRule : this.buildSlotRules()) {
            int comparison;
            if (!slotInventoryFilterRule.getItemSelection().matches(itemStack) || !slotInventoryFilterRule.matches(itemStack)) continue;
            Slot slot = container.getSlot(slotInventoryFilterRule.getContainerSlot());
            Comparator<ItemStack> comparator = slotInventoryFilterRule.getPriority().getComparator();
            if (slotInventoryFilterRule.getPriority().equals(InventoryItemCategoryRegistry.FIRST_AVAILABLE) || comparator != null && (comparison = comparator.compare(slot.getStack(), itemStack)) >= 0) continue;
            foundBetter = true;
            break;
        }
        if (!foundBetter) {
            this.clickQueue.clear();
            InventoryClickQueue.enqueueDropMouseStack(0, this.clickQueue);
            return;
        }
    }

    private void condenseInventory(Map<ItemInventoryFilterRule, List<Slot>> slotsByRule, int windowId) {
        for (Map.Entry<ItemInventoryFilterRule, List<Slot>> entry : slotsByRule.entrySet()) {
            List<Slot> matchingSlots = entry.getValue();
            matchingSlots.removeIf(InventoryManager::isFullOrEmptyStack);
            if (matchingSlots.size() <= 1) continue;
            matchingSlots.sort(InventoryManager::compareStackSizes);
            ArrayList<Integer> consumedSlots = new ArrayList<Integer>();
            slotLoop: for (int targetIndex = 0; targetIndex < matchingSlots.size(); ++targetIndex) {
                Slot targetSlot = matchingSlots.get(targetIndex);
                ItemStack targetStack = targetSlot.getStack();
                int targetCount = targetStack.t();
                if (consumedSlots.contains(targetSlot.getSlotNumber())) continue;
                for (int sourceIndex = targetIndex + 1; sourceIndex < matchingSlots.size(); ++sourceIndex) {
                    Slot sourceSlot = matchingSlots.get(sourceIndex);
                    if (targetSlot.getSlotNumber() == sourceSlot.getSlotNumber()) continue;
                    ItemStack sourceStack = sourceSlot.getStack();
                    int sourceCount = sourceStack.t();
                    if (!targetStack.getItem().equals(sourceStack.getItem())) continue;
                    int combinedCount = targetCount + sourceCount;
                    int maxStackSize = targetStack.P();
                    if (combinedCount <= maxStackSize) {
                        consumedSlots.add(sourceSlot.getSlotNumber());
                        InventoryClickQueue.enqueueMove(sourceSlot.getSlotNumber(), targetSlot.getSlotNumber(), windowId, this.clickQueue);
                        continue slotLoop;
                    }
                    int remainingCapacity = maxStackSize - targetCount;
                    InventoryClickQueue.enqueueClick(sourceSlot.getSlotNumber(), targetSlot.getSlotNumber(), windowId, this.clickQueue);
                    InventoryClickQueue.enqueueClick(targetSlot.getSlotNumber(), sourceSlot.getSlotNumber(), windowId, this.clickQueue);
                    InventoryClickQueue.enqueueClick(sourceSlot.getSlotNumber(), targetSlot.getSlotNumber(), windowId, this.clickQueue);
                    if (remainingCapacity == 0) continue slotLoop;
                }
            }
        }
    }

    @Override
    public void onDisable() {
        this.reset();
    }

    private int findBestSourceSlot(int targetSlot, boolean tieBreak) {
        int bestSlot = -1;
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        ItemStack equippedStack = localPlayer.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getSlot(targetSlot).getStack();
        double score = 0.0;
        double itemValue = 999.0;
        if (equippedStack.isNotNull()) {
            score = ItemStackScoreUtil.L(equippedStack);
            itemValue = this.getSlotItemValue(targetSlot);
        }
        double bestScore = score;
        double bestValue = itemValue;
        for (int candidateSlot = 9; candidateSlot < 45; ++candidateSlot) {
            ItemStack candidateStack = localPlayer.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getSlot(candidateSlot).getStack();
            if (!candidateStack.isNotNull() || this.getArmorSlotForItem(candidateStack) != targetSlot) continue;
            double candidateScore = ItemStackScoreUtil.L(candidateStack);
            double candidateValue = this.getSlotItemValue(candidateSlot);
            if (candidateScore > bestScore) {
                bestScore = candidateScore;
                bestSlot = candidateSlot;
                bestValue = candidateValue;
                continue;
            }
            if (!tieBreak || candidateScore != bestScore || !(candidateValue < bestValue)) continue;
            bestValue = candidateValue;
            bestSlot = candidateSlot;
        }
        return bestSlot;
    }

    private List<Slot> getOtherValid(Container container, Slot targetSlot, SlotInventoryFilterRule slotRule) {
        int bestSlot;
        List<Slot> inventorySlots = container.getInventorySlots();
        if (slotRule instanceof ArmorSlotInventoryFilterRule && (bestSlot = this.findBestSourceSlot(slotRule.getContainerSlot(), false)) != -1 && !this.touchedSlots.contains(bestSlot)) {
            return Collections.singletonList(inventorySlots.get(bestSlot));
        }
        ArrayList<Slot> validSlots = new ArrayList<Slot>();
        ItemStack lastValidStack = null;
        for (int slotIndex = 9; slotIndex < inventorySlots.size(); ++slotIndex) {
            Slot candidateSlot = container.getSlot(slotIndex);
            ItemStack candidateStack = candidateSlot.getStack();
            if (this.touchedSlots.contains(slotIndex) || !candidateStack.isNotNull() || !slotRule.getItemSelection().matches(candidateStack) || !slotRule.matches(candidateStack)) continue;
            lastValidStack = candidateStack;
            validSlots.add(candidateSlot);
        }
        Comparator<ItemStack> itemComparator = slotRule.getPriority().getComparator();
        if (lastValidStack != null && lastValidStack.getItem().isNotNull() && itemComparator != null) {
            validSlots.sort((first, second) -> InventoryManager.compareSlotsByItemComparator(itemComparator, first, second));
            Collections.reverse(validSlots);
        }
        return validSlots;
    }

    @EventHandler
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
        if (this.shouldSkipProcessing()) {
            return;
        }
        if (this.handlePendingClose()) {
            return;
        }
        if (this.shouldSkipToggle()) {
            return;
        }
        if (this.pendingClose) {
            if (this.closeTimer.hasTimeElapsed(200 + ThreadLocalRandom.current().nextInt(200))) {
                this.finishManaging(true);
            }
            return;
        }
        if (this.activationMode.getValue() == this.toggleMode && this.combatCheckValue.getEffectiveValue().booleanValue()) {
            this.combatGuard.update(eventPrePlayerTick.getPlayer());
            if (this.combatGuard.isBlocked()) {
                this.needsScan = false;
                this.beginClose();
                this.openTimer.reset();
                return;
            }
        }
        if (this.activationMode.getValue() == this.toggleMode && this.clickQueue.isEmpty()) {
            this.needsScan = true;
        }
        if (this.processClickQueue()) {
            if (this.clickQueue.isEmpty()) {
                this.computeInventoryClicks();
                this.needsScan = false;
                if (this.clickQueue.isEmpty()) {
                    this.enqueueCleanupClicks();
                }
                if (this.clickQueue.isEmpty()) {
                    this.beginClose();
                }
            }
        } else if (!this.needsScan) {
            this.validateRotationItem();
            if (this.clickQueue.isEmpty()) {
                this.beginClose();
                return;
            }
        }
        if (this.needsScan) {
            this.computeInventoryClicks();
            this.needsScan = false;
            if (this.clickQueue.isEmpty()) {
                this.enqueueCleanupClicks();
            }
            if (this.clickQueue.isEmpty()) {
                this.idle = true;
            }
            if (this.activationMode.getValue() != this.toggleMode && this.clickQueue.isEmpty()) {
                this.beginClose();
                Vape.INSTANCE.getNotificationManager().showInfo("Inventory Manager", "No work available", 4000L);
                return;
            }
        }
        if ((this.activationMode.getValue() == this.onKeyMode || this.openInventoryValue.getEffectiveValue().booleanValue()) && this.openInventoryIfNeeded()) {
            return;
        }
    }

    private static int compareSlotsByItemComparator(Comparator<ItemStack> comparator, Slot first, Slot second) {
        return comparator.compare(first.getStack(), second.getStack());
    }

    private void beginClose() {
        this.pendingClose = true;
        this.closeTimer.reset();
    }

    private static int compareStackSizes(Slot first, Slot second) {
        ItemStack firstStack = first.getStack();
        ItemStack secondStack = second.getStack();
        return Integer.compare(firstStack.isNull() ? 0 : firstStack.t(), secondStack.isNull() ? 0 : secondStack.t());
    }

    private void reset() {
        this.clickTimer.reset();
        this.clickQueue.clear();
        this.touchedSlots.clear();
        this.pendingClose = false;
        this.keyPressed = false;
        this.forceClose = false;
    }

    @Override
    public boolean q$src$Z$12h8h4c() {
        if (this.onKeyMode.isSelected()) {
            return false;
        }
        return super.q$src$Z$12h8h4c();
    }

    private static boolean isFullOrEmptyStack(Slot slot) {
        ItemStack itemStack = slot.getStack();
        return itemStack.isNull() || itemStack.t() >= itemStack.P();
    }

    private boolean queueEmptyInventoryClick() {
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (!guiScreen.isInstance(MappedClasses.YS)) {
            return false;
        }
        GuiContainer guiContainer = new GuiContainer(guiScreen);
        Container container = guiContainer.getInventorySlots();
        for (int i = 9; i < 36; ++i) {
            Slot slot = container.getSlot(i);
            ItemStack itemStack = slot.getStack();
            if (!itemStack.isNull()) continue;
            InventoryClickQueue.enqueueClick(i, 0, container.getWindowId(), this.clickQueue);
            return true;
        }
        return false;
    }

    @Override
    public String getDetailedSuffix() {
        if (this.combatCheckValue.getEffectiveValue().booleanValue() && this.combatGuard.isBlocked()) {
            return ClientSettings.FORMAT_CODE + "c[In Combat]";
        }
        return super.getDetailedSuffix();
    }

    private boolean shouldSkipToggle() {
        if (this.activationMode.getValue() != this.toggleMode) {
            return false;
        }
        if (this.openInventoryValue.getEffectiveValue().booleanValue()) {
            return false;
        }
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (this.lastScreen == null) {
            this.lastScreen = guiScreen;
        }
        if (!this.lastScreen.equals(guiScreen)) {
            this.idle = false;
        }
        if (this.idle) {
            return true;
        }
        this.lastScreen = guiScreen;
        return false;
    }

    private int getArmorSlotForItem(ItemStack itemStack) {
        ItemMappingEntry itemMappingEntry = Vape.INSTANCE.getItemStackResolver().resolve(itemStack);
        for (String string : this.bootsKeywords) {
            if (!itemMappingEntry.getResourceKey().toLowerCase().contains(string)) continue;
            return 8;
        }
        for (String string : this.leggingsKeywords) {
            if (!itemMappingEntry.getResourceKey().toLowerCase().contains(string)) continue;
            return 7;
        }
        for (String string : this.chestplateKeywords) {
            if (!itemMappingEntry.getResourceKey().toLowerCase().contains(string)) continue;
            return 6;
        }
        for (String string : this.helmetKeywords) {
            if (!itemMappingEntry.getResourceKey().toLowerCase().contains(string)) continue;
            return 5;
        }
        return -1;
    }

    private boolean openInventoryIfNeeded() {
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (guiScreen.isNotNull()) {
            return false;
        }
        if (!this.clickQueue.isEmpty() && guiScreen.isNull()) {
            KeyBinding keyBinding = Minecraft.gameSettings().j();
            if (ForgeVersion.MC_1_16_5.d()) {
                KeyBindingHelper.incrementPressTime(keyBinding);
            } else {
                KeyBindingHelper.setPressedAndTick(keyBinding, true);
                KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
            }
            this.keyPressed = true;
            this.openTimer.reset();
            return true;
        }
        return false;
    }

    private void computeInventoryClicks() {
        this.validateRotationItem();
        this.touchedSlots.clear();
        InventoryCleanerProfile inventoryCleanerProfile = (InventoryCleanerProfile)this.profileValue.getValue();
        Container container = Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        for (SlotInventoryFilterRule slotInventoryFilterRule : this.buildSlotRules()) {
            Slot targetSlot;
            if (HiddenInventoryItemMatchers.ANY_ITEM.equals(slotInventoryFilterRule.getItemSelection().getMatcher())) {
                this.touchedSlots.add(slotInventoryFilterRule.getContainerSlot());
            }
            targetSlot = container.getSlot(slotInventoryFilterRule.getContainerSlot());
            ItemStack targetStack = targetSlot.getStack();
            if (targetStack.isNull() || !(slotInventoryFilterRule instanceof ArmorSlotInventoryFilterRule) && (!slotInventoryFilterRule.getItemSelection().matches(targetStack) || !slotInventoryFilterRule.matches(targetStack))) continue;
            List<Slot> validSlots = this.getOtherValid(container, targetSlot, slotInventoryFilterRule);
            if (!validSlots.isEmpty()) {
                Slot bestSlot = validSlots.get(0);
                if (bestSlot.getSlotNumber() != targetSlot.getSlotNumber()) continue;
                this.touchedSlots.add(slotInventoryFilterRule.getContainerSlot());
                continue;
            }
            if (!(slotInventoryFilterRule instanceof ArmorSlotInventoryFilterRule)) continue;
            this.touchedSlots.add(slotInventoryFilterRule.getContainerSlot());
        }
        for (SlotInventoryFilterRule slotInventoryFilterRule : this.buildSlotRules()) {
            int comparison;
            int targetSlotIndex = slotInventoryFilterRule.getContainerSlot();
            Slot targetSlot = container.getSlot(targetSlotIndex);
            ItemStack currentStack = targetSlot.getStack();
            List<Slot> validSlots = this.getOtherValid(container, targetSlot, slotInventoryFilterRule);
            if (currentStack.isNotNull() && currentStack.getItem().isNotNull()) {
                if (slotInventoryFilterRule.getItemSelection().matches(currentStack) && slotInventoryFilterRule.matches(currentStack)) {
                    if (validSlots.size() <= 1 || targetSlot.getSlotNumber() == validSlots.get(0).getSlotNumber()) continue;
                    Slot bestSlot = validSlots.get(0);
                    Comparator<ItemStack> comparator = slotInventoryFilterRule.getPriority().getComparator();
                    if (slotInventoryFilterRule.getPriority().equals(InventoryItemCategoryRegistry.FIRST_AVAILABLE)) continue;
                    if (comparator != null && (comparison = comparator.compare(targetSlot.getStack(), bestSlot.getStack())) >= 0) {
                        this.touchedSlots.add(slotInventoryFilterRule.getContainerSlot());
                        continue;
                    }
                }
                ItemInventoryFilterRule itemRule = inventoryCleanerProfile.findMatchingItemRule(targetSlot.getStack());
                if (!(slotInventoryFilterRule instanceof ArmorSlotInventoryFilterRule) && itemRule != null && itemRule.matches(targetSlot.getStack()) && itemRule.getAction() == InventoryFilterAction.MOVE) {
                    InventoryClickQueue.enqueueShiftClick(targetSlot.getSlotNumber(), container.getWindowId(), this.clickQueue);
                }
            }
            if (validSlots.isEmpty()) continue;
            Slot selectedSlot = validSlots.get(0);
            if (selectedSlot.equals(targetSlot)) continue;
            this.touchedSlots.add(slotInventoryFilterRule.getContainerSlot());
            boolean swapRequired = !this.isSlotEmpty(targetSlot);
            boolean shiftClick = this.isTargetSlotEmpty(slotInventoryFilterRule) && (selectedSlot.getSlotNumber() < 36 || slotInventoryFilterRule instanceof ArmorSlotInventoryFilterRule);
            if (shiftClick) {
                InventoryClickQueue.enqueueShiftClick(selectedSlot.getSlotNumber(), container.getWindowId(), this.clickQueue);
            } else if (swapRequired) {
                InventoryClickQueue.enqueueSwap(selectedSlot.getSlotNumber(), targetSlotIndex, container.getWindowId(), this.clickQueue);
            } else {
                InventoryClickQueue.enqueueMove(selectedSlot.getSlotNumber(), targetSlotIndex, container.getWindowId(), this.clickQueue);
            }
            this.touchedSlots.add(selectedSlot.getSlotNumber());
        }
    }

    public InventoryCleanerProfileValue getProfileValue() {
        return this.profileValue;
    }

    private void finishManaging(boolean closeScreen) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        ItemStack carriedStack = RotationUtil.Z();
        if (!this.forceClose && carriedStack.isNotNull() && this.queueEmptyInventoryClick()) {
            this.pendingClose = false;
            return;
        }
        if (this.activationMode.getValue() == this.onKeyMode) {
            this.setEnabled(false);
        }
        if (closeScreen && !Minecraft.currentScreen().isNull() && (this.activationMode.getValue() == this.onKeyMode || this.openInventoryValue.getEffectiveValue().booleanValue())) {
            localPlayer.Z$src$V$1ie832h();
            this.pendingClose = false;
        }
        this.clickQueue.clear();
        this.clickTimer.reset();
        this.touchedSlots.clear();
        this.pendingClose = false;
    }
}
