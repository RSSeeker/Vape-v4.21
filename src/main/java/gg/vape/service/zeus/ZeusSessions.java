package gg.vape.service.zeus;

import io.netty.channel.Channel;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

final class ZeusSessions {
    private final ConcurrentHashMap<Long, ZeusClientHandler> handlers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, ConcurrentLinkedQueue<Long>> pendingLocationChecks = new ConcurrentHashMap<>();

    ZeusClientHandler put(long userId, ZeusClientHandler handler) {
        return handlers.put(userId, handler);
    }

    boolean remove(long userId, ZeusClientHandler handler) {
        return handlers.remove(userId, handler);
    }

    boolean isOnline(long userId) {
        ZeusClientHandler handler = handlers.get(userId);
        return handler != null && handler.channel().isOpen();
    }

    ZeusClientHandler handler(long userId) {
        return handlers.get(userId);
    }

    void send(long userId, int packetId, Consumer<ByteBuf> writer) {
        ZeusClientHandler handler = handlers.get(userId);
        if (handler != null) {
            ZeusClientHandler.send(handler.channel(), packetId, writer);
        }
    }

    void send(Collection<Long> userIds, int packetId, Consumer<ByteBuf> writer) {
        for (Long userId : userIds) {
            send(userId, packetId, writer);
        }
    }

    void routeActivity(long senderId, int packetId, byte[] payload) {
        for (ZeusClientHandler handler : handlers.values()) {
            if (handler.activitySubscriptions().contains(senderId)) {
                send(handler.userId(), packetId, output -> {
                    output.writeLong(senderId);
                    output.writeBytes(payload);
                });
            }
        }
    }

    void routeSnapshot(long senderId, byte[] payload) {
        for (ZeusClientHandler handler : handlers.values()) {
            if (handler.activitySubscriptions().contains(senderId)) {
                send(handler.userId(), 37, output -> {
                    ZeusBuffer.writeVarInt(output, 1);
                    output.writeLong(senderId);
                    output.writeBytes(payload);
                });
            }
        }
    }

    void requestLocationCheck(long requesterId, long targetId) {
        pendingLocationChecks.computeIfAbsent(targetId, ignored -> new ConcurrentLinkedQueue<>())
                .add(requesterId);
    }

    Long consumeLocationRequester(long targetId) {
        ConcurrentLinkedQueue<Long> queue = pendingLocationChecks.get(targetId);
        return queue == null ? null : queue.poll();
    }

    void changedWorld(long userId) {
        for (ZeusClientHandler handler : handlers.values()) {
            if (handler.activitySubscriptions().remove(userId)) {
                send(handler.userId(), 36, output -> {
                    ZeusBuffer.writeVarInt(output, 1);
                    ZeusBuffer.writeVarInt(output, 1);
                    output.writeLong(userId);
                });
            }
        }
    }
}
