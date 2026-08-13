package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.utils.network.PacketDispatchGuard;
import gg.vape.utils.network.PacketDispatchMarkerRegistry;
import gg.vape.wrapper.impl.NetworkManager;
import gg.vape.wrapper.impl.Packet;

public class EventPacketSend
extends Event {
    private Packet packet;
    private final NetworkManager networkManager;
    private boolean modified = false;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public EventPacketSend(Object networkManagerHandle, Object packetHandle) {
        this.networkManager = new NetworkManager(networkManagerHandle);
        this.packet = new Packet(packetHandle);
    }

    public Object getPacketInstance() {
        return this.packet.getObject();
    }

    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    public boolean wasModified() {
        return this.modified;
    }


    @Override
    public boolean fire() {
        if (PacketDispatchGuard.b.o(this.packet)) {
            PacketDispatchGuard.b.onPacketSend(this);
            return this.isCanceled();
        }
        if (PacketDispatchMarkerRegistry.J(this.packet)) {
            PacketDispatchMarkerRegistry.p(this.packet);
            return this.isCanceled();
        }
        return super.fire();
    }

    public void forceCancel() {
        PacketDispatchMarkerRegistry.q(this.packet);
        this.setCancelled(true);
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
        this.modified = true;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public Packet getPacket() {
        return this.packet;
    }

    @Override
    public void setCancelled(boolean canceled) {
        super.setCancelled(canceled);
    }
}

