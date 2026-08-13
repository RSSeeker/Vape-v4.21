package gg.vape.protocol.event;

import gg.vape.friend.FriendRequestModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.UserOnlineEvent;

public class FriendRequestReceivedEvent
extends UserOnlineEvent {
    public FriendRequestReceivedEvent(ZeusClient zeusClient, FriendRequestModel friendRequestModel) {
        super(zeusClient, friendRequestModel);
    }
}

