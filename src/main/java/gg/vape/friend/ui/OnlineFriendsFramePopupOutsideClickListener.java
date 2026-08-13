package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendsFrame;
import gg.vape.ui.click.GuiMouseListener;
import java.awt.Point;

public class OnlineFriendsFramePopupOutsideClickListener
implements GuiMouseListener {
    private final OnlineFriendsFrame friendsFrame;

    public OnlineFriendsFramePopupOutsideClickListener(OnlineFriendsFrame onlineFriendsFrame) {
        this.friendsFrame = onlineFriendsFrame;
    }

    @Override
    public boolean Q(Point point) {
        if (!this.friendsFrame.q() && OnlineFriendsFrame.getNotificationOverlay(this.friendsFrame).w$src$Z$e457mb()) {
            OnlineFriendsFrame.getNotificationOverlay(this.friendsFrame).R();
            return true;
        }
        return false;
    }

}

