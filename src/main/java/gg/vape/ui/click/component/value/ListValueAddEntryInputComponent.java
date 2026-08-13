package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.value.ListValueEntryInputComponentBase;
import gg.vape.ui.click.component.value.ListValueOptionsPanel;
import gg.vape.value.ListValue;

public class ListValueAddEntryInputComponent
extends ListValueEntryInputComponentBase {
    final ListValueOptionsPanel optionsPanel;
    final ListValue listValue;

    @Override
    public void submit() {
        if (!this.hasNonBlankText()) {
            this.setText("");
            return;
        }
        String string = this.getText();
        this.listValue.createEntry(string, -1);
        this.listValue.notifyChanged();
        this.optionsPanel.refreshEntries();
        this.setText("");
    }


    public ListValueAddEntryInputComponent(ListValueOptionsPanel optionsPanel, boolean blockedList, String placeholder, ListValue listValue) {
        super(blockedList, placeholder);
        this.optionsPanel = optionsPanel;
        this.listValue = listValue;
    }
}
