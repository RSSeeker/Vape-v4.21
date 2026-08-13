package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class FriendChatMessageEvent
extends OnlineEvent {
    private final String t;
    private final UserModel q;

    public UserModel U() {
        return this.q;
    }

    public String g() {
        return this.t;
    }

    public FriendChatMessageEvent(ZeusClient oZ, UserModel oj_12, String string) {
        super(oZ);
        this.q = oj_12;
        this.t = string;
    }
}

