package gg.vape.module.utility.inventory;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.HotbarSlotRuleItemPickerFrame;
import gg.vape.module.utility.inventory.HotbarSlotRuleItemSearchComponent;
import gg.vape.ui.click.component.GuiClickListener;

public class HotbarSlotRuleItemPickerSearchCloseClickHandler
implements GuiClickListener {
    final HotbarSlotRuleItemSearchComponent searchComponent;

    @Override
    public void onPrimaryClick() {
        ClientSettings.getFrame(HotbarSlotRuleItemPickerFrame.class).commitSelection();
        ClientSettings.getFrame(HotbarSlotRuleItemPickerFrame.class).closePicker();
    }

    public HotbarSlotRuleItemPickerSearchCloseClickHandler(HotbarSlotRuleItemSearchComponent hotbarSlotRuleItemSearchComponent) {
        this.searchComponent = hotbarSlotRuleItemSearchComponent;
    }
}
