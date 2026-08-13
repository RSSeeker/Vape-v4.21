package gg.vape.event.listener;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventLivingUpdate;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.mapping.MappedClasses;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.SPacketEntityStatus;
import gg.vape.wrapper.impl.WorldClient;

public class ClientListenerBootstrapEventListener
implements EventListener {
    private static GuiComponent[] obfuscationState;


    @EventHandler(priority=EventPriority.LOW)
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        if (!eventPacketReceive.getPacket().isInstance(MappedClasses.lU)) {
            return;
        }
        SPacketEntityStatus entityStatusPacket = new SPacketEntityStatus(eventPacketReceive.getPacket().getObject());
        WorldClient world = Minecraft.theWorld();
        if (world.isNull()) {
            return;
        }
        Entity entity = world.V(entityStatusPacket.getEntityId());
        if (entity.isNull()) {
            return;
        }
        new EventLivingUpdate(entity).fire();
    }

    static {
        if (ClientListenerBootstrapEventListener.getObfuscationState() != null) {
            ClientListenerBootstrapEventListener.setObfuscationState(new GuiComponent[1]);
        }
    }

    public static GuiComponent[] getObfuscationState() {
        return obfuscationState;
    }

    public static void setObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }
}

