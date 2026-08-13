package gg.vape.protocol.event;

import gg.vape.friend.OnlineFriend;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class GroupInviteSentEvent
extends OnlineEvent {
    private final OnlineFriend I;

    public GroupInviteSentEvent(ZeusClient oZ, OnlineFriend yS) {
        super(oZ);
        this.I = yS;
    }

    public OnlineFriend n() {
        return this.I;
    }
}

