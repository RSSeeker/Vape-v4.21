package gg.vape.manager.client;

import gg.vape.Vape;
import gg.vape.friend.LocalOnlineFriend;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendActivityState;
import gg.vape.friend.PartyState;
import gg.vape.friend.activity.ActivityItemStack;
import gg.vape.friend.activity.ActivitySnapshotPayload;
import gg.vape.friend.ping.PingManager;
import gg.vape.manager.client.OnlineInventoryTracker;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.ClientActivitySnapshotPacket;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.World;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.Nullable;

public class OnlineActivityManager {
    private final Map<Long, OnlineFriend> pendingSubscriptions;
    private int subscriptionFlushTick;
    private final Map<Long, OnlineFriendActivityState> activityStatesByUserId;
    private final Map<Long, Long> worldChangeCooldowns = new ConcurrentHashMap<Long, Long>();
    private int snapshotTick;
    private final Set<Long> reportedLocationUserIds;
    private final ArrayList<Integer> reservedIntegers;
    private final Map<Long, Long> nearbySinceByUserId;

    public OnlineActivityManager() {
        this.activityStatesByUserId = new ConcurrentHashMap<Long, OnlineFriendActivityState>();
        this.pendingSubscriptions = new ConcurrentHashMap<Long, OnlineFriend>();
        this.nearbySinceByUserId = new ConcurrentHashMap<Long, Long>();
        this.reportedLocationUserIds = new LinkedHashSet<Long>();
        this.reservedIntegers = new ArrayList();
    }

