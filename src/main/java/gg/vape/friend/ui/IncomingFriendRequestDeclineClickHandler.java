package gg.vape.friend.ui;

import gg.vape.friend.ui.IncomingFriendRequestRow;
import gg.vape.ui.click.component.GuiClickListener;

public class IncomingFriendRequestDeclineClickHandler
implements GuiClickListener {
    final IncomingFriendRequestRow row;

    public IncomingFriendRequestDeclineClickHandler(IncomingFriendRequestRow row) {
        this.row = row;
    }

    @Override
    public void onPrimaryClick() {
        IncomingFriendRequestRow.decline(this.row);
    }
}
