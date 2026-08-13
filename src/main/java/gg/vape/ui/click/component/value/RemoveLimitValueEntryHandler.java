package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.value.ListValueOptionsPanel;
import gg.vape.unmap.ItemLimitData;
import gg.vape.value.LimitValue;

public class RemoveLimitValueEntryHandler
implements GuiClickListener {
    final ItemLimitData entry;
    final ListValueOptionsPanel optionsPanel;

    public RemoveLimitValueEntryHandler(ListValueOptionsPanel listValueOptionsPanel, ItemLimitData itemLimitData) {
        this.optionsPanel = listValueOptionsPanel;
        this.entry = itemLimitData;
    }

    @Override
    public void onPrimaryClick() {
        ((LimitValue)ListValueOptionsPanel.getListValueCompat(this.optionsPanel)).removeEntry(this.entry);
        ListValueOptionsPanel.getListValueCompat(this.optionsPanel).notifyChanged();
        this.optionsPanel.refreshEntries();
    }
}
