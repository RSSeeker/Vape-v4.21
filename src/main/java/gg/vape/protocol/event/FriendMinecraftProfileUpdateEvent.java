package gg.vape.protocol.event;

import gg.vape.friend.FriendModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import java.util.UUID;

public class FriendMinecraftProfileUpdateEvent
extends OnlineEvent {
    private final UUID F;
    private final long g;
    private final String h;

    public long long_b() {
        return this.g;
    }

    public String java_lang_String_b() {
        return this.h;
    }

    public UUID h() {
        return this.F;
    }

    public FriendMinecraftProfileUpdateEvent(ZeusClient zeusClient, FriendModel friendModel) {
        this(zeusClient, friendModel.getUserId(), friendModel.getMinecraftUuid(), friendModel.getMinecraftUsername());
    }

    public FriendMinecraftProfileUpdateEvent(ZeusClient zeusClient, long l, UUID uUID, String string) {
        super(zeusClient);
        this.g = l;
        this.F = uUID;
        this.h = string;
    }

    public /* synthetic */ long b() {
        return this.long_b();
    }

    public /* synthetic */ String b$src$Ljava_lang_String_$171yzxt() {
        return this.java_lang_String_b();
    }
}
