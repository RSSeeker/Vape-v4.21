package gg.vape.module.utility.inventory;

import gg.vape.module.utility.inventory.HotbarSlotRuleItemPickerFrame;
import gg.vape.module.utility.inventory.HotbarSlotRuleSlotSelectClickHandler;
import gg.vape.module.utility.inventory.ItemStackIconButtonComponent;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.List;

public class HotbarSlotRuleSlotSelectorComponent
extends GuiComponent {
    private List<ItemStackIconButtonComponent> slotButtons = new ArrayList<ItemStackIconButtonComponent>();
    private HotbarSlotRuleItemPickerFrame itemPickerFrame;


    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void I() {
    }

    @Override
    public double C() {
        return 50.0;
    }

    @Override
    public void K(double d) {
        super.K(d);
    }

    @Override
    public void H() {
    }

    @Override
    public void u() {
    }

    @Override
    public void c() {
        double d = this.n() + 10.0;
        double d2 = this.G$src$D$1b2f02a() + 5.0;
        for (int i = 0; i < this.slotButtons.size(); ++i) {
            ItemStackIconButtonComponent itemStackIconButtonComponent = this.slotButtons.get(i);
            itemStackIconButtonComponent.K(d2);
            itemStackIconButtonComponent.S(d);
            itemStackIconButtonComponent.h(this.itemPickerFrame.getGroupComponent().getRules().get(i).createItemStack());
            d2 += itemStackIconButtonComponent.A();
            itemStackIconButtonComponent.c(this.itemPickerFrame.getSelectedSlot() == i);
        }
        super.c();
    }

    public HotbarSlotRuleSlotSelectorComponent(HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame) {
        this.itemPickerFrame = hotbarSlotRuleItemPickerFrame;
        int n = 0;
        while (n < 9) {
            ItemStackIconButtonComponent itemStackIconButtonComponent = new ItemStackIconButtonComponent(HotbarSlotRuleSlotSelectorComponent.J.r, HotbarSlotRuleSlotSelectorComponent.J.m, 0);
            int n2 = n++;
            itemStackIconButtonComponent.addClickListener(new HotbarSlotRuleSlotSelectClickHandler(this, hotbarSlotRuleItemPickerFrame, n2));
            this.slotButtons.add(itemStackIconButtonComponent);
            this.addChildren(itemStackIconButtonComponent);
        }
    }

    @Override
    public void F() {
    }

    @Override
    public double x() {
        return 250.0;
    }
}

