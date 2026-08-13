package gg.vape.friend.ui;

import gg.vape.friend.ui.IncomingFriendRequestRow;
import gg.vape.ui.click.component.GuiClickListener;

public class IncomingFriendRequestAcceptClickHandler
implements GuiClickListener {
    final IncomingFriendRequestRow row;

    @Override
    public void onPrimaryClick() {
        IncomingFriendRequestRow.accept(this.row);
    }

    public IncomingFriendRequestAcceptClickHandler(IncomingFriendRequestRow row) {
        this.row = row;
    }
}
