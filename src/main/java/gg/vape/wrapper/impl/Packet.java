package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MPacket;
import gg.vape.module.blatant.backtrack.BacktrackPacketQueueEntry;
import gg.vape.wrapper.Wrapper;

import java.util.function.Consumer;

public class Packet
extends Wrapper {
    private static boolean z;

    public static void d(boolean bl) {
        z = bl;
    }

    public Packet(Object handle) {
        super(handle);
    }


    public boolean hasPriority() {
        return MPacket.hasPriority(Packet.vapeInstance.getMappings().packet, this.I);
    }

    public static void n(Packet packet, Consumer<Packet> consumer) {
        if (ForgeVersion.MC_1_21_4.d() && packet.isInstance(MappedClasses.ue)) {
            BacktrackPacketQueueEntry backtrackPacketQueueEntry = new BacktrackPacketQueueEntry(packet);
            for (Packet packet2 : backtrackPacketQueueEntry.getPackets()) {
                consumer.accept(packet2);
            }
        } else {
            consumer.accept(packet);
        }
    }

    public static boolean A() {
        return z;
    }

    public void processPacket(NetworkPacketHandle networkPacketHandle) {
        MPacket.processPacket(Packet.vapeInstance.getMappings().packet, this.I, networkPacketHandle.getObject());
    }

    public static boolean h() {
        boolean bl = Packet.A();
        return !bl;
    }

    static {
        if (!Packet.h()) {
            Packet.d(true);
        }
    }
}

