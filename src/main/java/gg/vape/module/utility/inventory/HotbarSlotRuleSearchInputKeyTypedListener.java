package gg.vape.module.utility.inventory;

import gg.vape.module.utility.inventory.HotbarSlotRuleItemPickerFrame;
import gg.vape.module.utility.inventory.HotbarSlotRuleItemSearchComponent;
import gg.vape.ui.click.component.GuiKeyTypedListener;

public class HotbarSlotRuleSearchInputKeyTypedListener
implements GuiKeyTypedListener {
    final HotbarSlotRuleItemPickerFrame pickerFrame;
    final HotbarSlotRuleItemSearchComponent searchComponent;

    public HotbarSlotRuleSearchInputKeyTypedListener(HotbarSlotRuleItemSearchComponent hotbarSlotRuleItemSearchComponent, HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame) {
        this.searchComponent = hotbarSlotRuleItemSearchComponent;
        this.pickerFrame = hotbarSlotRuleItemPickerFrame;
    }

    @Override
    public void onKeyTyped(char c, int n) {
        this.pickerFrame.s(HotbarSlotRuleItemSearchComponent.g(this.searchComponent).isShowEditButton());
    }
}
