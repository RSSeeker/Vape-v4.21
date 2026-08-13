package gg.vape.module.utility;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.UtilityMod;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.datas.ItemStackData;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class ThrowDebuff
extends UtilityMod {
    private TimerUtil scrollTimer;
    private final BooleanValue scroll;
    private final NumberValue scrollDelay;
    private boolean throwing = false;
    private final BooleanValue[] debuffValues;
    private final RandomValue delay;
    int savedSlot = 0;
    private final Queue<ItemStackData> itemsToThrow = new ArrayDeque<ItemStackData>();
    private TimerUtil delayTimer;
    private final ModeOption oneOfEachOption;
    private final ModeValue mode;
    private final ModeOption allOption = new ModeOption("All");
    private final ModeOption firstOption;

    @Override
    public void onDisable() {
        this.throwing = false;
        this.itemsToThrow.clear();
    }

    @EventHandler
    public void onTick(EventPreTick event) {
        KeyBinding useKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
        if (this.throwing) {
            KeyBindingHelper.updateKeyBinding(useKey, false, false);
            this.throwing = false;
            return;
        }
        if (this.itemsToThrow.isEmpty()) {
            if (this.selectHotbarSlotIncrementally(this.savedSlot)) {
                this.setEnabled(false);
            }
            return;
        }
        ItemStackData nextItem = this.itemsToThrow.peek();
        if (this.delayTimer.hasTimeElapsed((long)this.delay.getRandomValue()) && this.selectHotbarSlotIncrementally(nextItem.getSlot())) {
            KeyBindingHelper.updateKeyBinding(useKey, true, true);
            this.throwing = true;
            this.delayTimer.reset();
            this.itemsToThrow.poll();
        }
    }

    private boolean collectDebuffs() {
        ArrayList<Integer> hotbarSlots = new ArrayList<Integer>();
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
            hotbarSlots.add(hotbarSlot);
        }
        Object[] hotbarContents = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().M();
        ArrayList<BooleanValue> queuedDebuffTypes = new ArrayList<BooleanValue>();
        slotLoop: for (Integer hotbarSlot : hotbarSlots) {
            Item item;
            ItemStack itemStack = new ItemStack(hotbarContents[hotbarSlot]);
            if (itemStack.isNull() || (item = itemStack.getItem()).isNull() || !MappedClasses.Di.isInstance(item.getObject())) continue;
            ItemSplashPotion splashPotion = new ItemSplashPotion(item.getObject());
            for (BooleanValue debuffValue : this.debuffValues) {
                if (((ModeSelection)this.mode.getValue()).equals(this.oneOfEachOption) && queuedDebuffTypes.contains(debuffValue)) continue;
                String potionName = splashPotion.getItemStackDisplayName(itemStack).toLowerCase();
                String debuffName = debuffValue.getName().toLowerCase();
                if (!debuffValue.getEffectiveValue().booleanValue() || !potionName.contains(debuffName)) continue;
                this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
                queuedDebuffTypes.add(debuffValue);
                if (!((ModeSelection)this.mode.getValue()).equals(this.firstOption)) continue slotLoop;
                break slotLoop;
            }
        }
        return !this.itemsToThrow.isEmpty();
    }

    public ThrowDebuff() {
        super("ThrowDebuff", Category.INVENTORY, "");
        this.oneOfEachOption = new ModeOption("One of each");
        this.firstOption = new ModeOption("First");
        this.mode = ModeValue.create((Object)this, "Mode", "All - Throws all debuffs on hotbar\nOne of each - Throws one of each debuff\nFirst - Throws only first debuff on hotbar", (ModeSelection)this.oneOfEachOption, this.allOption, this.oneOfEachOption, this.firstOption);
        this.debuffValues = new BooleanValue[]{BooleanValue.create(this, "Harming", true), BooleanValue.create(this, "Weakness", true), BooleanValue.create(this, "Poison", true), BooleanValue.create(this, "Slowness", true)};
        this.delay = RandomValue.create(this, "Delay", "#.#", "", 0.0, 70.0, 120.0, 200.0);
        this.scroll = BooleanValue.create(this, "Scroll", false);
        this.scrollDelay = NumberValue.create(this, "Scroll delay", "#", "ms", 0.0, 100.0, 200.0);
        this.delayTimer = new TimerUtil();
        this.scrollTimer = new TimerUtil();
        this.setDefaultVisibility(false);
        this.addValue(this.mode);
        for (BooleanValue booleanValue : this.debuffValues) {
            this.addValue(booleanValue);
        }
        this.addValue(this.delay);
        this.scroll.addDependentValues(this.scrollDelay);
        this.addValue(this.scroll);
        this.addValue(this.scrollDelay);
    }

    @Override
    public void onEnable() {
        if (this.collectDebuffs()) {
            this.savedSlot = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
        } else {
            this.setEnabled(false);
        }
    }

    private boolean selectHotbarSlotIncrementally(int targetSlot) {
        if (!this.scroll.getEffectiveValue().booleanValue()) {
            Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(targetSlot);
            return true;
        }
        if (!this.scrollTimer.hasTimeElapsed(((Double)this.scrollDelay.getValue()).longValue())) {
            return false;
        }
        int currentSlot = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
        if (targetSlot > currentSlot) {
            ++currentSlot;
        } else if (targetSlot < currentSlot) {
            --currentSlot;
        } else {
            this.scrollTimer.reset();
            return true;
        }
        Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(currentSlot);
        return false;
    }
}

