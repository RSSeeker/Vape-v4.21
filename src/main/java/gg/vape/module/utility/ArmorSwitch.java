package gg.vape.module.utility;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.UtilityMod;
import gg.vape.module.utility.armorswitch.ArmorMaterialType;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Container;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Slot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gg.vape.module.utility.inventory.ArmorItemMappingEntry;

public class ArmorSwitch
extends UtilityMod {
    private final NumberValue delayValue;
    private static final int FIRST_ARMOR_SLOT = 5;
    private boolean screenReady;
    private final ModeValue set1;
    private ArmorMaterialType currentMaterial;
    private final HashMap<ModeOption, ArmorMaterialType> materialByOption = new HashMap();
    private int slotIndex;
    private final TimerUtil timer;
    private int clickPhase;
    private final Map<Integer, Integer> armorSlotTargets;
    private final ModeValue set2;
    private boolean finished;
    private boolean collecting;


    public ArmorSwitch() {
        super("ArmorSwitch", Category.INVENTORY, "Switches between armor sets");
        this.delayValue = NumberValue.create(this, "Delay", "#", "", 0.0, 100.0, 200.0);
        this.timer = new TimerUtil();
        this.armorSlotTargets = new HashMap<Integer, Integer>();
        ModeOption diamondOption = new ModeOption("Diamond");
        ModeOption goldOption = new ModeOption("Gold");
        ModeOption ironOption = new ModeOption("Iron");
        ModeOption leatherOption = new ModeOption("Leather");
        ModeOption chainOption = new ModeOption("Chain");
        this.materialByOption.put(diamondOption, ArmorMaterialType.DIAMOND);
        this.materialByOption.put(goldOption, ArmorMaterialType.GOLD);
        this.materialByOption.put(ironOption, ArmorMaterialType.IRON);
        this.materialByOption.put(leatherOption, ArmorMaterialType.LEATHER);
        this.materialByOption.put(chainOption, ArmorMaterialType.CHAINMAIL);
        this.set1 = ModeValue.create((Object)this, "Set 1", diamondOption, this.materialByOption.keySet().toArray(new ModeOption[this.materialByOption.size()]));
        this.set2 = ModeValue.create((Object)this, "Set 2", goldOption, this.materialByOption.keySet().toArray(new ModeOption[this.materialByOption.size()]));
        this.addValue(this.set1, this.set2, this.delayValue);
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNotNull() && localPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isCreativeMode()) {
            this.setEnabled(false);
            return;
        }
        if (this.finished) {
            this.setEnabled(false);
            return;
        }
        if (!Minecraft.currentScreen().isInstance(MappedClasses.YS)) {
            if (!this.screenReady) {
                KeyBinding keyBinding = Minecraft.gameSettings().j();
                if (ForgeVersion.MC_1_16_5.d()) {
                    KeyBindingHelper.incrementPressTime(keyBinding);
                } else {
                    KeyBindingHelper.setPressedAndTick(keyBinding, true);
                    KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
                }
            } else {
                this.setEnabled(false);
            }
            return;
        }
        boolean active = this.screenReady;
        if (!active) {
            this.screenReady = true;
            return;
        }
        if (this.collecting && this.timer.hasTimeElapsed(((Double)this.delayValue.getValue()).longValue())) {
            this.timer.reset();
            int slot = this.armorSlotTargets.get(this.slotIndex);
            this.performClick(this.slotIndex, slot);
            if (this.slotIndex > 8) {
                this.finished = true;
            }
            return;
        }
        boolean hasArmor = this.hasEquippedArmor();
        if (this.screenReady && hasArmor && !this.collecting) {
            boolean wearingSet1 = this.currentMaterial.equals((Object)this.materialByOption.get(this.set1.getValue()));
            if (this.findArmorSlots(wearingSet1 ? this.materialByOption.get(this.set2.getValue()) : this.materialByOption.get(this.set1.getValue()))) {
                this.collecting = true;
            } else {
                this.setEnabled(false);
            }
        }
        if (!hasArmor) {
            this.setEnabled(false);
        }
    }

    private boolean findArmorSlots(ArmorMaterialType armorMaterialType) {
        int helmetSlot = 0;
        int chestplateSlot = 0;
        int leggingsSlot = 0;
        int bootsSlot = 0;
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        Container container = localPlayer.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        List<Slot> inventorySlots = container.getInventorySlots();
        slotLoop: for (int slotIndex = 9; slotIndex < 45; ++slotIndex) {
            ArmorItemMappingEntry armorMapping;
            Item item;
            Slot slot = inventorySlots.get(slotIndex);
            if (!slot.hasStack() || !ItemStackScoreUtil.R(item = slot.getStack().getItem()) || !(armorMapping = (ArmorItemMappingEntry)Vape.INSTANCE.getItemStackResolver().resolve(slot.getStack())).getArmorMaterial().equals((Object)armorMaterialType)) continue;
            int armorType = ItemStackScoreUtil.H(item);
            switch (armorType) {
                case 0: {
                    helmetSlot = slotIndex;
                    continue slotLoop;
                }
                case 1: {
                    chestplateSlot = slotIndex;
                    continue slotLoop;
                }
                case 2: {
                    leggingsSlot = slotIndex;
                    continue slotLoop;
                }
                case 3: {
                    bootsSlot = slotIndex;
                }
            }
        }
        if (ForgeVersion.MC_1_16_5.v() && ForgeVersion.MC_1_12_2.d()) {
            this.armorSlotTargets.put(8, helmetSlot);
            this.armorSlotTargets.put(7, chestplateSlot);
            this.armorSlotTargets.put(6, leggingsSlot);
            this.armorSlotTargets.put(5, bootsSlot);
        } else {
            this.armorSlotTargets.put(5, helmetSlot);
            this.armorSlotTargets.put(6, chestplateSlot);
            this.armorSlotTargets.put(7, leggingsSlot);
            this.armorSlotTargets.put(8, bootsSlot);
        }
        return helmetSlot != 0 && chestplateSlot != 0 && leggingsSlot != 0 && bootsSlot != 0;
    }

    @Override
    public void onDisable() {
        if (Minecraft.currentScreen().isInstance(MappedClasses.YS)) {
            Minecraft.thePlayer().Z$src$V$1ie832h();
        }
    }

    @Override
    public void onEnable() {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNotNull() && localPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isCreativeMode()) {
            this.setEnabled(false);
            return;
        }
        this.slotIndex = FIRST_ARMOR_SLOT;
        this.clickPhase = 0;
        this.collecting = false;
        this.finished = false;
        this.screenReady = false;
    }

    private void performClick(int inventorySlot, int targetSlot) {
        int clickSlot = targetSlot;
        int mouseButton = 0;
        if (this.clickPhase == 1) {
            clickSlot = inventorySlot;
        }
        Minecraft.playerController().O(Minecraft.thePlayer().F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getWindowId(), clickSlot, 0, mouseButton, Minecraft.thePlayer());
        ++this.clickPhase;
        if (this.clickPhase >= 3) {
            this.clickPhase = 0;
            ++this.slotIndex;
        }
    }

    private boolean hasEquippedArmor() {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        Container container = localPlayer.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
        List<Slot> inventorySlots = container.getInventorySlots();
        for (int armorSlot = 5; armorSlot < 9; ++armorSlot) {
            Slot slot = inventorySlots.get(armorSlot);
            if (!slot.hasStack() || !ItemStackScoreUtil.R(slot.getStack().getItem())) continue;
            ArmorItemMappingEntry armorMapping = (ArmorItemMappingEntry)Vape.INSTANCE.getItemStackResolver().resolve(slot.getStack());
            this.currentMaterial = armorMapping.getArmorMaterial();
            return true;
        }
        return false;
    }
}
