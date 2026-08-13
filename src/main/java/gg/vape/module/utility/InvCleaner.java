package gg.vape.module.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.utility.inventory.InventoryActionModule;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.value.ModeValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.DamageSource;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionEntry;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.Slot;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class InvCleaner
extends Mod
implements InventoryActionModule {
    private final RandomValue delay;
    private final LimitValue blacklisted;
    private final BooleanValue removeFood;
    private final BooleanValue openInventory;
    private final ModeValue activation;
    private final ModeOption toggleOption;
    private final TimerUtil clickTimer;
    private final BooleanValue bestItems;
    private final BooleanValue inventoryOnly;
    private final BooleanValue removeNegativePotions;
    private ItemStack bestItemC;
    private ItemStack bestItemA;
    private final ModeOption onKeyOption;
    private ItemStack[] bestArmorPieces;
    private ItemStack bestItemB;
    private boolean active;
    private ItemStack bestItemD;
    private final Queue<Integer> clickQueue = new ArrayDeque<Integer>();
    private static final long MAGIC_ID = -3117147329120510770L;

    @Override
    public boolean isPerformingInventoryAction() {
        return this.isEnabled() && this.active && (this.openInventory.getEffectiveValue() != false || Minecraft.currentScreen().isNull());
    }

    public InvCleaner() {
        super("InvCleaner", (int)MAGIC_ID, Category.INVENTORY, "Cleans blacklisted items from your inventory");
        this.clickTimer = new TimerUtil();
        this.delay = RandomValue.createWithIncrement(this, "Delay", "#", "", 1.0, 100.0, 120.0, 200.0, 1.0);
        this.bestItems = BooleanValue.create(this, "Best Items", true, "Keeps the best set of armor, sword, axe, pickaxe and bow");
        this.removeNegativePotions = BooleanValue.create(this, "Remove Negative Potions", true, "Will always throw out negative potions");
        this.removeFood = BooleanValue.create(this, "Remove Food", true, "Remove Food except for Golden Apples");
        this.openInventory = BooleanValue.create(this, "Open Inventory", true, "Opens your inventory when cleaning.");
        this.inventoryOnly = BooleanValue.create(this, "Inventory Only", true, "Only cleans while your inventory is open.");
        this.blacklisted = LimitValue.create(this, "invcleaner-blacklisted", "Blacklisted", LimitValue.BLOCK_LIST_COLOR, Collections.emptyList());
        this.onKeyOption = new ModeOption("On Key");
        this.toggleOption = new ModeOption("Toggle");
        this.activation = ModeValue.create((Object)this, "Activation", this.onKeyOption, this.onKeyOption, this.toggleOption);
        this.setDefaultVisibility(false);
        this.activation.addDependentValues(this.openInventory, this.inventoryOnly);
        this.activation.addActiveMode(this.openInventory, this.onKeyOption);
        this.activation.addActiveMode(this.inventoryOnly, this.toggleOption);
        this.addValue(this.activation, this.delay, this.openInventory, this.inventoryOnly, this.bestItems, this.removeNegativePotions, this.removeFood, this.blacklisted);
    }

    private boolean isNegativeSplashPotion(ItemStack itemStack) {
        if (!itemStack.getItem().isInstance(MappedClasses.Di)) {
            return false;
        }
        ItemSplashPotion itemSplashPotion = new ItemSplashPotion(itemStack.getItem());
        List<PotionEffect> list = itemSplashPotion.getPotionEffects(itemStack);
        for (PotionEffect potionEffect : list) {
            PotionEntry potionEntry = PotionRegistry.R(potionEffect);
            if (!potionEntry.isResolved()) continue;
            return true;
        }
        return false;
    }

    private ItemStack findBestByComparator(List<Slot> list, Class<?> clazz, Comparator<ItemStack> comparator) {
        ArrayList<ItemStack> matchingItems = new ArrayList<ItemStack>();
        for (Slot slot : list) {
            ItemStack itemStack;
            if (!slot.hasStack() || (itemStack = slot.getStack()).isNull() || !itemStack.getItem().isInstance(clazz)) continue;
            matchingItems.add(itemStack);
        }
        Collections.reverse(matchingItems);
        matchingItems.sort(comparator);
        Collections.reverse(matchingItems);
        return matchingItems.isEmpty() ? null : matchingItems.get(0);
    }

    private boolean shouldRemove(ItemStack itemStack) {
        int armorType;
        Item item = itemStack.getItem();
        if (ItemStackScoreUtil.R(item) && this.bestArmorPieces[armorType = ItemStackScoreUtil.t(itemStack)] != null && !this.bestArmorPieces[armorType].equals(itemStack)) {
            return true;
        }
        int notBest = 1;
        notBest = this.bestItems.getEffectiveValue() != false ? (item.isInstance(MappedClasses.Vl) && !this.bestItemC.equals(itemStack) || item.isInstance(MappedClasses.DU) && !this.bestItemB.equals(itemStack) || ItemStackScoreUtil.h(item) && !this.bestItemA.equals(itemStack) || item.isInstance(MappedClasses.YP) && !this.bestItemD.equals(itemStack) ? 1 : 0) : 0;
        return this.blacklisted.isValid(itemStack, true) || notBest != 0 || this.removeFood.getEffectiveValue() != false && item.isInstance(MappedClasses.ITEM_FOOD) && !item.isInstance(MappedClasses.q3) || this.removeNegativePotions.getEffectiveValue() != false && item.isInstance(MappedClasses.Di) && this.isNegativeSplashPotion(itemStack);
    }

    @Override
    public void loadJson(JsonObject jsonObject) {
        super.loadJson(jsonObject);
        if (jsonObject.get("blacklisted-items") != null) {
            JsonArray jsonArray = jsonObject.get("blacklisted-items").getAsJsonArray();
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("id", this.blacklisted.getId());
            jsonObject2.add("value", (JsonElement)jsonArray);
            this.blacklisted.loadJson(jsonObject2);
        }
    }

    @Override
    public void I() {
        this.blacklisted.addEntry("280", -1);
        this.blacklisted.addEntry("287", -1);
        this.blacklisted.addEntry("318", -1);
        this.blacklisted.addEntry("345", -1);
        this.blacklisted.addEntry("288", -1);
        this.blacklisted.addEntry("374", -1);
        this.blacklisted.addEntry("116", -1);
        this.blacklisted.addEntry("54", -1);
        this.blacklisted.addEntry("145", -1);
    }

    @Override
    public boolean isRequiresBind() {
        return this.activation.getValue() == this.onKeyOption;
    }

    private void closeInventoryIfOpen(EntityPlayerSP localPlayer) {
        if (Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
            localPlayer.Z$src$V$1ie832h();
        }
    }

    public static double scoreArmorWithEnchantments(ItemStack itemStack) {
        double score = InvCleaner.armorScore(itemStack);
        score += (double)EnchantmentHelper.q(32, itemStack);
        score += (double)EnchantmentHelper.q(16, itemStack);
        score += (double)EnchantmentHelper.q(19, itemStack);
        score += (double)EnchantmentHelper.q(20, itemStack);
        score += (double)EnchantmentHelper.q(48, itemStack);
        return score + (double)EnchantmentHelper.q(34, itemStack);
    }

    public LimitValue getBlacklist() {
        return this.blacklisted;
    }

    private boolean buildCleanupQueue() {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (!Minecraft.currentScreen().isInstance(MappedClasses.Ft) && this.openInventory.getEffectiveValue().booleanValue() && this.activation.getValue() == this.onKeyOption) {
            KeyBinding keyBinding = Minecraft.gameSettings().j();
            if (ForgeVersion.MC_1_16_5.d()) {
                KeyBindingHelper.incrementPressTime(keyBinding);
            } else {
                KeyBindingHelper.setPressedAndTick(keyBinding, true);
                KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
            }
            return false;
        }
        if (localPlayer.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().isNull()) {
            return false;
        }
        List<Slot> inventorySlots = localPlayer.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getInventorySlots();
        inventorySlots.sort(Comparator.comparingInt(this::hotbarRegionOf));
        this.bestArmorPieces = this.collectBestArmor();
        this.bestItemA = this.findBestByComparator(inventorySlots, MappedClasses.V5, Comparator.comparingDouble(ClientSettings::getWeaponDamageScore));
        this.bestItemB = this.findBestByComparator(inventorySlots, MappedClasses.DU, Comparator.comparingDouble(ClientSettings::getToolDamageScore));
        this.bestItemC = this.findBestByComparator(inventorySlots, MappedClasses.Vl, Comparator.comparingDouble(ClientSettings::getHiddenItemScore));
        this.bestItemD = this.findBestByComparator(inventorySlots, MappedClasses.YP, Comparator.comparingDouble(ClientSettings::getWeaponDamageScore));
        slotLoop: for (Slot slot : localPlayer.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getInventorySlots()) {
            try {
                if (!slot.hasStack() || slot.getStack().isNull()) continue;
                for (Object armorStack : localPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i()) {
                    if (armorStack != null && armorStack.equals(slot.getStack())) continue slotLoop;
                }
                if (!this.shouldRemove(slot.getStack())) continue;
                this.queueSlot(slot.getSlotNumber());
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return true;
    }

    private int hotbarRegionOf(Slot slot) {
        int slotIndex = slot.getSlotNumber();
        if (slotIndex >= 36 && slotIndex <= 44) {
            return 0;
        }
        if (slotIndex >= 9 && slotIndex <= 17) {
            return 1;
        }
        if (slotIndex >= 18 && slotIndex <= 26) {
            return 2;
        }
        return 3;
    }

    private ItemStack[] collectBestArmor() {
        ItemStack[] itemStackArray = new ItemStack[4];
        ArrayList<ItemStack> armorItems = new ArrayList<ItemStack>();
        List<Slot> list = Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getInventorySlots();
        for (Slot wrapper : list) {
            if (!wrapper.hasStack() || !ItemStackScoreUtil.R(wrapper.getStack().getItem())) continue;
            armorItems.add(wrapper.getStack());
        }
        for (ItemStack itemStack : armorItems) {
            int armorType = ItemStackScoreUtil.t(itemStack);
            ItemStack existing = itemStackArray[armorType];
            if (existing != null && !(InvCleaner.armorScore(itemStack) > InvCleaner.armorScore(existing))) continue;
            itemStackArray[armorType] = itemStack;
        }
        return itemStackArray;
    }

    private static double armorScore(ItemStack itemStack) {
        int score = 0;
        if (itemStack.isNull()) {
            return score;
        }
        if (itemStack.getItem().isNotNull() && ItemStackScoreUtil.R(itemStack.getItem())) {
            score = (int)ItemStackScoreUtil.P(itemStack);
        }
        return score += EnchantmentHelper.B(new ItemStack[]{itemStack}, DamageSource.C(Minecraft.thePlayer()));
    }

    @Override
    public void onEnable() {
        this.clickQueue.clear();
        this.active = false;
    }

    private void queueSlot(int slotIndex) {
        if (this.clickQueue.contains(slotIndex)) {
            return;
        }
        this.clickQueue.add(slotIndex);
        this.clickQueue.add(-999);
        this.active = true;
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        if (Vape.INSTANCE.getModManager().isOtherInventoryActionActive(InvCleaner.class) || Vape.INSTANCE.getClientSettings().isLobbyCheckActive()) {
            this.active = false;
            return;
        }
        EntityPlayerSP localPlayer = event.getThePlayer();
        if (!this.active) {
            if (this.buildCleanupQueue() && !this.active && this.activation.getValue() == this.onKeyOption) {
                this.setEnabled(false);
                if (this.openInventory.getEffectiveValue().booleanValue()) {
                    this.closeInventoryIfOpen(localPlayer);
                }
            }
            return;
        }
        if (this.activation.getValue() == this.toggleOption && this.inventoryOnly.getEffectiveValue().booleanValue() && (!Minecraft.currentScreen().isInstance(MappedClasses.Ft) || localPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isCreativeMode())) {
            return;
        }
        if (this.active && this.openInventory.getEffectiveValue().booleanValue() && !Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
            this.toggle();
            return;
        }
        if (!this.clickQueue.isEmpty()) {
            if (this.clickTimer.hasTimeElapsed((long)this.delay.getRandomValue())) {
                this.clickTimer.reset();
                int slotIndex = this.clickQueue.poll();
                Minecraft.playerController().O(localPlayer.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getWindowId(), slotIndex, 0, 0, localPlayer);
            }
            return;
        }
        if (this.activation.getValue() == this.onKeyOption) {
            this.setEnabled(false);
            if (this.openInventory.getEffectiveValue().booleanValue()) {
                this.closeInventoryIfOpen(localPlayer);
            }
        } else {
            this.active = false;
        }
        if (localPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isCreativeMode() && Minecraft.currentScreen().isInstance(MappedClasses.Ft) && this.activation.getValue() == this.onKeyOption) {
            this.setEnabled(false);
        }
    }
}
