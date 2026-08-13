package gg.vape.friend;

import gg.vape.friend.FriendRequest;
import gg.vape.friend.FriendRequestModel;

public class OutgoingFriendRequest
extends FriendRequest {
    public OutgoingFriendRequest(FriendRequestModel friendRequestModel) {
        super(friendRequestModel, false);
    }
}

