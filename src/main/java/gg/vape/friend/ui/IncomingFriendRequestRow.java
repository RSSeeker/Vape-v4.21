package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.IncomingFriendRequest;
import gg.vape.friend.ui.FriendRequestRow;
import gg.vape.friend.ui.IncomingFriendRequestAcceptClickHandler;
import gg.vape.friend.ui.IncomingFriendRequestDeclineClickHandler;

public class IncomingFriendRequestRow
extends FriendRequestRow {
    private final IncomingFriendRequest request;

    public static void accept(IncomingFriendRequestRow row) {
        row.acceptRequest();
    }

    public IncomingFriendRequestRow(IncomingFriendRequest incomingFriendRequest) {
        super(incomingFriendRequest, null);
        this.request = incomingFriendRequest;
        this.getAddButton().addClickListener(new IncomingFriendRequestAcceptClickHandler(this));
        this.getActionPanel().h(this.getAddButton(), new Object[0]);
        this.getCloseButton().addClickListener(new IncomingFriendRequestDeclineClickHandler(this));
    }

    private void declineRequest() {
        Vape.INSTANCE.getOnlineManager().getFriendRequestManager().declineIncomingRequest(this.request);
    }

    private void acceptRequest() {
        Vape.INSTANCE.getOnlineManager().getFriendRequestManager().acceptIncomingRequest(this.request);
    }

    public static void decline(IncomingFriendRequestRow row) {
        row.declineRequest();
    }
}
