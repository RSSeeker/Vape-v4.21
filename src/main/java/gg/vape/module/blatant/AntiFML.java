package gg.vape.module.blatant;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;

public class AntiFML
extends Mod {
    private static final long MOD_ID = -7613235170337351361L;

    @EventHandler
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
    }

    @EventHandler
    public void onPacketSend(EventPacketSend eventPacketSend) {
        if (eventPacketSend.getPacket().getObject().getClass().getName().toLowerCase().contains(MappedClasses.F0)) {
            eventPacketSend.setCancelled(true);
        }
    }

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    public AntiFML() {
        super("Anti-FML", (int)MOD_ID, Category.OTHER, "Some servers prevent Forge from connecting\nthis will bypass that.\nYou must reconnect to the server inorder for this to work.");
    }

}

