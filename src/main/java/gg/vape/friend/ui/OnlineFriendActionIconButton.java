package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendActionPanel;
import gg.vape.ui.click.component.gui.IconActionButton;
import java.awt.Color;

class OnlineFriendActionIconButton
extends IconActionButton {
    private final OnlineFriendActionPanel actionPanel;

    OnlineFriendActionIconButton(OnlineFriendActionPanel onlineFriendActionPanel, String string, double d, double d2, double d3, Color color, double d4) {
        super(string, d, d2, d3, color, d4);
        this.actionPanel = onlineFriendActionPanel;
    }


    @Override
    public void setHovered(boolean hovered) {
        if (OnlineFriendActionPanel.isInviteDisabled(this.actionPanel)) {
            this.hovered = false;
            this.getIconColorAnimation().O();
            this.getBackgroundAnimation().O();
            return;
        }
        super.setHovered(hovered);
    }
}
