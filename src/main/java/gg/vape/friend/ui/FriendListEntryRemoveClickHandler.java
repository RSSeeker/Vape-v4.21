package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.ExternalFriend;
import gg.vape.friend.Friend;
import gg.vape.friend.FriendEntry;
import gg.vape.friend.ui.FriendListEntryRow;
import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.ui.click.component.GuiClickListener;

public class FriendListEntryRemoveClickHandler
implements GuiClickListener {
    private final FriendEntry friendEntry;
    private final FriendListEntryRow entryRow;

    public FriendListEntryRemoveClickHandler(FriendListEntryRow friendListEntryRow, FriendEntry friendEntry) {
        this.entryRow = friendListEntryRow;
        this.friendEntry = friendEntry;
    }


    @Override
    public void onPrimaryClick() {
        if (this.friendEntry instanceof Friend) {
            Vape.INSTANCE.getFriendManager().removeFriend((Friend)this.friendEntry);
        } else if (this.friendEntry instanceof ExternalFriend) {
            ExternalFriend externalFriend = (ExternalFriend)this.friendEntry;
            externalFriend.getOnlineFriend().setSyncWithFriends(false);
        }
        OnlineFriendUiHelper.refreshFriendLists();
    }
}

