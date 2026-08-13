package gg.vape.module.utility.inventory;

import gg.vape.module.utility.inventory.HotbarSlotRuleItemPickerFrame;
import gg.vape.ui.click.component.GuiClickListener;

class HotbarSlotRuleItemPickerHeaderCloseClickHandler
implements GuiClickListener {
    final HotbarSlotRuleItemPickerFrame pickerFrame;

    HotbarSlotRuleItemPickerHeaderCloseClickHandler(HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame) {
        this.pickerFrame = hotbarSlotRuleItemPickerFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.pickerFrame.commitSelection();
        this.pickerFrame.closePicker();
    }
}