    public boolean isTracking(OnlineFriend onlineFriend) {
        return this.isTracking(onlineFriend.getUser().getId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void startTracking(OnlineFriend onlineFriend) {
        if (onlineFriend.getUser().getId() == Vape.INSTANCE.getOnlineManager().getLocalFriend().getUser().getId()) {
            return;
        }
        this.nearbySinceByUserId.remove(onlineFriend.getUser().getId());
        Set<Long> reportedUsers = this.reportedLocationUserIds;
        synchronized (reportedUsers) {
            this.reportedLocationUserIds.remove(onlineFriend.getUser().getId());
        }
        if (!this.activityStatesByUserId.containsKey(onlineFriend.getUser().getId())) {
            this.activityStatesByUserId.put(onlineFriend.getUser().getId(), new OnlineFriendActivityState(onlineFriend));
        }
    }

    public void resetForWorldChange() {
        this.reset(true);
    }

    public void tickLocalSnapshot(ActivitySnapshotPayload activitySnapshotPayload) {
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (this.snapshotTick++ % 20 == 19 || activitySnapshotPayload.getHealth().getHurtTime() == 10) {
            if (partyState != null) {
                ZeusConnectionManager.T().u().V(new ClientActivitySnapshotPacket(activitySnapshotPayload));
            }
            this.snapshotTick = 0;
        }
        this.decrementHurtTimers();
        this.decrementSwingTimers();
    }

    private void decrementSwingTimers() {
        for (Long userId : this.activityStatesByUserId.keySet()) {
            OnlineFriendActivityState activityState = this.activityStatesByUserId.get(userId);
            if (activityState.getSwingProgressTicks() <= 0) continue;
            activityState.setSwingProgressTicks(activityState.getSwingProgressTicks() - 1);
        }
    }

    private void decrementHurtTimers() {
        for (Long userId : this.activityStatesByUserId.keySet()) {
            OnlineFriendActivityState activityState = this.activityStatesByUserId.get(userId);
            if (activityState.getHurtTime() <= 0) continue;
            activityState.setHurtTime(activityState.getHurtTime() - 1);
        }
    }

    public Collection<OnlineFriendActivityState> getActivityStates() {
        return this.activityStatesByUserId.values();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reset(boolean notifyServer) {
        this.worldChangeCooldowns.clear();
        this.activityStatesByUserId.clear();
        this.pendingSubscriptions.clear();
        this.nearbySinceByUserId.clear();
        Set<Long> reportedUsers = this.reportedLocationUserIds;
        synchronized (reportedUsers) {
            this.reportedLocationUserIds.clear();
        }
        this.subscriptionFlushTick = 0;
        PingManager.INSTANCE.clear();
        if (notifyServer) {
            ZeusConnectionManager.T().u().B();
        }
    }

    public void flushPendingSubscriptionsAndInventory(EntityPlayer player) {
        if (this.subscriptionFlushTick++ % 20 == 19 && !this.pendingSubscriptions.isEmpty()) {
            long[] userIds = new long[this.pendingSubscriptions.size()];
            int index = 0;
            for (Long userId : this.pendingSubscriptions.keySet()) {
                userIds[index++] = userId;
            }
            this.pendingSubscriptions.clear();
            ZeusConnectionManager.T().u().h(userIds);
            this.subscriptionFlushTick = 0;
        }
        OnlineFriendActivityState localActivityState = Vape.INSTANCE.getOnlineManager().getLocalFriend().getActivityState();
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        OnlineInventoryTracker onlineInventoryTracker = Vape.INSTANCE.getOnlineManager().getInventoryTracker();
        if (partyState != null && !this.isEmpty()) {
            boolean initialScan = !onlineInventoryTracker.isInitialized();
            boolean periodicFullScan = onlineInventoryTracker.getUnchangedTickCount() % 20 == 19;
            boolean scanFullInventory = initialScan || periodicFullScan;
            int previousHotbarSlot = onlineInventoryTracker.getSelectedHotbarSlot();
            Map<Integer, ActivityItemStack> inventoryChanges = onlineInventoryTracker.collectChanges(player, scanFullInventory);
            int currentHotbarSlot = onlineInventoryTracker.getSelectedHotbarSlot();
            for (int armorSlot = 0; armorSlot < onlineInventoryTracker.getArmor().length; ++armorSlot) {
                localActivityState.getArmor()[armorSlot] = onlineInventoryTracker.getArmor()[armorSlot];
            }
            for (int inventorySlot = 0; inventorySlot < onlineInventoryTracker.getInventory().length; ++inventorySlot) {
                localActivityState.getInventory()[inventorySlot] = onlineInventoryTracker.getInventory()[inventorySlot];
            }
            if (previousHotbarSlot != currentHotbarSlot) {
                ZeusConnectionManager.T().u().Z(currentHotbarSlot);
                localActivityState.setHeldItemSlot(currentHotbarSlot);
            }
            if (!inventoryChanges.isEmpty() || initialScan) {
                if (initialScan) {
                    onlineInventoryTracker.sendSnapshot();
                } else {
                    onlineInventoryTracker.sendChanges(inventoryChanges);
                }
                onlineInventoryTracker.setUnchangedTickCount(0);
            } else {
                onlineInventoryTracker.setUnchangedTickCount(onlineInventoryTracker.getUnchangedTickCount() + 1);
            }
        }
    }

    @Nullable
    public OnlineFriendActivityState getActivityState(long userId) {
        return this.activityStatesByUserId.get(userId);
    }

    public boolean isTracking(long userId) {
        return this.activityStatesByUserId.containsKey(userId);
    }


    @Nullable
    public OnlineFriendActivityState getActivityStateByMinecraftUsername(String username) {
        for (OnlineFriendActivityState activityState : this.getActivityStates()) {
            if (!username.equals(activityState.getFriend().getMinecraftUsername())) continue;
            return activityState;
        }
        return null;
    }

    @Nullable
    public OnlineFriendActivityState getActivityState(OnlineFriend onlineFriend) {
        return this.getActivityState(onlineFriend.getUser().getId());
    }

    public boolean isEmpty() {
        return this.activityStatesByUserId.isEmpty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void tickNearbyFriends(EntityPlayer localPlayer, World world) {
        if (world.isNull()) {
            return;
        }
        LocalOnlineFriend localOnlineFriend = Vape.INSTANCE.getOnlineManager().getLocalFriend();
        LinkedHashSet<OnlineFriend> relevantFriends = new LinkedHashSet<OnlineFriend>(Vape.INSTANCE.getOnlineFriendManager().getFriends());
        PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
        if (partyState != null) {
            relevantFriends.addAll(partyState.getMembers());
        }
        LinkedHashMap<String, OnlineFriend> friendsByMinecraftUsername = new LinkedHashMap<String, OnlineFriend>();
        for (OnlineFriend onlineFriend : relevantFriends) {
            if (onlineFriend.getUser().getId() == localOnlineFriend.getUser().getId() || onlineFriend.getMinecraftUsername().isEmpty()) continue;
            friendsByMinecraftUsername.put(onlineFriend.getMinecraftUsername(), onlineFriend);
        }
        for (Object playerHandle : world.X()) {
            EntityPlayer nearbyPlayer = new EntityPlayer(playerHandle);
            OnlineFriend nearbyFriend = friendsByMinecraftUsername.get(nearbyPlayer.getName());
            if (nearbyFriend == null || nearbyPlayer.l() < 20) continue;
            long userId = nearbyFriend.getUser().getId();
            Long cooldownTimestamp = this.worldChangeCooldowns.get(userId);
            if (cooldownTimestamp != null) {
                long elapsed = System.currentTimeMillis() - cooldownTimestamp;
                if (elapsed <= 2000L) continue;
                this.worldChangeCooldowns.remove(userId);
            }
            Long nearbySince = this.nearbySinceByUserId.get(userId);
            if (nearbySince != null) {
                long nearbyDuration = System.currentTimeMillis() - nearbySince;
                if (nearbyDuration <= 2000L) continue;
                Set<Long> reportedUsers = this.reportedLocationUserIds;
                synchronized (reportedUsers) {
                    if (this.reportedLocationUserIds.contains(userId)) {
                        continue;
                    }
                    this.reportedLocationUserIds.add(userId);
                    ZeusConnectionManager.T().u().R(userId, (int)nearbyPlayer.z(), (int)nearbyPlayer.N(), (int)nearbyPlayer.h());
                    continue;
                }
            }
            if (this.isTracking(nearbyFriend) || this.pendingSubscriptions.containsKey(userId) || this.nearbySinceByUserId.containsKey(userId)) continue;
            this.nearbySinceByUserId.put(userId, System.currentTimeMillis());
            this.pendingSubscriptions.put(userId, nearbyFriend);
        }
        this.flushPendingSubscriptionsAndInventory(localPlayer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeTrackedUser(long userId) {
        this.worldChangeCooldowns.put(userId, System.currentTimeMillis() + 2000L);
        this.activityStatesByUserId.remove(userId);
        this.pendingSubscriptions.remove(userId);
        this.nearbySinceByUserId.remove(userId);
        Set<Long> reportedUsers = this.reportedLocationUserIds;
        synchronized (reportedUsers) {
            this.reportedLocationUserIds.remove(userId);
        }
    }
}
