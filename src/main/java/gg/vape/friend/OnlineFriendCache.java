package gg.vape.friend;

import gg.vape.friend.OnlineFriend;
import gg.vape.friend.ui.OnlineFriendListEntry;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class OnlineFriendCache {
    private final Map<Long, OnlineFriendListEntry> listEntriesByUserId;
    private final Map<Long, OnlineFriend> friendsByUserId = new LinkedHashMap<Long, OnlineFriend>();

    public OnlineFriendListEntry getOrCreateListEntry(OnlineFriend onlineFriend, Supplier<OnlineFriendListEntry> supplier) {
        long userId = onlineFriend.getUser().getId();
        OnlineFriendListEntry listEntry = this.getListEntry(userId);
        if (listEntry != null) {
            return listEntry;
        }
        listEntry = supplier.get();
        this.listEntriesByUserId.put(userId, listEntry);
        return listEntry;
    }

    public OnlineFriendCache() {
        this.listEntriesByUserId = new LinkedHashMap<Long, OnlineFriendListEntry>();
    }

    @Nullable
    public OnlineFriendListEntry getListEntry(long userId) {
        return this.listEntriesByUserId.get(userId);
    }

    public Collection<OnlineFriend> getFriends() {
        return this.friendsByUserId.values();
    }


    public OnlineFriend getOrCreateFriend(long userId, Supplier<OnlineFriend> supplier) {
        OnlineFriend onlineFriend = this.getFriend(userId);
        if (onlineFriend != null) {
            return onlineFriend;
        }
        onlineFriend = supplier.get();
        this.friendsByUserId.put(onlineFriend.getUser().getId(), onlineFriend);
        return onlineFriend;
    }

    @Nullable
    public OnlineFriend getFriend(long userId) {
        return this.friendsByUserId.get(userId);
    }

    public void clear() {
        this.friendsByUserId.clear();
        this.listEntriesByUserId.clear();
    }
}

