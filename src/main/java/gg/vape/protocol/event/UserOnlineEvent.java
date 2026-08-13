package gg.vape.protocol.event;

import gg.vape.friend.FriendRequestModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public abstract class UserOnlineEvent
extends OnlineEvent {
    private final FriendRequestModel G;
    private static boolean y;

    public UserOnlineEvent(ZeusClient oZ, FriendRequestModel friendRequestModel) {
        super(oZ);
        this.G = friendRequestModel;
    }


    public static boolean M() {
        return y;
    }

    public static void L(boolean bl) {
        y = bl;
    }

    public static boolean I() {
        boolean bl = UserOnlineEvent.M();
        return true;
    }

    public FriendRequestModel q() {
        return this.G;
    }

    static {
        if (UserOnlineEvent.M()) {
            UserOnlineEvent.L(true);
        }
    }
}

