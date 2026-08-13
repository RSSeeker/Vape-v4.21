package gg.vape.protocol;

import gg.vape.Vape;
import gg.vape.account.MinecraftSessionWrapper;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendActivityState;
import gg.vape.friend.PartyState;
import gg.vape.friend.UserModel;
import gg.vape.friend.activity.ActivityItemStack;
import gg.vape.friend.activity.ActivityItemStackPayload;
import gg.vape.friend.activity.ActivitySnapshotPayload;
import gg.vape.friend.ping.BlockPingMarker;
import gg.vape.friend.ping.EntityPingMarker;
import gg.vape.friend.ping.OnlineFriendPingMarker;
import gg.vape.friend.ping.PingManager;
import gg.vape.friend.ping.PingMarker;
import gg.vape.friend.ui.OnlineRadarPreviewState;
import gg.vape.manager.client.OnlineAccountState;
import gg.vape.manager.client.OnlineActivityManager;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.manager.client.OnlineDisconnectReason;
import gg.vape.protocol.event.FriendChatMessageEvent;
import gg.vape.protocol.event.FriendMinecraftProfileUpdateEvent;
import gg.vape.protocol.event.FriendModelUpdateEvent;
import gg.vape.protocol.event.FriendPresenceStateEvent;
import gg.vape.protocol.event.FriendRemovedEvent;
import gg.vape.protocol.event.FriendRequestEvent;
import gg.vape.protocol.event.FriendRequestReceivedEvent;
import gg.vape.protocol.event.FriendRequestRemovedEvent;
import gg.vape.protocol.event.FriendRequestSentEvent;
import gg.vape.protocol.event.FriendServerAddressEvent;
import gg.vape.protocol.event.FriendVisibilityUpdateEvent;
import gg.vape.protocol.event.GroupChatMessageEvent;
import gg.vape.protocol.event.GroupCreatedEvent;
import gg.vape.protocol.event.GroupDeletedEvent;
import gg.vape.protocol.event.GroupInviteAcceptedEvent;
import gg.vape.protocol.event.GroupInviteSentEvent;
import gg.vape.protocol.event.GroupLeftEvent;
import gg.vape.protocol.event.GroupOptionUpdatedEvent;
import gg.vape.protocol.event.InitialOnlineFriendStateEvent;
import gg.vape.protocol.event.OnlineEventDispatcher;
import gg.vape.protocol.event.PartyInviteReceivedEvent;
import gg.vape.protocol.event.PartyInviteRemovedEvent;
import gg.vape.protocol.event.PartyLeaderChangedEvent;
import gg.vape.protocol.event.PartyMemberUpdateEvent;
import gg.vape.protocol.event.UserDisplayNameChangedEvent;
import gg.vape.protocol.packet.ActivitySnapshotsPacket;
import gg.vape.protocol.packet.AuthenticationPacket;
import gg.vape.protocol.packet.AuthenticationResponsePacket;
import gg.vape.protocol.packet.ChatToFriendPacket;
import gg.vape.protocol.packet.ChatToFriendResponsePacket;
import gg.vape.protocol.packet.ChatToFriendStatus;
import gg.vape.protocol.packet.ClientBlockLocationPacket;
import gg.vape.protocol.packet.ClientCpsPacket;
import gg.vape.protocol.packet.ClientFriendActivityUsersPacket;
import gg.vape.protocol.packet.ClientGroupLeaderKickPacket;
import gg.vape.protocol.packet.ClientGroupLeaderKickResponsePacket;
import gg.vape.protocol.packet.ClientGroupLeaderPromotePacket;
import gg.vape.protocol.packet.ClientGroupLeaderPromoteResponsePacket;
import gg.vape.protocol.packet.ClientHeldItemSlotPacket;
import gg.vape.protocol.packet.ClientInventorySnapshotPacket;
import gg.vape.protocol.packet.ClientInventoryUpdatePacket;
import gg.vape.protocol.packet.ClientMinecraftProfilePacket;
import gg.vape.protocol.packet.ClientProfileIdPacket;
import gg.vape.protocol.packet.ClientServerAddressPacket;
import gg.vape.protocol.packet.FriendActivityUsersAction;
import gg.vape.protocol.packet.FriendActivityUsersPacket;
import gg.vape.protocol.packet.FriendCpsPacket;
import gg.vape.protocol.packet.FriendDeletePacket;
import gg.vape.protocol.packet.FriendDeleteResponsePacket;
import gg.vape.protocol.packet.FriendMinecraftProfileUpdatePacket;
import gg.vape.protocol.packet.FriendRequestPacket;
import gg.vape.protocol.packet.FriendRequestResponsePacket;
import gg.vape.protocol.packet.FriendRequestResponseStatus;
import gg.vape.protocol.packet.FriendRequestUpdatePacket;
import gg.vape.protocol.packet.FriendRequestUpdateResponsePacket;
import gg.vape.protocol.packet.FriendRequestUpdateStatus;
import gg.vape.protocol.packet.FriendServerAddressPacket;
import gg.vape.protocol.packet.FriendVisibilityUpdatePacket;
import gg.vape.protocol.packet.FriendsListPacket;
import gg.vape.protocol.packet.FriendsListResponsePacket;
import gg.vape.protocol.packet.GroupChatPacket;
import gg.vape.protocol.packet.GroupChatResponsePacket;
import gg.vape.protocol.packet.GroupCreatePacket;
import gg.vape.protocol.packet.GroupCreateResponsePacket;
import gg.vape.protocol.packet.GroupDeletePacket;
import gg.vape.protocol.packet.GroupDeleteResponsePacket;
import gg.vape.protocol.packet.GroupDeleteStatus;
import gg.vape.protocol.packet.GroupInvitePacket;
import gg.vape.protocol.packet.GroupInviteResponsePacket;
import gg.vape.protocol.packet.GroupInviteStatePacket;
import gg.vape.protocol.packet.GroupInviteStateResponsePacket;
import gg.vape.protocol.packet.GroupInviteStateStatus;
import gg.vape.protocol.packet.GroupInviteStatus;
import gg.vape.protocol.packet.GroupInviteUpdateStatus;
import gg.vape.protocol.packet.GroupLeavePacket;
import gg.vape.protocol.packet.GroupLeaveResponsePacket;
import gg.vape.protocol.packet.GroupLeaveStatus;
import gg.vape.protocol.packet.GroupOption;
import gg.vape.protocol.packet.GroupOptionUpdatePacket;
import gg.vape.protocol.packet.GroupUninvitePacket;
import gg.vape.protocol.packet.GroupUninviteResponsePacket;
import gg.vape.protocol.packet.GroupUninviteStatus;
import gg.vape.protocol.packet.HandshakePacket;
import gg.vape.protocol.packet.HandshakeResponsePacket;
import gg.vape.protocol.packet.HeartbeatPacket;
import gg.vape.protocol.packet.HeartbeatResponsePacket;
import gg.vape.protocol.packet.LocationCheckPacket;
import gg.vape.protocol.packet.LocationCheckResponsePacket;
import gg.vape.protocol.packet.PartyMemberActionType;
import gg.vape.protocol.packet.PingPacket;
import gg.vape.protocol.packet.PingResponsePacket;
import gg.vape.protocol.packet.PingTargetData;
import gg.vape.protocol.packet.PingTargetKind;
import gg.vape.protocol.packet.PresenceStateUpdatePacket;
import gg.vape.protocol.packet.ServerDisconnectPacket;
import gg.vape.protocol.packet.ServerFriendChatMessagePacket;
import gg.vape.protocol.packet.ServerFriendModelPacket;
import gg.vape.protocol.packet.ServerFriendPresenceStatePacket;
import gg.vape.protocol.packet.ServerFriendRemovedPacket;
import gg.vape.protocol.packet.ServerFriendRequestPacket;
import gg.vape.protocol.packet.ServerFriendRequestRemovedPacket;
import gg.vape.protocol.packet.ServerGroupChatMessagePacket;
import gg.vape.protocol.packet.ServerGroupDeletedPacket;
import gg.vape.protocol.packet.ServerGroupInvitePacket;
import gg.vape.protocol.packet.ServerGroupInviteRemovedPacket;
import gg.vape.protocol.packet.ServerGroupInviteUpdatePacket;
import gg.vape.protocol.packet.ServerGroupLeaderChangedPacket;
import gg.vape.protocol.packet.ServerGroupOptionUpdatePacket;
import gg.vape.protocol.packet.ServerHeldItemSlotPacket;
import gg.vape.protocol.packet.ServerInventorySnapshotPacket;
import gg.vape.protocol.packet.ServerInventoryUpdatePacket;
import gg.vape.protocol.packet.ServerPartyMemberUpdatePacket;
import gg.vape.protocol.packet.ServerPingPacket;
import gg.vape.protocol.packet.ServerUserDisplayNamePacket;
import gg.vape.protocol.packet.ShowUsernamePacket;
import gg.vape.protocol.packet.UserDisplayNamePacket;
import gg.vape.protocol.packet.UserDisplayNameResponsePacket;
import gg.vape.protocol.packet.UserDisplayNameStatus;
import gg.vape.protocol.packet.ZeusClientReadyPacket;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import gg.vape.utils.NoopLogger;
import gg.vape.utils.RotationUtil;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

