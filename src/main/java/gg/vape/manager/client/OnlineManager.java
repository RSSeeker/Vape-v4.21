package gg.vape.manager.client;

import gg.vape.Vape;
import gg.vape.friend.FriendRequestManager;
import gg.vape.friend.LocalOnlineFriend;
import gg.vape.friend.OnlineFriendCache;
import gg.vape.friend.PartyManager;
import gg.vape.ui.click.component.GuiComponent;

public class OnlineManager {
    private static GuiComponent[] obfuscationComponents;
    private final OnlineFriendCache friendCache;
    private final OnlineInventoryTracker inventoryTracker;
    private final FriendRequestManager friendRequestManager;
    private final OnlineActivityManager activityManager;
    private final PartyManager partyManager;
    private final LocalOnlineFriend localFriend = new LocalOnlineFriend();

    public OnlineActivityManager getActivityManager() {
        return this.activityManager;
    }

    public LocalOnlineFriend getLocalFriend() {
        return this.localFriend;
    }

    public OnlineInventoryTracker getInventoryTracker() {
        return this.inventoryTracker;
    }

    public OnlineFriendCache getFriendCache() {
        return this.friendCache;
    }

    static {
        if (OnlineManager.getObfuscationComponents() == null) {
            OnlineManager.setObfuscationComponents(new GuiComponent[5]);
        }
    }

    public PartyManager getPartyManager() {
        return this.partyManager;
    }

    public static void setObfuscationComponents(GuiComponent[] components) {
        obfuscationComponents = components;
    }

    public OnlineManager() {
        this.friendCache = new OnlineFriendCache();
        this.friendRequestManager = new FriendRequestManager();
        this.partyManager = new PartyManager();
        this.activityManager = new OnlineActivityManager();
        this.inventoryTracker = new OnlineInventoryTracker();
    }

    public boolean isOffline() {
        return OnlineConnectionManager.INSTANCE.getConnectionState() == OnlineConnectionState.OFFLINE;
    }

    public void clearOnlineState() {
        if (!OnlineConnectionManager.INSTANCE.hasConnectedSuccessfully()) {
            return;
        }
        this.friendCache.clear();
        this.friendRequestManager.clear();
        this.partyManager.clear();
        this.localFriend.setMinecraftUsername("");
        this.localFriend.setMinecraftServer(null);
        Vape.INSTANCE.getOnlineFriendManager().clearFriends();
        this.activityManager.resetForWorldChange();
        this.inventoryTracker.reset();
    }


    public FriendRequestManager getFriendRequestManager() {
        return this.friendRequestManager;
    }

    public static GuiComponent[] getObfuscationComponents() {
        return obfuscationComponents;
    }
}

