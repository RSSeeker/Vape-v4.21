package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.impl.main.ClickGuiModulesSidecarPanel;
import java.awt.Point;

class ClickGuiModulesSidecarSecondaryMouseListener
implements GuiMouseListener {
    private final Runnable action;

    @Override
    public void g(Point point, MouseClickButton button) {
        this.action.run();
    }

    ClickGuiModulesSidecarSecondaryMouseListener(Runnable action) {
        this.action = action;
    }
}
