package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.AnchoredPopupFrame;
import java.awt.Point;

class AnchoredPopupFrameOutsideCloseMouseListener
implements GuiMouseListener {
    final GuiComponent J;
    final AnchoredPopupFrame q;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (!this.q.w$src$Z$e457mb() && !this.J.w$src$Z$e457mb()) {
            ClientSettings.removePopup(this.q);
            Runnable runnable = AnchoredPopupFrame.b(this.q);
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    AnchoredPopupFrameOutsideCloseMouseListener(AnchoredPopupFrame anchoredPopupFrame, GuiComponent guiComponent) {
        this.q = anchoredPopupFrame;
        this.J = guiComponent;
    }

}

