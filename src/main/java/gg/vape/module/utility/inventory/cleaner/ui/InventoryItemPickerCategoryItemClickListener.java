package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.mapping.ItemMappingEntry;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryItemPickerPanel;
import gg.vape.module.utility.inventory.cleaner.ui.ItemPickerSelection;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

public class InventoryItemPickerCategoryItemClickListener
implements GuiMouseListener {
    final ItemMappingEntry mappingEntry;
    final InventoryItemPickerPanel panel;

    public InventoryItemPickerCategoryItemClickListener(InventoryItemPickerPanel inventoryItemPickerPanel, ItemMappingEntry itemMappingEntry) {
        this.panel = inventoryItemPickerPanel;
        this.mappingEntry = itemMappingEntry;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        InventoryItemPickerPanel.select(this.panel, ItemPickerSelection.ofRight(this.mappingEntry));
    }
}
