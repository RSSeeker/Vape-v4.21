package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.AnchoredPopupFrame;
import java.awt.Point;

class AnchoredPopupFrameCloseMouseListener
implements GuiMouseListener {
    final AnchoredPopupFrame O;


    AnchoredPopupFrameCloseMouseListener(AnchoredPopupFrame anchoredPopupFrame) {
        this.O = anchoredPopupFrame;
    }

    @Override
    public void g(Point point, MouseClickButton uA) {
        ClientSettings.removePopup(this.O);
        Runnable runnable = AnchoredPopupFrame.b(this.O);
        if (runnable != null) {
            runnable.run();
        }
    }
}

