package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.ItemInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryFilterRuleRowBase;
import gg.vape.module.utility.inventory.cleaner.ui.ItemFilterSelectionComponent;
import gg.vape.module.utility.inventory.cleaner.ui.ItemInventoryFilterRuleRow;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

class ItemInventoryFilterRuleRowContent
extends InventoryFilterRuleRowBase {
    private final ItemFilterSelectionComponent selectionComponent;
    final ItemInventoryFilterRuleRow parent;
    private final ColorAnimation backgroundAnimation;
    private final ColorAnimation innerAnimation;

    @Override
    public double x() {
        return 32.0;
    }


    @Override
    public double C() {
        return 32.0;
    }

    @Override
    public void onEnable() {
        this.backgroundAnimation.J();
        this.innerAnimation.J();
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.backgroundAnimation.J();
            this.innerAnimation.J();
        }
    }

    @Override
    public void H() {
        GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.backgroundAnimation.getInterpolatedColor(), (float)(this.A() / 2.0));
        GuiRenderPrimitives.B(this.G$src$D$1b2f02a() + 1.0, this.n() + 1.0, this.A() - 2.0, this.L() - 2.0, this.innerAnimation.getInterpolatedColor(), (float)(this.A() / 2.0) - 2.0f);
        this.selectionComponent.K(this.G$src$D$1b2f02a());
        this.selectionComponent.S(this.n());
        this.selectionComponent.o(this.A());
        this.selectionComponent.Y(this.L());
    }

    @Override
    public void refresh() {
        this.backgroundAnimation.setStartColor(ItemInventoryFilterRuleRowContent.J.l);
        this.backgroundAnimation.setEndColor(ItemInventoryFilterRuleRowContent.J.l.brighter());
        this.innerAnimation.setStartColor(ItemInventoryFilterRuleRowContent.J.r);
        this.innerAnimation.setEndColor(ItemInventoryFilterRuleRowContent.J.R);
    }

    public ItemInventoryFilterRuleRowContent(ItemInventoryFilterRuleRow itemInventoryFilterRuleRow, InventoryCleanerProfile inventoryCleanerProfile, ItemInventoryFilterRule itemInventoryFilterRule) {
        this.parent = itemInventoryFilterRuleRow;
        this.backgroundAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), ItemInventoryFilterRuleRowContent.J.l);
        this.innerAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), ItemInventoryFilterRuleRowContent.J.l);
        this.selectionComponent = new ItemFilterSelectionComponent(itemInventoryFilterRule);
        this.refresh();
        this.setPropagateMouseEvents(true);
        this.addChildren(this.selectionComponent);
    }
}

