package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class PartyLeaderChangedEvent
extends OnlineEvent {
    private final UserModel j;

    public PartyLeaderChangedEvent(ZeusClient oZ, UserModel oj_12) {
        super(oZ);
        this.j = oj_12;
    }

    public UserModel z() {
        return this.j;
    }
}

