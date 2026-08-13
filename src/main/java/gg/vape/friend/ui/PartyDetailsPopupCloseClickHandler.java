package gg.vape.friend.ui;

import gg.vape.friend.ui.CurrentPartyPanel;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;

public class PartyDetailsPopupCloseClickHandler
implements GuiClickListener {
    private final CurrentPartyPanel panel;

    @Override
    public void onPrimaryClick() {
        ClientSettings.removePopup(CurrentPartyPanel.getDetailsPopup(this.panel));
        CurrentPartyPanel.setDetailsPanel(this.panel, null);
        CurrentPartyPanel.setDetailsPopup(this.panel, null);
    }

    public PartyDetailsPopupCloseClickHandler(CurrentPartyPanel panel) {
        this.panel = panel;
    }
}
