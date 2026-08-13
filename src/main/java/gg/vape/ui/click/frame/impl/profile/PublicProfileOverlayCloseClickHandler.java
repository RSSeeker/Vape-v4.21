package gg.vape.ui.click.frame.impl.profile;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.impl.profile.PublicProfileOverlayPopupFrame;
import java.awt.Point;

class PublicProfileOverlayCloseClickHandler
implements GuiMouseListener {
    final PublicProfileOverlayPopupFrame popupFrame;

    PublicProfileOverlayCloseClickHandler(PublicProfileOverlayPopupFrame publicProfileOverlayPopupFrame) {
        this.popupFrame = publicProfileOverlayPopupFrame;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        ClientSettings.removePopup(this.popupFrame);
    }
}
