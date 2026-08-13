package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerClickableRowComponentBase;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;

public class InventoryItemMatcherRowComponent
extends InventoryCleanerClickableRowComponentBase {
    private static final String ELLIPSIS = "...";
    private final IconGlyphComponent iconComponent;
    private final TruncatedTextComponent textComponent;

    public InventoryItemMatcherRowComponent(InventoryItemMatcher inventoryItemMatcher, GuiClickListener guiClickListener) {
        super(guiClickListener);
        this.iconComponent = new IconGlyphComponent(inventoryItemMatcher.getIconName(), 6.0f, 6.0f, InventoryItemMatcherRowComponent.J.Z);
        this.textComponent = new TruncatedTextComponent(inventoryItemMatcher.getName(), ELLIPSIS, 50.0, 0.8, InventoryItemMatcherRowComponent.J.Z, false);
        this.setPropagateMouseEvents(true);
        this.addChildren(this.textComponent, this.iconComponent);
    }

    @Override
    public void H() {
        super.H();
        this.iconComponent.K(this.G$src$D$1b2f02a() + 10.0);
        this.iconComponent.S(this.n() + this.L() / 2.0 - this.iconComponent.L() / 2.0);
        this.textComponent.Y(this.L());
        this.textComponent.K(this.iconComponent.G$src$D$1b2f02a() + this.iconComponent.A() + 5.0);
        this.textComponent.S(this.n());
    }
}

