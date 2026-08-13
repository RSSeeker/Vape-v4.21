package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.ArmorStatusHudModule;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.ArmorStatusItemComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayList;

public class ArmorStatusHudFrame
extends HudModuleConfigFrameBase {
    private final ArrayList<ArmorStatusItemComponent> armorItems = new ArrayList();
    private final ArmorStatusItemComponent chestplate;
    private boolean layoutInitialized;
    private final ArmorStatusItemComponent boots;
    private static final String FRAME_NAME = "ArmorStatusFrame";
    private final ArmorStatusItemComponent helmet;
    private final ArmorStatusItemComponent leggings;
    private final ArmorStatusHudModule module = (ArmorStatusHudModule)this.getModule();

    private boolean hasEquippedArmor() {
        for (ArmorStatusItemComponent armorItem : this.armorItems) {
            if (!armorItem.getEquippedItem().isNotNull()) continue;
            return true;
        }
        return false;
    }

    public int getItemHeight() {
        if (this.module == null) {
            return 20;
        }
        return this.module.compact.getEffectiveValue() != false ? 22 : 20;
    }

    @Override
    public void renderHudContent() {
        if (Minecraft.thePlayer().isNotNull()) {
            this.layoutArmorItems();
        }
    }

    @Override
    public double A() {
        if (this.module == null) {
            return 70.0;
        }
        return this.module.compact.getEffectiveValue() != false ? 26.0 : 54.0;
    }


    private void layoutArmorItems() {
        for (GuiComponent guiComponent : this.f()) {
            ArmorStatusItemComponent armorStatusItemComponent;
            if (!(guiComponent instanceof ArmorStatusItemComponent)
                    || !(armorStatusItemComponent = (ArmorStatusItemComponent)guiComponent)
                            .getSlot().getStack().isNotNull()) continue;
            armorStatusItemComponent.setVisible(true);
        }
        double itemY = this.n() + 2.0;
        boolean compact = this.module.compact.getEffectiveValue();
        ArmorStatusItemComponent armorStatusItemComponent = null;
        boolean previewMode = false;
        if (!ClientSettings.INSTANCE.inputEnabled && !this.hasEquippedArmor()) {
            previewMode = true;
        }
        for (ArmorStatusItemComponent armorStatusItemComponent2 : this.armorItems) {
            if (armorStatusItemComponent2.V$src$Z$1xhop3l()) {
                armorStatusItemComponent2.K(this.G$src$D$1b2f02a() + 2.0);
                armorStatusItemComponent2.S(itemY);
                itemY += (double)this.getItemHeight();
                if (compact) {
                    itemY += 2.0;
                }
                armorStatusItemComponent2.setLastVisibleEntry(false);
                armorStatusItemComponent = armorStatusItemComponent2;
            }
            armorStatusItemComponent2.setPreviewMode(previewMode);
            if (previewMode) {
                armorStatusItemComponent2.setVisible(true);
            }
            armorStatusItemComponent2.setCompact(compact);
        }
        if (armorStatusItemComponent != null) {
            armorStatusItemComponent.setLastVisibleEntry(true);
        }
        if (!this.layoutInitialized) {
            this.l$src$V$1mibm4x();
            this.layoutInitialized = true;
        }
    }

    @Override
    public String getName() {
        return FRAME_NAME;
    }

    public ArmorStatusHudFrame() {
        super(ArmorStatusHudModule.class);
        int helmetItemId = 310;
        int chestplateItemId = 311;
        int leggingsItemId = 312;
        int bootsItemId = 313;
        if (ForgeVersion.MC_1_21_10.d()) {
            helmetItemId = 970;
            chestplateItemId = 971;
            leggingsItemId = 972;
            bootsItemId = 973;
        } else if (ForgeVersion.MC_1_21_0.d()) {
            helmetItemId = 899;
            chestplateItemId = 900;
            leggingsItemId = 901;
            bootsItemId = 902;
        } else if (ForgeVersion.MC_1_20_6.d()) {
            helmetItemId = 868;
            chestplateItemId = 869;
            leggingsItemId = 870;
            bootsItemId = 871;
        } else if (ForgeVersion.MC_1_17.d()) {
            helmetItemId = 750;
            chestplateItemId = 751;
            leggingsItemId = 752;
            bootsItemId = 753;
        } else if (ForgeVersion.MC_1_16_5.d()) {
            helmetItemId = 634;
            chestplateItemId = 635;
            leggingsItemId = 636;
            bootsItemId = 637;
        }
        this.helmet = new ArmorStatusItemComponent(this, 5, ItemStack.S(Item.T(helmetItemId)));
        this.chestplate = new ArmorStatusItemComponent(this, 6,
                ItemStack.S(Item.T(chestplateItemId)));
        this.leggings = new ArmorStatusItemComponent(this, 7,
                ItemStack.S(Item.T(leggingsItemId)));
        this.boots = new ArmorStatusItemComponent(this, 8, ItemStack.S(Item.T(bootsItemId)));
        this.armorItems.add(this.helmet);
        this.armorItems.add(this.chestplate);
        this.armorItems.add(this.leggings);
        this.armorItems.add(this.boots);
        this.addChildren(this.helmet, this.chestplate, this.leggings, this.boots);
    }

    @Override
    public double L() {
        int visibleItemCount = 0;
        for (GuiComponent guiComponent : this.f()) {
            if (!(guiComponent instanceof ArmorStatusItemComponent) || !guiComponent.V$src$Z$1xhop3l()) continue;
            ++visibleItemCount;
        }
        if (visibleItemCount == 0) {
            return 0.0;
        }
        if (this.module == null) {
            return 20.0;
        }
        int itemHeight = this.getItemHeight();
        if (this.module.compact.getEffectiveValue().booleanValue()) {
            itemHeight += 2;
        }
        double totalHeight = visibleItemCount * itemHeight + 4;
        if (visibleItemCount == 1) {
            totalHeight = itemHeight + 4;
        }
        if (this.module.compact.getEffectiveValue().booleanValue()) {
            totalHeight -= 2.0;
        }
        return totalHeight;
    }
}

