package gg.vape.notification;

import gg.vape.value.BooleanValue;

public class FriendNotificationSettings {
    public final BooleanValue friendOnline;
    public final BooleanValue partyInviteAccepted;
    public final BooleanValue partyInvites;
    public final BooleanValue chats;
    public final BooleanValue friendRequests;
    public final BooleanValue general = BooleanValue.create(null, "Too many pings", true);

    public FriendNotificationSettings() {
        this.friendRequests = BooleanValue.create(null, "Friend requests", true);
        this.chats = BooleanValue.create(null, "Chats", true);
        this.friendOnline = BooleanValue.create(null, "Friend online", true);
        this.partyInvites = BooleanValue.create(null, "Party invites", true);
        this.partyInviteAccepted = BooleanValue.create(null, "Party invite accepted", true);
    }
}
