package gg.vape.module.utility;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.inventory.InventoryClick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.UtilityMod;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.value.ModeValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GuiContainer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Slot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Refill
extends UtilityMod
{
    private final TimerUtil delayTimer;
    private final ModeValue typeMode;
    private final ModeOption bothMode = new ModeOption("Both");
    private final BooleanValue hotbarClear;
    private boolean opened;
    private final BooleanValue vertical;
    private final RandomValue delay;
    private boolean finished;
    private final LimitValue allowedItems;
    private final ModeOption soupMode;
    private final Queue<InventoryClick> clickQueue;
    private final BooleanValue scatter;
    private final ModeOption potsMode = new ModeOption("Pots");

    private boolean hasHealingInInventory() {
        for (int inventorySlot = 9; inventorySlot < 36; ++inventorySlot) {
            Item item;
            ItemStack itemStack = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(inventorySlot);
            if (itemStack.isNull() || (item = itemStack.getItem()).isNull() || !ItemStackScoreUtil.o(itemStack, ((ModeSelection)this.typeMode.getValue()).equals(this.soupMode) || ((ModeSelection)this.typeMode.getValue()).equals(this.bothMode))) continue;
            return true;
        }
        return false;
    }

    private void processClickQueue() {
        if (this.delay.getMaximumValue() == 0.0) {
            while (!this.clickQueue.isEmpty()) {
                InventoryClick pendingClick = this.clickQueue.poll();
                pendingClick.execute();
            }
            return;
        }
        if (this.delayTimer.hasTimeElapsed((long)this.delay.getRandomValue())) {
            InventoryClick pendingClick = this.clickQueue.poll();
            pendingClick.execute();
            this.delayTimer.reset();
        }
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        if (!this.opened) {
            if (!Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
                KeyBinding keyBinding = Minecraft.gameSettings().j();
                if (ForgeVersion.MC_1_16_5.d()) {
                    KeyBindingHelper.incrementPressTime(keyBinding);
                } else {
                    KeyBindingHelper.setPressedAndTick(keyBinding, true);
                    KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
                }
            } else {
                this.opened = true;
            }
            return;
        }
        if (this.finished) {
            if (!Minecraft.currentScreen().isNull()) {
                Minecraft.thePlayer().Z$src$V$1ie832h();
            }
            this.finished = false;
            this.clickQueue.clear();
            this.setEnabled(false);
            return;
        }
        if (!Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
            this.finished = true;
            return;
        }
        if (!this.clickQueue.isEmpty()) {
            this.processClickQueue();
            return;
        }
        this.buildRefillClicks();
        if (this.clickQueue.isEmpty()) {
            this.finished = true;
        }
    }

    private boolean isJunk(ItemStack itemStack, List<ItemStack> retainedItems) {
        if (ItemStackScoreUtil.o(itemStack, ((ModeSelection)this.typeMode.getValue()).equals(this.soupMode) || ((ModeSelection)this.typeMode.getValue()).equals(this.bothMode))) {
            return false;
        }
        if (this.allowedItems.isValid(itemStack, true)) {
            if (!this.hasDuplicate(itemStack, retainedItems)) {
                retainedItems.add(itemStack);
                return false;
            }
            return true;
        }
        return true;
    }

    private void queueClick(int windowId, int slot, int mouseButton, int clickType) {
        this.clickQueue.add(new InventoryClick(windowId, slot, mouseButton, clickType));
    }

    public Refill() {
        super("Refill", Category.INVENTORY, "Refills your hotbar with healing items.");
        this.soupMode = new ModeOption("Soup");
        this.typeMode = ModeValue.create((Object)this, "Type", this.bothMode, this.bothMode, this.potsMode, this.soupMode);
        this.allowedItems = LimitValue.create(this, "refill-alloweditems", "Non Junk Items", LimitValue.ALLOW_LIST_COLOR, Collections.emptyList());
        this.vertical = BooleanValue.create(this, "Vertical", false);
        this.scatter = BooleanValue.create(this, "Scatter", false);
        this.hotbarClear = BooleanValue.create(this, "Hotbar clear", false, "Clears junk from your hotbar to refill.\nWhitelisted items will not be considered junk\nOnly one stack of each non-junk item is kept");
        this.delay = RandomValue.createWithIncrement(this, "Delay", "#", "ms", 50.0, 75.0, 125.0, 200.0, 5.0);
        this.clickQueue = new ConcurrentLinkedQueue<InventoryClick>();
        this.delayTimer = new TimerUtil();
        this.hotbarClear.addDependentValues(this.allowedItems);
        this.addValue(this.vertical, this.scatter, this.hotbarClear, this.allowedItems, this.delay, this.typeMode);
    }

    private boolean shouldDoubleClick() {
        return this.chance(0.2);
    }

    private boolean hasDuplicate(ItemStack candidate, List<ItemStack> retainedItems) {
        for (ItemStack retainedItem : retainedItems) {
            if (candidate.equals(retainedItem) || !candidate.f().equals(retainedItem.f())) continue;
            return true;
        }
        return false;
    }

    private boolean chance(double probability) {
        double boundedProbability = Math.max(Math.min(probability, 1.0), 0.0);
        return Math.random() <= boundedProbability;
    }

    private void buildRefillClicks() {
        GuiContainer inventoryScreen = new GuiContainer(Minecraft.currentScreen().getObject());
        ArrayList<Integer> healingSlots = new ArrayList<Integer>();
        ArrayList<Integer> selectedHealingSlots = new ArrayList<Integer>();
        int verticalRow = 0;
        List<Integer> targetHotbarSlots = this.findJunkHotbarSlots();
        int inventorySlot = 9;
        while (inventorySlot < 36) {
            Item item;
            Slot slot = inventoryScreen.getInventorySlots().getInventorySlots().get(inventorySlot);
            ItemStack itemStack = slot.getStack();
            if (!itemStack.isNull() && !(item = itemStack.getItem()).isNull() && ItemStackScoreUtil.o(itemStack, ((ModeSelection)this.typeMode.getValue()).equals(this.soupMode) || ((ModeSelection)this.typeMode.getValue()).equals(this.bothMode))) {
                healingSlots.add(inventorySlot);
            }
            if (this.vertical.getEffectiveValue().booleanValue()) {
                inventorySlot += 9;
                if (++verticalRow != 3) continue;
                ++inventorySlot;
                inventorySlot -= 27;
                verticalRow = 0;
                continue;
            }
            ++inventorySlot;
        }
        if (healingSlots.isEmpty()) {
            this.finished = true;
            return;
        }
        if (this.scatter.getEffectiveValue().booleanValue()) {
            Collections.shuffle(healingSlots);
        }
        for (int selectionIndex = 0; selectionIndex < targetHotbarSlots.size() && selectionIndex < healingSlots.size(); ++selectionIndex) {
            selectedHealingSlots.add(healingSlots.get(selectionIndex));
        }
        int targetIndex = 0;
        for (Integer healingSlot : selectedHealingSlots) {
            boolean targetOccupied = false;
            int hotbarSlot = 0;
            if (this.hotbarClear.getEffectiveValue().booleanValue()) {
                Item item;
                hotbarSlot = targetHotbarSlots.get(targetIndex);
                ItemStack itemStack = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(hotbarSlot);
                if (itemStack.isNotNull() && (item = itemStack.getItem()).isNotNull()) {
                    targetOccupied = true;
                }
            }
            if (targetOccupied) {
                this.queueClick(inventoryScreen.getInventorySlots().getWindowId(), healingSlot, 0, 0);
                this.queueClick(inventoryScreen.getInventorySlots().getWindowId(), 36 + hotbarSlot, 0, 0);
                this.queueClick(inventoryScreen.getInventorySlots().getWindowId(), healingSlot, 0, 0);
            } else {
                this.queueClick(inventoryScreen.getInventorySlots().getWindowId(), healingSlot, 0, 1);
                if (this.shouldDoubleClick()) {
                    this.queueClick(inventoryScreen.getInventorySlots().getWindowId(), healingSlot, 0, 1);
                }
            }
            ++targetIndex;
        }
    }

    private List<Integer> findJunkHotbarSlots() {
        ArrayList<ItemStack> retainedItems = new ArrayList<ItemStack>();
        ArrayList<Integer> availableSlots = new ArrayList<Integer>();
        Object[] hotbarContents = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().M();
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
            ItemStack itemStack = new ItemStack(hotbarContents[hotbarSlot]);
            if (itemStack.isNull()) {
                availableSlots.add(hotbarSlot);
                continue;
            }
            if (this.hotbarClear.getEffectiveValue().booleanValue()) {
                if (!this.isJunk(itemStack, retainedItems)) continue;
                availableSlots.add(hotbarSlot);
                continue;
            }
            if (!itemStack.toString().contains("tile.air")) continue;
            availableSlots.add(hotbarSlot);
        }
        return availableSlots;
    }


    @Override
    public void onEnable() {
        if (Minecraft.thePlayer().isNull()) {
            this.setEnabled(false);
            return;
        }
        if (this.findJunkHotbarSlots().isEmpty()) {
            this.setEnabled(false);
            return;
        }
        if (!this.hasHealingInInventory()) {
            this.setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
        this.opened = false;
    }
}

