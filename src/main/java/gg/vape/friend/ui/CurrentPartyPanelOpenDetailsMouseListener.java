package gg.vape.friend.ui;

import gg.vape.friend.ui.CurrentPartyPanel;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

public class CurrentPartyPanelOpenDetailsMouseListener
implements GuiMouseListener {
    private final CurrentPartyPanel panel;

    public CurrentPartyPanelOpenDetailsMouseListener(CurrentPartyPanel currentPartyPanel) {
        this.panel = currentPartyPanel;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        CurrentPartyPanel.openDetails(this.panel, point, mouseClickButton);
    }
}
