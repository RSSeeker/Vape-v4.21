package gg.vape.event.listener;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventWorldChange;
import gg.vape.wrapper.impl.WorldClient;

public class WorldChangeEventDispatcher
implements EventListener {
    private WorldClient currentWorld;


    @EventHandler(priority=EventPriority.LOW)
    public void onTick(EventPreTick eventTick) {
        WorldClient world = eventTick.getWorld();
        boolean worldUnavailable = world.isNull();
        if (this.currentWorld == null && !worldUnavailable) {
            new EventWorldChange(this.currentWorld, world).fire();
            this.currentWorld = world;
        } else if (this.currentWorld != null && !worldUnavailable && this.currentWorld.getObject() != world.getObject()) {
            new EventWorldChange(this.currentWorld, world).fire();
            this.currentWorld = world;
        } else if (this.currentWorld != null && worldUnavailable) {
            new EventWorldChange(this.currentWorld, null).fire();
            this.currentWorld = null;
        }
    }
}

