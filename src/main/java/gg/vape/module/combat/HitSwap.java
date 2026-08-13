package gg.vape.module.combat;

import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.input.AttackKeyController;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.control.AttackCancellationAdapter;
import gg.vape.module.control.PhysicalAttackCancellationAdapter;
import gg.vape.module.control.SyntheticAttackCancellationAdapter;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ItemLimitData;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import java.util.Arrays;

public class HitSwap
extends Mod {
    public final BooleanValue breachMaces;
    private boolean swingReleasePending = false;
    public final BooleanValue stunSlam;
    private int tickCounter;
    public final BooleanValue axes;
    private boolean stunSlamActive = false;
    private boolean stunSlamSecondPhase = false;
    private boolean breachSwapPending = false;
    public final LimitValue allowedItems;
    private boolean swapAttackPending = false;
    public final BooleanValue limitToItems = BooleanValue.create(this, "Limit to items", false);
    private boolean swapActive;
    public final BooleanValue smashOnly;
    public final BooleanValue densityMaces;
    private int originalSlot = -1;
    private static final long MODULE_ID = -7666507152973844357L;
    public final BooleanValue maces = BooleanValue.create(this, "Maces", true);
    public final BooleanValue swords;

    private boolean shouldFilterByEnchant() {
        return (this.densityMaces.getEffectiveValue() != false || this.breachMaces.getEffectiveValue() != false) && this.hasMaceInHotbar();
    }

    private boolean hasMaceInHotbar() {
        InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        for (int slot = 0; slot < 9; ++slot) {
            if (this.isValidMace(inventory.c(slot), true)) {
                return true;
            }
        }
        return false;
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onKeyPress(EventKeyPress event) {
        if (!event.isKeybinding(Minecraft.gameSettings().F()) || !event.isDown()) {
            return;
        }
        this.handleAttack(new PhysicalAttackCancellationAdapter(event));
    }

    @Override
    public void onDisable() {
        this.swapAttackPending = false;
        this.stunSlamSecondPhase = false;
        if (this.swingReleasePending) {
            AttackKeyController.releaseAttackKey();
        }
        this.swingReleasePending = false;
        this.swapActive = false;
        this.originalSlot = -1;
        this.tickCounter = 0;
        this.breachSwapPending = false;
        this.stunSlamActive = false;
    }

    private boolean isTargetSmashReady(EntityOtherPlayerMP targetPlayer) {
        EnumHand activeHand = RotationUtil.q(targetPlayer);
        if (activeHand == null) {
            return false;
        }
        ItemStack activeItem = targetPlayer.i(activeHand);
        float useDuration = targetPlayer.d(activeHand);
        if (useDuration <= 0.0f) {
            return false;
        }
        int maximumUseDuration = activeItem.getItem().I(activeItem, targetPlayer);
        return maximumUseDuration - useDuration > 5.0f;
    }

    public boolean hasAlternateAxe() {
        if (this.axes.getEffectiveValue().booleanValue()) {
            InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
            int selectedSlot = inventory.v();
            for (int slot = 0; slot < 9; ++slot) {
                ItemStack stack = inventory.c(slot);
                if (slot != selectedSlot && stack.isNotNull() && stack.getItem().isNotNull()
                        && stack.getItem().isInstance(MappedClasses.YP)) {
                    return true;
                }
            }
        }
        return false;
    }

    private EntityOtherPlayerMP getTargetPlayer() {
        return RotationUtil.m(Minecraft.thePlayer(), 5.0, 90.0, true);
    }

    private boolean canHandleAttack() {
        if (Minecraft.currentScreen().isNotNull()) {
            return false;
        }
        ItemStack heldItem = Minecraft.thePlayer().getHeldItemHand();
        return !this.limitToItems.getEffectiveValue() || this.allowedItems.isValid(heldItem, false);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onSyntheticAttack(SyntheticAttackRequestEvent event) {
        if (event.getSource() == this) {
            return;
        }
        this.handleAttack(new SyntheticAttackCancellationAdapter(event));
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onMouseButton(EventMouseButton event) {
        if (!event.isKeybinding(Minecraft.gameSettings().F()) || !event.isDown()) {
            return;
        }
        this.handleAttack(new PhysicalAttackCancellationAdapter(event));
    }

    private int findAxeSlot() {
        InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        int selectedSlot = inventory.v();
        for (int slot = 0; slot < 9; ++slot) {
            ItemStack stack = inventory.c(slot);
            if (slot != selectedSlot && stack.isNotNull() && stack.getItem().isNotNull()
                    && stack.getItem().isInstance(MappedClasses.YP)) {
                return slot;
            }
        }
        return -1;
    }

    public boolean hasValidWeaponSwap() {
        int weaponSlot = this.findWeaponSlot();
        if (weaponSlot < 0) {
            return false;
        }
        InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        return this.isValidMace(inventory.c(weaponSlot), this.shouldFilterByEnchant());
    }

    private int findMaceSlot() {
        InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        for (int slot = 0; slot < 9; ++slot) {
            if (this.isValidMace(inventory.c(slot), this.shouldFilterByEnchant())) {
                return slot;
            }
        }
        return -1;
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onTick(EventPreTick eventPreTick) {
        if (eventPreTick.getThePlayer().isNull()) {
            return;
        }
        if (this.swingReleasePending) {
            AttackKeyController.releaseAttackKey();
            this.swingReleasePending = false;
        }
        if (this.breachSwapPending && this.swapActive && this.tickCounter >= 1) {
            int maceSlot = this.findMaceSlot();
            if (maceSlot != -1) {
                eventPreTick.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(maceSlot);
                AttackKeyController.releaseAttackKey();
                AttackKeyController.requestSyntheticAttack(this);
                this.swingReleasePending = true;
            }
            this.breachSwapPending = false;
            return;
        }
        if (this.swapAttackPending && this.swapActive) {
            AttackKeyController.releaseAttackKey();
            AttackKeyController.requestSyntheticAttack(this);
            this.swingReleasePending = true;
            this.swapAttackPending = false;
            if (this.stunSlamActive) {
                this.stunSlamSecondPhase = true;
            }
        }
        if (this.stunSlamActive && this.swapActive && this.stunSlamSecondPhase && this.tickCounter >= 1) {
            int maceSlot = this.findMaceSlot();
            if (maceSlot != -1) {
                eventPreTick.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(maceSlot);
                AttackKeyController.releaseAttackKey();
                AttackKeyController.requestSyntheticAttack(this);
                this.swingReleasePending = true;
            }
            this.stunSlamSecondPhase = false;
            this.stunSlamActive = false;
            return;
        }
        if (this.swapActive) {
            int restoreDelayTicks = this.stunSlamActive || this.stunSlamSecondPhase ? 2 : 1;
            if (this.tickCounter++ > restoreDelayTicks) {
                if (this.originalSlot != -1) {
                    eventPreTick.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.originalSlot);
                    this.originalSlot = -1;
                }
                this.swapActive = false;
                this.tickCounter = 0;
                this.stunSlamActive = false;
                this.stunSlamSecondPhase = false;
            }
        }
    }

    private void handleAttack(AttackCancellationAdapter cancellation) {
        if (this.swapActive) {
            return;
        }
        if (!this.canHandleAttack()) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        if (player.l$src$Z$1io4duf()) {
            return;
        }
        RayTraceResult crosshairHit = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (crosshairHit.getEntity().isNull()) {
            return;
        }
        ItemStack heldItem = player.getHeldItemHand();
        EntityOtherPlayerMP targetPlayer = this.getTargetPlayer();
        int maceSlot = this.findMaceSlot();
        if (this.stunSlam.getEffectiveValue().booleanValue() && this.maces.getEffectiveValue().booleanValue()
                && heldItem.isNotNull() && heldItem.getItem().isNotNull()
                && targetPlayer != null && this.isTargetSmashReady(targetPlayer) && maceSlot >= 0) {
            if (heldItem.getItem().isInstance(MappedClasses.YP)) {
                this.originalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
                this.breachSwapPending = true;
                this.swapActive = true;
                this.tickCounter = 0;
                return;
            }
            int axeSlot = this.findAxeSlot();
            if (axeSlot >= 0) {
                cancellation.setCancelled(true);
                this.originalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
                player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(axeSlot);
                this.stunSlamActive = true;
                this.swapActive = true;
                this.tickCounter = 0;
                this.swapAttackPending = true;
                return;
            }
        }
        int weaponSlot = this.findWeaponSlot();
        if (weaponSlot >= 0) {
            cancellation.setCancelled(true);
            this.originalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
            player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(weaponSlot);
            this.swapActive = true;
            this.tickCounter = 0;
            this.swapAttackPending = true;
        }
    }

    private boolean isValidMace(ItemStack itemStack, boolean requireConfiguredEnchantment) {
        if (this.smashOnly.getEffectiveValue().booleanValue() && !RotationUtil.u(Minecraft.thePlayer())) {
            return false;
        }
        if (itemStack.isNull()) {
            return false;
        }
        if (itemStack.getItem().isNull()) {
            return false;
        }
        Item item = itemStack.getItem();
        if (item.isInstance(MappedClasses.zx)) {
            if (!requireConfiguredEnchantment) {
                return true;
            }
            if (this.densityMaces.getEffectiveValue().booleanValue() && EnchantmentHelper.e("density", itemStack) > 0) {
                return true;
            }
            if (this.breachMaces.getEffectiveValue().booleanValue() && EnchantmentHelper.e("breach", itemStack) > 0) {
                return true;
            }
            return this.densityMaces.getEffectiveValue() == false && this.breachMaces.getEffectiveValue() == false;
        }
        return false;
    }

    public HitSwap() {
        super("HitSwap", (int)MODULE_ID, Category.UTILITY, "Swaps into another weapon on attack, copying its attributes\nAKA BreachSwap, ZeroTick");
        this.smashOnly = BooleanValue.create(this, "Smash only", true, "Only swap to mace if will smash");
        this.breachMaces = BooleanValue.create(this, "Breach maces", false, "Will use Maces with Breach enchantment");
        this.densityMaces = BooleanValue.create(this, "Density maces", true, "Will use Maces with Breach enchantment");
        this.stunSlam = BooleanValue.create(this, "Stun slam", false, "When holding an axe and attacking a shielded player:\nHits with axe first (breaks shield), then swaps to mace for a follow-up slam");
        this.axes = BooleanValue.create(this, "Axes", true);
        this.swords = BooleanValue.create(this, "Swords", false);
        this.allowedItems = LimitValue.create(this, "bs-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, Arrays.asList(new ItemLimitData("swords")));
        this.addValue(this.maces, this.smashOnly, this.breachMaces, this.densityMaces, this.stunSlam, this.axes, this.swords, this.limitToItems, this.allowedItems);
        this.limitToItems.addDependentValues(this.allowedItems);
        this.maces.addDependentValues(this.smashOnly, this.breachMaces, this.densityMaces, this.stunSlam);
    }

    private int findWeaponSlot() {
        InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        int selectedSlot = inventory.v();
        for (int slot = 0; slot < 9; ++slot) {
            ItemStack stack = inventory.c(slot);
            if (slot == selectedSlot || stack.isNull() || stack.getItem().isNull()) {
                continue;
            }
            Item item = stack.getItem();
            EntityOtherPlayerMP targetPlayer = this.getTargetPlayer();
            EnumHand activeHand = targetPlayer != null ? RotationUtil.q(targetPlayer) : null;
            if (this.axes.getEffectiveValue().booleanValue() && item.isInstance(MappedClasses.YP) && activeHand != null) {
                ItemStack activeItem = targetPlayer.i(activeHand);
                float useDuration = targetPlayer.d(activeHand);
                int maximumUseDuration = activeItem.getItem().I(activeItem, targetPlayer);
                if (useDuration > 0.0f) {
                    if (maximumUseDuration - useDuration > 5.0f) {
                        return slot;
                    }
                    return -2;
                }
            }
            if (this.maces.getEffectiveValue().booleanValue() && this.isValidMace(stack, this.shouldFilterByEnchant())) {
                return slot;
            }
            if (this.swords.getEffectiveValue().booleanValue() && ItemStackScoreUtil.h(item)) {
                return slot;
            }
        }
        return -1;
    }
}
