package gg.vape.service.zeus;

import gg.vape.service.store.AccountRecord;
import gg.vape.service.store.FileStore;
import gg.vape.service.store.FriendRequestRecord;
import gg.vape.service.store.PartyRecord;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

final class ZeusClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final int PROTOCOL_VERSION = 10;
    private final FileStore store;
    private final ZeusSessions sessions;
    private final Set<Long> activitySubscriptions = ConcurrentHashMap.newKeySet();
    private boolean authenticated;
    private String token;
    private AccountRecord account;
    private Channel channel;

    ZeusClientHandler(FileStore store, ZeusSessions sessions) {
        this.store = store;
        this.sessions = sessions;
    }

    @Override
    public void channelActive(ChannelHandlerContext context) {
        this.channel = context.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, ByteBuf frame) throws Exception {
        int packetId = ZeusBuffer.readVarInt(frame);
        if (packetId == 0) {
            UUID requestId = ZeusBuffer.readUuid(frame);
            sendTracked(context, 0, requestId, ignored -> {
            });
            return;
        }
        if (!authenticated) {
            handleUnauthenticated(context, packetId, frame);
        } else {
            handleAuthenticated(context, packetId, frame);
        }
        if (frame.isReadable()) {
            frame.skipBytes(frame.readableBytes());
        }
    }

    private void handleUnauthenticated(ChannelHandlerContext context, int packetId, ByteBuf frame)
            throws IOException {
        if (packetId == 1) {
            UUID requestId = ZeusBuffer.readUuid(frame);
            int clientVersion = ZeusBuffer.readVarInt(frame);
            int status = clientVersion == PROTOCOL_VERSION ? 0 : clientVersion < PROTOCOL_VERSION ? 1 : 2;
            sendTracked(context, 2, requestId, payload -> ZeusBuffer.writeVarInt(payload, status));
            if (status != 0) {
                context.close();
            }
            return;
        }
        if (packetId != 2) {
            throw new IllegalArgumentException("Unexpected unauthenticated packet id " + packetId);
        }
        UUID requestId = ZeusBuffer.readUuid(frame);
        String accessToken = ZeusBuffer.readString(frame, 255);
        UUID minecraftUuid = ZeusBuffer.readUuid(frame);
        String minecraftUsername = ZeusBuffer.readString(frame, 16);
        AccountRecord resolved = store.account(accessToken).orElse(null);
        if (resolved == null || resolved.banned || !resolved.licensed || !resolved.registered) {
            send(context, 1, payload -> ZeusBuffer.writeVarInt(payload,
                    resolved != null && resolved.banned ? 2 : 3));
            context.close();
            return;
        }
        store.updateMinecraftIdentity(accessToken, minecraftUuid, minecraftUsername);
        store.updatePresence(resolved.userId, 0);
        this.token = accessToken;
        this.account = resolved;
        sendTracked(context, 3, requestId, payload -> {
            ZeusBuffer.writeVarInt(payload, 1);
            writeUser(payload, resolved);
        });
        this.authenticated = true;
        ZeusClientHandler previous = sessions.put(resolved.userId, this);
        if (previous != null && previous != this && previous.channel().isOpen()) {
            send(previous.channel(), 1, payload -> ZeusBuffer.writeVarInt(payload, 1));
            previous.channel().close();
        }
        broadcastPresence(0);
        System.out.printf("Zeus authenticated user=%d name=%s minecraft=%s%n",
                resolved.userId, resolved.username, minecraftUsername);
    }

    private void handleAuthenticated(ChannelHandlerContext context, int packetId, ByteBuf frame)
            throws IOException {
        switch (packetId) {
            case 1:
                handleShowUsername(frame);
                break;
            case 2:
                handleDisplayName(context, frame);
                break;
            case 3:
                handleFriendsList(context, frame);
                break;
            case 4:
                handleFriendDelete(context, frame);
                break;
            case 5:
                handleFriendRequest(context, frame);
                break;
            case 6:
                handleFriendRequestUpdate(context, frame);
                break;
            case 7:
                handleFriendChat(context, frame);
                break;
            case 8:
                handlePartyCreate(context, frame);
                break;
            case 9:
                handlePartyLeave(context, frame);
                break;
            case 10:
                handlePartyDelete(context, frame);
                break;
            case 11:
                handlePartyInvite(context, frame);
                break;
            case 12:
                handlePartyUninvite(context, frame);
                break;
            case 13:
                handlePartyKick(context, frame);
                break;
            case 14:
                handlePartyPromote(context, frame);
                break;
            case 15:
                handlePartyInviteDecision(context, frame);
                break;
            case 16:
                handlePartyOption(frame);
                break;
            case 17:
                handlePartyChat(context, frame);
                break;
            case 18:
                handlePing(context, frame);
                break;
            case 19:
                handleMinecraftProfile(frame);
                break;
            case 20:
                handleServerAddress(frame);
                break;
            case 21:
                handleActivityUsers(frame);
                break;
            case 22:
                sessions.routeSnapshot(account.userId, readRemaining(frame));
                break;
            case 23:
                handleBlockLocation(frame);
                break;
            case 24:
                handleLocationResponse(frame);
                break;
            case 25:
                handlePresence(frame);
                break;
            case 26:
                sessions.routeActivity(account.userId, 40, readRemaining(frame));
                break;
            case 27:
                handleInventorySnapshot(frame);
                break;
            case 28:
                sessions.routeActivity(account.userId, 42, readRemaining(frame));
                break;
            case 29:
                sessions.routeActivity(account.userId, 43, readRemaining(frame));
                break;
            case 30:
                store.updateActiveProfile(account.userId, frame.readLong());
                break;
            case 31:
                store.updateActiveProfile(account.userId, -1L);
                break;
            default:
                System.out.printf("Ignoring unknown Zeus packet id=%d user=%d bytes=%d%n",
                        packetId, account.userId, frame.readableBytes());
        }
    }

    private void handleShowUsername(ByteBuf frame) throws IOException {
        boolean showUsername = frame.readBoolean();
        store.updateShowUsername(account.userId, showUsername);
        for (long friendId : store.friendIds(account.userId)) {
            sessions.send(friendId, 39, output -> {
                output.writeLong(account.userId);
                output.writeBoolean(showUsername);
            });
        }
    }

    private void handleDisplayName(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        String displayName = ZeusBuffer.readString(frame, 16);
        boolean valid = displayName.matches("^[a-zA-Z\\d_.-]*$") && !displayName.trim().isEmpty();
        boolean updated = valid && store.updateDisplayName(token, displayName);
        sendTracked(context, 2, requestId, payload -> {
            ZeusBuffer.writeVarInt(payload, updated ? 0 : valid ? 2 : 3);
            if (updated) {
                ZeusBuffer.writeString(payload, displayName);
                payload.writeLong(account.userId);
            } else {
                payload.writeLong(0L);
            }
        });
        if (updated) {
            for (long friendId : store.friendIds(account.userId)) {
                sessions.send(friendId, 33, output -> {
                    output.writeLong(account.userId);
                    ZeusBuffer.writeString(output, displayName);
                });
            }
        }
    }

    private void handleFriendsList(ChannelHandlerContext context, ByteBuf frame) {
        UUID requestId = ZeusBuffer.readUuid(frame);
        List<AccountRecord> friends = new ArrayList<>();
        for (Long friendId : store.friendIds(account.userId)) {
            store.account(friendId).ifPresent(friends::add);
        }
        List<FriendRequestRecord> incoming = store.incomingRequests(account.userId);
        List<FriendRequestRecord> outgoing = store.outgoingRequests(account.userId);
        sendTracked(context, 3, requestId, output -> {
            output.writeInt(friends.size());
            friends.forEach(friend -> writeFriend(output, friend));
            output.writeInt(incoming.size());
            incoming.forEach(request -> writeFriendRequest(output, request));
            output.writeInt(outgoing.size());
            outgoing.forEach(request -> writeFriendRequest(output, request));
        });
    }

    private void handleFriendDelete(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        long otherId = readUserId(frame);
        boolean removed = store.deleteFriend(account.userId, otherId);
        sendTracked(context, 5, requestId, output -> output.writeBoolean(removed));
        if (removed) {
            sessions.send(otherId, 6, output -> writeUser(output, account));
        }
    }

    private void handleFriendRequest(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        String targetName = ZeusBuffer.readString(frame, 16);
        FileStore.FriendRequestCreation result = store.createFriendRequest(account.userId, targetName);
        sendTracked(context, 10, requestId, output -> {
            ZeusBuffer.writeVarInt(output, result.status());
            if (result.status() == 0) {
                writeFriendRequest(output, result.request());
            }
        });
        if (result.status() == 0) {
            sessions.send(result.target().userId, 8, output -> writeFriendRequest(output, result.request()));
        }
    }

    private void handleFriendRequestUpdate(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        long friendRequestId = frame.readLong();
        boolean accepted = frame.readBoolean();
        FileStore.FriendRequestUpdate result = store.updateFriendRequest(
                account.userId, friendRequestId, accepted);
        sendTracked(context, 11, requestId, output -> {
            output.writeLong(friendRequestId);
            ZeusBuffer.writeVarInt(output, result.status());
            if (result.status() == 0 && result.other() != null) {
                writeFriend(output, result.other());
            }
        });
        if (result.request() != null) {
            sessions.send(result.request().senderId, 9, output -> output.writeLong(friendRequestId));
            if (accepted && result.other() != null) {
                sessions.send(result.other().userId, 4, output -> writeFriend(output, account));
            }
        }
    }

    private void handleFriendChat(ChannelHandlerContext context, ByteBuf frame) {
        UUID requestId = ZeusBuffer.readUuid(frame);
        long targetId = frame.readLong();
        String message = ZeusBuffer.readString(frame, 255);
        int status = !store.areFriends(account.userId, targetId) ? 1
                : !sessions.isOnline(targetId) ? 2 : 0;
        long timestamp = System.currentTimeMillis();
        sendTracked(context, 12, requestId, output -> {
            ZeusBuffer.writeVarInt(output, status);
            ZeusBuffer.writeString(output, status == 0 ? message : "");
            output.writeLong(timestamp);
        });
        if (status == 0) {
            sessions.send(targetId, 30, output -> {
                writeUser(output, account);
                ZeusBuffer.writeString(output, message);
                output.writeLong(timestamp);
            });
        }
    }

    private void handlePartyCreate(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        PartyRecord party = store.createParty(account.userId);
        sendTracked(context, 14, requestId, output -> ZeusBuffer.writeVarInt(output, party == null ? 1 : 0));
    }

    private void handlePartyLeave(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        FileStore.PartyLeaveResult result = store.leaveParty(account.userId);
        sendTracked(context, 15, requestId,
                output -> ZeusBuffer.writeVarInt(output, result.successful() ? 0 : 1));
        if (result.successful()) {
            notifyPartyMember(result.party(), account, 1);
            if (result.newLeaderId() >= 0 && result.newLeaderId() != account.userId) {
                store.account(result.newLeaderId()).ifPresent(leader ->
                        sessions.send(result.party().members, 24, output -> writeUser(output, leader)));
            }
        }
    }

    private void handlePartyDelete(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        PartyRecord party = store.deleteParty(account.userId);
        sendTracked(context, 16, requestId, output -> ZeusBuffer.writeVarInt(output, party == null ? 1 : 0));
        if (party != null) {
            Set<Long> recipients = new LinkedHashSet<>(party.members);
            recipients.remove(account.userId);
            sessions.send(recipients, 17, output -> {
            });
        }
    }

    private void handlePartyInvite(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        long targetId = readUserId(frame);
        int status;
        FileStore.PartyInviteResult result = null;
        if (!sessions.isOnline(targetId)) {
            status = 3;
        } else if (!store.account(targetId).isPresent()) {
            status = 4;
        } else {
            result = store.inviteToParty(account.userId, targetId);
            status = result.status();
        }
        int finalStatus = status;
        sendTracked(context, 20, requestId, output -> ZeusBuffer.writeVarInt(output, finalStatus));
        if (status == 0 && result != null) {
            sessions.send(targetId, 26, output -> writeUser(output, account));
            AccountRecord target = store.account(targetId)
                    .orElseThrow(() -> new IllegalStateException("Target account not found"));
            Set<Long> members = new LinkedHashSet<>(result.party().members);
            members.remove(account.userId);
            sessions.send(members, 19, output -> {
                ZeusBuffer.writeVarInt(output, 1);
                writeUser(output, account);
                writeUser(output, target);
            });
        }
    }

    private void handlePartyUninvite(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        long targetId = readUserId(frame);
        boolean removed = store.uninvite(account.userId, targetId);
        sendTracked(context, 21, requestId, output -> ZeusBuffer.writeVarInt(output, removed ? 0 : 1));
        if (removed) {
            sessions.send(targetId, 25, output -> writeUser(output, account));
        }
    }

    private void handlePartyKick(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        long targetId = frame.readLong();
        PartyRecord party = store.partyFor(account.userId).orElse(null);
        boolean removed = store.kickPartyMember(account.userId, targetId);
        sendTracked(context, 22, requestId, output -> ZeusBuffer.writeVarInt(output, removed ? 0 : 1));
        if (removed && party != null) {
            AccountRecord target = store.account(targetId).orElse(null);
            if (target != null) {
                notifyPartyMember(party, target, 1);
                sessions.send(targetId, 18, output -> {
                    writeGroupUser(output, target);
                    ZeusBuffer.writeVarInt(output, 1);
                });
            }
        }
    }

    private void handlePartyPromote(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        long targetId = frame.readLong();
        PartyRecord party = store.promotePartyMember(account.userId, targetId);
        sendTracked(context, 23, requestId, output -> ZeusBuffer.writeVarInt(output, party == null ? 1 : 0));
        if (party != null) {
            AccountRecord leader = store.account(targetId)
                    .orElseThrow(() -> new IllegalStateException("Leader account not found"));
            sessions.send(party.members, 24, output -> writeUser(output, leader));
        }
    }

    private void handlePartyInviteDecision(ChannelHandlerContext context, ByteBuf frame) throws IOException {
        UUID requestId = ZeusBuffer.readUuid(frame);
        long inviterId = frame.readLong();
        boolean accepted = frame.readBoolean();
        FileStore.PartyInviteDecision result = store.decidePartyInvite(
                account.userId, inviterId, accepted);
        sendTracked(context, 27, requestId, output -> {
            ZeusBuffer.writeVarInt(output, result.status());
            if (result.status() == 0) {
                writeParty(output, result.party());
            }
        });
        if (result.party() != null) {
            if (accepted && result.status() == 0) {
                Set<Long> existing = new LinkedHashSet<>(result.party().members);
                existing.remove(account.userId);
                sessions.send(existing, 18, output -> {
                    writeGroupUser(output, account);
                    ZeusBuffer.writeVarInt(output, 0);
                });
                sessions.send(existing, 19, output -> {
                    ZeusBuffer.writeVarInt(output, 0);
                    writeUser(output, account);
                });
            } else {
                sessions.send(inviterId, 19, output -> {
                    ZeusBuffer.writeVarInt(output, 2);
                    writeUser(output, account);
                });
            }
        }
    }

    private void handlePartyOption(ByteBuf frame) throws IOException {
        int option = ZeusBuffer.readVarInt(frame);
        boolean value = frame.readBoolean();
        PartyRecord party = option == 0 ? store.updatePartyOpenInvites(account.userId, value) : null;
        if (party != null) {
            sessions.send(party.members, 28, output -> {
                ZeusBuffer.writeVarInt(output, option);
                output.writeBoolean(value);
            });
        }
    }

    private void handlePartyChat(ChannelHandlerContext context, ByteBuf frame) {
        UUID requestId = ZeusBuffer.readUuid(frame);
        String message = ZeusBuffer.readString(frame, 255);
        PartyRecord party = store.partyFor(account.userId).orElse(null);
        int status = party == null ? 1 : 0;
        long timestamp = System.currentTimeMillis();
        sendTracked(context, 29, requestId, output -> {
            ZeusBuffer.writeVarInt(output, status);
            ZeusBuffer.writeString(output, status == 0 ? message : "");
            output.writeLong(timestamp);
        });
        if (party != null) {
            sessions.send(party.members, 13, output -> {
                output.writeLong(account.userId);
                ZeusBuffer.writeString(output, message);
                output.writeLong(timestamp);
            });
        }
    }

    private void handlePing(ChannelHandlerContext context, ByteBuf frame) {
        UUID requestId = ZeusBuffer.readUuid(frame);
        byte[] targetData = readRemaining(frame);
        PartyRecord party = store.partyFor(account.userId).orElse(null);
        Set<Long> recipients = party == null
                ? new LinkedHashSet<>(store.friendIds(account.userId))
                : new LinkedHashSet<>(party.members);
        recipients.remove(account.userId);
        int audience = party == null ? 0 : 1;
        for (Long recipient : recipients) {
            sessions.send(recipient, 32, output -> {
                ZeusBuffer.writeVarInt(output, audience);
                output.writeLong(account.userId);
                output.writeBytes(targetData);
            });
        }
        sendTracked(context, 31, requestId, output -> {
            output.writeBoolean(true);
            ZeusBuffer.writeVarInt(output, recipients.size());
            output.writeLong(System.currentTimeMillis());
        });
    }

    private void handleMinecraftProfile(ByteBuf frame) throws IOException {
        UUID minecraftUuid = ZeusBuffer.readUuid(frame);
        String minecraftUsername = ZeusBuffer.readString(frame, 16);
        store.updateMinecraftIdentity(token, minecraftUuid, minecraftUsername);
        for (Long friendId : store.friendIds(account.userId)) {
            sessions.send(friendId, 34, output -> {
                output.writeLong(account.userId);
                ZeusBuffer.writeUuid(output, minecraftUuid);
                ZeusBuffer.writeString(output, minecraftUsername);
            });
        }
    }

    private void handleServerAddress(ByteBuf frame) throws IOException {
        String serverAddress = frame.readBoolean() ? ZeusBuffer.readString(frame, 255) : null;
        store.updateServerAddress(account.userId, serverAddress);
        for (Long friendId : store.friendIds(account.userId)) {
            sessions.send(friendId, 35, output -> {
                output.writeLong(account.userId);
                output.writeBoolean(serverAddress != null);
                if (serverAddress != null) {
                    ZeusBuffer.writeString(output, serverAddress);
                }
            });
        }
    }

    private void handleActivityUsers(ByteBuf frame) {
        int action = ZeusBuffer.readVarInt(frame);
        if (action == 1) {
            activitySubscriptions.clear();
            sessions.changedWorld(account.userId);
            return;
        }
        int count = ZeusBuffer.readVarInt(frame);
        List<Long> accepted = new ArrayList<>();
        for (int index = 0; index < count && index < 128; index++) {
            long targetId = frame.readLong();
            if (store.areFriends(account.userId, targetId)
                    || store.partyFor(account.userId).map(party -> party.members.contains(targetId)).orElse(false)) {
                activitySubscriptions.add(targetId);
                accepted.add(targetId);
            }
        }
        send(channel, 36, output -> {
            ZeusBuffer.writeVarInt(output, 0);
            ZeusBuffer.writeVarInt(output, accepted.size());
            accepted.forEach(output::writeLong);
        });
    }

    private void handleBlockLocation(ByteBuf frame) {
        long targetId = frame.readLong();
        int x = frame.readInt();
        int y = frame.readInt();
        int z = frame.readInt();
        boolean allowed = store.areFriends(account.userId, targetId)
                || store.partyFor(account.userId).map(party -> party.members.contains(targetId)).orElse(false);
        if (!allowed || !sessions.isOnline(targetId)) {
            return;
        }
        sessions.requestLocationCheck(account.userId, targetId);
        sessions.send(targetId, 38, output -> {
            output.writeInt(x);
            output.writeInt(y);
            output.writeInt(z);
        });
    }

    private void handleLocationResponse(ByteBuf frame) {
        boolean nearby = frame.readBoolean();
        Long requesterId = sessions.consumeLocationRequester(account.userId);
        if (!nearby || requesterId == null) {
            return;
        }
        ZeusClientHandler requester = sessions.handler(requesterId);
        if (requester == null) {
            return;
        }
        requester.activitySubscriptions.add(account.userId);
        activitySubscriptions.add(requesterId);
        sessions.send(requesterId, 36, output -> writeActivityUsers(output, account.userId));
        sessions.send(account.userId, 36, output -> writeActivityUsers(output, requesterId));
    }

    private void handlePresence(ByteBuf frame) throws IOException {
        int presence = ZeusBuffer.readVarInt(frame);
        store.updatePresence(account.userId, presence);
        broadcastPresence(presence);
    }

    private void handleInventorySnapshot(ByteBuf frame) {
        int selectedSlot = frame.readInt();
        byte[] inventory = readRemaining(frame);
        ByteBuf converted = Unpooled.buffer();
        ZeusBuffer.writeVarInt(converted, selectedSlot);
        converted.writeBytes(inventory);
        byte[] payload = new byte[converted.readableBytes()];
        converted.readBytes(payload);
        converted.release();
        sessions.routeActivity(account.userId, 41, payload);
    }

    private void broadcastPresence(int presence) {
        for (Long friendId : store.friendIds(account.userId)) {
            sessions.send(friendId, 7, output -> {
                writeUser(output, account);
                ZeusBuffer.writeVarInt(output, presence);
            });
        }
    }

    private void notifyPartyMember(PartyRecord party, AccountRecord member, int action) {
        if (party == null) {
            return;
        }
        sessions.send(party.members, 18, output -> {
            writeGroupUser(output, member);
            ZeusBuffer.writeVarInt(output, action);
        });
    }

    private void writeFriendRequest(ByteBuf output, FriendRequestRecord request) {
        output.writeLong(request.requestId);
        java.util.Optional<AccountRecord> sender = store.account(request.senderId);
        if (sender.isPresent()) {
            writeUser(output, sender.get());
        } else {
            writeUnknownUser(output, request.senderId);
        }
        java.util.Optional<AccountRecord> receiver = store.account(request.receiverId);
        if (receiver.isPresent()) {
            writeUser(output, receiver.get());
        } else {
            writeUnknownUser(output, request.receiverId);
        }
    }

    private void writeFriend(ByteBuf output, AccountRecord friend) {
        writeUser(output, friend);
        ZeusBuffer.writeUuid(output, safeUuid(friend.minecraftUuid));
        ZeusBuffer.writeString(output, safeString(friend.minecraftUsername, 16));
        output.writeBoolean(friend.showUsername);
        ZeusBuffer.writeVarInt(output, sessions.isOnline(friend.userId) ? friend.presence : 2);
        output.writeBoolean(friend.serverAddress != null);
        if (friend.serverAddress != null) {
            ZeusBuffer.writeString(output, safeString(friend.serverAddress, 128));
        }
    }

    private void writeParty(ByteBuf output, PartyRecord party) {
        AccountRecord leader = store.account(party.leaderId)
                .orElseThrow(() -> new IllegalStateException("Party leader account not found"));
        writeGroupUser(output, leader);
        ZeusBuffer.writeVarInt(output, party.members.size());
        party.members.forEach(memberId -> store.account(memberId)
                .ifPresent(member -> writeGroupUser(output, member)));
        ZeusBuffer.writeVarInt(output, party.invitedUsers.size());
        party.invitedUsers.forEach(memberId -> store.account(memberId)
                .ifPresent(member -> writeGroupUser(output, member)));
    }

    private static void writeGroupUser(ByteBuf output, AccountRecord user) {
        writeUser(output, user);
        ZeusBuffer.writeUuid(output, safeUuid(user.minecraftUuid));
        ZeusBuffer.writeString(output, safeString(user.minecraftUsername, 16));
        output.writeBoolean(user.serverAddress != null);
        if (user.serverAddress != null) {
            ZeusBuffer.writeString(output, safeString(user.serverAddress, 128));
        }
        output.writeInt(0);
    }

    private static void writeUser(ByteBuf output, AccountRecord user) {
        output.writeLong(user.userId);
        ZeusBuffer.writeString(output, safeString(user.username, 16));
    }

    private static void writeUnknownUser(ByteBuf output, long userId) {
        output.writeLong(userId);
        ZeusBuffer.writeString(output, "Unknown");
    }

    private static long readUserId(ByteBuf frame) {
        long userId = frame.readLong();
        ZeusBuffer.readString(frame, 16);
        return userId;
    }

    private static void writeActivityUsers(ByteBuf output, long userId) {
        ZeusBuffer.writeVarInt(output, 0);
        ZeusBuffer.writeVarInt(output, 1);
        output.writeLong(userId);
    }

    private static byte[] readRemaining(ByteBuf frame) {
        byte[] bytes = new byte[frame.readableBytes()];
        frame.readBytes(bytes);
        return bytes;
    }

    private static UUID safeUuid(String value) {
        try {
            return UUID.fromString(value);
        } catch (RuntimeException ignored) {
            return new UUID(0L, 0L);
        }
    }

    private static String safeString(String value, int maximumLength) {
        if (value == null) {
            return "";
        }
        return value.substring(0, Math.min(value.length(), maximumLength));
    }

    long userId() {
        return account == null ? -1L : account.userId;
    }

    Channel channel() {
        return channel;
    }

    Set<Long> activitySubscriptions() {
        return activitySubscriptions;
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) {
        if (account != null && sessions.remove(account.userId, this)) {
            sessions.changedWorld(account.userId);
            try {
                store.updatePresence(account.userId, 2);
            } catch (IOException exception) {
                System.err.println("Failed to persist offline presence: " + exception.getMessage());
            }
            broadcastPresence(2);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        System.err.println("Zeus connection error: " + cause.getMessage());
        context.close();
    }

    private static void sendTracked(ChannelHandlerContext context, int packetId, UUID requestId,
                                    Consumer<ByteBuf> payloadWriter) {
        send(context.channel(), packetId, payload -> {
            ZeusBuffer.writeUuid(payload, requestId);
            payloadWriter.accept(payload);
        });
    }

    private static void send(ChannelHandlerContext context, int packetId, Consumer<ByteBuf> payloadWriter) {
        send(context.channel(), packetId, payloadWriter);
    }

    static void send(Channel channel, int packetId, Consumer<ByteBuf> payloadWriter) {
        if (channel == null || !channel.isOpen()) {
            return;
        }
        ByteBuf payload = Unpooled.buffer();
        ZeusBuffer.writeVarInt(payload, packetId);
        payloadWriter.accept(payload);
        ByteBuf framed = channel.alloc().buffer(ZeusBuffer.varIntSize(payload.readableBytes())
                + payload.readableBytes());
        ZeusBuffer.writeVarInt(framed, payload.readableBytes());
        framed.writeBytes(payload);
        payload.release();
        channel.writeAndFlush(framed);
    }
}
