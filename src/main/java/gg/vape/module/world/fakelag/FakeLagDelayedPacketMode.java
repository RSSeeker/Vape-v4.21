package gg.vape.module.world.fakelag;

import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.world.FakeLag;
import gg.vape.utils.SleepUtil;
import gg.vape.utils.network.PacketDispatchTask;
import gg.vape.utils.network.TimedPacketDispatchTask;
import gg.vape.wrapper.impl.Minecraft;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FakeLagDelayedPacketMode
extends SubModule<FakeLag> {
    private final Queue<TimedPacketDispatchTask> pendingPackets = new ConcurrentLinkedQueue<TimedPacketDispatchTask>();
    private boolean flushing = false;

    @Override
    public void onBeforeDisable() {
        this.flushing = true;
        this.flush(true);
    }

    private void flush(boolean force) {
        TimedPacketDispatchTask pendingPacket;
        while ((pendingPacket = this.pendingPackets.peek()) != null && (pendingPacket.j(((Double)((FakeLag)this.getParent()).delay.getValue()).longValue()) || force)) {
            this.pendingPackets.poll().z().t();
        }
    }


    public FakeLagDelayedPacketMode(Mod parent, String name) {
        super(parent, name);
    }

    @Override
    public String getDetailedSuffix() {
        return "Latency " + ((FakeLag)this.getParent()).delay.getDisplayValue() + "ms";
    }

    @Override
    public void onDisable() {
        this.flushing = false;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPacketSend(EventPacketSend eventPacketSend) {
        if (Minecraft.thePlayer().isNull() || Minecraft.theWorld().isNull()) {
            this.flush(false);
            return;
        }
        if (eventPacketSend.isCanceled()) {
            this.flush(true);
            return;
        }
        if (this.flushing) {
            while (this.flushing) {
                SleepUtil.sleep(50L);
            }
            return;
        }
        this.flush(false);
        this.pendingPackets.add(new TimedPacketDispatchTask(new PacketDispatchTask(eventPacketSend.getPacket(), true, eventPacketSend.getNetworkManager()), null));
        eventPacketSend.setCancelled(true);
    }
}

