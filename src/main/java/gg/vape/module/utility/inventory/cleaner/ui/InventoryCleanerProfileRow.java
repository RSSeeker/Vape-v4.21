package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfile;
import gg.vape.module.utility.inventory.cleaner.InventoryCleanerProfileValue;
import gg.vape.module.utility.inventory.cleaner.SlotInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerProfileRowClickHandler;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerSlotRulePreview;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

public class InventoryCleanerProfileRow
extends GuiComponent {
    private final InventoryCleanerProfileValue profileValue;
    private final PanelComponent slotPreviews = new PanelComponent(0.0, 0.0);
    private final InventoryCleanerProfile profile;
    private static final String WIDTH_WRAP = "widthwrap";

    @Override
    public void u() {
        this.slotPreviews.T$src$V$1wse0de();
    }

    public InventoryCleanerProfileRow(InventoryCleanerProfileValue inventoryCleanerProfileValue, InventoryCleanerProfile inventoryCleanerProfile, Runnable runnable) {
        this.profileValue = inventoryCleanerProfileValue;
        this.bindValue(inventoryCleanerProfileValue);
        this.profile = inventoryCleanerProfile;
        this.slotPreviews.k(true);
        this.slotPreviews.setShowDisabledOverlay(false);
        this.addMouseListener(new InventoryCleanerProfileRowClickHandler(this, inventoryCleanerProfile, inventoryCleanerProfileValue, runnable));
        this.populateSlotPreviews();
    }

    @Override
    public void H() {
        this.w(this.profile.getName());
        double contentX = this.G$src$D$1b2f02a() + 5.0;
        double width = this.A() - 10.0;
        GuiRenderPrimitives.d(contentX, this.n(), width, this.L(), this.profile.equals(this.profileValue.getValue()) ? InventoryCleanerProfileRow.J.y : (this.w$src$Z$e457mb() ? InventoryCleanerProfileRow.J.R : InventoryCleanerProfileRow.J.m));
        this.slotPreviews.K(contentX);
        this.slotPreviews.S(this.n());
        this.slotPreviews.o(width);
        this.slotPreviews.Y(this.L());
        this.slotPreviews.setShowDisabledOverlay(false);
        this.slotPreviews.c();
    }

    private void populateSlotPreviews() {
        this.slotPreviews.removeMarkedChildren();
        this.slotPreviews.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(WIDTH_WRAP);
        double spacing = 2.75;
        this.slotPreviews.h(new SpacerComponent(spacing, 0.0), new Object[0]);
        for (int i = 0; i < 9; ++i) {
            SlotInventoryFilterRule slotRule = this.profile.getOrCreateSlotRule(i);
            InventoryCleanerSlotRulePreview preview = new InventoryCleanerSlotRulePreview(this.profileValue, this.profile, slotRule);
            this.slotPreviews.h(new PaddedComponent(1.0, 1.5, 0.0, spacing, preview), new Object[0]);
        }
        this.slotPreviews.H(true);
    }

    @Override
    public double C() {
        return 11.0;
    }


    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (this.slotPreviews.w$src$Z$e457mb()) {
            this.slotPreviews.dispatchMouseEvent(guiMouseEvent);
        }
    }

    @Override
    public void F() {
        this.slotPreviews.J();
    }
}

