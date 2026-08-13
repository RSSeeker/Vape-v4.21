package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.SlotInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryFilterRuleRowBase;
import gg.vape.module.utility.inventory.cleaner.ui.ItemFilterSelectionComponent;
import gg.vape.module.utility.inventory.cleaner.ui.SlotInventoryFilterRuleRow;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

class SlotInventoryFilterRuleRowContent
extends InventoryFilterRuleRowBase {
    private final SlotInventoryFilterRule rule;
    private final InventoryCleanerProfile profile;
    private final ColorAnimation dotColorAnimation;
    private final ColorAnimation backgroundColorAnimation;
    final SlotInventoryFilterRuleRow parent;
    private final ItemFilterSelectionComponent itemSelection;

    SlotInventoryFilterRuleRowContent(SlotInventoryFilterRuleRow slotInventoryFilterRuleRow, InventoryCleanerProfile inventoryCleanerProfile, SlotInventoryFilterRule slotInventoryFilterRule) {
        this.parent = slotInventoryFilterRuleRow;
        this.backgroundColorAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), SlotInventoryFilterRuleRowContent.J.l);
        this.dotColorAnimation = new ColorAnimation(0.15, new Color(0, 0, 0, 0), SlotInventoryFilterRuleRowContent.J.K);
        this.profile = inventoryCleanerProfile;
        this.rule = slotInventoryFilterRule;
        this.itemSelection = new ItemFilterSelectionComponent(slotInventoryFilterRule);
        this.itemSelection.setBlatantMod(true);
        this.refresh();
        this.setPropagateMouseEvents(true);
        this.addChildren(this.itemSelection);
    }


    @Override
    public void refresh() {
        this.backgroundColorAnimation.setStartColor(this.rule.getItemSelection().isEmpty() ? SlotInventoryFilterRuleRowContent.J.t : SlotInventoryFilterRuleRowContent.J.l);
        this.backgroundColorAnimation.setEndColor(this.rule.getItemSelection().isEmpty() ? SlotInventoryFilterRuleRowContent.J.l : SlotInventoryFilterRuleRowContent.J.y);
    }

    @Override
    public void H() {
        boolean enabled = this.rule.getItemSelection().isEmpty();
        if (enabled) {
            GuiRenderPrimitives.B(this.G$src$D$1b2f02a() + 1.0, this.n() + 1.0, this.A() - 2.0, this.L() - 2.0, new Color(0, 0, 0, 127), 2.0f);
            GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.backgroundColorAnimation.getInterpolatedColor(), 4.0f, 1.0f, 1.0f);
            this.itemSelection.K(this.G$src$D$1b2f02a());
            this.itemSelection.S(this.n());
            this.itemSelection.o(this.A());
            this.itemSelection.Y(this.L());
            return;
        }
        GuiRenderPrimitives.B(this.G$src$D$1b2f02a() + 1.0, this.n() + 1.0, this.A() - 2.0, this.L() - 2.0, SlotInventoryFilterRuleRowContent.J.m, 2.0f);
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.backgroundColorAnimation.getInterpolatedColor(), 4.0f, 1.0f, 1.0f);
        this.itemSelection.K(this.G$src$D$1b2f02a());
        this.itemSelection.S(this.n());
        this.itemSelection.o(this.A());
        this.itemSelection.Y(this.L());
        double dotSize = 1.5;
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + this.A() / 2.0 - 0.75, this.n() + this.L() - 4.0, 1.5, 1.0, this.dotColorAnimation.getInterpolatedColor());
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + this.A() / 2.0 - 0.75 - 3.0, this.n() + this.L() - 4.0, 1.5, 1.0, this.dotColorAnimation.getInterpolatedColor());
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + this.A() / 2.0 - 0.75 + 3.0, this.n() + this.L() - 4.0, 1.5, 1.0, this.dotColorAnimation.getInterpolatedColor());
    }

    @Override
    public void onEnable() {
        this.backgroundColorAnimation.J();
        this.dotColorAnimation.J();
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.backgroundColorAnimation.J();
            this.dotColorAnimation.J();
        }
    }

    @Override
    public double x() {
        return 32.0;
    }

    @Override
    public double C() {
        return 32.0;
    }
}

