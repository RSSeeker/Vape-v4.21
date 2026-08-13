package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.ToolTips;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.ItemStack;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.Map;

public class ItemStackSlotComponent
extends GuiComponent {
    private int iconSize;
    boolean tooltipDirty = false;
    private final Enchantment[] supportedEnchantments = new Enchantment[]{Enchantment.protection(), Enchantment.unbreaking(), Enchantment.sharpness(), Enchantment.fireAspect(), Enchantment.efficiency(), Enchantment.featherFalling(), Enchantment.power(), Enchantment.flame(), Enchantment.punch(), Enchantment.fortune(), Enchantment.infinity(), Enchantment.thorns(), Enchantment.knockback()};
    boolean selected = false;
    private ItemStack itemStack;

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }

    @Override
    public void u() {
        if (!this.tooltipDirty) {
            return;
        }
        if (this.itemStack != null) {
            boolean armorItem = ItemStackScoreUtil.R(this.itemStack.getItem());
            boolean weaponOrTool = ItemStackScoreUtil.V$src$Z$dcbuai(this.itemStack) || ItemStackScoreUtil.Y(this.itemStack);
            if (!armorItem && !weaponOrTool) {
                ToolTips toolTips = new ToolTips(this, this.itemStack.x() + " (" + this.itemStack.t() + ")");
                this.setToolTips(toolTips);
            } else {
                Map<Enchantment, Short> enchantments;
                String itemName = this.itemStack.x();
                String durabilityText = new BigDecimal((double)(this.itemStack.getItem().a() - this.itemStack.L()) / (double)this.itemStack.getItem().a() * 100.0 + "").setScale(0, 4).toPlainString() + "% durability";
                String enchantmentText = "";
                try {
                    enchantments = EnchantmentUtil.A(this.itemStack);
                    for (Map.Entry<Enchantment, Short> enchantmentEntry : enchantments.entrySet()) {
                        Enchantment enchantment = enchantmentEntry.getKey();
                        short level = enchantmentEntry.getValue();
                        String translatedName = enchantment.getTranslatedName(level);
                        enchantmentText = enchantmentText + "\n" + translatedName;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                String itemSummary = armorItem ? "+" + (int)ItemStackScoreUtil.L(this.itemStack) + " Protection\n" : "+" + (int)ItemStackScoreUtil.I$src$F$dh3k81(this.itemStack) + " Damage\n";
                String tooltipText = itemSummary + durabilityText + enchantmentText;
                ToolTips toolTips = new ToolTips(this, tooltipText, 0.75, ItemStackSlotComponent.J.A, false, itemName, 0.9, ItemStackSlotComponent.J.B, true);
                this.setToolTips(toolTips);
            }
        } else {
            this.setToolTips(null);
        }
        this.tooltipDirty = false;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public void c() {
        super.c();
        this.onDisable();
        if (this.itemStack != null) {
            double availableWidth = this.A() - (double)this.iconSize;
            double availableHeight = this.L() - (double)this.iconSize;
            double offsetX = availableWidth / 2.0;
            double offsetY = availableHeight / 2.0;
            ItemIconRenderer.renderItemStack(this.itemStack, (float)(this.G$src$D$1b2f02a() + offsetX), (float)(this.n() + offsetY), this.iconSize, this.iconSize, 1.0f, 1.0f);
            if (this.selected) {
                GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), new Color(126, 84, 217, 215), 1.0f, 0.8f, 1.0f);
            }
        }
    }

    @Override
    public void F() {
    }

    @Override
    public void H() {
    }

    public boolean isSelected() {
        return this.selected;
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    public ItemStackSlotComponent() {
        this(12.0, 12.0, 10);
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.tooltipDirty = true;
    }

    public ItemStackSlotComponent(double width, double height, int requestedIconSize) {
        this.o(width);
        this.Y(height);
        this.iconSize = (int)Math.min(Math.min((double)requestedIconSize, width), height);
    }

    @Override
    public void onDisable() {
        if (!this.isShowDisabledOverlay()) {
            return;
        }
        GuiRenderPrimitives.e(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.getDisabledOverlayColor(), false, 1.0f, 1.0f);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void I() {
    }

    @Override
    public double C() {
        return 0.0;
    }
}
