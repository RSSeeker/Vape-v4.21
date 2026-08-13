package gg.vape.module.utility.inventory;

import gg.vape.module.utility.inventory.HotbarSlotRuleItemPickerFrame;
import gg.vape.module.utility.inventory.HotbarSlotRuleItemPickerSearchCloseClickHandler;
import gg.vape.module.utility.inventory.HotbarSlotRuleSearchInputKeyTypedListener;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.component.gui.TextButton;

public class HotbarSlotRuleItemSearchComponent
extends GuiComponent {
    private TextButton saveExitButton;
    private LabeledTextInputComponent searchInput = new LabeledTextInputComponent("Search Item Name");

    @Override
    public void F() {
    }

    @Override
    public void I() {
    }

    @Override
    public double x() {
        return 220.0;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public static LabeledTextInputComponent g(HotbarSlotRuleItemSearchComponent hotbarSlotRuleItemSearchComponent) {
        return hotbarSlotRuleItemSearchComponent.searchInput;
    }

    public HotbarSlotRuleItemSearchComponent(HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame) {
        this.saveExitButton = new TextButton("Save & Exit", HotbarSlotRuleItemSearchComponent.J.B);
        this.searchInput.addKeyTypedListener(new HotbarSlotRuleSearchInputKeyTypedListener(this, hotbarSlotRuleItemPickerFrame));
        this.saveExitButton.addClickListener(new HotbarSlotRuleItemPickerSearchCloseClickHandler(this));
        this.addChildren(this.searchInput, this.saveExitButton);
    }

    @Override
    public void H() {
        double unit = 27.5;
        this.searchInput.K(this.G$src$D$1b2f02a());
        this.searchInput.S(this.n() + 20.0 - 5.0 - 2.5);
        this.searchInput.o(unit * 6.0 + 5.0);
        this.saveExitButton.o(unit * 2.0);
        this.saveExitButton.Y(15.0);
        this.saveExitButton.K(this.G$src$D$1b2f02a() + this.searchInput.A());
        this.saveExitButton.S(this.n() + 20.0 - 5.0);
    }

    @Override
    public void u() {
    }

    @Override
    public double C() {
        return 40.0;
    }
}

