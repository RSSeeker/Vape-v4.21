package gg.vape.friend.ui;

import gg.vape.friend.ui.FriendRequestsPanel;
import gg.vape.ui.click.component.TextInputComponentBase;

class FriendRequestUsernameInputComponent
extends TextInputComponentBase {
    final FriendRequestsPanel owner;


    FriendRequestUsernameInputComponent(FriendRequestsPanel friendRequestsPanel, String placeholder) {
        super(placeholder);
        this.owner = friendRequestsPanel;
    }

    @Override
    public double C() {
        return 22.0;
    }

    @Override
    public double x() {
        return 104.0;
    }

    @Override
    public void submit() {
        if (this.getText().equals("")) {
            return;
        }
        FriendRequestsPanel.submitUsername(this.owner, this.getText());
        this.setText("");
    }

    @Override
    public float getLeftInset() {
        return 2.0f;
    }
}
