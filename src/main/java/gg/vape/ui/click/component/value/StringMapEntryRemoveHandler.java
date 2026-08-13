package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.value.StringMapEntryComponent;
import gg.vape.ui.click.component.value.StringMapValueComponent;

class StringMapEntryRemoveHandler
implements GuiClickListener {
    final StringMapEntryComponent entry;
    final StringMapValueComponent owner;

    @Override
    public void onPrimaryClick() {
        StringMapValueComponent.getStringMapValueCompat(this.owner).removeEntry(this.entry.getKeyText());
        StringMapValueComponent.refreshEntriesCompat(this.owner);
    }

    StringMapEntryRemoveHandler(StringMapValueComponent stringMapValueComponent, StringMapEntryComponent stringMapEntryComponent) {
        this.owner = stringMapValueComponent;
        this.entry = stringMapEntryComponent;
    }
}
