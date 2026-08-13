package gg.vape.ui.click.component.gui;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.PopupMenuButtonComponent;
import java.awt.Point;

public class InteractivePopupOutsideCloseMouseListener
implements GuiMouseListener {
    final PopupMenuButtonComponent owner;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (!this.owner.t() && !this.owner.getPopupFrame().t()) {
            this.owner.togglePopup();
        }
    }


    public InteractivePopupOutsideCloseMouseListener(PopupMenuButtonComponent popupMenuButtonComponent) {
        this.owner = popupMenuButtonComponent;
    }
}

