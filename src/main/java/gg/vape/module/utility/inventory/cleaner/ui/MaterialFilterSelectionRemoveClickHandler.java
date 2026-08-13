package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.ui.MaterialFilterSelectionList;
import gg.vape.module.utility.inventory.cleaner.ui.MaterialFilterSelectionRow;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

class MaterialFilterSelectionRemoveClickHandler
implements GuiMouseListener {
    final MaterialFilterSelectionList list;
    final MaterialFilterSelectionRow row;

    MaterialFilterSelectionRemoveClickHandler(MaterialFilterSelectionList materialFilterSelectionList, MaterialFilterSelectionRow materialFilterSelectionRow) {
        this.list = materialFilterSelectionList;
        this.row = materialFilterSelectionRow;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.list.removeSelectionRow(this.row);
    }
}
