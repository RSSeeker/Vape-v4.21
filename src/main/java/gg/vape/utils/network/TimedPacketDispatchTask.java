package gg.vape.utils.network;

import gg.vape.utils.network.PacketDispatchTask;
import gg.vape.utils.network.TimedPacketDispatchTaskMarker;

public class TimedPacketDispatchTask {
    private final PacketDispatchTask X;
    private final long M;

    private TimedPacketDispatchTask(PacketDispatchTask vo_22) {
        this.X = vo_22;
        this.M = System.currentTimeMillis();
    }

    public PacketDispatchTask z() {
        return this.X;
    }

    public TimedPacketDispatchTask(PacketDispatchTask vo_22, TimedPacketDispatchTaskMarker u_02) {
        this(vo_22);
    }


    public boolean j(long l) {
        long l2 = System.currentTimeMillis();
        boolean bl = l2 - this.M > l;
        return bl;
    }
}

