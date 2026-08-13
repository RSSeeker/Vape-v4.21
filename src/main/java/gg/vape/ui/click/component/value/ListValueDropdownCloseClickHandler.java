package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.value.ListValueComponent;
import gg.vape.ui.click.component.value.ListValueDropdownLayer;

public class ListValueDropdownCloseClickHandler
implements GuiClickListener {
    final ListValueComponent component;
    final ListValueDropdownLayer dropdownLayer;

    public ListValueDropdownCloseClickHandler(ListValueDropdownLayer listValueDropdownLayer, ListValueComponent listValueComponent) {
        this.dropdownLayer = listValueDropdownLayer;
        this.component = listValueComponent;
    }

    @Override
    public void onPrimaryClick() {
        this.component.setExpanded(false);
    }
}
