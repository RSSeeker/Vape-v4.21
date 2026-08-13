package gg.vape.friend.ui;

import gg.vape.friend.ui.PartyDetailsPanel;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

public class PartyOverviewPanelPopupClickListener
implements GuiMouseListener {
    private final PartyDetailsPanel detailsPanel;

    public PartyOverviewPanelPopupClickListener(PartyDetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        PartyDetailsPanel.openMembersPopup(this.detailsPanel);
    }
}
