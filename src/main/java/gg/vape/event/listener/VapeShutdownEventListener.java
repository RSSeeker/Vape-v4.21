package gg.vape.event.listener;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.UseEntityPacketBridge;

public class VapeShutdownEventListener
implements EventListener {

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPacketSend(EventPacketSend eventPacketSend) {
        UseEntityPacketBridge useEntityPacketBridge;
        int entityId;
        Packet packet = eventPacketSend.getPacket();
        if (UseEntityPacketBridge.isUseEntityPacket(packet) && ClientSettings.isReservedEntityId(entityId = (useEntityPacketBridge = new UseEntityPacketBridge(packet.getObject())).getEntityId())) {
            eventPacketSend.setCancelled(true);
        }
    }
}

