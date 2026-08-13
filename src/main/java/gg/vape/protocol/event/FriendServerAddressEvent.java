package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import org.jetbrains.annotations.Nullable;

public class FriendServerAddressEvent
extends OnlineEvent {
    private final long f;
    @Nullable
    private final String g;

    public long a() {
        return this.f;
    }

    public FriendServerAddressEvent(ZeusClient zeusClient, long l, @Nullable String string) {
        super(zeusClient);
        this.f = l;
        this.g = string;
    }

    @Nullable
    public String Z() {
        return this.g;
    }
}

