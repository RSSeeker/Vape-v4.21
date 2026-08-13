package gg.vape.event;

import gg.vape.event.impl.EventPacketSend;
import gg.vape.utils.network.PacketDispatchGuard;

public class PacketSendDispatchGuardCallback {
    private final EventPacketSend event;

    public PacketSendDispatchGuardCallback(EventPacketSend eventPacketSend) {
        this.event = eventPacketSend;
    }

    public void dispatch(PacketDispatchGuard dispatchGuard) {
        dispatchGuard.o(this.event);
    }
}
