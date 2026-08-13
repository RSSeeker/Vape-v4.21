package gg.vape.friend.ui;

import gg.vape.friend.ui.CurrentPartyPanel;
import gg.vape.ui.click.component.GuiClickListener;

public class CurrentPartyLeaveDeleteClickHandler
implements GuiClickListener {
    private final CurrentPartyPanel panel;

    public CurrentPartyLeaveDeleteClickHandler(CurrentPartyPanel panel) {
        this.panel = panel;
    }

    @Override
    public void onPrimaryClick() {
        CurrentPartyPanel.performLeaveOrDisband(this.panel);
    }
}
