package gg.vape.event.forge;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventChat;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.ClientChatReceivedEvent;
import gg.vape.wrapper.impl.ForgeVersion;

public class ForgeClientChatReceivedEvent
implements EventListener {

    @EventHandler(priority=EventPriority.LOW)
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        if (!eventPacketReceive.getPacket().isInstance(MappedClasses.Zu)) {
            return;
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            return;
        }
        ClientChatReceivedEvent clientChatReceivedEvent = new ClientChatReceivedEvent(eventPacketReceive.getPacket().getObject());
        EventChat eventChat = new EventChat(clientChatReceivedEvent.P());
        eventChat.fire();
        if (eventChat.isCanceled()) {
            eventPacketReceive.setCancelled(true);
        } else {
            clientChatReceivedEvent.t(eventChat.getMessage());
        }
    }
}

