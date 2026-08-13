package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.mapping.ItemMappingEntry;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryItemPickerPanel;
import gg.vape.module.utility.inventory.cleaner.ui.ItemPickerSelection;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

public class InventoryItemPickerSearchResultClickListener
implements GuiMouseListener {
    final InventoryItemPickerPanel panel;
    final ItemMappingEntry mappingEntry;

    private void selectItem(ItemMappingEntry itemMappingEntry) {
        InventoryItemPickerPanel.select(this.panel, ItemPickerSelection.ofRight(itemMappingEntry));
        InventoryItemPickerPanel.getSelectedItemIds(this.panel).remove(itemMappingEntry.getResourceKey());
        this.panel.showSearchView();
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        ClientSettings.UI_EXECUTOR.execute(() -> this.selectItem(this.mappingEntry));
    }

    public InventoryItemPickerSearchResultClickListener(InventoryItemPickerPanel inventoryItemPickerPanel, ItemMappingEntry itemMappingEntry) {
        this.panel = inventoryItemPickerPanel;
        this.mappingEntry = itemMappingEntry;
    }
}

