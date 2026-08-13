package gg.vape.ui.click.frame.impl.profile;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.impl.profile.PublicProfileOverlayPopupFrame;
import java.awt.Point;

class PublicProfileOverlayOutsideClickCloseHandler
implements GuiMouseListener {
    final PublicProfileOverlayPopupFrame popupFrame;

    PublicProfileOverlayOutsideClickCloseHandler(PublicProfileOverlayPopupFrame publicProfileOverlayPopupFrame) {
        this.popupFrame = publicProfileOverlayPopupFrame;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (!PublicProfileOverlayPopupFrame.isOutsideClickCloseEnabled(this.popupFrame)) {
            return;
        }
        if (!this.popupFrame.w$src$Z$e457mb()) {
            ClientSettings.removePopup(this.popupFrame);
        }
    }

}

