package gg.vape.module.blatant.backtrack;

import gg.vape.wrapper.impl.Packet;
import java.util.ArrayList;
import java.util.List;

public class BacktrackPacketQueueEntry
extends Packet {
    public List<Packet> getPackets() {
        ArrayList<Packet> packets = new ArrayList<Packet>();
        for (Object packetHandle : this.getPacketHandles()) {
            packets.add(new Packet(packetHandle));
        }
        return packets;
    }

    public Iterable getPacketHandles() {
        return BacktrackPacketQueueEntry.vapeInstance.getMappingsMapperCompat().CS.getPacketHandles(this.I);
    }

    public BacktrackPacketQueueEntry(Object handle) {
        super(handle);
    }
}

