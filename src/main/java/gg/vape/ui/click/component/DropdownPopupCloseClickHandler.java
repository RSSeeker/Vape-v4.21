package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.DropdownSelectComponent;
import gg.vape.ui.click.frame.PopupFrame;
import java.awt.Point;

public class DropdownPopupCloseClickHandler
implements GuiMouseListener {
    final DropdownSelectComponent<?> owner;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        PopupFrame popupFrame = this.owner.getPopupFrame();
        if (popupFrame != null && !this.owner.t() && !popupFrame.t()) {
            this.owner.togglePopup();
        }
    }


    public DropdownPopupCloseClickHandler(DropdownSelectComponent<?> dropdown) {
        this.owner = dropdown;
    }
}

