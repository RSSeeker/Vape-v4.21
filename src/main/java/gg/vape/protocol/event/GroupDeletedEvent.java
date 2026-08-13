package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class GroupDeletedEvent
extends OnlineEvent {
    public GroupDeletedEvent(ZeusClient oZ) {
        super(oZ);
    }
}

