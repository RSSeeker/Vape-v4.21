package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.value.ListValueOptionsPanel;
import gg.vape.value.OptionalLimitEntry;
import gg.vape.value.OptionalLimitValue;

class RemoveOptionalLimitEntryHandler
implements GuiClickListener {
    final ListValueOptionsPanel optionsPanel;
    final OptionalLimitEntry entry;

    @Override
    public void onPrimaryClick() {
        ((OptionalLimitValue)ListValueOptionsPanel.getListValueCompat(this.optionsPanel)).removeEntry(this.entry);
        ListValueOptionsPanel.getListValueCompat(this.optionsPanel).notifyChanged();
        this.optionsPanel.refreshEntries();
    }

    RemoveOptionalLimitEntryHandler(ListValueOptionsPanel listValueOptionsPanel, OptionalLimitEntry optionalLimitEntry) {
        this.optionsPanel = listValueOptionsPanel;
        this.entry = optionalLimitEntry;
    }
}
