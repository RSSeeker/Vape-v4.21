package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventMotion;
import gg.vape.event.impl.EventPostMotion;
import gg.vape.event.impl.EventPostPlayerTick;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.inventory.InventoryClick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.unmap.ItemLimitData;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.CPacketHeldItemChange;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PlayerControllerMP;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionRegistry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoHeal
extends Mod {
    private final ModeOption legitMode;
    private final BooleanValue replaceItems;
    private final NumberValue healthThreshold;
    private boolean potionUsePending;
    private final ModeOption discardBowlsMode;
    private final ModeOption stackBowlsMode;
    private final LimitValue customHealingItems;
    private final BooleanValue useCustomItems;
    private int pendingPotionSlot = -1;
    private final NumberValue useDelay;
    private final ModeOption throwBowlsMode;
    private final BooleanValue useResistancePotions;
    private final TimerUtil useTimer;
    private final ModeValue useMode;
    private final BooleanValue inventoryOnly;
    private final ModeOption silentMode;
    private final NumberValue refillHotbarSlot;
    private boolean closeInventoryPending;
    private int previousHotbarSlot;
    private final ModeValue bowlMode;
    private int soupItemId;
    private final BooleanValue useSoup = BooleanValue.create(this, "Soup", false, "Uses soups to heal.");
    private final RandomValue swapBackDelay;
    private final BooleanValue useRegenerationPotions;
    private final Queue<InventoryClick> pendingInventoryClicks;
    private boolean inventoryOpenedForRefill;
    private boolean legitUseInProgress;
    private final BooleanValue useSpeedPotions;
    private int bowlItemId;
    private final BooleanValue usePotions;
    private int pendingHealSlot;

    private int findHealingPotionSlot() {
        EntityPlayerSP player = Minecraft.thePlayer();
        int potionSlot = -1;
        for (int slot = 0; slot < 45; ++slot) {
            ItemStack stack = player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getSlot(slot).getStack();
            if (!player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getSlot(slot).hasStack()
                    || !this.isHealingPotion(stack)) {
                continue;
            }
            if (slot < 8) {
                potionSlot = slot;
                break;
            }
            if (!this.replaceItems.getEffectiveValue()) {
                break;
            }
            int hotbarSlot = ((Double)this.refillHotbarSlot.getValue()).intValue() - 1;
            this.moveInventoryItem(slot, 36 + hotbarSlot);
            potionSlot = hotbarSlot;
            break;
        }
        return potionSlot;
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        if (!event.getPlayer().isInstance(MappedClasses.z5)
                || Vape.INSTANCE.getClientSettings().isLobbyCheckActive()
                || Minecraft.currentScreen().isNotNull()) {
            return;
        }
        boolean executedQueuedClick = false;
        if (this.useMode.getValue() == this.legitMode) {
            if (this.closeInventoryPending) {
                if (!Minecraft.currentScreen().isNull()) {
                    Minecraft.thePlayer().Z$src$V$1ie832h();
                }
                this.closeInventoryPending = false;
            } else {
                while (!this.pendingInventoryClicks.isEmpty()) {
                    this.pendingInventoryClicks.poll().execute();
                    executedQueuedClick = true;
                }
            }
        }
        if (this.legitUseInProgress || this.inventoryOpenedForRefill) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        int threshold = ((Double)this.healthThreshold.getValue()).intValue();
        boolean shouldHeal = player.w$src$F$15l9epb()
                / Math.max(player.I$src$F$14vyvep(), 1.0f) <= (float)threshold / 20.0f
                && this.useTimer.hasTimeElapsed(((Double)this.useDelay.getValue()).intValue());
        if (shouldHeal) {
            for (int containerSlot = 36; containerSlot < 45; ++containerSlot) {
                ItemStack stack = player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm()
                        .getSlot(containerSlot).getStack();
                if (!player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getSlot(containerSlot).hasStack()
                        || !this.isHealingItem(stack)) {
                    continue;
                }
                if (this.useMode.getValue() == this.legitMode) {
                    this.legitUseInProgress = true;
                    this.pendingHealSlot = containerSlot - 36;
                    this.previousHotbarSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
                    this.v(executedQueuedClick ? 51L : 0L, false);
                } else {
                    this.pendingHealSlot = -1;
                    this.previousHotbarSlot = -1;
                    player.sendQueue().addToSendQueue(CPacketHeldItemChange.create(containerSlot - 36));
                    Minecraft.playerController().sendUseItem(player, player.getWorld(), stack);
                    player.sendQueue().addToSendQueue(CPacketHeldItemChange.create(
                            player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v()));
                }
                this.useTimer.reset();
                return;
            }
        }
        this.pendingHealSlot = -1;
    }

    @EventHandler
    public void onMotionUpdate(EventPostMotion event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (!this.usePotions.getEffectiveValue()) {
            return;
        }
        if (this.potionUsePending && this.pendingPotionSlot != -1
                && this.useTimer.hasTimeElapsed(((Double)this.useDelay.getValue()).intValue())) {
            ItemStack potionStack = player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm()
                    .getSlot(36 + this.pendingPotionSlot).getStack();
            if (potionStack.isNotNull()) {
                int selectedSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
                player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.pendingPotionSlot);
                player.sendQueue().addToSendQueue(CPacketHeldItemChange.create(this.pendingPotionSlot));
                Minecraft.playerController().sendUseItem(player, player.getWorld(), potionStack);
                player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(selectedSlot);
                player.sendQueue().addToSendQueue(CPacketHeldItemChange.create(selectedSlot));
            }
            this.useTimer.reset();
            this.potionUsePending = false;
            this.pendingPotionSlot = -1;
        }
    }

    private void queueInventoryClick(int windowId, int slot, int button, int mode) {
        this.pendingInventoryClicks.add(new InventoryClick(windowId, slot, button, mode));
    }

    private boolean isHealingPotion(ItemStack stack) {
        if (stack.isNull()) {
            return false;
        }
        if (stack.getItem().isInstance(MappedClasses.Di) && ItemSplashPotion.isSplashPotion(stack)) {
            EntityPlayerSP player = Minecraft.thePlayer();
            ItemSplashPotion splashPotion = new ItemSplashPotion(stack.getItem().getObject());
            if (splashPotion.getRawPotionEffects(stack) != null) {
                for (Object effectHandle : splashPotion.getRawPotionEffects(stack)) {
                    PotionEffect potionEffect = new PotionEffect(effectHandle);
                    boolean belowHealthThreshold = player.w$src$F$15l9epb()
                            <= (Double)this.healthThreshold.getValue();
                    if (potionEffect.C() == PotionRegistry.z.getResolvedId() && belowHealthThreshold) {
                        return true;
                    }
                    if (potionEffect.C() == PotionRegistry.U.getResolvedId() && this.useSpeedPotions.getEffectiveValue()
                            && !player.i(PotionRegistry.U)) {
                        return true;
                    }
                    if (potionEffect.C() == PotionRegistry.i.getResolvedId()
                            && this.useRegenerationPotions.getEffectiveValue() && belowHealthThreshold
                            && !player.i(PotionRegistry.i)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onScheduledAction() {
        if (!this.legitUseInProgress) {
            return;
        }
        try {
            KeyBinding useItemKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
            int keyCode = useItemKey.getKeyCode();
            Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6()
                    .g(this.pendingHealSlot);
            boolean releasePhysicalUseKey = Minecraft.thePlayer().l$src$Z$1io4duf()
                    && ClientSettings.isInputDown(keyCode);
            if (releasePhysicalUseKey) {
                KeyBindingHelper.setPressedAndTick(useItemKey, false);
                Thread.sleep(51L);
            }
            KeyBindingHelper.setPressedAndTick(useItemKey, true);
            Thread.sleep(51L);
            KeyBindingHelper.updateKeyBinding(useItemKey, false, false);
            long swapDelay = Math.max((long)this.swapBackDelay.getRandomValue()
                    + (releasePhysicalUseKey ? -35L : 0L), 0L);
            Thread.sleep(swapDelay);
            Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6()
                    .g(this.previousHotbarSlot);
            if (ClientSettings.isInputDown(keyCode)) {
                KeyBindingHelper.setPressedAndTick(useItemKey, true);
            }
        }
        catch (Exception exception) {
            this.legitUseInProgress = false;
            this.pendingHealSlot = -1;
            this.previousHotbarSlot = -1;
        }
        this.pendingHealSlot = -1;
        this.previousHotbarSlot = -1;
        this.legitUseInProgress = false;
    }

    private void moveInventoryItem(int sourceSlot, int targetContainerSlot) {
        int hotbarSlot = targetContainerSlot - 36;
        if (this.useMode.getValue() == this.legitMode) {
            this.queueInventoryClick(
                    Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getWindowId(),
                    sourceSlot, hotbarSlot, 2);
        } else {
            Minecraft.playerController().O(
                    Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getWindowId(),
                    sourceSlot, hotbarSlot, 2, Minecraft.thePlayer());
        }
    }

    @Override
    public String getSimpleSuffix() {
        return this.useMode.getDisplayValue();
    }

    public AutoHeal() {
        super("AutoHeal", 0, 16711819, Category.HIDDEN, "Automatically heals for you when health is under threshold.");
        this.replaceItems = BooleanValue.create(this, "Replace", false, "Refills empty slots with healing items.");
        this.inventoryOnly = BooleanValue.create(this, "Inventory Only", false, "Only refills/crafts when your inventory is open.");
        this.usePotions = BooleanValue.create(this, "Potions", false, "Uses splash healing potions to heal.");
        this.useRegenerationPotions = BooleanValue.create(this, "Regen", true, "Uses regeneration potions when available.");
        this.useSpeedPotions = BooleanValue.create(this, "Speed", false, "Uses speed potions when available.");
        this.useResistancePotions = BooleanValue.create(this, "Resistance", false, "Uses resistance potions when available.");
        this.useCustomItems = BooleanValue.create(this, "Use Custom Items", false, "Uses other custom healing items.\nUses the same delay as soup/potion.");
        this.customHealingItems = LimitValue.create(this, "autoheal-customitems", "Healing Items", LimitValue.ALLOW_LIST_COLOR, new ItemLimitData("397:3"));
        this.throwBowlsMode = new ModeOption("Throw");
        this.stackBowlsMode = new ModeOption("Stack");
        this.discardBowlsMode = new ModeOption("None");
        this.bowlMode = ModeValue.create((Object)this, "Bowl Mode", this.stackBowlsMode,
                this.stackBowlsMode, this.throwBowlsMode, this.discardBowlsMode);
        this.silentMode = new ModeOption("Silent");
        this.legitMode = new ModeOption("Legit");
        this.useMode = ModeValue.create((Object)this, "Mode", this.legitMode, this.silentMode, this.legitMode);
        this.swapBackDelay = RandomValue.createWithDescription(this, "Swap Delay", "#", "ms", 0.0, 75.0, 125.0, 200.0, 5.0, "The delay between using the item and swapping back.");
        this.useDelay = NumberValue.create(this, "Delay", "#", "ms", 50.0, 500.0, 1000.0, 50.0, "The delay to wait before healing again.");
        this.healthThreshold = NumberValue.create((Object)this, "Health", "#", "HP", 1.0, 17.0, 20.0, 1.0);
        this.refillHotbarSlot = NumberValue.create(this, "Slot", "#", "", 1.0, 6.0, 9.0, 1.0, "The slot to fill a potion for autopot.");
        this.useTimer = new TimerUtil();
        this.pendingInventoryClicks = new ConcurrentLinkedQueue<InventoryClick>();
        this.useMode.addModeDependentValues(this.silentMode, this.usePotions, this.bowlMode);
        this.useMode.addModeDependentValues(this.legitMode, this.swapBackDelay);
        this.useCustomItems.addDependentValues(this.customHealingItems);
        this.useSoup.addDependentValues(this.bowlMode);
        this.usePotions.addDependentValues(this.useRegenerationPotions, this.useSpeedPotions,
                this.useResistancePotions, this.refillHotbarSlot);
        this.addValue(this.useMode, this.useSoup, this.bowlMode, this.usePotions,
                this.useRegenerationPotions, this.useSpeedPotions, this.useResistancePotions,
                this.refillHotbarSlot, this.useCustomItems, this.customHealingItems,
                this.replaceItems, this.healthThreshold, this.useDelay, this.swapBackDelay);
        if (ForgeVersion.MC_1_17.d()) {
            this.bowlItemId = 730;
            this.soupItemId = 731;
        } else {
            this.bowlItemId = 281;
            this.soupItemId = 282;
        }
    }

    @EventHandler
    public void onMotionUpdate(EventPreMotion event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (!this.usePotions.getEffectiveValue()) {
            return;
        }
        int threshold = ((Double)this.healthThreshold.getValue()).intValue();
        boolean needsPotion = (player.w$src$F$15l9epb() <= threshold
                && (!this.useRegenerationPotions.getEffectiveValue() || !player.i(PotionRegistry.i))
                || this.useSpeedPotions.getEffectiveValue() && !player.i(PotionRegistry.U)
                || this.useResistancePotions.getEffectiveValue() && !player.i(PotionRegistry.P))
                && player.b$src$Z$fqlxe4() && player.u$src$Z$g120nz();
        int potionSlot = this.findHealingPotionSlot();
        if (potionSlot != -1 && needsPotion
                && this.useTimer.hasTimeElapsed(((Double)this.useDelay.getValue()).intValue())) {
            if (this.pendingPotionSlot == -1) {
                this.potionUsePending = true;
                this.pendingPotionSlot = potionSlot;
                EventMotion.setRotationPitch(88.99f);
            }
        } else {
            this.potionUsePending = false;
        }
    }

    private boolean isHealingItem(ItemStack stack) {
        if (stack.isNull() || stack.getItem().isNull()) {
            return false;
        }
        Item item = stack.getItem();
        if (this.useSoup.getEffectiveValue() && item.P() == this.soupItemId) {
            return true;
        }
        return this.useCustomItems.getEffectiveValue() && this.customHealingItems.matches(stack);
    }

    private void manageInventory(EntityPlayerSP player) {
        if (this.inventoryOnly.getEffectiveValue() && !Minecraft.currentScreen().isInstance(MappedClasses.YS)) {
            return;
        }
        if (this.legitUseInProgress) {
            return;
        }
        if (!this.inventoryOpenedForRefill && this.useSoup.getEffectiveValue()
                && this.bowlMode.getValue() != this.discardBowlsMode) {
            for (int slot = 9; slot < 45; ++slot) {
                boolean inHotbar = slot >= 36;
                ItemStack stack = player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm()
                        .getSlot(slot).getStack();
                if (!player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getSlot(slot).hasStack()
                        || stack.getItem().isNull() || stack.getItem().P() != this.bowlItemId) {
                    continue;
                }
                PlayerControllerMP controller = Minecraft.playerController();
                if (this.bowlMode.getValue() == this.throwBowlsMode) {
                    controller.O(0, slot, 1, 4, player);
                    return;
                }
                if (this.bowlMode.getValue() != this.stackBowlsMode || slot == 17) {
                    continue;
                }
                boolean bowlStackSlotEmpty = !player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm()
                        .getSlot(17).hasStack();
                if (bowlStackSlotEmpty || player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm()
                        .getSlot(17).getStack().getItem().P() != this.bowlItemId) {
                    if (inHotbar) {
                        this.moveInventoryItem(17, slot);
                    } else {
                        controller.O(0, slot, 0, 0, player);
                        controller.O(0, 17, 0, 0, player);
                        controller.O(0, slot, 0, 0, player);
                    }
                } else if (inHotbar) {
                    controller.O(0, slot, 0, 1, player);
                } else {
                    controller.O(0, slot, 0, 0, player);
                    controller.O(0, 17, 0, 0, player);
                }
                return;
            }
        }
        if (this.replaceItems.getEffectiveValue()) {
            for (int inventorySlot = 9; inventorySlot < 36; ++inventorySlot) {
                ItemStack healingStack = player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm()
                        .getSlot(inventorySlot).getStack();
                if (!player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm()
                        .getSlot(inventorySlot).hasStack() || !this.isHealingItem(healingStack)) {
                    continue;
                }
                for (int hotbarContainerSlot = 36; hotbarContainerSlot < 45; ++hotbarContainerSlot) {
                    ItemStack hotbarStack = player.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm()
                            .getSlot(hotbarContainerSlot).getStack();
                    if (hotbarStack.isNotNull() && hotbarStack.getItem().isNotNull()) {
                        continue;
                    }
                    if (!this.inventoryOpenedForRefill) {
                        KeyBinding inventoryKey = Minecraft.gameSettings().j();
                        if (ForgeVersion.MC_1_16_5.d()) {
                            KeyBindingHelper.incrementPressTime(inventoryKey);
                        } else {
                            KeyBindingHelper.setPressedAndTick(inventoryKey, true);
                            KeyBindingHelper.updateKeyBinding(inventoryKey, false, false);
                        }
                        this.inventoryOpenedForRefill = true;
                        return;
                    }
                    this.closeInventoryPending = false;
                    this.moveInventoryItem(inventorySlot, hotbarContainerSlot);
                    return;
                }
            }
            if (this.inventoryOpenedForRefill) {
                this.inventoryOpenedForRefill = false;
                this.closeInventoryPending = true;
            }
        }
    }

    @EventHandler
    public void onPlayerTick(EventPostPlayerTick event) {
        if (!event.getPlayer().isInstance(MappedClasses.z5)
                || Vape.INSTANCE.getClientSettings().isLobbyCheckActive()) {
            return;
        }
        this.manageInventory(event.getThePlayer());
    }
}
