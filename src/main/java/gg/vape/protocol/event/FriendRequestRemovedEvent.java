package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class FriendRequestRemovedEvent
extends OnlineEvent {
    private final long g;

    public long v() {
        return this.g;
    }

    public FriendRequestRemovedEvent(ZeusClient oZ, long l) {
        super(oZ);
        this.g = l;
    }
}

