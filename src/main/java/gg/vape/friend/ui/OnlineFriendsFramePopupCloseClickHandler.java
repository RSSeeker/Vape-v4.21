package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendsFrame;
import gg.vape.ui.click.component.GuiClickListener;

public class OnlineFriendsFramePopupCloseClickHandler
implements GuiClickListener {
    private final OnlineFriendsFrame friendsFrame;

    @Override
    public void onPrimaryClick() {
        OnlineFriendsFrame.toggleFriendRequestsPopup(this.friendsFrame);
    }

    public OnlineFriendsFramePopupCloseClickHandler(OnlineFriendsFrame onlineFriendsFrame) {
        this.friendsFrame = onlineFriendsFrame;
    }
}
