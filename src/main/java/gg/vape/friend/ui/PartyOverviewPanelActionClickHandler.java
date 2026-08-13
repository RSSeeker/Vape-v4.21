package gg.vape.friend.ui;

import gg.vape.friend.ui.PartyDetailsPanel;
import gg.vape.ui.click.component.GuiClickListener;

public class PartyOverviewPanelActionClickHandler
implements GuiClickListener {
    private final PartyDetailsPanel detailsPanel;

    @Override
    public void onPrimaryClick() {
        PartyDetailsPanel.toggleSettingsPopup(this.detailsPanel);
    }

    public PartyOverviewPanelActionClickHandler(PartyDetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }
}
