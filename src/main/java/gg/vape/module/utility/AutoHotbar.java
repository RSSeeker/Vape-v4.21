package gg.vape.module.utility;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.inventory.InventoryClick;
import gg.vape.inventory.InventoryClickAction;
import gg.vape.inventory.InventoryClickQueue;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.utility.inventory.ArmorSlotComparator;
import gg.vape.module.utility.inventory.BowSlotComparator;
import gg.vape.module.utility.inventory.HotbarSlotRule;
import gg.vape.module.utility.inventory.HotbarSlotRuleValue;
import gg.vape.module.utility.inventory.InventoryActionModule;
import gg.vape.module.utility.inventory.InventoryManagerPrimaryItemScoreComparator;
import gg.vape.module.utility.inventory.InventoryManagerSecondaryItemScoreComparator;
import gg.vape.module.utility.inventory.FoodSlotComparator;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Container;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GuiContainer;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Slot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoHotbar
extends Mod
implements InventoryActionModule {
    private final NumberValue delayMs = NumberValue.create(this, "Delay", "#", "", 0.0, 110.0, 300.0);
    private final List<Integer> touchedSlots;
    private boolean pendingClose;
    private final TimerUtil clickTimer;
    private final ModeOption toggleMode;
    private final ModeValue activationMode;
    private final HashMap<Class<?>, Comparator<Slot>> comparators;
    private boolean pendingOpen = true;
    private final ModeOption onKeyMode;
    private final HotbarSlotRuleValue hotbarRules;
    private boolean didClick = false;
    private final BooleanValue openInventoryOption;
    private final Queue<InventoryClick> clickQueue = new ConcurrentLinkedQueue<InventoryClick>();

    private boolean hasItem(Slot slot) {
        ItemStack itemStack = slot.getStack();
        if (itemStack.isNotNull()) {
            Item item = itemStack.getItem();
            return item.isNotNull();
        }
        return false;
    }

    private void registerComparators() {
        Comparator<Slot> primaryItemComparator = new InventoryManagerPrimaryItemScoreComparator(this);
        Comparator<Slot> bowComparator = new BowSlotComparator(this);
        Comparator<Slot> secondaryItemComparator = new InventoryManagerSecondaryItemScoreComparator(this);
        Comparator<Slot> armorComparator = new ArmorSlotComparator(this);
        Comparator<Slot> foodComparator = new FoodSlotComparator(this);
        this.comparators.put(MappedClasses.V5, primaryItemComparator);
        this.comparators.put(MappedClasses.YP, primaryItemComparator);
        this.comparators.put(MappedClasses.DU, bowComparator);
        this.comparators.put(MappedClasses.FM, bowComparator);
        this.comparators.put(MappedClasses.Vl, secondaryItemComparator);
        this.comparators.put(MappedClasses.Di, armorComparator);
        this.comparators.put(MappedClasses.ITEM_FOOD, foodComparator);
    }

    private boolean queueEmptyHotbarClick() {
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (!guiScreen.isInstance(MappedClasses.YS)) {
            return false;
        }
        GuiContainer guiContainer = new GuiContainer(guiScreen);
        Container container = guiContainer.getInventorySlots();
        for (int inventorySlot = 9; inventorySlot < 36; ++inventorySlot) {
            Slot slot = container.getSlot(inventorySlot);
            ItemStack itemStack = slot.getStack();
            if (!itemStack.isNull()) continue;
            new InventoryClickQueue(InventoryClickAction.CLICK, inventorySlot, 0).appendTo(container.getWindowId(), this.clickQueue);
            return true;
        }
        return false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.pendingOpen = false;
        this.pendingClose = false;
        this.clickQueue.clear();
        this.clickTimer.reset();
        this.touchedSlots.clear();
    }

    @Override
    public boolean isRequiresBind() {
        return this.activationMode.getValue() == this.onKeyMode;
    }

    public AutoHotbar() {
        super("AutoHotbar", -6656, Category.INVENTORY, "Automatically arranges hotbar to your liking.\nDoes not work in creative.");
        this.clickTimer = new TimerUtil();
        this.comparators = new HashMap<Class<?>, Comparator<Slot>>();
        this.touchedSlots = new ArrayList<Integer>();
        this.onKeyMode = new ModeOption("On Key");
        this.toggleMode = new ModeOption("Toggle");
        this.activationMode = ModeValue.create((Object)this, "Activation", this.onKeyMode, this.onKeyMode, this.toggleMode);
        this.openInventoryOption = BooleanValue.create(this, "Open Inventory", true);
        this.setDefaultVisibility(false);
        this.hotbarRules = HotbarSlotRuleValue.create(this, "hotbar");
        this.activationMode.addActiveMode(this.openInventoryOption, this.toggleMode);
        this.addValue(this.activationMode, this.openInventoryOption, this.delayMs, this.hotbarRules);
        this.registerComparators();
    }

    private Slot findBestMatchingSlot(Slot targetSlot, HotbarSlotRule hotbarSlotRule) {
        Container container;
        if (Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
            GuiContainer inventoryScreen = new GuiContainer(Minecraft.currentScreen());
            container = inventoryScreen.getInventorySlots();
        } else {
            container = Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        }
        List<Slot> matchingSlots = new ArrayList<Slot>();
        if (hotbarSlotRule.matches(targetSlot.getStack())) {
            matchingSlots.add(targetSlot);
        }
        for (Slot candidateSlot : container.getInventorySlots()) {
            if (!hotbarSlotRule.matches(candidateSlot.getStack()) || this.touchedSlots.contains(candidateSlot.getSlotNumber()) || matchingSlots.contains(candidateSlot)) continue;
            matchingSlots.add(candidateSlot);
        }
        if (!matchingSlots.isEmpty()) {
            if (hotbarSlotRule.isOnlyBest()) {
                Item item = hotbarSlotRule.getItem();
                Class<?> itemClass = item.getObject().getClass();
                if (this.comparators.containsKey(itemClass)) {
                    Comparator<Slot> comparator = this.comparators.get(itemClass);
                    matchingSlots.sort(comparator);
                    Collections.reverse(matchingSlots);
                    if (hotbarSlotRule.matches(targetSlot.getStack()) && comparator.compare(matchingSlots.get(0), targetSlot) == 0) {
                        return null;
                    }
                }
                return matchingSlots.get(0);
            }
            return matchingSlots.get(0);
        }
        return null;
    }

    private boolean isFirstEmptyHotbarSlot(int hotbarIndex) {
        Container container;
        if (Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
            GuiContainer guiContainer = new GuiContainer(Minecraft.currentScreen());
            container = guiContainer.getInventorySlots();
        } else {
            container = Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        }
        for (int candidateIndex = 0; candidateIndex < 9; ++candidateIndex) {
            Slot slot = container.getSlot(36 + candidateIndex);
            if (!slot.getStack().isNull()) continue;
            return candidateIndex == hotbarIndex;
        }
        return false;
    }

    @Override
    public boolean isPerformingInventoryAction() {
        return this.isEnabled() && this.clickQueue.size() > 0 && (this.openInventoryOption.getEffectiveValue() != false || Minecraft.currentScreen().isNull());
    }

    @Override
    public void onEnable() {
        if (this.activationMode.getValue() == this.onKeyMode) {
            this.pendingOpen = true;
            this.pendingClose = false;
        } else {
            this.pendingOpen = false;
            this.pendingClose = false;
        }
        this.clickQueue.clear();
        this.clickTimer.reset();
        this.touchedSlots.clear();
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        Container container;
        if (Vape.INSTANCE.getModManager().isOtherInventoryActionActive(AutoHotbar.class) || Vape.INSTANCE.getClientSettings().isLobbyCheckActive()) {
            this.clickQueue.clear();
            this.clickTimer.reset();
            this.touchedSlots.clear();
            return;
        }
        if (Minecraft.thePlayer().C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isCreativeMode()) {
            return;
        }
        if (this.pendingClose) {
            this.handleClosing();
            return;
        }
        if (Minecraft.currentScreen().isInstance(MappedClasses.Ft) && this.activationMode.getValue() == this.toggleMode && !this.openInventoryOption.getEffectiveValue().booleanValue()) {
            return;
        }
        if (!Minecraft.currentScreen().isInstance(MappedClasses.YS) && (this.activationMode.getValue() == this.onKeyMode || this.openInventoryOption.getEffectiveValue().booleanValue() && this.pendingOpen)) {
            if (this.pendingOpen) {
                KeyBinding keyBinding = Minecraft.gameSettings().j();
                if (ForgeVersion.MC_1_16_5.d()) {
                    KeyBindingHelper.incrementPressTime(keyBinding);
                } else {
                    KeyBindingHelper.setPressedAndTick(keyBinding, true);
                    KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
                }
            } else if (this.activationMode.getValue() == this.onKeyMode) {
                this.setEnabled(false);
            }
            return;
        }
        this.pendingOpen = false;
        this.pendingClose = false;
        if (this.clickQueue.size() > 0) {
            if (this.clickTimer.hasTimeElapsed(((Double)this.delayMs.getValue()).intValue())) {
                this.clickQueue.poll().execute();
                this.clickTimer.reset();
                this.didClick = true;
            }
            return;
        }
        if (Minecraft.currentScreen().isInstance(MappedClasses.YS)) {
            GuiContainer inventoryScreen = new GuiContainer(Minecraft.currentScreen());
            container = inventoryScreen.getInventorySlots();
        } else {
            container = Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        }
        List<HotbarSlotRule> hotbarSlotRules = this.hotbarRules.getRules();
        boolean queuedAction = false;
        if (hotbarSlotRules.size() == 9) {
            for (int hotbarIndex = 0; hotbarIndex < 9; ++hotbarIndex) {
                HotbarSlotRule hotbarSlotRule = hotbarSlotRules.get(hotbarIndex);
                int targetSlotIndex = 36 + hotbarIndex;
                Slot targetSlot = container.getSlot(targetSlotIndex);
                Slot sourceSlot = this.findBestMatchingSlot(targetSlot, hotbarSlotRule);
                if (sourceSlot == null) continue;
                ItemStack targetStack = targetSlot.getStack();
                ItemStack sourceStack = sourceSlot.getStack();
                if (sourceSlot.equals(targetSlot)) {
                    this.touchedSlots.add(targetSlotIndex);
                    if (targetStack.isNotNull() && targetStack.t() < targetStack.P()) {
                        List<Slot> overflowSlots = this.findOverflowSlots(container, targetSlot, hotbarSlotRule);
                        if (!overflowSlots.isEmpty()) {
                            sourceSlot = overflowSlots.get(0);
                        }
                    }
                }
                if (sourceSlot.equals(targetSlot)) continue;
                if (this.openInventoryOption.getEffectiveValue().booleanValue() && !Minecraft.currentScreen().isInstance(MappedClasses.YS)) {
                    this.pendingOpen = true;
                    this.pendingClose = false;
                    return;
                }
                this.touchedSlots.add(targetSlotIndex);
                this.touchedSlots.add(sourceSlot.getSlotNumber());
                int combinedStackSize = 0;
                if (targetStack.isNotNull()) {
                    combinedStackSize += targetStack.P();
                }
                boolean targetEmpty = !this.hasItem(targetSlot);
                boolean canShiftClick = this.isFirstEmptyHotbarSlot(hotbarIndex) && sourceSlot.getSlotNumber() < 36;
                new InventoryClickQueue(canShiftClick ? InventoryClickAction.SHIFT_CLICK : (targetEmpty ? InventoryClickAction.SWAP : InventoryClickAction.MOVE), sourceSlot.getSlotNumber(), targetSlotIndex).appendTo(container.getWindowId(), this.clickQueue);
                if ((combinedStackSize += sourceStack.t()) > sourceStack.P()) {
                    new InventoryClickQueue(InventoryClickAction.CLICK, sourceSlot.getSlotNumber(), targetSlotIndex).appendTo(container.getWindowId(), this.clickQueue);
                }
                queuedAction = true;
                break;
            }
        }
        if (!queuedAction && this.activationMode.getValue() == this.onKeyMode) {
            this.pendingClose = true;
            this.clickTimer.reset();
        }
        if (this.didClick && this.activationMode.getValue() == this.toggleMode && this.openInventoryOption.getEffectiveValue().booleanValue() && this.clickQueue.isEmpty()) {
            this.pendingClose = true;
        }
    }

    private void handleClosing() {
        ItemStack carriedStack = RotationUtil.Z();
        if (carriedStack.isNotNull() && this.queueEmptyHotbarClick()) {
            this.pendingClose = false;
            return;
        }
        if (this.activationMode.getValue() == this.onKeyMode) {
            this.setEnabled(false);
        }
        if (!Minecraft.currentScreen().isNull()) {
            Minecraft.thePlayer().Z$src$V$1ie832h();
            this.pendingClose = false;
        }
        this.clickQueue.clear();
        this.clickTimer.reset();
        this.touchedSlots.clear();
        this.pendingClose = false;
        this.didClick = false;
    }


    private List<Slot> findOverflowSlots(Container container, Slot targetSlot, HotbarSlotRule hotbarSlotRule) {
        List<Slot> inventorySlots = container.getInventorySlots();
        ArrayList<Slot> overflowSlots = new ArrayList<Slot>();
        ItemStack targetStack = targetSlot.getStack();
        if (targetStack.isNotNull()) {
            for (int slotIndex = 9; slotIndex < inventorySlots.size(); ++slotIndex) {
                Slot candidateSlot = container.getSlot(slotIndex);
                ItemStack candidateStack = candidateSlot.getStack();
                if (!candidateStack.isNotNull() || !hotbarSlotRule.matches(candidateStack) || this.touchedSlots.contains(slotIndex)) continue;
                overflowSlots.add(candidateSlot);
            }
        }
        return overflowSlots;
    }
}
