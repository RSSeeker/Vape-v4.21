package gg.vape.protocol.event;

import gg.vape.friend.PartyState;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class GroupCreatedEvent
extends OnlineEvent {
    private final PartyState Y;

    public GroupCreatedEvent(ZeusClient oZ, PartyState wF) {
        super(oZ);
        this.Y = wF;
    }

    public PartyState V() {
        return this.Y;
    }
}

