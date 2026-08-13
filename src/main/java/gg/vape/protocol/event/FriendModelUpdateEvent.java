package gg.vape.protocol.event;

import gg.vape.friend.FriendModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEventPayloadBase;

public class FriendModelUpdateEvent
extends OnlineEventPayloadBase {
    private final FriendModel P;

    public FriendModelUpdateEvent(ZeusClient zeusClient, FriendModel friendModel) {
        super(zeusClient, friendModel.getUser());
        this.P = friendModel;
    }

    public FriendModel q() {
        return this.P;
    }
}
