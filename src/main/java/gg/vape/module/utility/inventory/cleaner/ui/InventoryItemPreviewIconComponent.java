package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.ui.InventoryItemPreviewComponent;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class InventoryItemPreviewIconComponent
extends GuiComponent {
    @Nullable
    private final ItemStack itemStack;
    final InventoryItemPreviewComponent parent;
    @Nullable
    private final Item item;
    private final ColorAnimation borderAnimation;

    @Override
    public double C() {
        return 12.0;
    }

    @Override
    public void H() {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), InventoryItemPreviewIconComponent.J.y);
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.borderAnimation.getInterpolatedColor(), 1.5f, 0.75f, 1.0f);
        if (this.itemStack != null) {
            ItemIconRenderer.renderItemStack(this.itemStack, (float)(this.G$src$D$1b2f02a() + 2.0), (float)(this.n() + 2.0), 8, 8);
        }
    }

    @Override
    public double x() {
        return 12.0;
    }

    public InventoryItemPreviewIconComponent(@Nullable InventoryItemPreviewComponent inventoryItemPreviewComponent, ItemStack itemStack) {
        this.parent = inventoryItemPreviewComponent;
        this.borderAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), InventoryItemPreviewIconComponent.J.O);
        this.item = itemStack != null && itemStack.isNotNull() ? itemStack.getItem() : null;
        this.itemStack = itemStack != null && itemStack.isNotNull() ? itemStack : null;
    }

    public InventoryItemPreviewIconComponent(@Nullable InventoryItemPreviewComponent inventoryItemPreviewComponent, Item item) {
        this.parent = inventoryItemPreviewComponent;
        this.borderAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), InventoryItemPreviewIconComponent.J.O);
        this.item = item != null && item.isNotNull() ? item : null;
        this.itemStack = item != null && item.isNotNull() ? ItemStack.S(item) : null;
    }

    @Override
    public void onEnable() {
        this.borderAnimation.J();
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.borderAnimation.J();
        }
    }

}

