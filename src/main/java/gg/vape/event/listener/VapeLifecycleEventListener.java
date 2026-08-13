package gg.vape.event.listener;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.PlayerEventNameFormat;

public class VapeLifecycleEventListener
implements EventListener {

    @EventHandler
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        PlayerEventNameFormat resourcePackPacket;
        String resourcePackUrl;
        if (eventPacketReceive.getPacket().isInstance(MappedClasses.l3) && (resourcePackUrl = (resourcePackPacket = new PlayerEventNameFormat(eventPacketReceive.getPacket())).getUrl()).contains("vapeclient")) {
            resourcePackPacket.setUrl(resourcePackUrl.replace("vapeclient", "-"));
        }
    }
}