@ChannelHandler.Sharable
public class ZeusClient
extends SimpleChannelInboundHandler<ZeusSerializablePacket> {
    private final Map<UUID, OnlineRadarPreviewState<@Nullable Consumer<ZeusTrackedPacket<?>>, @Nullable Runnable>> d = new HashMap();
    private UserModel X;
    private Channel z;
    static final boolean H = !ZeusClient.class.desiredAssertionStatus();

    private void D(ServerGroupDeletedPacket serverGroupDeletedPacket) {
        OnlineEventDispatcher.O.G(new GroupDeletedEvent(this));
    }

    public void c(UserModel userModel, boolean bl, Consumer<GroupInviteStateResponsePacket> consumer, Runnable runnable) {
        this.w(new GroupInviteStatePacket(userModel, bl), arg_0 -> this.lambda$sendGroupInviteStatePacket$18(consumer, arg_0), runnable);
    }

    private static void lambda$sendUserDisplayNamePacket$19(Consumer consumer, UserDisplayNameResponsePacket userDisplayNameResponsePacket) {
        if (userDisplayNameResponsePacket.getStatus() == UserDisplayNameStatus.SUCCESSFUL) {
            Vape.INSTANCE.getOnlineManager().getLocalFriend().setDisplayName(userDisplayNameResponsePacket.getDisplayName());
        }
        consumer.accept(userDisplayNameResponsePacket);
    }

    public void o(PingTargetData pingTargetData, Consumer<PingResponsePacket> consumer) {
        this.w(new PingPacket(pingTargetData), consumer, null);
    }

    public void R(long l, int n, int n2, int n3) {
        this.V(new ClientBlockLocationPacket(l, n, n2, n3));
    }

    private void lambda$sendGroupUninvitePacket$17(UserModel userModel, Consumer consumer, GroupUninviteResponsePacket groupUninviteResponsePacket) {
        if (groupUninviteResponsePacket.getStatus() == GroupUninviteStatus.SUCCESS) {
            OnlineEventDispatcher.O.G(new PartyInviteRemovedEvent(this, Vape.INSTANCE.getOnlineManager().getFriendCache().getOrCreateFriend(userModel.getId(), () -> ZeusClient.lambda$null$16(userModel))));
        }
        consumer.accept(groupUninviteResponsePacket);
    }

    public void P(Map<Integer, ActivityItemStackPayload> map) {
        this.V(new ClientInventoryUpdatePacket(map));
    }

    public void H(int n) {
        this.V(new ClientHeldItemSlotPacket(n));
    }

    private void o(ServerPingPacket serverPingPacket) {
        if (serverPingPacket.s() == Vape.INSTANCE.getOnlineManager().getLocalFriend().getUser().getId()) {
            return;
        }
        PingTargetData pingTargetData = serverPingPacket.m();
        OnlineFriend onlineFriend = Vape.INSTANCE.getOnlineManager().getFriendCache().getFriend(serverPingPacket.s());
        String string = onlineFriend != null ? onlineFriend.getDisplayName() : String.valueOf(serverPingPacket.s());
        PingMarker pingMarker = PingManager.INSTANCE.getMarker(onlineFriend);
        PingMarker pingMarker2 = null;
        if (pingTargetData.K() == PingTargetKind.POSITION) {
            if (!(H || pingTargetData.W() != null && pingTargetData.w() != null && pingTargetData.L() != null)) {
                throw new AssertionError();
            }
            if (pingMarker instanceof OnlineFriendPingMarker && pingMarker.isNear(pingTargetData.W(), pingTargetData.w(), pingTargetData.L())) {
                pingMarker.retrigger();
                return;
            }
            pingMarker2 = new OnlineFriendPingMarker(onlineFriend, new double[]{pingTargetData.W(), pingTargetData.w(), pingTargetData.L()});
        } else if (pingTargetData.K() == PingTargetKind.BLOCK) {
            double[] dArray;
            if (!(H || pingTargetData.y() != null && pingTargetData.u() != null && pingTargetData.r() != null)) {
                throw new AssertionError();
            }
            if (pingMarker instanceof BlockPingMarker && (dArray = pingMarker.getWorldPosition())[0] == (double)pingTargetData.y().intValue() && dArray[1] == (double)pingTargetData.u().intValue() && dArray[2] == (double)pingTargetData.r().intValue()) {
                pingMarker.retrigger();
                return;
            }
            pingMarker2 = new BlockPingMarker(onlineFriend, new double[]{pingTargetData.y().intValue(), pingTargetData.u().intValue(), pingTargetData.r().intValue()});
        } else if (pingTargetData.K() == PingTargetKind.ENTITY) {
            if (!(H || pingTargetData.e() != null && pingTargetData.W() != null && pingTargetData.w() != null && pingTargetData.L() != null)) {
                throw new AssertionError();
            }
            Long l = pingTargetData.t();
            if (l != null && l.longValue() == Vape.INSTANCE.getOnlineManager().getLocalFriend().getUser().getId()) {
                return;
            }
            if (pingMarker instanceof EntityPingMarker && pingMarker.isNear(pingTargetData.W(), pingTargetData.w(), pingTargetData.L())) {
                pingMarker.retrigger();
                return;
            }
            pingMarker2 = new EntityPingMarker(onlineFriend, pingTargetData.t(), pingTargetData.e(), new double[]{pingTargetData.W(), pingTargetData.w(), pingTargetData.L()});
        }
        if (pingMarker2 != null) {
            PingManager.INSTANCE.addMarker(pingMarker2);
        }
    }

    private void W(ServerInventoryUpdatePacket serverInventoryUpdatePacket) {
        OnlineActivityManager onlineActivityManager = Vape.INSTANCE.getOnlineManager().getActivityManager();
        OnlineFriendActivityState onlineFriendActivityState = onlineActivityManager.getActivityState(serverInventoryUpdatePacket.getUserId());
        if (onlineFriendActivityState == null) {
            return;
        }
        for (Map.Entry<Integer, ActivityItemStackPayload> entry : serverInventoryUpdatePacket.getInventoryItems().entrySet()) {
            if (entry.getKey() >= 36) {
                onlineFriendActivityState.getArmor()[entry.getKey().intValue() - 36] = ActivityItemStack.fromPayload(entry.getValue());
                continue;
            }
            onlineFriendActivityState.getInventory()[entry.getKey().intValue()] = ActivityItemStack.fromPayload(entry.getValue());
        }
        onlineFriendActivityState.setDataAvailable(true);
    }

    public void p(long l) {
        NoopLogger.info("Started using public profile " + l);
        this.V(new ClientProfileIdPacket(l));
    }

    private void y(ServerInventorySnapshotPacket serverInventorySnapshotPacket) {
        OnlineActivityManager onlineActivityManager = Vape.INSTANCE.getOnlineManager().getActivityManager();
        OnlineFriendActivityState onlineFriendActivityState = onlineActivityManager.getActivityState(serverInventorySnapshotPacket.getUserId());
        if (onlineFriendActivityState == null) {
            return;
        }
        Arrays.fill(onlineFriendActivityState.getArmor(), null);
        Arrays.fill(onlineFriendActivityState.getInventory(), null);
        onlineFriendActivityState.setHeldItemSlot(serverInventorySnapshotPacket.getHeldItemSlot());
        for (Map.Entry<Integer, ActivityItemStackPayload> entry : serverInventorySnapshotPacket.getInventoryItems().entrySet()) {
            if (entry.getKey() >= 36) {
                onlineFriendActivityState.getArmor()[entry.getKey().intValue() - 36] = ActivityItemStack.fromPayload(entry.getValue());
                continue;
            }
            onlineFriendActivityState.getInventory()[entry.getKey().intValue()] = ActivityItemStack.fromPayload(entry.getValue());
        }
        onlineFriendActivityState.setDataAvailable(true);
    }

    private void L(ServerFriendRequestPacket serverFriendRequestPacket) {
        OnlineEventDispatcher.O.G(new FriendRequestReceivedEvent(this, serverFriendRequestPacket.getRequest()));
    }

    public void v(PresenceState presenceState) {
        this.V(new PresenceStateUpdatePacket(presenceState));
    }

    private static void lambda$sendFriendRequestUpdatePacket$9(boolean bl, Consumer consumer, FriendRequestUpdateResponsePacket friendRequestUpdateResponsePacket) {
        if (friendRequestUpdateResponsePacket.getStatus() != FriendRequestUpdateStatus.UNKNOWN && bl) {
            OnlineEventDispatcher.O.G(new FriendModelUpdateEvent(ZeusConnectionManager.T().u(), friendRequestUpdateResponsePacket.getUpdatedFriend()));
        }
        consumer.accept(friendRequestUpdateResponsePacket);
    }

    private void A(ServerUserDisplayNamePacket serverUserDisplayNamePacket) {
        OnlineEventDispatcher.O.G(new UserDisplayNameChangedEvent(this, serverUserDisplayNamePacket.getUserId(), serverUserDisplayNamePacket.getDisplayName()));
    }

    public void h(long[] lArray) {
        this.V(ClientFriendActivityUsersPacket.X(lArray));
    }

    public void u(Consumer<GroupLeaveResponsePacket> consumer, Runnable runnable) {
        this.w(new GroupLeavePacket(), arg_0 -> this.lambda$sendGroupLeavePacket$12(consumer, arg_0), runnable);
    }

    private void s(FriendMinecraftProfileUpdatePacket friendMinecraftProfileUpdatePacket) {
        OnlineEventDispatcher.O.G(new FriendMinecraftProfileUpdateEvent(this, friendMinecraftProfileUpdatePacket.H(), friendMinecraftProfileUpdatePacket.l(), friendMinecraftProfileUpdatePacket.G()));
    }

    @SuppressWarnings("unchecked")
    public <R extends ZeusTrackedPacket<?>> void w(ZeusTrackedPacket<R> zeusTrackedPacket, @Nullable Consumer<R> consumer, @Nullable Runnable runnable) {
        Consumer<ZeusTrackedPacket<?>> trackedConsumer = (Consumer<ZeusTrackedPacket<?>>)(Consumer<?>)consumer;
        this.d.put(zeusTrackedPacket.o$src$Ljava_util_UUID_$1pm4r8s(), OnlineRadarPreviewState.create(trackedConsumer, runnable));
        this.V(zeusTrackedPacket);
    }

    public void J(UserModel userModel, Consumer<GroupInviteResponsePacket> consumer, Runnable runnable) {
        this.w(new GroupInvitePacket(userModel), arg_0 -> this.lambda$sendGroupInvitePacket$15(userModel, consumer, arg_0), runnable);
    }

    public void i(UserModel userModel, Consumer<FriendDeleteResponsePacket> consumer, Runnable runnable) {
        this.w(new FriendDeletePacket(userModel), arg_0 -> ZeusClient.lambda$sendFriendDeletePacket$7(consumer, arg_0), runnable);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ZeusSerializablePacket zeusSerializablePacket) {
        if (zeusSerializablePacket instanceof ZeusTrackedPacket) {
            ZeusTrackedPacket zeusTrackedPacket = (ZeusTrackedPacket)zeusSerializablePacket;
            OnlineRadarPreviewState<Consumer<ZeusTrackedPacket<?>>, Runnable> onlineRadarPreviewState = this.d.remove(zeusTrackedPacket.o$src$Ljava_util_UUID_$1pm4r8s());
            if (onlineRadarPreviewState == null) {
                throw new RuntimeException("Failed to find queued future for packet " + zeusSerializablePacket + " (" + zeusTrackedPacket.o$src$Ljava_util_UUID_$1pm4r8s() + ")");
            }
            if (onlineRadarPreviewState.getKey() != null) {
                onlineRadarPreviewState.getKey().accept(zeusTrackedPacket);
            }
            if (onlineRadarPreviewState.getValue() != null) {
                onlineRadarPreviewState.getValue().run();
            }
            return;
        }
        if (zeusSerializablePacket instanceof ServerFriendModelPacket) {
            this.v((ServerFriendModelPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerFriendRemovedPacket) {
            this.f((ServerFriendRemovedPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerFriendRequestPacket) {
            this.L((ServerFriendRequestPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerFriendRequestRemovedPacket) {
            this.v((ServerFriendRequestRemovedPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerFriendPresenceStatePacket) {
            this.G((ServerFriendPresenceStatePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerGroupChatMessagePacket) {
            this.F((ServerGroupChatMessagePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerGroupInviteRemovedPacket) {
            this.X((ServerGroupInviteRemovedPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerGroupInvitePacket) {
            this.r((ServerGroupInvitePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerGroupDeletedPacket) {
            this.D((ServerGroupDeletedPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerFriendChatMessagePacket) {
            this.I((ServerFriendChatMessagePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerPartyMemberUpdatePacket) {
            this.r((ServerPartyMemberUpdatePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerGroupInviteUpdatePacket) {
            this.h((ServerGroupInviteUpdatePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof FriendMinecraftProfileUpdatePacket) {
            this.s((FriendMinecraftProfileUpdatePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof FriendVisibilityUpdatePacket) {
            this.r((FriendVisibilityUpdatePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof FriendServerAddressPacket) {
            this.N((FriendServerAddressPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerUserDisplayNamePacket) {
            this.A((ServerUserDisplayNamePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerGroupOptionUpdatePacket) {
            this.S((ServerGroupOptionUpdatePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerGroupLeaderChangedPacket) {
            this.h((ServerGroupLeaderChangedPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerPingPacket) {
            this.o((ServerPingPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof FriendActivityUsersPacket) {
            this.L((FriendActivityUsersPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ActivitySnapshotsPacket) {
            this.u((ActivitySnapshotsPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof LocationCheckPacket) {
            this.z((LocationCheckPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerHeldItemSlotPacket) {
            this.L((ServerHeldItemSlotPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerInventorySnapshotPacket) {
            this.y((ServerInventorySnapshotPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerInventoryUpdatePacket) {
            this.W((ServerInventoryUpdatePacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof FriendCpsPacket) {
            this.v((FriendCpsPacket)zeusSerializablePacket);
        } else if (zeusSerializablePacket instanceof ServerDisconnectPacket) {
            this.G((ServerDisconnectPacket)zeusSerializablePacket);
        }
    }

    public void Y(GroupOption groupOption, Object object) {
        this.V(new GroupOptionUpdatePacket(groupOption, object));
    }

    public void Z(int n) {
        this.V(new ClientCpsPacket(n));
    }

    public void y(Consumer<FriendsListResponsePacket> consumer) {
        this.z(new FriendsListPacket(), arg_0 -> ZeusClient.lambda$sendFriendsListPacket$6(consumer, arg_0));
    }

    private void I(ServerFriendChatMessagePacket serverFriendChatMessagePacket) {
        OnlineEventDispatcher.O.G(new FriendChatMessageEvent(this, serverFriendChatMessagePacket.getSender(), serverFriendChatMessagePacket.getMessage()));
    }

    public void V(UserModel userModel, Consumer<GroupUninviteResponsePacket> consumer, Runnable runnable) {
        this.w(new GroupUninvitePacket(userModel), arg_0 -> this.lambda$sendGroupUninvitePacket$17(userModel, consumer, arg_0), runnable);
    }

    private void r(FriendVisibilityUpdatePacket friendVisibilityUpdatePacket) {
        OnlineEventDispatcher.O.G(new FriendVisibilityUpdateEvent(this, friendVisibilityUpdatePacket.getUserId(), friendVisibilityUpdatePacket.isVisible()));
    }

    private static OnlineFriend lambda$null$14(UserModel userModel) {
        return new OnlineFriend(userModel);
    }

    public void a(@Nullable String string) {
        this.V(new ClientServerAddressPacket(string));
    }

    private static void lambda$sendHeartbeatPacket$4(HeartbeatResponsePacket heartbeatResponsePacket) {
    }

    private void v(ServerFriendModelPacket serverFriendModelPacket) {
        OnlineEventDispatcher.O.G(new FriendModelUpdateEvent(ZeusConnectionManager.T().u(), serverFriendModelPacket.getFriend()));
    }

    public void N(Consumer<HandshakeResponsePacket> consumer) {
        this.z(new HandshakePacket(10), arg_0 -> ZeusClient.lambda$sendHandshake$2(consumer, arg_0));
    }

    private void v(FriendCpsPacket friendCpsPacket) {
        OnlineActivityManager onlineActivityManager = Vape.INSTANCE.getOnlineManager().getActivityManager();
        OnlineFriendActivityState onlineFriendActivityState = onlineActivityManager.getActivityState(friendCpsPacket.getUserId());
        if (onlineFriendActivityState == null) {
            return;
        }
        onlineFriendActivityState.setHeldItemSlot(friendCpsPacket.getClicksPerSecond());
        onlineFriendActivityState.setDataAvailable(true);
    }

    public ZeusProtocolState k() {
        return this.z.attr(ZeusProtocolConstants.Q).get();
    }

    private void lambda$sendGroupCreatePacket$11(Consumer consumer, GroupCreateResponsePacket groupCreateResponsePacket) {
        consumer.accept(groupCreateResponsePacket);
        OnlineEventDispatcher.O.G(new GroupCreatedEvent(this, new PartyState(Vape.INSTANCE.getOnlineManager().getLocalFriend())));
    }

    private static void lambda$sendFriendDeletePacket$7(Consumer consumer, FriendDeleteResponsePacket friendDeleteResponsePacket) {
        consumer.accept(friendDeleteResponsePacket);
    }

    private void lambda$switchToAuthenticated$3(FriendsListResponsePacket friendsListResponsePacket) {
        this.Y();
        OnlineEventDispatcher.O.G(new InitialOnlineFriendStateEvent(this, friendsListResponsePacket.getFriends(), friendsListResponsePacket.getIncomingRequests(), friendsListResponsePacket.getOutgoingRequests()));
    }

    public void V(ZeusSerializablePacket zeusSerializablePacket) {
        if (this.z == null || !this.z.isOpen()) {
            if (zeusSerializablePacket instanceof ZeusTrackedPacket) {
                // empty if block
            }
            return;
        }
        try {
            if (zeusSerializablePacket instanceof ZeusTrackedPacket) {
                if (!(zeusSerializablePacket instanceof HeartbeatPacket)) {
                    // empty if block
                }
                if (!this.d.containsKey(((ZeusTrackedPacket)zeusSerializablePacket).o$src$Ljava_util_UUID_$1pm4r8s())) {
                    this.d.put(((ZeusTrackedPacket)zeusSerializablePacket).o$src$Ljava_util_UUID_$1pm4r8s(), OnlineRadarPreviewState.create(ZeusClient::lambda$sendPacket$0, ZeusClient::lambda$sendPacket$1));
                }
            } else if (!(zeusSerializablePacket instanceof HeartbeatPacket)) {
                // empty if block
            }
            this.z.writeAndFlush((Object)zeusSerializablePacket);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void U(String string, Consumer<UserDisplayNameResponsePacket> consumer, Runnable runnable) {
        this.w(new UserDisplayNamePacket(string), arg_0 -> ZeusClient.lambda$sendUserDisplayNamePacket$19(consumer, arg_0), runnable);
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        this.z.close();
        this.z = null;
    }

    private void z(LocationCheckPacket locationCheckPacket) {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        if (entityPlayerSP.isNull()) {
            this.V(new LocationCheckResponsePacket(false));
            return;
        }
        double d = RotationUtil.y(entityPlayerSP.z(), entityPlayerSP.N(), entityPlayerSP.h(), locationCheckPacket.f(), locationCheckPacket.L(), locationCheckPacket.r());
        if (d <= 10.0) {
            this.V(new LocationCheckResponsePacket(true));
        } else {
            this.V(new LocationCheckResponsePacket(false));
        }
    }

    private static OnlineFriend lambda$handleServerGroupInviteUpdatePacket$20(ServerGroupInviteUpdatePacket serverGroupInviteUpdatePacket) {
        return new OnlineFriend(serverGroupInviteUpdatePacket.f());
    }

    private static void lambda$sendPacket$0(ZeusTrackedPacket zeusTrackedPacket) {
    }

    private void lambda$sendAuthenticationPacket$5(Consumer consumer, AuthenticationResponsePacket authenticationResponsePacket) {
        if (!H && authenticationResponsePacket.s() == null) {
            throw new AssertionError();
        }
        Vape.INSTANCE.getOnlineManager().getLocalFriend().setUser(authenticationResponsePacket.s());
        this.f(authenticationResponsePacket.s());
        consumer.accept(authenticationResponsePacket);
    }

    public void Y() {
        boolean bl = OnlineConnectionManager.INSTANCE.getSettings().getShareUsername().getEffectiveValue();
        this.V(new ShowUsernamePacket(bl));
    }

    private void u(ActivitySnapshotsPacket activitySnapshotsPacket) {
        OnlineActivityManager onlineActivityManager = Vape.INSTANCE.getOnlineManager().getActivityManager();
        for (int i = 0; i < activitySnapshotsPacket.getUserIds().length; ++i) {
            long userId = activitySnapshotsPacket.getUserIds()[i];
            ActivitySnapshotPayload activitySnapshotPayload = activitySnapshotsPacket.getSnapshots()[i];
            OnlineFriendActivityState onlineFriendActivityState = onlineActivityManager.getActivityState(userId);
            if (onlineFriendActivityState == null) continue;
            onlineFriendActivityState.applySnapshot(activitySnapshotPayload);
        }
    }

    private void lambda$sendGroupInvitePacket$15(UserModel userModel, Consumer consumer, GroupInviteResponsePacket groupInviteResponsePacket) {
        if (groupInviteResponsePacket.getStatus() == GroupInviteStatus.SUCCESS) {
            OnlineEventDispatcher.O.G(new GroupInviteSentEvent(this, Vape.INSTANCE.getOnlineManager().getFriendCache().getOrCreateFriend(userModel.getId(), () -> ZeusClient.lambda$null$14(userModel))));
        }
        consumer.accept(groupInviteResponsePacket);
    }

    private static void lambda$sendPacket$1() {
    }

    public void C(UUID uUID, String string) {
        this.V(new ClientMinecraftProfilePacket(uUID, string));
    }

    public void l(Consumer<GroupDeleteResponsePacket> consumer, Runnable runnable) {
        this.w(new GroupDeletePacket(), arg_0 -> this.lambda$sendGroupDeletePacket$13(consumer, arg_0), runnable);
    }

    public void M() {
        NoopLogger.info("Stopped using public profile");
        this.V(new ZeusClientReadyPacket());
    }

    public <R extends ZeusTrackedPacket<?>> void z(ZeusTrackedPacket<R> zeusTrackedPacket, Consumer<R> consumer) {
        this.w(zeusTrackedPacket, consumer, null);
    }

    private static void lambda$sendClientGroupLeaderPromotePacket$23(Consumer consumer, ClientGroupLeaderPromoteResponsePacket clientGroupLeaderPromoteResponsePacket) {
        consumer.accept(clientGroupLeaderPromoteResponsePacket);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private static void lambda$sendClientGroupLeaderKickPacket$22(Consumer consumer, ClientGroupLeaderKickResponsePacket clientGroupLeaderKickResponsePacket) {
        consumer.accept(clientGroupLeaderKickResponsePacket);
    }

    private static void lambda$sendFriendsListPacket$6(Consumer consumer, FriendsListResponsePacket friendsListResponsePacket) {
        consumer.accept(friendsListResponsePacket);
    }

    public void Y(long l, boolean bl, Consumer<FriendRequestUpdateResponsePacket> consumer) {
        this.z(new FriendRequestUpdatePacket(l, bl), arg_0 -> ZeusClient.lambda$sendFriendRequestUpdatePacket$9(bl, consumer, arg_0));
    }

    public Channel D() {
        return this.z;
    }

    private void lambda$sendGroupInviteStatePacket$18(Consumer consumer, GroupInviteStateResponsePacket groupInviteStateResponsePacket) {
        if (groupInviteStateResponsePacket.getStatus() == GroupInviteStateStatus.SUCCESSFULLY_ACCEPTED) {
            if (!H && groupInviteStateResponsePacket.getPartyState() == null) {
                throw new AssertionError();
            }
            OnlineEventDispatcher.O.G(new GroupInviteAcceptedEvent(this, new PartyState(groupInviteStateResponsePacket.getPartyState())));
        }
        consumer.accept(groupInviteStateResponsePacket);
    }

    private void F(ServerGroupChatMessagePacket serverGroupChatMessagePacket) {
        new GroupChatMessageEvent(this, new UserModel(serverGroupChatMessagePacket.getSenderUserId(), null), new UserModel(serverGroupChatMessagePacket.getSenderUserId(), null), serverGroupChatMessagePacket.getMessage()).u();
    }

    private void N(FriendServerAddressPacket friendServerAddressPacket) {
        OnlineEventDispatcher.O.G(new FriendServerAddressEvent(this, friendServerAddressPacket.getUserId(), friendServerAddressPacket.getServerAddress()));
    }

    private void f(UserModel userModel) {
        this.X = userModel;
        this.z.attr(ZeusProtocolConstants.Q).set(ZeusProtocolState.AUTHENTICATED);
        this.y(this::lambda$switchToAuthenticated$3);
    }

    public void p(UserModel userModel, String string, Consumer<ChatToFriendResponsePacket> consumer) {
        this.z(new ChatToFriendPacket(userModel, string), arg_0 -> ZeusClient.lambda$sendChatToFriendPacket$10(consumer, arg_0));
    }

    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        this.z = channelHandlerContext.channel();
        this.z.attr(ZeusProtocolConstants.Q).set(ZeusProtocolState.UNAUTHENTICATED);
    }

    private static OnlineFriend lambda$handleServerGroupInviteUpdatePacket$21(ServerGroupInviteUpdatePacket serverGroupInviteUpdatePacket) {
        return new OnlineFriend(serverGroupInviteUpdatePacket.f());
    }

    public void c(UserModel userModel, Consumer<ClientGroupLeaderKickResponsePacket> consumer, Runnable runnable) {
        this.w(new ClientGroupLeaderKickPacket(userModel), arg_0 -> ZeusClient.lambda$sendClientGroupLeaderKickPacket$22(consumer, arg_0), runnable);
    }

    public void w(Consumer<GroupCreateResponsePacket> consumer, Runnable runnable) {
        this.w(new GroupCreatePacket(), arg_0 -> this.lambda$sendGroupCreatePacket$11(consumer, arg_0), runnable);
    }

    public void L(String string, Consumer<GroupChatResponsePacket> consumer) {
        this.z(new GroupChatPacket(string), consumer::accept);
    }

    private void lambda$sendGroupDeletePacket$13(Consumer consumer, GroupDeleteResponsePacket groupDeleteResponsePacket) {
        if (groupDeleteResponsePacket.getStatus() == GroupDeleteStatus.SUCCESS) {
            OnlineEventDispatcher.O.G(new GroupDeletedEvent(this));
        }
        consumer.accept(groupDeleteResponsePacket);
    }

    private void lambda$sendGroupLeavePacket$12(Consumer consumer, GroupLeaveResponsePacket groupLeaveResponsePacket) {
        consumer.accept(groupLeaveResponsePacket);
        if (groupLeaveResponsePacket.getStatus() == GroupLeaveStatus.SUCCESS) {
            OnlineEventDispatcher.O.G(new GroupLeftEvent(this));
        }
    }

    private void r(ServerGroupInvitePacket serverGroupInvitePacket) {
        OnlineEventDispatcher.O.G(new FriendRequestEvent(this, serverGroupInvitePacket.Z()));
    }

    private void L(ServerHeldItemSlotPacket serverHeldItemSlotPacket) {
        OnlineFriend onlineFriend = Vape.INSTANCE.getOnlineManager().getFriendCache().getFriend(serverHeldItemSlotPacket.getUserId());
        if (onlineFriend == null) {
            return;
        }
        OnlineActivityManager onlineActivityManager = Vape.INSTANCE.getOnlineManager().getActivityManager();
        OnlineFriendActivityState onlineFriendActivityState = onlineActivityManager.getActivityState(serverHeldItemSlotPacket.getUserId());
        if (onlineFriendActivityState == null) {
            return;
        }
        onlineFriendActivityState.setClicksPerSecond(serverHeldItemSlotPacket.getHeldItemSlot());
    }

    public UserModel i() {
        return this.X;
    }

    public void s(UserModel userModel, Consumer<ClientGroupLeaderPromoteResponsePacket> consumer, Runnable runnable) {
        this.w(new ClientGroupLeaderPromotePacket(userModel), arg_0 -> ZeusClient.lambda$sendClientGroupLeaderPromotePacket$23(consumer, arg_0), runnable);
    }

    private void X(ServerGroupInviteRemovedPacket serverGroupInviteRemovedPacket) {
        OnlineEventDispatcher.O.G(new PartyInviteReceivedEvent(this, serverGroupInviteRemovedPacket.G()));
    }

    public void Z(String string, Consumer<FriendRequestResponsePacket> consumer) {
        this.z(new FriendRequestPacket(string), arg_0 -> this.lambda$sendFriendRequestPacket$8(consumer, arg_0));
    }

    private void h(ServerGroupInviteUpdatePacket serverGroupInviteUpdatePacket) {
        if (serverGroupInviteUpdatePacket.e() == GroupInviteUpdateStatus.SENT) {
            if (!H && serverGroupInviteUpdatePacket.k() == null) {
                throw new AssertionError();
            }
            if (Vape.INSTANCE.getOnlineManager().getLocalFriend().getUser().getId() == serverGroupInviteUpdatePacket.k().getId()) {
                return;
            }
            OnlineEventDispatcher.O.G(new GroupInviteSentEvent(this, Vape.INSTANCE.getOnlineManager().getFriendCache().getOrCreateFriend(serverGroupInviteUpdatePacket.f().getId(), () -> ZeusClient.lambda$handleServerGroupInviteUpdatePacket$20(serverGroupInviteUpdatePacket))));
        } else if (serverGroupInviteUpdatePacket.e() == GroupInviteUpdateStatus.DECLINED) {
            OnlineEventDispatcher.O.G(new PartyInviteRemovedEvent(this, Vape.INSTANCE.getOnlineManager().getFriendCache().getOrCreateFriend(serverGroupInviteUpdatePacket.f().getId(), () -> ZeusClient.lambda$handleServerGroupInviteUpdatePacket$21(serverGroupInviteUpdatePacket))));
        }
    }

    private void h(ServerGroupLeaderChangedPacket serverGroupLeaderChangedPacket) {
        OnlineEventDispatcher.O.G(new PartyLeaderChangedEvent(this, serverGroupLeaderChangedPacket.getNewLeader()));
    }

    public void N(int n, Map<Integer, ActivityItemStackPayload> map) {
        this.V(new ClientInventorySnapshotPacket(n, map));
    }

    private void G(ServerFriendPresenceStatePacket serverFriendPresenceStatePacket) {
        OnlineEventDispatcher.O.G(new FriendPresenceStateEvent(this, serverFriendPresenceStatePacket.getUser(), serverFriendPresenceStatePacket.getPresenceState()));
    }

    private static void lambda$sendHandshake$2(Consumer consumer, HandshakeResponsePacket handshakeResponsePacket) {
        consumer.accept(handshakeResponsePacket);
    }

    private void r(ServerPartyMemberUpdatePacket serverPartyMemberUpdatePacket) {
        if (serverPartyMemberUpdatePacket.o() == PartyMemberActionType.ADD) {
            // empty if block
        }
        OnlineEventDispatcher.O.G(new PartyMemberUpdateEvent(this, serverPartyMemberUpdatePacket.R(), serverPartyMemberUpdatePacket.o()));
    }

    public void B() {
        this.V(ClientFriendActivityUsersPacket.u());
    }

    private static OnlineFriend lambda$null$16(UserModel userModel) {
        return new OnlineFriend(userModel);
    }

    private void v(ServerFriendRequestRemovedPacket serverFriendRequestRemovedPacket) {
        OnlineEventDispatcher.O.G(new FriendRequestRemovedEvent(this, serverFriendRequestRemovedPacket.getUserId()));
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        super.exceptionCaught(channelHandlerContext, throwable);
    }

    private void f(ServerFriendRemovedPacket serverFriendRemovedPacket) {
        OnlineEventDispatcher.O.G(new FriendRemovedEvent(this, serverFriendRemovedPacket.m()));
    }

    public void p() {
        this.z(new HeartbeatPacket(), ZeusClient::lambda$sendHeartbeatPacket$4);
    }

    private void G(ServerDisconnectPacket serverDisconnectPacket) {
        if (serverDisconnectPacket.q() == OnlineDisconnectReason.BANNED) {
            OnlineConnectionManager.INSTANCE.setAccountState(OnlineAccountState.BANNED);
        }
        OnlineConnectionManager.INSTANCE.setDisconnectReason(serverDisconnectPacket.q());
        OnlineConnectionManager.INSTANCE.connect();
    }

    public void J(String string, Consumer<AuthenticationResponsePacket> consumer) {
        MinecraftSessionWrapper minecraftSessionWrapper = Minecraft.Q$src$Lgg_vape_account_MinecraftSessionWrapper_$1ftnn3u();
        this.z(new AuthenticationPacket(string, minecraftSessionWrapper.getProfileId(), minecraftSessionWrapper.getUsername()), arg_0 -> this.lambda$sendAuthenticationPacket$5(consumer, arg_0));
    }

    private void L(FriendActivityUsersPacket friendActivityUsersPacket) {
        block4: {
            block3: {
                if (friendActivityUsersPacket.G() != FriendActivityUsersAction.ADD) break block3;
                for (long l : friendActivityUsersPacket.Y()) {
                    OnlineFriend onlineFriend = Vape.INSTANCE.getOnlineManager().getFriendCache().getFriend(l);
                    if (onlineFriend == null) continue;
                    Vape.INSTANCE.getOnlineManager().getActivityManager().startTracking(onlineFriend);
                }
                break block4;
            }
            if (friendActivityUsersPacket.G() != FriendActivityUsersAction.CHANGED_WORLD) break block4;
            for (long l : friendActivityUsersPacket.Y()) {
                if (l == Vape.INSTANCE.getOnlineManager().getLocalFriend().getUser().getId()) {
                    Vape.INSTANCE.getOnlineManager().getActivityManager().reset(false);
                    continue;
                }
                Vape.INSTANCE.getOnlineManager().getActivityManager().removeTrackedUser(l);
            }
        }
    }

    private void lambda$sendFriendRequestPacket$8(Consumer consumer, FriendRequestResponsePacket friendRequestResponsePacket) {
        if (friendRequestResponsePacket.getStatus() == FriendRequestResponseStatus.SENT) {
            OnlineEventDispatcher.O.G(new FriendRequestSentEvent(this, friendRequestResponsePacket.getRequest()));
        }
        consumer.accept(friendRequestResponsePacket);
    }

    private static void lambda$sendChatToFriendPacket$10(Consumer consumer, ChatToFriendResponsePacket chatToFriendResponsePacket) {
        if (chatToFriendResponsePacket.Z() == ChatToFriendStatus.SUCCESS) {
            consumer.accept(chatToFriendResponsePacket);
        }
    }

    private void S(ServerGroupOptionUpdatePacket serverGroupOptionUpdatePacket) {
        OnlineEventDispatcher.O.G(new GroupOptionUpdatedEvent(this, serverGroupOptionUpdatePacket.getOption(), serverGroupOptionUpdatePacket.getValue()));
    }
}
