package gg.vape.ui.click.frame.impl.quickactions;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionRowComponent;
import java.awt.Point;

class QuickActionRowClickListener
implements GuiMouseListener {
    private final Class targetFrameClass;
    private final QuickActionRowComponent actionRow;

    QuickActionRowClickListener(QuickActionRowComponent actionRow, Class targetFrameClass) {
        this.actionRow = actionRow;
        this.targetFrameClass = targetFrameClass;
    }

    @Override
    public void g(Point clickPoint, MouseClickButton clickButton) {
        Object previousFrame = ClientSettings.getFrame(this.targetFrameClass);
        ClientSettings.showFrame(this.targetFrameClass);
        ((Frame)ClientSettings.getFrame(this.targetFrameClass)).c(((GuiComponent)previousFrame).V$src$Z$1xhop3l());
    }
}
