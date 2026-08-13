package gg.vape.protocol.event;

import gg.vape.friend.FriendModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class FriendVisibilityUpdateEvent
extends OnlineEvent {
    private static int[] s;
    private final long y;
    private final boolean h;

    public static int[] m() {
        return s;
    }

    public long N() {
        return this.y;
    }

    public boolean q() {
        return this.h;
    }

    public FriendVisibilityUpdateEvent(ZeusClient zeusClient, long l, boolean bl) {
        super(zeusClient);
        this.y = l;
        this.h = bl;
    }

    public static void J(int[] nArray) {
        s = nArray;
    }

    public FriendVisibilityUpdateEvent(ZeusClient zeusClient, FriendModel friendModel) {
        this(zeusClient, friendModel.getUserId(), friendModel.isVisible());
    }

    static {
        if (FriendVisibilityUpdateEvent.m() == null) {
            FriendVisibilityUpdateEvent.J(new int[4]);
        }
    }
}
