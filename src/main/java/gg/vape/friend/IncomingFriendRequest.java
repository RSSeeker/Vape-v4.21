package gg.vape.friend;

import gg.vape.friend.FriendRequest;
import gg.vape.friend.FriendRequestModel;

public class IncomingFriendRequest
extends FriendRequest {
    public IncomingFriendRequest(FriendRequestModel friendRequestModel) {
        super(friendRequestModel, true);
    }
}

