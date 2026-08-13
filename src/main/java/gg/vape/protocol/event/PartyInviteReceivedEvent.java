package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class PartyInviteReceivedEvent
extends OnlineEvent {
    private final UserModel R;

    public UserModel R() {
        return this.R;
    }

    public PartyInviteReceivedEvent(ZeusClient zeusClient, UserModel userModel) {
        super(zeusClient);
        this.R = userModel;
    }
}

