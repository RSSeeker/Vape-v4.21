package gg.vape.manager.client;

import gg.vape.friend.FriendModel;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.UserModel;
import gg.vape.friend.ui.OnlineFriendUiHelper;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

public class OnlineFriendManager {
    private final Set<OnlineFriend> friends = new HashSet<OnlineFriend>();


    public void removeFriend(OnlineFriend onlineFriend) {
        this.friends.remove(onlineFriend);
        OnlineFriendUiHelper.refreshFriendLists();
    }

    public void clearFriends() {
        this.friends.clear();
        OnlineFriendUiHelper.refreshFriendLists();
    }

    @Nullable
    public OnlineFriend getByDisplayName(String displayName) {
        for (OnlineFriend onlineFriend : this.friends) {
            if (!onlineFriend.getDisplayName().equals(displayName)) continue;
            return onlineFriend;
        }
        return null;
    }

    public Set<OnlineFriend> getFriends() {
        return this.friends;
    }

    public void addFriend(OnlineFriend onlineFriend) {
        this.friends.add(onlineFriend);
        OnlineFriendUiHelper.refreshFriendLists();
    }

    @Nullable
    public OnlineFriend getByFriendModel(FriendModel friendModel) {
        return this.getByUser(friendModel.getUser());
    }

    @Nullable
    public OnlineFriend getByUser(UserModel userModel) {
        for (OnlineFriend onlineFriend : this.friends) {
            if (onlineFriend.getUser().getId() != userModel.getId()) continue;
            return onlineFriend;
        }
        return null;
    }
}

