package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerClickableRowComponentBase;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryItemPreviewComponent;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.wrapper.impl.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InventoryItemStackSelectionRowComponent
extends InventoryCleanerClickableRowComponentBase {
    private final TruncatedTextComponent itemName;
    private final TextButton addButton;
    private final InventoryItemPreviewComponent itemPreview;

    public InventoryItemStackSelectionRowComponent(ItemStack itemStack) {
        this.itemName = new TruncatedTextComponent(itemStack.x(), "...", 50.0, 0.8, InventoryItemStackSelectionRowComponent.J.A, false);
        this.addButton = new TextButton("ADD", 0.55, InventoryItemStackSelectionRowComponent.J.B, InventoryItemStackSelectionRowComponent.J.O);
        this.addButton.Y(8.0);
        this.addButton.o(14.0);
        this.addButton.setDeriveTextColorFromBackground(false);
        this.addButton.setNormalTextColor(InventoryItemStackSelectionRowComponent.J.A);
        this.addButton.setUseAlternateFont(true);
        this.addButton.setVisible(false);
        this.itemPreview = new InventoryItemPreviewComponent(itemStack, false);
        this.addChildren(this.itemName, this.addButton, this.itemPreview);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void K(@Nullable GuiClickListener guiClickListener) {
        super.K(guiClickListener);
        this.addButton.setClickListener(guiClickListener);
    }

    @Override
    public void H() {
        super.H();
        this.itemPreview.K(this.G$src$D$1b2f02a() + 10.0);
        this.itemPreview.S(this.n() + this.L() / 2.0 - this.itemPreview.L() / 2.0);
        this.itemName.Y(this.L());
        this.itemName.K(this.itemPreview.G$src$D$1b2f02a() + this.itemPreview.A() + 5.0);
        this.itemName.S(this.n());
        this.addButton.K(this.G$src$D$1b2f02a() + this.A() - this.addButton.A() - 8.0);
        this.addButton.S(this.n() + this.L() / 2.0 - this.addButton.L() / 2.0);
    }

    @Override
    public void F() {
        this.addButton.setVisible(true);
    }

    @Override
    public void onEnable() {
        this.addButton.setVisible(false);
    }
}
