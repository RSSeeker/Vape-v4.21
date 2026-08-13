package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfileValue;
import gg.vape.module.utility.inventory.cleaner.SlotInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ui.ItemFilterSelectionComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

class InventoryCleanerSlotRulePreview
extends GuiComponent {
    private final ItemFilterSelectionComponent selectionComponent;

    @Override
    public double C() {
        return 8.0;
    }

    public InventoryCleanerSlotRulePreview(InventoryCleanerProfileValue inventoryCleanerProfileValue, InventoryCleanerProfile inventoryCleanerProfile, SlotInventoryFilterRule slotInventoryFilterRule) {
        this.bindValue(inventoryCleanerProfileValue);
        this.selectionComponent = new ItemFilterSelectionComponent(slotInventoryFilterRule.getItemSelection());
        this.setPropagateMouseEvents(true);
        this.addChildren(this.selectionComponent);
    }

    @Override
    public double x() {
        return 8.0;
    }

    @Override
    public void H() {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A() + 1.0, this.L() + 1.0, InventoryCleanerSlotRulePreview.J.r);
        this.selectionComponent.K(this.G$src$D$1b2f02a() + 0.5);
        this.selectionComponent.S(this.n() + 0.5);
        this.selectionComponent.o(this.A());
        this.selectionComponent.Y(this.L());
        this.selectionComponent.setIconWidth(6.0f);
        this.selectionComponent.setIconHeight(6.0f);
        this.selectionComponent.setScale(0.5f);
    }
}
