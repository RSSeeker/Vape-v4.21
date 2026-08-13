package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEventPayloadBase;

public class GroupChatMessageEvent
extends OnlineEventPayloadBase {
    private final String d;
    private final UserModel h;

    public GroupChatMessageEvent(ZeusClient oZ, UserModel oj_12, UserModel oj_13, String string) {
        super(oZ, oj_12);
        this.h = oj_13;
        this.d = string;
    }

    public String K() {
        return this.d;
    }

    public UserModel V() {
        return this.h;
    }
}

