package gg.vape.friend;

import com.google.gson.JsonObject;
import gg.vape.friend.Friend;
import gg.vape.friend.FriendEntry;
import gg.vape.friend.OnlineFriend;

public class ExternalFriend
extends FriendEntry {
    private final OnlineFriend onlineFriend;


    @Override
    public JsonObject toJson() {
        return null;
    }

    @Override
    public String getName() {
        if (this.onlineFriend.isVisible()) {
            return this.onlineFriend.getMinecraftUsername();
        }
        return "";
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    public OnlineFriend getOnlineFriend() {
        return this.onlineFriend;
    }

    @Override
    public Friend loadJson(JsonObject jsonObject) {
        return null;
    }

    public ExternalFriend(OnlineFriend onlineFriend) {
        this.onlineFriend = onlineFriend;
    }
}

