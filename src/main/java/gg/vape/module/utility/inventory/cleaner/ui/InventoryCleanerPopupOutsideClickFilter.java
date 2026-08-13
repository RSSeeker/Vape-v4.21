package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerPopupFrame;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.frame.PopupFrame;
import java.awt.Point;

class InventoryCleanerPopupOutsideClickFilter
implements GuiMouseListener {
    final InventoryCleanerPopupFrame owner;
    final PopupFrame popup;

    InventoryCleanerPopupOutsideClickFilter(InventoryCleanerPopupFrame inventoryCleanerPopupFrame, PopupFrame popupFrame) {
        this.owner = inventoryCleanerPopupFrame;
        this.popup = popupFrame;
    }

    @Override
    public boolean Q(Point point) {
        if (this.owner.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().getBounds().R(point) && !this.popup.getBounds().R(point)) {
            return true;
        }
        return GuiMouseListener.super.Q(point);
    }

}

