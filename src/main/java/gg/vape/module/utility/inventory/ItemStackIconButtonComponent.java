package gg.vape.module.utility.inventory;

import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.awt.Color;

public class ItemStackIconButtonComponent
extends InteractiveComponent {
    private ItemStack itemStack;
    private boolean selected;
    private double size = 27.5;
    private Color backgroundColor;
    private Color hoverColor;
    private int itemId;

    @Override
    public void H() {
        double d = this.L() / 2.0;
        if (this.selected) {
            GuiRenderPrimitives.P(this.G$src$D$1b2f02a() + 1.0, this.n() + 1.0, this.size - 1.0, this.size - 1.0, ItemStackIconButtonComponent.J.y, 2.0f, 1.0f, 2.0f);
        }
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 1.0, this.n() + 1.0, this.size - 2.0, this.size - 2.0, this.w$src$Z$e457mb() ? this.hoverColor : this.backgroundColor);
        if (this.itemStack != null && this.itemStack.isNotNull()) {
            int n = 16;
            double d2 = (double)n / 2.0;
            float f = (float)(this.G$src$D$1b2f02a() + d - d2);
            float f2 = (float)(this.n() + d - d2);
            ItemIconRenderer.renderItemStack(this.itemStack, f, f2, n, n);
        }
    }

    @Override
    public void u() {
    }

    public ItemStackIconButtonComponent(Color color, Color color2, ItemStack itemStack) {
        this.backgroundColor = color;
        this.hoverColor = color2;
        if (itemStack != null && itemStack.isNotNull()) {
            this.itemStack = itemStack;
            this.itemId = itemStack.getItem().P();
            this.w(itemStack.x());
        }
    }

    public void P(int n) {
        this.itemId = n;
        Item item = Item.T(n);
        if (item.isNotNull()) {
            this.itemStack = ItemStack.S(item);
            this.w(this.itemStack.x());
        }
    }

    @Override
    public double C() {
        return this.size;
    }

    public void c(boolean bl) {
        this.selected = bl;
    }

    public void h(ItemStack itemStack) {
        this.itemStack = itemStack;
        if (itemStack != null && itemStack.isNotNull()) {
            this.w(itemStack.x());
        }
    }

    public ItemStackIconButtonComponent(Color color, Color color2, int n) {
        this.backgroundColor = color;
        this.hoverColor = color2;
        this.P(n);
    }

    @Override
    public double x() {
        return this.size;
    }

    @Override
    public void I() {
    }


    @Override
    public void F() {
    }
}

