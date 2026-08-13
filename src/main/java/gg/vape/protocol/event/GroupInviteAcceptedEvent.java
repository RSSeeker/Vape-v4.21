package gg.vape.protocol.event;

import gg.vape.friend.PartyState;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class GroupInviteAcceptedEvent
extends OnlineEvent {
    private final PartyState c;

    public PartyState P() {
        return this.c;
    }

    public GroupInviteAcceptedEvent(ZeusClient oZ, PartyState wF) {
        super(oZ);
        this.c = wF;
    }
}

